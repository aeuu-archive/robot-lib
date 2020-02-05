package io.arct.ftclib.drive

import io.arct.ftclib.hardware.gamepad.Gamepad
import io.arct.robotlib.drive.Drive
import io.arct.robotlib.extensions.round
import io.arct.robotlib.hardware.motors.Motor
import io.arct.robotlib.robot.Robot
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class HolonomicDrive(override val robot: Robot, vararg motors: Motor) : Drive {
    private val lfm: Motor = motors[0]
    private val rfm: Motor = motors[1]
    private val lbm: Motor = motors[2]
    private val rbm: Motor = motors[3]

    init {
        lfm.zeroPower = Motor.ZeroPowerBehavior.Brake
        rfm.zeroPower = Motor.ZeroPowerBehavior.Brake
        lbm.zeroPower = Motor.ZeroPowerBehavior.Brake
        rbm.zeroPower = Motor.ZeroPowerBehavior.Brake
    }

    override fun move(direction: Double, power: Double, distance: Double?): Drive = if (distance != null) {
        val x = sin(direction * PI / 180).round(14)
        val y = cos(direction * PI / 180).round(14)

        lfm.target((+y + x) * power, distance * distanceConstant)
        rfm.target((-y + x) * power, distance * distanceConstant)
        lbm.target((+y - x) * power, distance * distanceConstant)
        rbm.target((-y - x) * power, distance * distanceConstant)

        while (lfm.busy || rfm.busy || lbm.busy || rbm.busy);

        this
    } else {
        val x = sin(direction * PI / 180).round(14)
        val y = cos(direction * PI / 180).round(14)

        lfm.power = (+y + x) * power
        rfm.power = (-y + x) * power
        lbm.power = (+y - x) * power
        rbm.power = (-y - x) * power

        this
    }

    override fun rotate(power: Double, distance: Double?): Drive = if (distance != null) {
        lfm.target(power, distance * rotationConstant)
        rfm.target(power, distance * rotationConstant)
        lbm.target(power, distance * rotationConstant)
        rbm.target(power, distance * rotationConstant)

        while (lfm.busy || rfm.busy || lbm.busy || rbm.busy);

        this
    } else {
        lfm.power = power
        rfm.power = power
        lbm.power = power
        rbm.power = power

        this
    }

    fun gamepad(gamepad: Gamepad, invert: Boolean = false): HolonomicDrive {
        val main = if (invert) gamepad.right else gamepad.left
        val rotation = if (invert) gamepad.left else gamepad.right

        val x = main.x
        val y = main.y
        val r = rotation.x

        lfm.power = (+y + x + r)
        rfm.power = (-y + x + r)
        lbm.power = (+y - x + r)
        rbm.power = (-y - x + r)

        return this
    }

    companion object {
        var distanceConstant = 1.0
        var rotationConstant = 1.0
    }
}