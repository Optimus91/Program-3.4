package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by kalee1 on 11/16/17.
 */

public class VisionSystem
{
    protected AnalogInput camera;
    protected String allianceColor;
    final static double JEWEL_VOLTAGE_THRESHOLD = 1.2;

    // Implement Later
//    OpenGLMatrix lastLocation = null;
//    VuforiaLocalizer vuforia;
//
//    VuforiaTrackables relicTrackables;
//    VuforiaTrackable relicTemplate;
//    int cameraMonitorViewId;
//    VuforiaLocalizer.Parameters parameters;
//
//    RelicRecoveryVuMark vuMark;



    public VisionSystem(HardwareMap hardwareMap, Telemetry telemetry, String color )
    {
        try
        {
            camera = hardwareMap.get(AnalogInput.class, "camera");
        }
        catch (Exception p_exeception)
        {
            telemetry.addData("camera not found in config file", 0);
            camera = null;
        }
        allianceColor = color;

    }

    public double getVoltage()
    {
        return camera.getVoltage();
    }

    public boolean keepJewelOnRight()
    {
        boolean jewelOnRight = true;

        if ( allianceColor.toLowerCase().equals( "blue") )
        {
            jewelOnRight = camera.getVoltage() < JEWEL_VOLTAGE_THRESHOLD;
        }
        else if ( allianceColor.toLowerCase().equals( "red") )
        {
            jewelOnRight = camera.getVoltage() > JEWEL_VOLTAGE_THRESHOLD;
        }

        return jewelOnRight;  // returns true if the jewel to keep is on the right

    }


}
