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

    var adjustmentPower: Double = FtcMotor.adjustmentPower
    var targetPositionTolerance: Double = FtcMotor.targetPositionTolerance
    var distanceConstant: Double = Motor.distanceConstant

    override val busy: Boolean
        get() = sdk.isBusy

    override val controller: FtcMotorController<DcMotorController>
        get() = FtcMotorController(sdk.controller, port)

    override val port: Int
        get() = sdk.portNumber

    override val position: Double
        get() = sdk.currentPosition.toDouble()

    override var zeroPower: ZeroPowerBehavior
        get() = fromSdk(sdk.zeroPowerBehavior)
        set(v) {
            sdk.zeroPowerBehavior = toSdk(v)
        }

    init {
        sdk.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        sdk.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    override fun move(power: Double, position: Double): Motor =
        target(position).run(power).motor

    override fun target(position: Double): Target {
        if (!encoder)
            encoder = true

        return Target(this, abs(position * distanceConstant))
    }

    override fun reset(): FtcDevice<T> {
        val mode = sdk.mode
        sdk.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        sdk.mode = mode

        return this
    }

    class Target internal constructor(
        override val motor: FtcMotor<*>,
        override val position: Double
    ) : Motor.Target {
        private var running: Boolean = false

        override val power: Double = 1.0

        override fun start(power: Double?): Target {
            if (!motor.encoder)
                motor.encoder = true

            motor.reset()

            running = true
            motor.power = power ?: this.power

            return this
        }

        override fun run(power: Double?): Target =
            start(power).await()

        override fun stop(): Target {
            running = false
            motor.stop()

            return this
        }

        override fun await(): Target {
            waitTarget()
            adjust()

            return this
        }

        fun waitTarget(): Target {
            while (running && abs(motor.position) < position - motor.targetPositionTolerance);
            motor.stop()

            return this
        }

        fun adjust(): Target {
            var range = (abs(motor.position) - motor.targetPositionTolerance)..(abs(motor.position) + motor.targetPositionTolerance)

            while (running && !range.contains(abs(position))) {
                if (abs(motor.position) > position)
                    motor.power = (if (position < 0) 1.0 else -1.0) * abs(motor.adjustmentPower)

                if (abs(position) < position)
                    motor.power = (if (position > 0) 1.0 else -1.0) * abs(motor.adjustmentPower)

                range = (abs(motor.position) - motor.targetPositionTolerance)..(abs(motor.position) + motor.targetPositionTolerance)
            }

            motor.stop()

            if (running && !range.contains(abs(motor.position)))
                adjust()

            running = false
            return this
        }
    }

    companion object {
        var adjustmentPower: Double = 0.1
        var targetPositionTolerance: Double = 0.0
    }
}