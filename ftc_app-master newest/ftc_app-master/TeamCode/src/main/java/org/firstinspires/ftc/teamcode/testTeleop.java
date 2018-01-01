package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODERS;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODERS;

@TeleOp(name="Jewel Servo Test", group="Teleop")

public class testTeleop extends OpMode {
    ////////////////////////////////////////////
    // This is the Teleop program for driver control.
    ////////////////////////////////////////////////
    DcMotor rightFront;
    DcMotor leftFront;
    DcMotor rightRear;
    DcMotor leftRear;
    Servo arm;
    Servo glyph;
    Servo shoulder;
    Servo hand;
    Servo elbow;
    Servo pivot;
    float launchspeed1;
    double powerval;
    double rightVal=0;
    double leftVal=0;
    double incrementDir=0;
    double elbowPos=1;
    double shoulderPos=0.95;
    double pivotPos =0.522;
    public testTeleop() {
    }
    @Override
    public void init() {
        telemetry.addData ("0", "I AM HERE");
        arm=hardwareMap.servo.get("jewelSword");
        pivot=hardwareMap.servo.get("jewelSwivel");
        arm.setPosition(0.0);
        pivot.setPosition(0.5);
    }
    @Override
    public void loop() {


        if(gamepad2.y){
            shoulderPos+=0.001;
        }
        else if(gamepad2.a){
            shoulderPos-=0.001;
        }
        shoulderPos = Range.clip(shoulderPos,0,1);

        if(gamepad2.dpad_up){
            elbowPos+=0.001;
        }
        else if(gamepad2.dpad_down){
            elbowPos-=0.001;
        }
        elbowPos = Range.clip(elbowPos,0,1);


        pivot.setPosition(elbowPos);
        arm.setPosition(shoulderPos);

        telemetry.addData("arm: ",shoulderPos);
        telemetry.addData("pivot: ",elbowPos);

    }
    @Override
    public void stop() {
    }
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}