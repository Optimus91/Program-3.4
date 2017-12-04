package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * frontJewelBlueMeet1Vuforia extends the <code>Error404_Hardware_Tier2</code> class and
 * contains the instructions to complete our autonomous missions on the
 * front blue corner of the field.
 *
 * @author Team 8668
 * @see Error404_Hardware_Tier2
 */
@Autonomous(name="Blue Front", group="Jewel")
//@Disabled
public class frontJewelBlueMeet1Vuforia extends Error404_Hardware_Tier2

{
    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private double timer=0;
    private int cryptoboxSlide=300;
    public frontJewelBlueMeet1Vuforia()
    {
    }

    /**
     * Adding gyro functionality to the Tier1 init method for this program
     */
    @Override public void init(){
        super.init();
        driveStraight("RUE",0,"r",0);
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
        arm.setPosition(0);
        swivel.setPosition(0.52);
        glyph.setPosition(0.25);
        encoder=leftFront.getCurrentPosition();
    }

    /**
     * Overriding the start method to provide a place to put things at this tier when the driver presses start.
     */
    @Override public void start(){
        super.start();
    }



    /**
     * The autonomous program for this mission.
     * It uses a state machine to step through the movements necessary to execute the mission.
     * In this mission we:
     * <ul>
     *     <li>Identify the correct jewel with the pixycam and knock it off</li>
     *     <li>Drive to the safezone</li>
     *     <li>On the way to the safezone read the pictograph on the wall</li>
     *     <li>Insert pre-loaded glyph into the column of the cryptobox specified by the pictograph</li>
     * </ul>
     */
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
                    state=3;
                    break;
                }
                else if(camera.getVoltage()>1.2){
                    telemetry.addData("On right","");
                    state=2;
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
                driveStraight("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()-encoder>100) {
                    driveStraight("RUE",0,"r",0);
                    state=7;
                    encoder=leftFront.getCurrentPosition();
                    timer =getRuntime();
                }
                break;
            case 6:
                if(readCryptograph().equals("LEFT")){
                    //cryptoboxSlide=130;
                    cryptoboxSlide=110;
                    state=7;
                }
                else if(readCryptograph().equals("RIGHT")){
                    cryptoboxSlide=475;
                    //cryptoboxSlide=505;
                    state=7;
                }
                else if(readCryptograph().equals("CENTER")){
                    //cryptoboxSlide=320;
                    cryptoboxSlide=300;
                    state=7;
                }
                if(((int)(getRuntime()-timer))>3) {
                    state=7;
                }
                break;
            case 7:
                driveStraight("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()-encoder>300+cryptoboxSlide) {
                    pointTurn("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                    }
                break;
            case 8:
                pointTurn("RUE",0.3,"r",0);
                if(Math.abs(getHeading())>85){
                    state++;
                    driveStraight("RUE",0,"f",0);
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 9:
                state++;
                break;
            case 10:
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder>150) {
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
