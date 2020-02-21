package io.arct.ftclib.bindings

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import io.arct.ftclib.eventloop.LinearOperationMode
import io.arct.ftclib.eventloop.OperationMode

abstract class LinearOpModeBinding : LinearOpMode() {
    private lateinit var mode: LinearOperationMode

    protected abstract fun getOperationMode(): LinearOperationMode

    override fun runOpMode() {
        OperationMode.current = this
        mode = getOperationMode()

        mode.init()

        waitForStart()

        mode.start()
        mode.run()
        mode.stop()
    }
}
