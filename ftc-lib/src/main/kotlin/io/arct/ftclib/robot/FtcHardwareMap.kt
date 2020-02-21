package io.arct.ftclib.robot

import io.arct.ftclib.eventloop.OperationMode
import io.arct.ftclib.hardware.gamepad.Gamepad
import io.arct.ftclib.internal.robotMap
import io.arct.ftclib.internal.sdkMap
import io.arct.robotlib.exceptions.CouldNotFindDeviceException
import io.arct.robotlib.exceptions.InvalidDeviceException
import io.arct.robotlib.hardware.Device
import io.arct.robotlib.hardware.input.Controller
import io.arct.robotlib.robot.HardwareMap
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class FtcHardwareMap internal constructor(private val opMode: OperationMode) : HardwareMap {
    override fun <T : Device> get(type: KClass<T>, identifier: String, vararg arguments: Any?): T =
        if (type == Controller::class || type == Gamepad::class)
            getGamepad(identifier) as T
        else try {
            val ftcType: KClass<T> = robotMap[type] as KClass<T>? ?: type
            val sdkType: Class<*> = sdkMap[ftcType] ?: throw InvalidDeviceException(type.simpleName ?: "Device")

            val device: Any = opMode.sdk.hardwareMap.get(sdkType, identifier)

            ftcType.primaryConstructor!!.call(device, opMode, *arguments)
        } catch (e: Exception) {
            throw CouldNotFindDeviceException(type.simpleName, identifier)
        }

    private fun getGamepad(identifier: String): Gamepad = when (identifier) {
        "gamepad 0" -> Gamepad(opMode.sdk::gamepad1)
        "gamepad 1" -> Gamepad(opMode.sdk::gamepad2)
        else -> throw Exception()
    }
}