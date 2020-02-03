package io.arct.robotlib.hardware.motors

import io.arct.robotlib.hardware.Device
import io.arct.robotlib.hardware.controllers.ServoController

interface Servo : Device {
    val controller: ServoController
    var position: Double

    fun move(target: Double, time: Long, stepsPerSecond: Int = 500): Servo
}
