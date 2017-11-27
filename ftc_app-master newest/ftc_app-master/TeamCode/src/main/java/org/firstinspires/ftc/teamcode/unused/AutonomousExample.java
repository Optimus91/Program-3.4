package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@Disabled
@Autonomous(name="State Machine Demo", group="Workshop")

public class AutonomousExample extends OpMode
{
    int state=0;
    DcMotor left;
    DcMotor right;
    int leftPosition;
    int rightPosition;
    public AutonomousExample()
    {
    }
    @Override public void init(){
            left = hardwareMap.dcMotor.get("left");
            right = hardwareMap.dcMotor.get("right");
            right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            right.setDirection(DcMotorSimple.Direction.FORWARD);
            left.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    @Override public void start(){
            leftPosition=left.getCurrentPosition();
            rightPosition=right.getCurrentPosition();
    }

    //////////////////////////////////////////////////////////////////////
    //                                                                  //
    //This code should drive forward, turn right, drive again, and stop.//
    //                                                                  //
    //////////////////////////////////////////////////////////////////////
    @Override public void loop ()
    {

        switch (state) {
            case 0:
                right.setPower(0.4);
                left.setPower(0.4);
                if((right.getCurrentPosition()-rightPosition)>1200){
                    //replace 3000 with desired encoder value
                    state = 1;
                    right.setPower(0); //stop
                    left.setPower(0);
                    right.setDirection(DcMotorSimple.Direction.REVERSE);
                    left.setDirection(DcMotorSimple.Direction.FORWARD);
                    leftPosition=left.getCurrentPosition();
                }
                break;
            case 1:
                right.setPower(0.5);
                left.setPower(0.5);
                if((left.getCurrentPosition()-leftPosition)>1200){
                    //replace 3000 with desired encoder value
                    state = 2;
                    right.setPower(0); //stop
                    left.setPower(0);
                    right.setDirection(DcMotorSimple.Direction.FORWARD);
                    left.setDirection(DcMotorSimple.Direction.FORWARD);
                    leftPosition=left.getCurrentPosition();
                }
                break;
            case 2:
                right.setPower(0.5);
                left.setPower(0.5);
                if((left.getCurrentPosition()-leftPosition)>1000){
                    //replace 3000 with desired encoder value
                    state = 3;
                    right.setPower(0); //stop
                    left.setPower(0);
                    right.setDirection(DcMotorSimple.Direction.REVERSE);
                    left.setDirection(DcMotorSimple.Direction.FORWARD);
                    leftPosition=left.getCurrentPosition();
                }
                break;
            case 3:
                //case 3 code goes here
                state = 4;
                break;

            default: //if variable "state" is not used in any of the above case statements, default case is run (ie: state=4)
                break;

        }
        telemetry.addData("right: ",right.getCurrentPosition());
        telemetry.addData("left: ",left.getCurrentPosition());
        telemetry.addData("state: ",state);


    }
}