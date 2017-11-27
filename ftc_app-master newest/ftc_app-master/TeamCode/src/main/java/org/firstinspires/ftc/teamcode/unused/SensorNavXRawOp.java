
package org.firstinspires.ftc.teamcode.NAVX;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


import java.text.DecimalFormat;

/**
 * SensorNavXRawOp
 * <p>
 * This sample demonstrates how to acquire the raw
 * Gyroscope, Accelerometer and Magnetometer data.  This raw
 * data is typically not as useful as the "processed" data
 * (see the navXProcessedOp for details), however is provided
 * for those interested in accessing the raw data.
 *
 * Gyroscope data is units of Degrees/second.
 * Accelerometer data is in units of G.
 * Magnetometer data is in units if microTorr (uT)
 *
 * Magnetometer data is not valid unless magnetometer calibration
 * has been performed.  Without calibration, the magnetometer
 * data will be all zeros.  For details on how to calibrate the
 * magnetometer, please see the Magnetometer Calibration documentation:
 * http://navx-micro.kauailabs.com/guidance/magnetometer-calibration/
 *
 * Note that due to limitations imposed by the Core Device
 * Interface Module's I2C interface mechanisms, the acquisition
 * of both processed data and raw data requires two separate
 * I2C bus transfers, and thus takes longer than acquiring
 * only the raw or only the processed data.
 */
@TeleOp(name = "Sensor: navX Raw Data", group = "Sensor")
@Disabled
public class SensorNavXRawOp extends OpMode {

  private final int NAVX_DIM_I2C_PORT = 0;
  private String startDate;
  private ElapsedTime runtime = new ElapsedTime();
  private AHRS navx_device;
  @Override
  public void init() {
      navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
              NAVX_DIM_I2C_PORT,
              AHRS.DeviceDataType.kQuatAndRawData);

  }
  @Override
  public void stop() {
    navx_device.close();
  }
  /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
  @Override
  public void init_loop() {
    telemetry.addData("navX Op Init Loop", runtime.toString());
  }

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void loop() {

      boolean connected = navx_device.isConnected();
      telemetry.addData("1 navX-Device", connected ? "Connected" : "Disconnected" );
      String gyrocal, gyro_raw, accel_raw, mag_raw;
      boolean magnetometer_calibrated;
      if ( connected ) {
          DecimalFormat df = new DecimalFormat("#.##");
          magnetometer_calibrated = navx_device.isMagnetometerCalibrated();
          gyro_raw = df.format(navx_device.getRawGyroX()) + ", " +
                      df.format(navx_device.getRawGyroY()) + ", " +
                      df.format(navx_device.getRawGyroZ());
          accel_raw = df.format(navx_device.getRawAccelX()) + ", " +
                      df.format(navx_device.getRawAccelY()) + ", " +
                      df.format(navx_device.getRawAccelZ());
          if ( magnetometer_calibrated ) {
              mag_raw = df.format(navx_device.getRawMagX()) + ", " +
                      df.format(navx_device.getRawMagY()) + ", " +
                      df.format(navx_device.getRawMagZ());
          } else {
              mag_raw = "Uncalibrated";
          }
      } else {
        gyro_raw =
            accel_raw =
            mag_raw = "-------";
      }
      telemetry.addData("2 Gyros (Degrees/Sec):", gyro_raw);
      telemetry.addData("3 Accelerometers  (G):", accel_raw );
      telemetry.addData("4 Magnetometers  (uT):", mag_raw );
  }

}
