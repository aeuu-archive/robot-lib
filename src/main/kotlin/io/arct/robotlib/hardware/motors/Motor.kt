package io.arct.robotlib.hardware.motors

import io.arct.robotlib.hardware.controllers.MotorController

interface Motor : BasicMotor {
    val busy: Boolean
    val controller: MotorController
    val port: Int
    val position: Int
    var zeroPower: ZeroPowerBehavior

    fun move(power: Double, position: Double): Motor
    fun target(power: Double, position: Double): Motor
    fun wait(): Motor

    fun stop(): Motor {
        power = 0.0
        return this
    }

    enum class ZeroPowerBehavior {
        Coast,
        Brake,
        Unknown;
    }

    companion object {
        var distanceConstant = 4.0
    }
}
