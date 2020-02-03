package io.arct.robotlib.hardware.motors

import io.arct.robotlib.hardware.Device

interface BasicMotor : Device {
    var direction: Direction
    var power: Double

    enum class Direction {
        Forward,
        Reverse,
        Unknown;

        val inverse: Direction
            get() = when (this) {
                Forward -> Reverse
                Reverse -> Forward
                else -> this
            }
    }
}
