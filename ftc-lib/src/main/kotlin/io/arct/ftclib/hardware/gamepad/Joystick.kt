package io.arct.ftclib.hardware.gamepad

class Joystick internal constructor(private val g: () -> com.qualcomm.robotcore.hardware.Gamepad, private val type: Type) {
    private val gamepad
        get() = g.invoke()

    val x: Double get() = (if (type == Type.Left) gamepad.left_stick_x else gamepad.right_stick_x).toDouble()
    val y: Double get() = -(if (type == Type.Left) gamepad.left_stick_y else gamepad.right_stick_y).toDouble()
    val button: Boolean get() = if (type == Type.Left) gamepad.left_stick_button else gamepad.right_stick_button

    val atOrigin: Boolean
        get() = x == 0.0 && y == 0.0

    enum class Type {
        Left,
        Right
    }
}
