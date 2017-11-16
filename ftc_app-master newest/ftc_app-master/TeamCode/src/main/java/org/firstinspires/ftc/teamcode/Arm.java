package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by kalee1 on 11/16/17.
 */

public class Arm
{
    protected Servo arm;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry)
    {
        try {
            arm = hardwareMap.get(Servo.class, "jewelSword");
        } catch (Exception p_exeception) {
            telemetry.addData("Jewel Sword not found in config file", 0);
            arm = null;
        }


    }

    public void down()
    {
        arm.setPosition(0.5);
    }

    public void up()
    {
        arm.setPosition(0.0);
    }

}
