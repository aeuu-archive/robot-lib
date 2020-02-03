package io.arct.robotlib.hardware.controllers

import io.arct.robotlib.hardware.Device

interface ServoController : Device {
    var pwm: PwmStatus

    fun position(port: Int): Double
    fun position(port: Int, new: Double)

    enum class PwmStatus {
        Enabled,
        Disabled,
        Mixed,
        Unknown;
    }
}
