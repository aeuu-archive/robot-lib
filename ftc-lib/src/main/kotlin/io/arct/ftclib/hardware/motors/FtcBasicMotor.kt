package io.arct.ftclib.hardware.motors

import com.qualcomm.robotcore.hardware.DcMotorSimple
import io.arct.ftclib.eventloop.OperationMode
import io.arct.ftclib.hardware.FtcDevice
import io.arct.ftclib.internal.fromSdk
import io.arct.ftclib.internal.toSdk
import io.arct.robotlib.hardware.motors.BasicMotor
import io.arct.robotlib.hardware.motors.BasicMotor.Direction

open class FtcBasicMotor<T : DcMotorSimple> internal constructor(sdk: T, opMode: OperationMode) : FtcDevice<T>(sdk, opMode), BasicMotor {
    override var direction: Direction
        get() = fromSdk(sdk.direction)
        set(v) {
            sdk.direction = toSdk(v)
        }

    override var power: Double
        get() = sdk.power
        set(v) {
            sdk.power = v
        }
}