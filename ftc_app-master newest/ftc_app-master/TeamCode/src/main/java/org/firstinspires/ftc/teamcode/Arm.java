package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
<<<<<<< HEAD
 * This class checks that all the servos being used are in the hardware map
 */
=======
 * Created by kalee1 on 11/16/17.
 */

>>>>>>> develop
public class Arm
{
    protected Servo arm;
    protected Servo swivel;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry)
    {
        try {
            arm = hardwareMap.get(Servo.class, "jewelSword");
        } catch (Exception p_exeception) {
            telemetry.addData("Jewel Sword not found in config file", 0);
            arm = null;
        }
        try {
            swivel = hardwareMap.get(Servo.class, "jewelSwivel");
<<<<<<< HEAD
            swivel.setPosition(0.5);
=======
            swivel.setPosition(0.52);
>>>>>>> develop
        } catch (Exception p_exeception) {
            telemetry.addData("Jewel Swivel not found in config file", 0);
            swivel = null;
        }

    }

    public void plungDown()
    {
<<<<<<< HEAD
        arm.setPosition(0.5);
=======
        arm.setPosition(0.75);
>>>>>>> develop
    }

    public void pullUp()
    {
        arm.setPosition(0.0);
    }

<<<<<<< HEAD
    public void swingLeft() { swivel.setPosition(0.75); }

    public void swingRight() { swivel.setPosition(0.25);}
=======
    public void swingLeft() { swivel.setPosition(0.44); }

    public void swingCenter() { swivel.setPosition(0.52); }

    public void swingRight() { swivel.setPosition(0.64);}
>>>>>>> develop

}
