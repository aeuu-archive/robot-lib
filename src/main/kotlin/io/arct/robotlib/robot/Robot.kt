package io.arct.robotlib.robot

import io.arct.robotlib.hardware.Device

interface Robot {
    val hardware: HardwareMap
}

inline infix fun <reified T : Device> Robot.device(identifier: String): T =
    hardware device identifier