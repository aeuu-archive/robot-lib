package io.arct.ftclib.hardware.gamepad

class DPad internal constructor(private val g: () -> com.qualcomm.robotcore.hardware.Gamepad) {
    private val gamepad
        get() = g.invoke()

    val up: Boolean get() = gamepad.dpad_up
    val down: Boolean get() = gamepad.dpad_down
    val left: Boolean get() = gamepad.dpad_left
    val right: Boolean get() = gamepad.dpad_right
}
