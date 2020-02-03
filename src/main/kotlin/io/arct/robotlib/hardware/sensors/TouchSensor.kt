package io.arct.robotlib.hardware.sensors

import io.arct.robotlib.hardware.Device

interface TouchSensor : Device {
    val value: Double
    val pressed: Boolean
}
