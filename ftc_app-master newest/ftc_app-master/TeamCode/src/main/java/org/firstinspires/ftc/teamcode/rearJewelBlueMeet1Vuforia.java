package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="Blue Jewel Rear Vuforia", group="Jewel")
@Disabled

public class rearJewelBlueMeet1Vuforia extends Error404_Hardware_Tier2

{
    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private int test=0;
    public rearJewelBlueMeet1Vuforia()
    {
    }
    @Override public void init(){
        super.init();
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
    }
    @Override public void start(){
        //resetAllEncoders_withWait();
        //gyroCalibrate();

    }
    @Override public void loop ()
    {
        switch (state)
        {
            case 0:
                slide_sideways("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()>50) {
                    slide_sideways("RUE", 0, "r", 0);
                    state++;
                }
                break;
            case 1:
                arm.setPosition(0.55);

                if(camera.getVoltage()<1.2){
                    telemetry.addData("On left","");
                    driveStraight("RUE",0,"f",0);
                    state=2;
                    encoder=leftFront.getCurrentPosition();
                    break;
                }
                else if(camera.getVoltage()>1.2){
                    telemetry.addData("On right","");
                    driveStraight("RUE",0,"r",0);
                    state=3;
                    encoder=leftFront.getCurrentPosition();
                    break;
                }
            case 2:
                driveStraight("RUE",0.2,"f",0);
                if(leftFront.getCurrentPosition()-encoder>50) {
                    driveStraight("RUE",0,"r",0);
                    state=4;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 3:
                driveStraight("RUE",0.2,"r",0);
                if(leftFront.getCurrentPosition()-encoder>50) {
                    driveStraight("RUE",0,"r",0);
                    state=4;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 4:
                arm.setPosition(0);
                driveStraight("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()-encoder>400) {
                    slide_sideways("RUE",0,"l",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 5:
                slide_sideways("RUE",0.3,"l",0);
                if(leftFront.getCurrentPosition()-encoder>400) {
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 6:
                driveStraight("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()-encoder>100) {
                    slide_sideways("RUE",0,"l",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            default:
                break;


        }
        telemetry.addData("1. State: ", state);
        telemetry.addData("2. loops: ", test);
        telemetry.addData("3. Gyro: ", getHeading());
        telemetry.addData("3. Camera:  ", camera.getVoltage());
        telemetry.addData("4. Left Front Position: ", leftFront.getCurrentPosition());
        telemetry.addData("5. Delta Position: ", encoder);



    } // loop
} //
