package io.arct.robotlib.eventloop

import io.arct.robotlib.robot.Robot

interface ProgramLoop {
    val robot: Robot

    fun exit()

    fun init() {}
    fun loop()
    fun stop() {}
}