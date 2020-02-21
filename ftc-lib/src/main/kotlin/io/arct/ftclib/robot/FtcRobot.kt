package io.arct.ftclib.robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftclib.eventloop.OperationMode
import io.arct.ftclib.hardware.gamepad.Gamepad
import io.arct.robotlib.robot.HardwareMap
import io.arct.robotlib.robot.Robot
import io.arct.robotlib.robot.device

class FtcRobot internal constructor(opMode: OperationMode) : Robot {
    override val hardware: HardwareMap = FtcHardwareMap(opMode)

    val __sdk__opMode: OpMode = opMode.sdk

    val gamepad: List<Gamepad> = listOf(
        this device "gamepad 0",
        this device "gamepad 1"
    )
}