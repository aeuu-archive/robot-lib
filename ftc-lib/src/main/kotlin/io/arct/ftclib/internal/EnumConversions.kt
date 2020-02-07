package io.arct.ftclib.internal

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareDevice
import io.arct.ftclib.hardware.FtcDevice
import io.arct.ftclib.hardware.motors.FtcMotor
import io.arct.robotlib.hardware.motors.BasicMotor
import io.arct.robotlib.hardware.motors.Motor

internal fun fromSdk(value: DcMotor.ZeroPowerBehavior): Motor.ZeroPowerBehavior = when (value) {
    DcMotor.ZeroPowerBehavior.BRAKE -> Motor.ZeroPowerBehavior.Brake
    DcMotor.ZeroPowerBehavior.FLOAT -> Motor.ZeroPowerBehavior.Coast
    else -> Motor.ZeroPowerBehavior.Unknown
}

internal fun toSdk(value: Motor.ZeroPowerBehavior): DcMotor.ZeroPowerBehavior = when (value) {
    Motor.ZeroPowerBehavior.Brake -> DcMotor.ZeroPowerBehavior.BRAKE
    Motor.ZeroPowerBehavior.Coast -> DcMotor.ZeroPowerBehavior.FLOAT
    else -> DcMotor.ZeroPowerBehavior.UNKNOWN
}

internal fun fromSdk(value: DcMotorSimple.Direction): BasicMotor.Direction = when (value) {
    DcMotorSimple.Direction.FORWARD -> BasicMotor.Direction.Forward
    DcMotorSimple.Direction.REVERSE -> BasicMotor.Direction.Reverse
    else -> BasicMotor.Direction.Unknown
}

internal fun toSdk(value: BasicMotor.Direction): DcMotorSimple.Direction? = when (value) {
    BasicMotor.Direction.Forward -> DcMotorSimple.Direction.FORWARD
    BasicMotor.Direction.Reverse -> DcMotorSimple.Direction.REVERSE
    else -> null
}

internal fun fromSdk(value: HardwareDevice.Manufacturer): FtcDevice.Manufacturer = when (value) {
    HardwareDevice.Manufacturer.Adafruit -> FtcDevice.Manufacturer.Adafruit
    HardwareDevice.Manufacturer.AMS -> FtcDevice.Manufacturer.AMS
    HardwareDevice.Manufacturer.Broadcom -> FtcDevice.Manufacturer.Broadcom
    HardwareDevice.Manufacturer.HiTechnic -> FtcDevice.Manufacturer.HiTechnic
    HardwareDevice.Manufacturer.Lego -> FtcDevice.Manufacturer.Lego
    HardwareDevice.Manufacturer.Lynx -> FtcDevice.Manufacturer.Lynx
    HardwareDevice.Manufacturer.Matrix -> FtcDevice.Manufacturer.Matrix
    HardwareDevice.Manufacturer.ModernRobotics -> FtcDevice.Manufacturer.ModernRobotics
    HardwareDevice.Manufacturer.STMicroelectronics -> FtcDevice.Manufacturer.STMicroelectronics
    HardwareDevice.Manufacturer.Other -> FtcDevice.Manufacturer.Other
    else -> FtcDevice.Manufacturer.Unknown
}

internal fun toSdk(value: FtcDevice.Manufacturer): HardwareDevice.Manufacturer = when (value) {
    FtcDevice.Manufacturer.Adafruit -> HardwareDevice.Manufacturer.Adafruit
    FtcDevice.Manufacturer.AMS -> HardwareDevice.Manufacturer.AMS
    FtcDevice.Manufacturer.Broadcom -> HardwareDevice.Manufacturer.Broadcom
    FtcDevice.Manufacturer.HiTechnic -> HardwareDevice.Manufacturer.HiTechnic
    FtcDevice.Manufacturer.Lego -> HardwareDevice.Manufacturer.Lego
    FtcDevice.Manufacturer.Lynx -> HardwareDevice.Manufacturer.Lynx
    FtcDevice.Manufacturer.Matrix -> HardwareDevice.Manufacturer.Matrix
    FtcDevice.Manufacturer.ModernRobotics -> HardwareDevice.Manufacturer.ModernRobotics
    FtcDevice.Manufacturer.STMicroelectronics -> HardwareDevice.Manufacturer.STMicroelectronics
    FtcDevice.Manufacturer.Other -> HardwareDevice.Manufacturer.Other
    else -> HardwareDevice.Manufacturer.Unknown
}