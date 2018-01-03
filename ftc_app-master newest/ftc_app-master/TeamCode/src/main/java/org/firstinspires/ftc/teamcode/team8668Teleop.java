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

@TeleOp(name="8668 Teleop meccanum", group="Teleop")

public class team8668Teleop extends OpMode {
    ////////////////////////////////////////////
    // This is the Teleop program for driver control.
    ////////////////////////////////////////////////
    DcMotor rightFront;
    DcMotor leftFront;
    DcMotor rightRear;
    DcMotor leftRear;
    DcMotor leftGlyph;
    DcMotor rightGlyph;

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
    public team8668Teleop() {
    }
    @Override
    public void init() {
        telemetry.addData ("0", "I AM HERE");
        arm=hardwareMap.servo.get("jewelSword");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront.setMode(RUN_USING_ENCODER);
        rightFront.setMode(RUN_USING_ENCODER);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        rightRear.setMode(RUN_USING_ENCODER);
        leftRear.setMode(RUN_USING_ENCODER);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("","V 2");
        shoulder = hardwareMap.servo.get("shoulder");
        elbow = hardwareMap.servo.get("elbow");
        hand = hardwareMap.servo.get("hand");
        glyph = hardwareMap.servo.get("glyph");
        pivot=hardwareMap.servo.get("jewelSwivel");
        leftGlyph = hardwareMap.dcMotor.get("leftGlyph");
        rightGlyph = hardwareMap.dcMotor.get("rightGlyph");
        leftGlyph.setMode(RUN_USING_ENCODER);
        rightGlyph.setMode(RUN_USING_ENCODER);
        leftGlyph.setDirection(DcMotor.Direction.REVERSE);
        rightGlyph.setDirection(DcMotor.Direction.FORWARD);
        hand.setPosition(1);
        glyph.setPosition(0.25);
        arm.setPosition(0.0);
        pivot.setPosition(0.5);
    }
    @Override
    public void loop() {
        //////////////////////////////////////////
        /////Drive Train//////////////////////////
        /////////////////////////////////////////

        float yL_val = -gamepad1.left_stick_y;            //reading raw values from the joysticks
        float xL_val = gamepad1.left_stick_x;            //reading raw values from the joysticks
        float xR_val = gamepad1.right_stick_x;

        //clip the right/left values so that the values never exceed +/- 1.
        yL_val = (float) scaleInput(yL_val);
        xL_val = (float) scaleInput(xL_val);
        xR_val = (float) scaleInput(xR_val);

        float RF =(yL_val-xR_val-xL_val);  //these are the calculations need to make a simple
        float LF =(yL_val+xR_val+xL_val);  // mecaccnum drive. The left joystick controls moving
        float RR= (yL_val-xR_val+xL_val);  //straight forward/backward and straight sideways. The
        float LR =(yL_val+xR_val-xL_val);  //right joystick controls turning.

        RF = Range.clip(RF, -1, 1);
        LF = Range.clip(LF, -1, 1);
        RR = Range.clip(RR, -1, 1);
        LR = Range.clip(LR, -1, 1);

        if(gamepad1.y){
            arm.setPosition(0.79);
        }
        else if(gamepad1.a){
            arm.setPosition(0.0);
        }
        if(gamepad2.y){
            shoulderPos+=0.001;
        }
        else if(gamepad2.a){
            shoulderPos-=0.001;
        }
        shoulderPos = Range.clip(shoulderPos,0.76,0.95);

        if(gamepad2.dpad_up){
            elbowPos+=0.001;
        }
        else if(gamepad2.dpad_down){
            elbowPos-=0.001;
        }
        elbowPos = Range.clip(elbowPos,0,1);

        if(gamepad2.right_bumper){
            hand.setPosition(0.8);
        }
        if(gamepad2.left_bumper){
            hand.setPosition(1);
        }

        if(gamepad1.x){
            pivotPos=0.40;
        }
        else if(gamepad1.b){
            pivotPos=0.64;
        }
        else {pivotPos=0.52;}



        rightFront.setPower(RF);
        leftFront.setPower(LF);
        rightRear.setPower(RR);
        leftRear.setPower(LR);
        elbow.setPosition(elbowPos);
        shoulder.setPosition(shoulderPos);
        pivot.setPosition(pivotPos);
        rightGlyph.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
        leftGlyph.setPower(gamepad1.right_trigger-gamepad1.left_trigger);

      telemetry.addData("shoulder: ",shoulder.getPosition());
      telemetry.addData("grabber:",hand.getPosition());
      telemetry.addData("elbow: ",elbow.getPosition());
      telemetry.addData("pusher: ",glyph.getPosition());
      telemetry.addData("pivot: ",pivot.getPosition());

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