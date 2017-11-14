package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

@Autonomous(name="Red Jewel Front", group="Jewel")

public class frontJewelRedMeet1 extends Error404_Hardware_Tier2

{
    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private int tempDelta=0;
    private int patternSeen=0;

    public frontJewelRedMeet1()
    {
    }
    @Override public void init(){
        super.init();
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
        telemetry.update();
        glyph.setPosition(0.25);
        }
    @Override public void start(){
    }

    @Override public void loop ()
    {
        switch (state)
        {
            case 0:
                telemetry.update();
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
                if(leftFront.getCurrentPosition()-encoder>700) {
                    pointTurn("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
//            case 5:
//                pointTurn("RUE",0.3,"r",0);
//                if(Math.abs(getHeading())>89) {
//                    driveStraight("RUE",0,"f",0);
//                    state++;
//                    encoder=leftFront.getCurrentPosition();
//                }
//                break;
//            case 6:
//                telemetry.update();
//                arm.setPosition(0);
//                driveStraight("RUE",0.3,"f",0);
//                if(leftFront.getCurrentPosition()-encoder>60) {
//                    pointTurn("RUE",0,"r",0);
//                    state++;
//                    encoder=leftFront.getCurrentPosition();
//                }
//                break;
//            case 7:
//                glyph.setPosition(0.75);
//                break;
//            case 8:
//                try {
//                    Thread.sleep(1550);                 // one second.
//                } catch (InterruptedException ex) {
//                    Thread.currentThread().interrupt();
//                }
//                state++;
//                break;
            default:
               // glyph.setPosition(0.25);
                break;

        }
        telemetry.addData("1. State: ", state);
        telemetry.addData("2. Gyro: ", getHeading());
        telemetry.addData("3. Camera:  ", camera.getVoltage());
        telemetry.addData("4. Left Front Position: ", leftFront.getCurrentPosition());
        telemetry.addData("5. Delta Position: ", encoder);
        telemetry.update();

    } // loop
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
} //
