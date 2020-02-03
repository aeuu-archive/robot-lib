package io.arct.ftclib.internal

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.*
import io.arct.ftclib.hardware.controllers.FtcMotorController
import io.arct.ftclib.hardware.controllers.FtcServoController
import io.arct.ftclib.hardware.devices.FtcDigitalDevice
import io.arct.ftclib.hardware.motors.FtcBasicMotor
import io.arct.ftclib.hardware.motors.FtcContinuousServo
import io.arct.ftclib.hardware.motors.FtcMotor
import io.arct.ftclib.hardware.motors.FtcServo
import io.arct.ftclib.hardware.sensors.FtcColorSensor
import io.arct.ftclib.hardware.sensors.FtcGyroSensor
import io.arct.ftclib.hardware.sensors.FtcImu
import io.arct.ftclib.hardware.sensors.FtcTouchSensor
import io.arct.robotlib.hardware.Device
import io.arct.robotlib.hardware.controllers.MotorController
import io.arct.robotlib.hardware.controllers.ServoController
import io.arct.robotlib.hardware.devices.DigitalDevice
import io.arct.robotlib.hardware.motors.BasicMotor
import io.arct.robotlib.hardware.motors.ContinuousServo
import io.arct.robotlib.hardware.motors.Motor
import io.arct.robotlib.hardware.motors.Servo
import io.arct.robotlib.hardware.sensors.ColorSensor
import io.arct.robotlib.hardware.sensors.GyroSensor
import io.arct.robotlib.hardware.sensors.TouchSensor
import kotlin.reflect.KClass

internal val robotMap: Map<KClass<out Device>, KClass<out Device>> = mapOf(
    MotorController::class to FtcMotorController::class,
    ServoController::class to FtcServoController::class,
    DigitalDevice::class to   FtcDigitalDevice::class,
    BasicMotor::class to      FtcBasicMotor::class,
    ContinuousServo::class to FtcContinuousServo::class,
    Motor::class to           FtcMotor::class,
    Servo::class to           FtcServo::class,
    ColorSensor::class to     FtcColorSensor::class,
    GyroSensor::class to      FtcGyroSensor::class,
    TouchSensor::class to     FtcTouchSensor::class
)

internal val sdkMap: Map<KClass<out Device>, Class<*>> = mapOf(
    FtcMotorController::class to HardwareDevice::class.java,
    FtcServoController::class to com.qualcomm.robotcore.hardware.ServoController::class.java,
    FtcDigitalDevice::class to   DigitalChannel::class.java,
    FtcBasicMotor::class to      DcMotorSimple::class.java,
    FtcContinuousServo::class to CRServo::class.java,
    FtcMotor::class to           DcMotor::class.java,
    FtcServo::class to           com.qualcomm.robotcore.hardware.Servo::class.java,
    FtcColorSensor::class to     com.qualcomm.robotcore.hardware.ColorSensor::class.java,
    FtcGyroSensor::class to      com.qualcomm.robotcore.hardware.GyroSensor::class.java,
    FtcImu::class to             BNO055IMU::class.java,
    FtcTouchSensor::class to     com.qualcomm.robotcore.hardware.TouchSensor::class.java
)
