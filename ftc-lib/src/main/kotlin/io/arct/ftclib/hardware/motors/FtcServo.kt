package io.arct.ftclib.hardware.motors

import io.arct.ftclib.eventloop.OperationMode
import io.arct.ftclib.hardware.FtcDevice
import io.arct.ftclib.hardware.controllers.FtcServoController
import io.arct.robotlib.hardware.controllers.ServoController
import io.arct.robotlib.hardware.motors.Servo

open class FtcServo<T: com.qualcomm.robotcore.hardware.Servo> internal constructor(sdk: T, private val opMode: OperationMode) : FtcDevice<T>(sdk, opMode), Servo {
    override val controller: ServoController
        get() = FtcServoController(sdk.controller, opMode)

    override var position: Double
        get() = sdk.position
        set(v) {
            sdk.position = v
        }

    override fun move(target: Double, time: Long, stepsPerSecond: Int): Servo {
        val steps = (stepsPerSecond * target / 1000).toInt()
        val stepDistance = (position - target) / steps
        val delay = 1000L / stepsPerSecond

        val originalPosition = position

        for (i in 0..steps) {
            position = originalPosition + i * stepDistance
            Thread.sleep(delay)
        }

        return this
    }
}