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
    protected Servo pivot;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry)
    {
        try {
            arm = hardwareMap.get(Servo.class, "jewelSword");
        } catch (Exception p_exeception) {
            telemetry.addData("Jewel Sword not found in config file", 0);
            arm = null;
        }
        try {
            pivot = hardwareMap.get(Servo.class, "jewelSwordPivot");
            pivot.setPosition(0.5);
        } catch (Exception p_exeception) {
            telemetry.addData("Jewel Sword Pivot not found in config file", 0);
            pivot = null;
        }

    }

    public void plungDown()
    {
        arm.setPosition(0.5);
    }

    public void pullUp()
    {
        arm.setPosition(0.0);
    }

    public void swingLeft() { pivot.setPosition(0.75); }

    public void swingRight() { pivot.setPosition(0.25);}

}
