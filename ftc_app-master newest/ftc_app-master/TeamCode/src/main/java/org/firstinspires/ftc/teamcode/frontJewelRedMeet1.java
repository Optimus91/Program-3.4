package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

/**
 * frontJewelRedMeet1 extends the <code>Error404_Hardware_Tier2</code> class and
 * contains the instructions to complete our autonomous missions on the
 * front red corner of the field.
 *
 * @author Team 8668
 * @see Error404_Hardware_Tier2
 */
@Autonomous(name="Red Jewel Front", group="Jewel")
@Disabled
public class frontJewelRedMeet1 extends Error404_Hardware_Tier2

{
    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private int tempDelta=0;
    private int patternSeen=0;
    double timer=0;

    public frontJewelRedMeet1()
    {
    }

    /**
     * Adding gyro functionality to the Tier1 init method for this program
     */
    @Override public void init(){
        super.init();
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
        telemetry.update();
        glyph.setPosition(0.25);
        }

    /**
     * Overriding the start method to provide a place to put things at this tier when the driver presses start.
     */
    @Override public void start(){
        slide_sideways("RUE",0,"r",0);
        encoder=leftFront.getCurrentPosition();
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
                telemetry.update();
                slide_sideways("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()-encoder>50) {
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
                if(leftFront.getCurrentPosition()-encoder>50) {
                    driveStraight("RUE",0,"f",0);
                    state=4;
                    encoder=leftFront.getCurrentPosition();
                    tempDelta=-50;
                }
                break;
            case 3:
                driveStraight("RUE",0.2,"r",0);
                if(leftFront.getCurrentPosition()-encoder>50) {
                    driveStraight("RUE",0,"f",0);
                    state=4;
                    encoder=leftFront.getCurrentPosition();
                    tempDelta=50;
                }
                break;
            case 4:
                telemetry.update();
                arm.setPosition(0);
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder>730) {
                    pointTurn("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 5:
                pointTurn("RUE",0.3,"r",0);
                if(Math.abs(getHeading())>87) {
                    driveStraight("RUE",0,"f",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 6:
                telemetry.update();
                arm.setPosition(0);
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder>110) {
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
                if(((int)(getRuntime()-timer))>2){
                    state++;
                }
                break;
            case 9:
                glyph.setPosition(0.25);
                driveStraight("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()-encoder>60) {
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
        telemetry.addData("6. Timer: ", getRuntime());

        telemetry.update();

    } // loop
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
} //
