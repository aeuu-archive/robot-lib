package io.arct.robotlib.hardware.motors

import io.arct.robotlib.hardware.controllers.ServoController

interface ContinuousServo : BasicMotor {
    val controller: ServoController
    val port: Int

    fun move(power: Double): ContinuousServo
}
