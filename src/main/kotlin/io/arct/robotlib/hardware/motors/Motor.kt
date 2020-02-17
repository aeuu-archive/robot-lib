package io.arct.robotlib.hardware.motors

import io.arct.robotlib.hardware.controllers.MotorController
import kotlin.math.abs

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

        fun start(power: Double): Target
        fun run(power: Double): Target
        fun stop(): Target
        fun await(): Target
    }

    object Constants {
        var distance = 4.0
            set(v) { field = abs(v) }
    }
}