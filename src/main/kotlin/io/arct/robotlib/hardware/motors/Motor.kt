package io.arct.robotlib.hardware.motors

import io.arct.robotlib.hardware.controllers.MotorController

interface Motor : BasicMotor {
    val busy: Boolean
    val controller: MotorController
    val port: Int
    val position: Double
    var zeroPower: ZeroPowerBehavior

    fun move(power: Double, position: Double): Motor
    fun target(position: Double): Target

    fun stop(): Motor {
        power = 0.0
        return this
    }

    enum class ZeroPowerBehavior {
        Coast,
        Brake,
        Unknown;
    }

    interface Target {
        val motor: Motor

        val position: Double
        val power: Double

        fun start(power: Double? = null): Target
        fun run(power: Double? = null): Target
        fun stop(): Target
        fun await(): Target
    }

    companion object {
        var distanceConstant = 4.0
    }
}