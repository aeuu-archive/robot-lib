package io.arct.ftclib.hardware.controllers

import io.arct.ftclib.hardware.FtcDevice
import io.arct.robotlib.hardware.controllers.ServoController
import io.arct.robotlib.hardware.controllers.ServoController.PwmStatus

open class FtcServoController<T : com.qualcomm.robotcore.hardware.ServoController> internal constructor(sdk: T) : FtcDevice<T>(sdk), ServoController {
    override var pwm: PwmStatus
        get() = fromSdk(sdk.pwmStatus)
        set(v) { if (v == PwmStatus.Enabled) sdk.pwmEnable() else if (v == PwmStatus.Disabled) sdk.pwmDisable() }

    override fun position(port: Int) =
        sdk.getServoPosition(port)

    override fun position(port: Int, new: Double) =
        sdk.setServoPosition(port, new)

    companion object {
        private fun fromSdk(value: com.qualcomm.robotcore.hardware.ServoController.PwmStatus): PwmStatus =
            when (value) {
                com.qualcomm.robotcore.hardware.ServoController.PwmStatus.ENABLED -> PwmStatus.Enabled
                com.qualcomm.robotcore.hardware.ServoController.PwmStatus.DISABLED -> PwmStatus.Disabled
                com.qualcomm.robotcore.hardware.ServoController.PwmStatus.MIXED -> PwmStatus.Mixed
                else -> PwmStatus.Unknown
            }
    }
}