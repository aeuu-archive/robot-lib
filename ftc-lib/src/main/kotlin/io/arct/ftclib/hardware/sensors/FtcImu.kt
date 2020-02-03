package io.arct.ftclib.hardware.sensors

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.I2cAddr
import io.arct.ftclib.hardware.FtcDevice
import io.arct.robotlib.hardware.Device
import org.firstinspires.ftc.robotcore.external.navigation.*

open class FtcImu internal constructor(private val sdk: BNO055IMU) : Device {
    override val name: String = sdk.systemStatus.name
    override val version: Int = 0

    val manufacturer: FtcDevice.Manufacturer = FtcDevice.Manufacturer.Other
    val connectionInfo: String = sdk.systemStatus.toString()

    val acceleration: Acceleration
        get() = sdk.acceleration

    val orientation: Orientation
        get() = sdk.angularOrientation

    val angularVelocity: AngularVelocity?
        get() = sdk.angularVelocity

    val calibrationStatus: Byte
        get() = sdk.calibrationStatus.calibrationStatus

    val gravity: Acceleration
        get() = sdk.gravity

    val acceleratorCalibrated: Boolean
        get() = sdk.isAccelerometerCalibrated

    val gyroCalibrated: Boolean
        get() = sdk.isGyroCalibrated

    val magnetometerCalibrated: Boolean
        get() = sdk.isMagnetometerCalibrated

    val systemCalibrated: Boolean
        get() = sdk.isSystemCalibrated

    val linearAcceleration: Acceleration
        get() = sdk.linearAcceleration

    val magneticFlux: MagneticFlux
        get() = sdk.magneticFieldStrength

    val overallAcceleration: Acceleration
        get() = sdk.overallAcceleration

    val position: Position
        get() = sdk.position

    val quaternionOrientation: Quaternion?
        get() = sdk.quaternionOrientation

    val temperature: Temperature
        get() = sdk.temperature

    val velocity: Velocity
        get() = sdk.velocity

    override fun close(): Device {
        sdk.close()

        return this
    }

    override fun reset(): Device =
        init()

    fun init(
        accelerationBandwidth: BNO055IMU.AccelBandwidth? = null,
        accelerationPowerMode: BNO055IMU.AccelPowerMode? = null,
        accelerationRange: BNO055IMU.AccelRange? = null,
        accelerationUnit: BNO055IMU.AccelUnit? = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC,
        accelerationIntegration: BNO055IMU.AccelerationIntegrator? = null,
        angleUnit: BNO055IMU.AngleUnit? = BNO055IMU.AngleUnit.DEGREES,
        calibrationData: BNO055IMU.CalibrationData? = null,
        calibrationDataFile: String? = null,
        gyroBandwidth: BNO055IMU.GyroBandwidth? = null,
        gyroPowerMode: BNO055IMU.GyroPowerMode? = null,
        gyroRange: BNO055IMU.GyroRange? = null,
        i2cAddress: I2cAddr? = null,
        enableLogging: Boolean? = null,
        loggingTag: String? = null,
        magOpMode: BNO055IMU.MagOpMode? = null,
        magPowerMode: BNO055IMU.MagPowerMode? = null,
        magRate: BNO055IMU.MagRate? = null,
        mode: BNO055IMU.SensorMode? = null,
        pitchMode: BNO055IMU.PitchMode? = null,
        temperatureUnit: BNO055IMU.TempUnit? = null,
        useExternalCrystal: Boolean? = null
    ): FtcImu {
        val p = BNO055IMU.Parameters()

        accelerationBandwidth?.let { p.accelBandwidth = it }
        accelerationPowerMode?.let { p.accelPowerMode = it }
        accelerationRange?.let { p.accelRange = it }
        accelerationUnit?.let { p.accelUnit = it }
        accelerationIntegration?.let { p.accelerationIntegrationAlgorithm = it }
        angleUnit?.let { p.angleUnit = it }
        calibrationData?.let { p.calibrationData = it }
        calibrationDataFile?.let { p.calibrationDataFile = it }
        gyroBandwidth?.let { p.gyroBandwidth = it }
        gyroPowerMode?.let { p.gyroPowerMode = it }
        gyroRange?.let { p.gyroRange = it }
        i2cAddress?.let { p.i2cAddr = it }
        enableLogging?.let { p.loggingEnabled = it }
        loggingTag?.let { p.loggingTag = it }
        magOpMode?.let { p.magOpMode = it }
        magPowerMode?.let { p.magPowerMode = it }
        magRate?.let { p.magRate = it }
        mode?.let { p.mode = it }
        pitchMode?.let { p.pitchMode = it }
        temperatureUnit?.let { p.temperatureUnit = it }
        useExternalCrystal?.let { p.useExternalCrystal = it }

        sdk.initialize(p)

        return this
    }
}