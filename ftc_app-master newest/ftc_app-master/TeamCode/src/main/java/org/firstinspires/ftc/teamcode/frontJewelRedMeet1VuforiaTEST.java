package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

@Autonomous(name="Red Jewel Front Vuforia", group="Jewel")
@Disabled

public class frontJewelRedMeet1VuforiaTEST extends Error404_Hardware_Tier2

{
    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private int tempDelta=0;
    private int patternSeen=0;
    private int jewelMove=50;
    private int leftColumnTarget=300;
    private int centerColumnTarget=400;
    private int rightColumnTarget=500;

    public frontJewelRedMeet1VuforiaTEST()
    {
    }
    @Override public void init(){
        super.init();
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
        telemetry.update();
    }
    @Override public void start(){
        super.start();
    }

    @Override public void loop ()
    {
        switch (state)
        {
            case 0:                                      // Getting ready
                telemetry.update();
                slide_sideways("RUE",0.3,"r",0);
                if(leftFront.getCurrentPosition()>50) {
                    slide_sideways("RUE", 0, "r", 0);
                    state++;
                }
                break;
            /*case 1:
                arm.setPosition(0.5);                    //Init arm

                if(camera.getVoltage()<1.2){              //Check for red ball position
                    telemetry.addData("On left","");
                    driveStraight("RUE",0,"r",0);
                    state=3;
                    encoder=leftFront.getCurrentPosition();
                    break;
                }
                else if(camera.getVoltage()>1.2){                   /////
                    telemetry.addData("On right","");
                    driveStraight("RUE",0,"f",0);
                    state=2;
                    encoder=leftFront.getCurrentPosition();
                    break;
                }*/
            case 2:                                        //Red ball on right
                driveStraight("RUE",0.2,"f",0);
                if(leftFront.getCurrentPosition()-encoder>jewelMove) {
                    driveStraight("RUE",0,"f",0);
                    state=4;
                    encoder=leftFront.getCurrentPosition();
                    tempDelta=-50;
                }
                break;
            case 3:                                       //Red ball on left
                driveStraight("RUE",0.2,"r",0);
                if(leftFront.getCurrentPosition()-encoder>jewelMove) {
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
                if(leftFront.getCurrentPosition()-encoder-tempDelta>centerColumnTarget) {
                    slide_sideways("RUE",0,"l",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 5:
                if(readCryptograph()=="LEFT"){
                    tempDelta=600;
                    break;
                }
                if(readCryptograph()=="CENTER"){
                    tempDelta=400;
                    break;
                }
                if(readCryptograph()=="RIGHT"){
                    tempDelta=200;
                    break;
            }
                else{};
                break;
            case 6:
                slide_sideways("RUE",0.3,"l",0);
                if(leftFront.getCurrentPosition()>50) {                 ///jewelMove///
                    driveStraight("RUE",0,"f",0);
                    state++;
                }
                break;
            case 7:
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder+tempDelta>400) {          ///centerColumnTarget///
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
        telemetry.addData("6. Pattern: ", readCryptograph());
        telemetry.update();

    } // loop
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
} //
