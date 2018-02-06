package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DigitalChannel;



import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODERS;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODERS;

@TeleOp(name="8668 Teleop meccanum", group="Teleop")

/**
 * team8668Teleop extends the <code>OpMode</code> class.
 *
 * Contains all of the button and joy-stick initializations for moving the robot with the
 * remotes as well as the motor and servo initializations for Teleop.
 *
 * @author Team 8668
 * @see OpMode
 */
public class team8668Teleop extends OpMode {
    
    /** The front right drive motor on the robot. */
    DcMotor rightFront;
    /** The front left drive motor on the robot. */
    DcMotor leftFront;
    /** The right rear drive motor on the robot. */
    DcMotor rightRear;
    /** The left rear drive motor on the robot. */
    DcMotor leftRear;
    /** The left glyph intake motor -- used in conjunction with the rightGlyph motor to suck in glyphs. */
    DcMotor leftGlyph;
    /** The right glyph intake motor -- used in conjunction with the leftGlyph motor to suck in glyphs. */
    DcMotor rightGlyph;
    DcMotor encoderMotor;

   
    /** The arm servo raises and lowers the jewel arm. */
    Servo arm;
    /** The swivel servo swings the jewel sword back and forth to knock off the glyph. */
    Servo swivel;
    /** The shoulder servo is a high-torque servo that extends the relic arm up and over the field wall. */
    Servo shoulder;
    /** The elbow servo raises and lowers the relic for extra flexibility when deploying the relic. */
    Servo elbow;
    /** The hand servo controls the claw that grabs the relic. */
    Servo hand;
    Servo elbow;
    Servo swivel;
    Servo glyphter;

    DigitalChannel bottom;
    DigitalChannel top;

    float launchspeed1;
    double powerval;
    double rightVal=0;
    double leftVal=0;
    double incrementDir=0;

    /** Setting the start position of the elbow servo. */
    double elbowPos=1;
    /** Setting the start position of the shoulder servo. */
    double shoulderPos=0.95;
    double swivelPos =0.522;
    /** Setting the start position for the hand servo. */
    double handPos=0.7;
    double glyphterSpeed=0.5;
    int encoderDelta=0;

    public team8668Teleop() {
    }

    /** Setting the modes and names for all the motors and servos. */
    @Override
    public void init() {
        //Initialize all motors, servos, and sensors, as well as setting some servos to initilaize to a particualr position.
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
        encoderMotor = hardwareMap.dcMotor.get("encoderMotor");
        encoderMotor.setMode(RUN_USING_ENCODER);
        encoderMotor.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("","V 2");
        shoulder = hardwareMap.servo.get("shoulder");
        elbow = hardwareMap.servo.get("elbow");
        hand = hardwareMap.servo.get("hand");
        glyph = hardwareMap.servo.get("glyph");
        swivel=hardwareMap.servo.get("jewelSwivel");
        leftGlyph = hardwareMap.dcMotor.get("leftGlyph");
        rightGlyph = hardwareMap.dcMotor.get("rightGlyph");
        glyphter = hardwareMap.servo.get("glyphter");
        bottom = hardwareMap.get(DigitalChannel.class, "bottomTouch");
        top = hardwareMap.get(DigitalChannel.class, "topTouch");
        leftGlyph.setMode(RUN_USING_ENCODER);
        rightGlyph.setMode(RUN_USING_ENCODER);
        leftGlyph.setDirection(DcMotor.Direction.REVERSE);
        rightGlyph.setDirection(DcMotor.Direction.FORWARD);
        hand.setPosition(0.7);
        arm.setPosition(0.0);
        swivel.setPosition(0.5);

    }

    /** Reading the raw input from the controllers and turning them into movement values for the motors and servos. */
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

        RF = Range.clip(RF, -1, 1);          //make sure power stays between -1 and 1
        LF = Range.clip(LF, -1, 1);
        RR = Range.clip(RR, -1, 1);
        LR = Range.clip(LR, -1, 1);

        if(gamepad1.y){
            arm.setPosition(0.79);    //move down jewel arm
        }
        else if(gamepad1.a){
            arm.setPosition(0.0);     //move up jewel arm
        }
        if(gamepad2.y){
            shoulderPos+=0.005;       //increment shoulder servo
        }
        else if(gamepad2.a){
            shoulderPos-=0.005;       //increment shoulder servo down
        }
        if(gamepad1.left_stick_button){
            shoulderPos=0.7;
        }
        shoulderPos = Range.clip(shoulderPos,0.7,0.95); //keep shoulder servo value in given range

