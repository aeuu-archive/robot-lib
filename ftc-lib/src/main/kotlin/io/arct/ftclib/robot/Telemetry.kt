package io.arct.ftclib.robot

import io.arct.ftclib.eventloop.OperationMode

class Telemetry internal constructor(opMode: OperationMode) {
    private val sdk = opMode.sdk.telemetry

    var autoClear: Boolean
        get() = sdk.isAutoClear
        set(v) { sdk.isAutoClear = v }

    fun add(line: String): Telemetry {
        sdk.addLine(line)

        return this
    }

    fun add(data: List<String>): Telemetry {
        for (line in data)
            sdk.addLine(line)

        return this
    }

    fun update(): Telemetry {
        sdk.update()

        return this
    }

    fun clear(): Telemetry {
        sdk.clear()

        return this
    }

    fun clearAll(): Telemetry {
        sdk.clearAll()

        return this
    }
}
