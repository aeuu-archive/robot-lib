package io.arct.ftclib.bindings

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftclib.eventloop.OperationMode

abstract class OpModeBinding : OpMode() {
    private lateinit var mode: OperationMode

    protected abstract fun getOperationMode(): OperationMode

    override fun init() {
        OperationMode.current = this
        mode = getOperationMode()

        mode.init()
    }

    override fun init_loop() = mode.initLoop()
    override fun start() = mode.start()
    override fun loop() = mode.loop()
    override fun stop() = mode.stop()
}
