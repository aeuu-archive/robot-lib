package io.arct.ftclib.drive

import io.arct.ftclib.hardware.gamepad.Gamepad
import io.arct.ftclib.hardware.motors.FtcMotor
import io.arct.robotlib.drive.Drive
import io.arct.robotlib.extensions.map
import io.arct.robotlib.hardware.motors.Motor
import io.arct.robotlib.navigation.Angles
import io.arct.robotlib.robot.Robot
import kotlin.math.hypot

class MecanumDrive(
    override val robot: Robot,
    vararg motors: FtcMotor<*>,

    var autoAlign: Boolean = true,
    fieldCentric: Boolean = true,

    var alignment: Double = 0.0,
    private var rotation: (() -> Number)? = null
) : Drive {
    private val lfm: FtcMotor<*> = motors[0].also { it.direction = it.direction.inverse }
    private val rfm: FtcMotor<*> = motors[1]
    private val lbm: FtcMotor<*> = motors[2].also { it.direction = it.direction.inverse }
    private val rbm: FtcMotor<*> = motors[3]

    var distanceConstant: Double = Constants.distance
    var rotationConstant: Double = Constants.rotation

    var fieldCentric: Boolean = fieldCentric
        get() = rotation != null && field

    private var target: Double = rotation?.invoke()?.toDouble() ?: 0.0

    init {
        lfm.zeroPower = Motor.ZeroPowerBehavior.Brake
        rfm.zeroPower = Motor.ZeroPowerBehavior.Brake
        lbm.zeroPower = Motor.ZeroPowerBehavior.Brake
        rbm.zeroPower = Motor.ZeroPowerBehavior.Brake
    }

    override fun move(direction: Double, power: Double, distance: Double?): MecanumDrive {
        val dir = (direction + if (fieldCentric) rotation!!().toDouble() else 0.0) + alignment

        val a = speed(-dir + 90)
        val b = speed(-dir)

        if (distance != null) {
            listOf(
                lfm.target(distance * distanceConstant),
                rfm.target(distance * distanceConstant),
                lbm.target(distance * distanceConstant),
                rbm.target(distance * distanceConstant)
            ).map {
                it.init()
            }.mapIndexed { index, it ->
                val pwr = if (index == 0 || index == 3) a else b
                it.power(pwr * power)
            }.reversed().map {
                it.waitTarget()
            }.map {
                it.stop()
            }

            if (autoAlign)
                rotate(power, target - rotation!!().toDouble())
        } else {
            lfm.power = a * power
            rfm.power = b * power
            lbm.power = b * power
            rbm.power = a * power
        }

        return this
    }

    override fun rotate(power: Double, distance: Double?) = if (distance != null) {
        listOf(
            lfm.target(distance * rotationConstant),
            lbm.target(distance * rotationConstant),
            rfm.target(distance * rotationConstant),
            rbm.target(distance * rotationConstant)
        ).map {
            it.init()
        }.mapIndexed { index, it ->
            val pwr = if (index == 0 || index == 1) 1 else -1
            it.power(pwr * power)
        }.reversed().map {
            it.waitTarget()
        }.map {
            it.stop()
        }

        this
    } else {
        lfm.power = power
        lbm.power = power
        rfm.power = -power
        rbm.power = -power

        this
    }

    private fun speed(angle: Double): Double {
        val a = Angles.generalAngle(angle)

        return if (a in 0.0..90.0)
            1.0
        else if (a > 90 && a < 180)
            a.map(90.0..180.0, 1.0..-1.0)
        else if (a in 180.0..270.0)
            -1.0
        else
            a.map(270.0..360.0, -1.0..1.0)
    }

     fun gamepad(gamepad: Gamepad, invert: Boolean = false): MecanumDrive {
        val main = if (invert) gamepad.right else gamepad.left
        val precision = if (invert) gamepad.left else gamepad.right

        return when {
            gamepad.a || gamepad.b || gamepad.x || gamepad.y ->
                rotate(
                    when {
                        gamepad.y -> 0.0
                        gamepad.b -> 90.0
                        gamepad.a -> 180.0
                        gamepad.x -> -80.0
                        else -> 0.0
                    } - Angles.normalizeAngle(rotation!!().toDouble()), 0.35
                )

            gamepad.lt - gamepad.rt != 0.0 && (main.x != 0.0 || main.y != 0.0) ->
                rotate((gamepad.lt - gamepad.rt).map(-1.0..1.0, -0.5..0.5))

            gamepad.lb ->
                rotate(0.25)

            gamepad.rb ->
                rotate(-0.25)

            gamepad.lt - gamepad.rt != 0.0 ->
                rotate(gamepad.lt - gamepad.rt)

            main.x != 0.0 || main.y != 0.0 ->
                move(Angles.fromCoordinates(main.x, main.y), hypot(main.x, main.y))

            else ->
                move(
                    Angles.fromCoordinates(precision.x, precision.y),
                    hypot(precision.x, precision.y).map(-1.0..1.0, -0.5..0.5)
                )
        }
    }

    object Constants {
        var distance = 1.0
        var rotation = 1.0
    }
}