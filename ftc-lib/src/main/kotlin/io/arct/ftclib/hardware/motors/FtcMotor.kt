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
        set(value) {
            if (value != encoder)
                sdk.mode = if (value) DcMotor.RunMode.RUN_USING_ENCODER else DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }

    var adjustmentPower: Double = Constants.adjustmentPower
        set(v) { field = abs(v) }

    var targetPositionTolerance: Double = Constants.targetPositionTolerance
        set(v) { field = abs(v) }

    var distanceConstant: Double = Motor.Constants.distance
        set(v) { field = abs(v) }

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

        return Target(this, abs(position))
    }

    override fun reset(): FtcDevice<T> {
        val mode = sdk.mode
        sdk.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        sdk.mode = mode

        return this
    }

    class Target internal constructor(
        override val motor: FtcMotor<*>,
        position: Double
    ) : Motor.Target {
        private var running: Boolean = false

        private val posNeg: Double = if (position < 0) -1.0 else 1.0
        override val position: Double = abs(position * motor.distanceConstant)

        private val motorPos: Double get() = abs(motor.position)

        fun init(): Target {
            if (!motor.encoder)
                motor.encoder = true

            motor.reset()
            return this
        }

        fun power(power: Double): Target {
            running = true
            motor.power = posNeg * power

            return this
        }

        fun waitTarget(): Target {
            while (running && motorPos < position - motor.targetPositionTolerance);
            return this
        }

        override fun start(power: Double): Target =
            init().power(power)

        override fun run(power: Double): Target =
            start(power).await()

        override fun stop(): Target {
            running = false
            motor.stop()

            return this
        }

        override fun await(): Target {
            waitTarget()
            motor.stop()
            adjust()

            return this
        }

        fun adjust(): Target {
            while (running) {
                if (range.contains(motor.position))
                    break

                if (motorPos > position)
                    motor.power = (if (motor.position < 0) 1.0 else -1.0) * abs(motor.adjustmentPower)

                if (motorPos < position)
                    motor.power = (if (motor.position > 0) 1.0 else -1.0) * abs(motor.adjustmentPower)
            }

            motor.stop()

            if (running && !range.contains(abs(motor.position)))
                adjust()

            running = false
            return this
        }

        private val range get() = (motorPos - motor.targetPositionTolerance)..(motorPos + motor.targetPositionTolerance)
    }

    object Constants {
        var adjustmentPower: Double = 0.1
            set(v) { field = abs(v) }

        var targetPositionTolerance: Double = 0.0
            set(v) { field = abs(v) }
    }
}