package io.arct.ftclib.hardware.sensors

import io.arct.ftclib.eventloop.OperationMode
import io.arct.ftclib.hardware.FtcDevice
import io.arct.robotlib.hardware.sensors.ColorSensor

class FtcColorSensor<T : com.qualcomm.robotcore.hardware.ColorSensor> internal constructor(sdk: T, opMode: OperationMode) : FtcDevice<T>(sdk, opMode), ColorSensor {
    init {
        sdk.enableLed(true)
    }

    override val alpha: Int
        get() = sdk.alpha()

    override val argb: Int
        get() = sdk.argb()

    override val red: Int
        get() = sdk.red()

    override val green: Int
        get() = sdk.green()

    override val blue: Int
        get() = sdk.blue()

    override var led: Boolean = true
        set(v) {
            sdk.enableLed(v)
            field = v
        }
}