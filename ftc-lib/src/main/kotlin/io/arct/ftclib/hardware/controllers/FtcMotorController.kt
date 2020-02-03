package io.arct.ftclib.hardware.controllers

import com.qualcomm.robotcore.hardware.DcMotorController
import io.arct.ftclib.hardware.FtcDevice
import io.arct.ftclib.internal.fromSdk
import io.arct.ftclib.internal.toSdk
import io.arct.robotlib.hardware.controllers.MotorController
import io.arct.robotlib.hardware.motors.Motor

open class FtcMotorController<T : DcMotorController> internal constructor(sdk: T, private val port: Int) : FtcDevice<T>(sdk), MotorController {
    override var power: Double
        get() = sdk.getMotorPower(port)
        set(v) { sdk.setMotorPower(port, v) }

    override val position: Int
        get() = sdk.getMotorCurrentPosition(port)

    override var target: Int
        get() = sdk.getMotorTargetPosition(port)
        set(v) { sdk.setMotorTargetPosition(port, v) }

    override var zeroPowerBehavior: Motor.ZeroPowerBehavior
        get() = fromSdk(sdk.getMotorZeroPowerBehavior(port))
        set(v) { sdk.setMotorZeroPowerBehavior(port, toSdk(v)) }
}