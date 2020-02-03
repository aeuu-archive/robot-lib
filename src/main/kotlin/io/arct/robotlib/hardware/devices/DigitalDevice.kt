package io.arct.robotlib.hardware.devices

import io.arct.robotlib.hardware.Device

interface DigitalDevice : Device {
    var value: Boolean
    var mode: Mode

    enum class Mode {
        Input,
        Output,
        Both
    }
}
