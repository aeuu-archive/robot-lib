package io.arct.ftclib.hardware

import com.qualcomm.robotcore.hardware.HardwareDevice
import io.arct.ftclib.eventloop.OperationMode
import io.arct.ftclib.internal.fromSdk
import io.arct.ftclib.robot.FtcRobot
import io.arct.robotlib.hardware.Device
import io.arct.robotlib.robot.get

open class FtcDevice<T : HardwareDevice> internal constructor(val sdk: T, opMode: OperationMode) : Device {
    override val name: String = sdk.deviceName
    override val version: Int = sdk.version

    val connectionInfo: String = sdk.connectionInfo
    val manufacturer: Manufacturer = fromSdk(sdk.manufacturer)

    override fun close(): FtcDevice<T> {
        sdk.close()
        return this
    }

    override fun reset(): FtcDevice<T> {
        sdk.resetDeviceConfigurationForOpMode()
        return this
    }

    enum class Manufacturer {
        Adafruit,
        AMS,
        Broadcom,
        HiTechnic,
        Lego,
        Lynx,
        Matrix,
        ModernRobotics,
        STMicroelectronics,
        Other,
        Unknown;
    }
}