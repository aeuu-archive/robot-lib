package io.arct.robotlib.hardware.controllers

import io.arct.robotlib.hardware.Device
import io.arct.robotlib.hardware.motors.Motor

interface MotorController : Device {
    var power: Double
    val position: Int
    var target: Int
    var zeroPowerBehavior: Motor.ZeroPowerBehavior
}
