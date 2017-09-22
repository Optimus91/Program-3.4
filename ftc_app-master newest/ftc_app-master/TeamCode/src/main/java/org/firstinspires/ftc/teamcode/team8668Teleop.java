package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODERS;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODERS;

@TeleOp(name="Teleop meccanum", group="Teleop")

public class team8668Teleop extends OpMode {
    ////////////////////////////////////////////
    // This is the Teleop program for driver control.
    ////////////////////////////////////////////////
    DcMotor rightFront;
    DcMotor leftFront;
    DcMotor rightRear;
    DcMotor leftRear;
    DcMotor ballcollector;
    DcMotor balllauncher1;
    DcMotor balllauncher2;
    Servo leftPush;
    Servo rightPush;
    float launchspeed1;
    double powerval;
    double rightVal=0;
    double leftVal=0;
    double incrementDir=0;
    public team8668Teleop() {
    }
    @Override
    public void init() {
        powerval=0.55;
        telemetry.addData ("0", "I AM HERE");
        leftPush = hardwareMap.servo.get("leftPush");
        rightPush = hardwareMap.servo.get("rightPush");
        balllauncher1 = hardwareMap.dcMotor.get("balllauncher1");
        balllauncher1.setMode(RUN_USING_ENCODERS);
        balllauncher1.setDirection(DcMotor.Direction.FORWARD);
        balllauncher2 = hardwareMap.dcMotor.get("balllauncher2");
        balllauncher2.setMode(RUN_USING_ENCODERS);
        balllauncher2.setDirection(DcMotor.Direction.REVERSE);
        ballcollector = hardwareMap.dcMotor.get("ballcollector");
        ballcollector.setMode(RUN_WITHOUT_ENCODERS);
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront.setMode(RUN_USING_ENCODERS);
        rightFront.setMode(RUN_USING_ENCODERS);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        rightRear.setMode(RUN_USING_ENCODERS);
        leftRear.setMode(RUN_USING_ENCODERS);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("","V 2");
        leftVal=0.5;
        rightVal=0.5;
    }
    @Override
    public void loop() {
        //////////////////////////////////////////
        /////Drive Train//////////////////////////
        /////////////////////////////////////////

        float yL_val = -gamepad1.left_stick_y*((float)0.7);            //reading raw values from the joysticks
        float xL_val = gamepad1.left_stick_x*((float)0.7);            //reading raw values from the joysticks
        float xR_val = gamepad1.right_stick_x/2;

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

        //////////////////////////////////////////
        /////Ball Collector//////////////////////
        /////////////////////////////////////////
        float collector = gamepad2.right_trigger-gamepad2.left_trigger;
        collector = (float) scaleInput(collector);
        collector = Range.clip(collector, -1, 1); //To keep power value within the acceptable range.

        //////////////////////////////////////////////////////////
        //////BEACON PRESSER////////////////
        /////////////////////////////////////

        if(gamepad1.y){
            leftVal=0.15;
            rightVal=0.15;
        }
        else if(gamepad1.a){
            leftVal =1.0;
            rightVal=1.0;
        }
        else if(gamepad1.x){
            leftVal=0.5;
            rightVal=0.5;
        }
        else if(gamepad1.b){
            leftVal=0.75;
            rightVal=0.75;
        }
        else{
            // leftVal = 0.0;
            // rightVal=0.0;
        }
        rightPush.setPosition(rightVal);
        leftPush.setPosition(leftVal);


        /////////////////////////////////////////////////////////////
        //     LAUNCHER MOTORS                ////////////////
        //The below statements allow the driver to write a constant speed to the launcher motors.
        // The driver can also increase or decrease the launcher power in increments of 5%.
        /////////////////////////////////////
        float launcher = gamepad2.right_stick_y;
        launcher = (float) scaleInput(launcher);

        if(gamepad2.x){
            incrementDir=1;
        }
        if(gamepad2.b){
            incrementDir=2;
        }
        if(incrementDir==1&&!gamepad2.x){
            powerval=powerval-0.05;
            incrementDir=0;
        }
        if(incrementDir==2&&!gamepad2.b){
            powerval=powerval+0.05;
            incrementDir=0;
        }
        ballcollector.setPower(collector);
        powerval=Range.clip(powerval, 0.05, 1);
        if(gamepad2.a){                   //Preset values for motor speeds for ball launcher
            launchspeed1=(float)powerval;
        }
        else if(gamepad2.y){
            launchspeed1=-1;
        }

        else{
            launchspeed1=0;
        }

        float launchpower1=launcher+launchspeed1;
        launchpower1=Range.clip(launchpower1, -1, 1); //clip the launcher power to keep in within aceptable range
        balllauncher1.setPower(launchpower1);
        balllauncher2.setPower(launchpower1);
        rightFront.setPower(RF);
        leftFront.setPower(LF);
        rightRear.setPower(RR);
        leftRear.setPower(LR);

        telemetry.addData ("01: Ball Launcher Power: ", powerval);
        telemetry.addData ("02: right pusher:  "+rightPush.getPosition()+ "||||   left pusher: "+leftPush.getPosition()+"","");
        telemetry.addData ("03: Ball Collector Power: ", collector);
        telemetry.addData ("04: back right position: ", rightRear.getCurrentPosition());
        telemetry.addData ("04: back left position: ", leftRear.getCurrentPosition());

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