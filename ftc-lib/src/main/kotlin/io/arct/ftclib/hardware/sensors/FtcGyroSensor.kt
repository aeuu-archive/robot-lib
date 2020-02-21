package io.arct.ftclib.hardware.sensors

import io.arct.ftclib.eventloop.OperationMode
import io.arct.ftclib.hardware.FtcDevice
import io.arct.robotlib.hardware.sensors.GyroSensor

open class FtcGyroSensor<T : com.qualcomm.robotcore.hardware.GyroSensor> internal constructor(sdk: T, opMode: OperationMode) : FtcDevice<T>(sdk, opMode), GyroSensor {
    val heading: Int
        get() = sdk.heading

    val rotationFraction: Double
        get() = sdk.rotationFraction

    override val calibrating: Boolean
        get() = sdk.isCalibrating

    override val x: Int
        get() = sdk.rawX()

    override val y: Int
        get() = sdk.rawY()

    override val z: Int
        get() = sdk.rawZ()

    override fun calibrate(): FtcGyroSensor<T> {
        sdk.calibrate()

        return this
    }

    override fun reset(): FtcGyroSensor<T> {
        sdk.resetZAxisIntegrator()

        return this
    }
}