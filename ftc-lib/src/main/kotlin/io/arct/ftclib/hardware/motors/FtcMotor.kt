package io.arct.ftclib.hardware.motors

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorController
import io.arct.ftclib.hardware.FtcDevice
import io.arct.ftclib.hardware.controllers.FtcMotorController
import io.arct.ftclib.internal.fromSdk
import io.arct.ftclib.internal.toSdk
import io.arct.robotlib.hardware.motors.Motor
import io.arct.robotlib.hardware.motors.Motor.ZeroPowerBehavior
import kotlin.math.abs

open class FtcMotor<T : DcMotor> internal constructor(sdk: T) : FtcBasicMotor<T>(sdk), Motor {
    var encoder: Boolean
        get() = sdk.mode == DcMotor.RunMode.RUN_USING_ENCODER
        set(value) { sdk.mode = if (value) DcMotor.RunMode.RUN_USING_ENCODER else DcMotor.RunMode.RUN_WITHOUT_ENCODER }

    private var targetPosition: Double = 0.0

    var adjustmentPower: Double = FtcMotor.adjustmentPower
    var targetPositionTolerance: Double = FtcMotor.targetPositionTolerance
    var distanceConstant: Double = Motor.distanceConstant

    override val busy: Boolean
        get() = sdk.isBusy

    override val controller: FtcMotorController<DcMotorController>
        get() = FtcMotorController(sdk.controller, port)

    override val port: Int
        get() = sdk.portNumber

    override val position: Int
        get() = sdk.currentPosition

    override var zeroPower: ZeroPowerBehavior
        get() = fromSdk(sdk.zeroPowerBehavior)
        set(v) {
            sdk.zeroPowerBehavior = toSdk(v)
        }

    init {
        sdk.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        sdk.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    override fun move(power: Double, position: Double): Motor {
        target(power, position)
        return wait()
    }

    override fun target(power: Double, position: Double): Motor {
        if (!encoder)
            encoder = true

        reset()

        targetPosition = abs(position * distanceConstant)
        this.power =  (if (position < 0) -1.0 else 1.0) * power

        return this
    }

    fun waitTarget() {
        while (abs(position) < targetPosition - targetPositionTolerance);
        stop()
    }

    fun readjust() {
        var range = (abs(position) - targetPositionTolerance)..(abs(position) + targetPositionTolerance)

        while (!range.contains(abs(position).toDouble())) {
            if (abs(position) > targetPosition)
                power = (if (position < 0) 1.0 else -1.0) * abs(adjustmentPower)

            if (abs(position) < targetPosition)
                power = (if (position > 0) 1.0 else -1.0) * abs(adjustmentPower)

            range = (abs(position) - targetPositionTolerance)..(abs(position) + targetPositionTolerance)
        }

        stop()

        if (!range.contains(abs(position).toDouble()))
            readjust()
    }


    override fun wait(): Motor {
        waitTarget()
        readjust()
        return this
    }

    override fun reset(): FtcDevice<T> {
        val mode = sdk.mode
        sdk.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        sdk.mode = mode

        return this
    }

    companion object {
        var adjustmentPower: Double = 0.1
        var targetPositionTolerance: Double = 0.0
    }
}