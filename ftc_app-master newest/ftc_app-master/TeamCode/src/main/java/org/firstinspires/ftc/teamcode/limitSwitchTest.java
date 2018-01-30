package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;

/*
 * This is an example LinearOpMode that shows how to use
 * the REV Robotics Color-Distance Sensor.
 *
 * It assumes the sensor is configured with the name "sensor_color_distance".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 */
@Autonomous(name = " Limit Switch Test", group = "Sensor")
public class limitSwitchTest extends LinearOpMode {



    DigitalChannel limitSwitch;

    @Override
    public void runOpMode() {


        limitSwitch=hardwareMap.get(DigitalChannel.class,"switch");

        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("Pressed: ", limitSwitch.getState());


            telemetry.update();
        }
    }
}
