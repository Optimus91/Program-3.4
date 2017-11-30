package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * frontJewelBlueMeet1Vuforia extends the <code>Error404_Hardware_Tier2</code> class and
 * contains the instructions to complete our autonomous missions on the
 * front blue corner of the field.
 *
 * @author Team 8668
 * @see Error404_Hardware_Tier2
 */
@Autonomous(name="Blue Jewel Front Vuforia", group="Jewel")
@Disabled
public class frontJewelBlueMeet1Vuforia extends Error404_Hardware_Tier2

{
    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private int test=0;
    public frontJewelBlueMeet1Vuforia()
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
                slide_sideways("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()>50) {
                    slide_sideways("RUE", 0, "r", 0);
                    state++;
                }
                break;
            case 1:
                arm.setPosition(0.5);

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
                    encoder=leftFront.getCurrentPosition();
                    state=3;
                    break;
                }
            case 2:
                driveStraight("RUE",0.2,"f",0);
                if(leftFront.getCurrentPosition()-encoder>120) {
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
                if(leftFront.getCurrentPosition()-encoder>800) {
                    driveStraight("RUE",0,"r",0);
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
