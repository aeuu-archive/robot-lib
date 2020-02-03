package io.arct.robotlib.hardware.sensors

import io.arct.robotlib.hardware.Device

interface GyroSensor : Device {
    val calibrating: Boolean

    val x: Int
    val y: Int
    val z: Int

    fun calibrate(): GyroSensor
    override fun reset(): GyroSensor
}
