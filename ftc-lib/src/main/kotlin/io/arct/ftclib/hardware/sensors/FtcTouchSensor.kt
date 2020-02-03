package io.arct.ftclib.hardware.sensors

import io.arct.ftclib.hardware.FtcDevice
import io.arct.robotlib.hardware.sensors.TouchSensor

open class FtcTouchSensor<T : com.qualcomm.robotcore.hardware.TouchSensor> internal constructor(sdk: T) : FtcDevice<T>(sdk), TouchSensor {
    override val value: Double
        get() = sdk.value

    override val pressed: Boolean
        get() = sdk.isPressed
}