        if(gamepad2.dpad_up){
            elbowPos+=0.001;         //increment elbow out
        }
        else if(gamepad2.dpad_down){ //increment elbow in
            elbowPos-=0.001;
        }
        elbowPos = Range.clip(elbowPos,0,1); //keep elbow servo value in given range

        if(gamepad2.right_bumper){
            handPos=0.4;                      //open grabber
        }
        if(gamepad2.left_bumper){
            handPos=0.7;                      //close grabber
        }
        if(gamepad2.right_trigger>0.05){      //increment grabber open
            handPos-=(gamepad2.right_trigger*0.001);
        }

        if(gamepad2.left_trigger>0.05){       //increment grabber close
            handPos+=(gamepad2.left_trigger*0.001);
        }

        if(gamepad1.x){                       //turn jewel servo to the side
            swivelPos=0.40;
        }
        else if(gamepad1.b){                  //turn jewel servo to the other side
            swivelPos=0.64;
        }
        else {swivelPos=0.52;}
        Range.clip(handPos, 0.35, 0.8);
        rightFront.setPower(RF);              //Set all values to correct devices
        leftFront.setPower(LF);
        rightRear.setPower(RR);
        leftRear.setPower(LR);
        elbow.setPosition(elbowPos);
        shoulder.setPosition(shoulderPos);
        swivel.setPosition(swivelPos);
        hand.setPosition(handPos);
        rightGlyph.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
        leftGlyph.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
//1500
        if(top.getState() && bottom.getState() && !gamepad2.left_stick_button && !gamepad2.right_stick_button){
            glyphterSpeed = (((-1)*(scaleInput(gamepad2.left_stick_y)/2)+0.5));
        }
        if(!top.getState()) {
            if (gamepad2.left_stick_y > 0.1) {
                glyphterSpeed = (((-1) * (scaleInput(gamepad2.left_stick_y) / 2) + 0.5));
            }
            else{
                glyphterSpeed=0.5;
            }
        }
        if(!bottom.getState()) {
            encoderDelta=encoderMotor.getCurrentPosition();
            if (gamepad2.left_stick_y < 0.1) {
                glyphterSpeed = (((-1) * (scaleInput(gamepad2.left_stick_y) / 2) + 0.5));
            }
            else{
                glyphterSpeed=0.5;
            }
        }
        if(gamepad2.left_stick_button){
            if((encoderMotor.getCurrentPosition()-encoderDelta)<1500){
                glyphterSpeed=1;
            }
            else if((encoderMotor.getCurrentPosition()-encoderDelta)>1500){
                glyphterSpeed=0;
            }
            }
        if(gamepad2.right_stick_button){
            if((encoderMotor.getCurrentPosition()-encoderDelta)<3000){
                glyphterSpeed=1;
            }
            else if((encoderMotor.getCurrentPosition()-encoderDelta)>3000){
                glyphterSpeed=0;
            }
        }
        glyphter.setPosition(glyphterSpeed);
      telemetry.addData("shoulder: ",shoulder.getPosition());
      telemetry.addData("grabber:",hand.getPosition());          //print info to telemetry
      telemetry.addData("elbow: ",elbow.getPosition());
      telemetry.addData("pusher: ",glyph.getPosition());
      telemetry.addData("swivel: ",swivel.getPosition());
      telemetry.addData("hand: ",handPos);
      telemetry.addData("glyphter position: ", encoderMotor.getCurrentPosition());
      telemetry.addData("glyphter position with delta: ", (encoderMotor.getCurrentPosition()-encoderDelta));
      telemetry.addData("glyphter speed: ", glyphterSpeed);
      telemetry.addData("glyphter bottom: ", bottom.getState());
      telemetry.addData("glyphter top: ", top.getState());


    }

    /** Stop everything */
    @Override
    public void stop() {
    }

    /**
     * Takes the raw data input from the joystick and returns it as a curve to help with smoother robot movement.
     *
     * @param dVal The value of the d stick recorded as a double. */
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