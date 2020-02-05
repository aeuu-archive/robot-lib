package io.arct.ftclib.hardware.motors

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorController
import com.qualcomm.robotcore.hardware.DcMotorEx
import io.arct.ftclib.hardware.controllers.FtcMotorController
import io.arct.ftclib.internal.fromSdk
import io.arct.ftclib.internal.toSdk
import io.arct.robotlib.hardware.motors.Motor
import io.arct.robotlib.hardware.motors.Motor.Companion.distanceConstant
import io.arct.robotlib.hardware.motors.Motor.ZeroPowerBehavior
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.math.abs

open class FtcMotor<T : DcMotor> internal constructor(sdk: T) : FtcBasicMotor<T>(sdk), Motor {
    private val ext: DcMotorEx?
        get() = if (extended) sdk as DcMotorEx else null

    private var oldMode: Mode = mode

    var enabled: Boolean?
        get() = ext?.isMotorEnabled
        set(v) {
            if (v == true) ext?.setMotorEnable() else ext?.setMotorDisable()
        }

    val extended: Boolean
        get() = sdk is DcMotorEx

    var mode: Mode
        get() = fromSdk(sdk.mode)
        set(v) {
            sdk.mode = toSdk(v)
        }

    var targetPosition: Int
        get() = sdk.targetPosition
        set(v) {
            sdk.targetPosition = v
        }

    var targetPositionTolerance: Int?
        get() = ext?.targetPositionTolerance
        set(v) {
            if (v != null) ext?.targetPositionTolerance = v
        }

    override val busy: Boolean
        get() = sdk.isBusy

    override val controller: FtcMotorController<DcMotorController>
        get() = FtcMotorController(sdk.controller, port)

    override val port: Int
        get() = sdk.portNumber

    override val position: Int
        get() = sdk.currentPosition

    override var velocity: Double?
        get() = ext?.velocity
        set(v) {
            if (v != null) ext?.velocity = v
        }

    override var zeroPower: ZeroPowerBehavior
        get() = fromSdk(sdk.zeroPowerBehavior)
        set(v) {
            sdk.zeroPowerBehavior = toSdk(v)
        }

    override fun move(power: Double, position: Double): Motor {
        target(power, position)
        return wait()
    }

    override fun target(power: Double, position: Double): Motor {
        oldMode = mode
        mode = Mode.Reset

        targetPosition = position.toInt() + ((if (power < 0) -1.0 else 1.0) * position * distanceConstant).toInt()

        mode = Mode.Position
        this.power = abs(power)

        return this
    }

    override fun wait(): Motor {
        while (busy);

        mode = oldMode
        stop()

        return this
    }

    enum class Mode {
        Position,
        Encoder,
        Simple,
        Reset,
        Unknown;
    }
}