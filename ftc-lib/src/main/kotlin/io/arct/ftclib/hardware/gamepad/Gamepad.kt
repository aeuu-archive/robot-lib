package io.arct.ftclib.hardware.gamepad

import io.arct.robotlib.hardware.Device
import io.arct.robotlib.hardware.input.Controller

class Gamepad internal constructor(private val g: () -> com.qualcomm.robotcore.hardware.Gamepad) : Controller {
    private val gamepad
        get() = g.invoke()

    val left: Joystick = Joystick(g, Joystick.Type.Left)
    val right: Joystick = Joystick(g, Joystick.Type.Right)

    val dpad: DPad = DPad(g)

    val a: Boolean get() = gamepad.a
    val b: Boolean get() = gamepad.b
    val x: Boolean get() = gamepad.x
    val y: Boolean get() = gamepad.y

    val lb: Boolean get() = gamepad.left_bumper
    val rb: Boolean get() = gamepad.right_bumper

    val lt: Double get() = gamepad.left_trigger.toDouble()
    val rt: Double get() = gamepad.right_trigger.toDouble()

    val back: Boolean get() = gamepad.back
    val guide: Boolean get() = gamepad.guide
    val start: Boolean get() = gamepad.start

    override val name: String = "Gamepad"
    override val version: Int = 1

    override fun close(): Device =
        this

    override fun reset(): Device =
        this
}
