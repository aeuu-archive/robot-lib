package io.arct.robotlib.robot

import io.arct.robotlib.hardware.Device
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

interface HardwareMap {
    fun <T : Device> get(type: KClass<T>, identifier: String, vararg arguments: Any?): T
}

inline fun <reified T : Device> HardwareMap.get(identifier: String, vararg arguments: Any?): T =
    get(T::class, identifier, *arguments)

inline infix fun <reified T : Device> HardwareMap.device(identifier: String): T =
    get(identifier)

inline operator fun <reified T : Device> HardwareMap.invoke(identifier: String, vararg arguments: Any?): T =
    get(identifier, *arguments)

inline operator fun <reified T : Device> HardwareMap.getValue(thisRef: Any?, property: KProperty<*>): T =
    invoke(property.name)