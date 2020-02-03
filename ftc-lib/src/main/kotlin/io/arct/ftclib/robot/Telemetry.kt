package io.arct.ftclib.robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftclib.eventloop.OperationMode

class Telemetry internal constructor(private val sdk: org.firstinspires.ftc.robotcore.external.Telemetry) {
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
