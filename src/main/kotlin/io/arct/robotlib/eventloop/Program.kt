package io.arct.robotlib.eventloop

interface Program : ProgramLoop {
    val active: Boolean

    fun run()
}
