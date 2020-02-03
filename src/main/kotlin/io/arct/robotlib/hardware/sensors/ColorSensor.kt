package io.arct.robotlib.hardware.sensors

import io.arct.robotlib.hardware.Device

interface ColorSensor : Device {
    val alpha: Int
    val argb: Int
    val red: Int
    val green: Int
    val blue: Int

    var led: Boolean
}
