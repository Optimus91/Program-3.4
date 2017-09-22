package org.firstinspires.ftc.teamcode;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/*
 * You can use the X button on either gamepad to turn the LED on and off.
 */
@Autonomous(name = "Nick Sensor Demo", group = "sensor")

public class nick_sensor extends LinearOpMode {
    ColorSensor RGB;
    GyroSensor gyro;
    TouchSensor touch;
    //Servo tester;
    @Override
    public void runOpMode() throws InterruptedException {
        // write some device information (connection info, name and type)
        // to the log file.
        // Assumes config file matches these designations
        //ie: mr, ods, touch, ir
        RGB = hardwareMap.colorSensor.get("mr");
        gyro = hardwareMap.gyroSensor.get("gyro");
        touch = hardwareMap.touchSensor.get("touch");
        // tester = hardwareMap.servo.get("tester");

        //////////////////////////////////
        //    Color Sensor preps       //
        // turn the RGB LED on in the beginning, just so user will know that the sensor is active.
        // hsvValues is an array that will hold the hue, saturation, and value information.
        // bPrevState and bCurrState represent the previous and current state of the button.
        //////////////////////////////////////////////////////////////////////////////////
        RGB.enableLed(true);
        float hsvValues[] = {0F,0F,0F};
//      boolean bPrevState = false;
//      boolean bCurrState = false;
        // wait one cycle.
        gyro.calibrate();
        waitForStart();
        while (opModeIsActive()) {

            Color.RGBToHSV(RGB.red()*8, RGB.green()*8, RGB.blue()*8, hsvValues);
            telemetry.addData("01 - White", RGB.alpha());
            telemetry.addData("02 - Red  ", RGB.red());
            telemetry.addData("03 - Green", RGB.green());
            telemetry.addData("04 - Blue ", RGB.blue());
            telemetry.addData("05 - Hue", hsvValues[0]);
            telemetry.addData("14. h", String.format("%03d", gyro.getHeading()));
            telemetry.addData("15", touch.isPressed());
            telemetry.update();
        }
    }
}
