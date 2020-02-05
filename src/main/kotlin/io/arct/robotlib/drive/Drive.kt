package io.arct.robotlib.drive

import io.arct.robotlib.navigation.Direction
import io.arct.robotlib.robot.Robot

interface Drive {
    val robot: Robot

    fun move(direction: Double, power: Double, distance: Double? = null): Drive

    fun move(direction: Direction, power: Double, distance: Double? = null) =
        move(direction.value, power, distance)

    fun rotate(power: Double, distance: Double? = null): Drive

    fun stop(): Drive =
        move(0.0, 0.0)
}