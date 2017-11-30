package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * rearJewelRedMeet1 extends the <code>Error404_Hardware_Tier2</code> class and
 * contains the instructions to complete our autonomous missions on the
 * rear red corner of the field.
 *
 * @author Team 8668
 * @see Error404_Hardware_Tier2
 */
@Autonomous(name="Red Jewel Rear", group="Jewel")
public class rearJewelRedMeet1 extends Error404_Hardware_Tier2

{
    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private int test=0;
    double timer =0;
    public rearJewelRedMeet1()
    {
    }

    /**
     * Adding gyro functionality to the Tier1 init method for this program
     */
    @Override public void init(){
        super.init();
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
    }

    /**
     * Overriding the start method to provide a place to put things at this tier when the driver presses start.
     */
    @Override public void start(){
        //resetAllEncoders_withWait();
        //gyroCalibrate();

    }

    /**
     * The autonomous program for this mission.
     * It uses a state machine to step through the movements necessary to execute the mission.
     * In this mission we:
     * <ul>
     *     <li>Identify the correct jewel with the pixycam and knock it off</li>
     *     <li>Drive to the safezone</li>
     *     <li>Insert pre-loaded glyph into the cryptobox</li>
     * </ul>
     */
    @Override public void loop ()
    {
        switch (state)
        {
            case 0:
                slide_sideways("RUE",0.2,"r",0);
                if(leftFront.getCurrentPosition()>50) {
                    slide_sideways("RUE", 0, "r", 0);
                    state++;
                }
                break;
            case 1:
                arm.setPosition(0.5);

                if(camera.getVoltage()<1.2){
                    telemetry.addData("On left","");
                    driveStraight("RUE",0,"r",0);
                    state=3;
                    encoder=leftFront.getCurrentPosition();
                    break;
                }
                else if(camera.getVoltage()>1.2){
                    telemetry.addData("On right","");
                    driveStraight("RUE",0,"f",0);
                    state=2;
                    encoder=leftFront.getCurrentPosition();
                    break;
                }
            case 2:
                driveStraight("RUE",0.2,"f",0);
                if(leftFront.getCurrentPosition()-encoder>70) {
                    driveStraight("RUE",0,"f",0);
                    state=4;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 3:
                driveStraight("RUE",0.2,"r",0);
                if(leftFront.getCurrentPosition()-encoder>70) {
                    driveStraight("RUE",0,"f",0);
                    state=4;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 4:
                arm.setPosition(0);
                driveStraight("RUE",0.2,"f",0);
                if(leftFront.getCurrentPosition()-encoder>500) {
                    slide_sideways("RUE",0,"l",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 5:
                slide_sideways("RUE",0.3,"l",0);
                if(leftFront.getCurrentPosition()-encoder>460) {
                    driveStraight("RUE",0,"f",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                    timer =getRuntime();
                }
                break;
            case 6:
                driveStraight("RUE",0.3,"f",0);
                if(((int)(getRuntime()-timer))>1.3) {
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 7:
                glyph.setPosition(0.75);
                timer =getRuntime();
                state++;
                break;
            case 8:
                if(((int)(getRuntime()-timer))>1.5){
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 9:
                driveStraight("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()-encoder>80) {
                    driveStraight("RUE",0,"f",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            default:
                glyph.setPosition(0.25);
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
