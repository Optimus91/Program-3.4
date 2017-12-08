package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Rear", group="Jewel")
//@Disabled

public class rearJewelRedMeet1Vuforia extends Error404_Hardware_Tier2

{
    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private double timer=0;
    private int cryptoboxSlide=305;
    public rearJewelRedMeet1Vuforia()
    {
    }
    @Override public void init(){
        super.init();
        driveStraight("RUE",0,"f",0);
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
        arm.setPosition(0);
        swivel.setPosition(0.52);
        glyph.setPosition(0.25);
        encoder=leftFront.getCurrentPosition();
    }
    @Override public void start(){
        super.start();
    }

    @Override public void loop ()
    {
        switch (state)
        {
            case 0:
                arm.setPosition(0.75);
                    state++;
                break;
            case 1:
                timer =getRuntime();
                if(camera.getVoltage()<1.2){
                    telemetry.addData("On left","");
                    state=2;
                    break;
                }
                else if(camera.getVoltage()>1.2){
                    telemetry.addData("On right","");
                    state=3;
                    break;
                }
            case 2:
                if(((int)(getRuntime()-timer))>1) {
                    swivel.setPosition(0.7);
                    state=4;
                    timer =getRuntime();
                }
                break;
            case 3:
                if(((int)(getRuntime()-timer))>1) {
                    swivel.setPosition(0.4);
                    state=4;
                    timer =getRuntime();
                }
                break;
            case 4:
                if(((int)(getRuntime()-timer))>1) {
                    arm.setPosition(0);
                    swivel.setPosition(0.5);
                    state++;
                }
                break;
            case 5:
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder>220) {
                    driveStraight("RUE",0,"f",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                    timer =getRuntime();
                }
                break;
            case 6:
                if(readCryptograph().equals("LEFT")){
                    cryptoboxSlide=470;
                    state=7;
                }
                else if(readCryptograph().equals("RIGHT")){
                    cryptoboxSlide=115;
                    state=7;
                }
                else if(readCryptograph().equals("CENTER")){
                    cryptoboxSlide=305;
                    state=7;
                }
                if(((int)(getRuntime()-timer))>3) {
                    state=7;
                }
                break;
            case 7:
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder>460) {
                    pointTurn("RUE",0,"f",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                    }
                break;
            case 8:
                state++;
                break;
            case 9:
                slide_sideways("RUE",0.3,"l",0);
                if(leftFront.getCurrentPosition()-encoder>cryptoboxSlide) {
                    driveStraight("RUE",0,"f",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 10:
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder>90) {
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 11:
                glyph.setPosition(0.75);
                timer =getRuntime();
                state++;
                break;
            case 12:
                if(((int)(getRuntime()-timer))>4){
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 13:
                glyph.setPosition(0.75);
                driveStraight("RUE",0.1,"r",0);
                if(leftFront.getCurrentPosition()-encoder>90) {
                    driveStraight("RUE",0,"f",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            default:
                break;


        }
        telemetry.addData("1. State: ", state);
        telemetry.addData("2. Gyro: ", getHeading());
        telemetry.addData("3. Camera:  ", camera.getVoltage());
        telemetry.addData("4. Left Front Position: ", leftFront.getCurrentPosition());
        telemetry.addData("5. Delta Position: ", encoder);



    } // loop
} //
