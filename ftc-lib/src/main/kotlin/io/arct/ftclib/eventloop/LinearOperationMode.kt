package io.arct.ftclib.eventloop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.robotlib.eventloop.Program

abstract class LinearOperationMode : OperationMode(), Program {
    private val sdk: LinearOpMode = (current as LinearOpMode?)!!

    val started: Boolean
        get() = sdk.isStarted

    val stopRequested: Boolean
        get() = sdk.isStopRequested

    override val active: Boolean
        get() = sdk.opModeIsActive()

    fun idle(): LinearOperationMode {
        sdk.idle()

        return this
    }

    fun sleep(ms: Long): LinearOperationMode {
        sdk.sleep(ms)

        return this
    }

    override fun init() {}

    final override fun loop() {}
    final override fun initLoop() {}
}
