package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
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
    // Pixy cam stuff for jewel ID
    protected AnalogInput camera;
    protected String allianceColor;
    final static double JEWEL_VOLTAGE_THRESHOLD = 1.2;


    // Vuforia stuff for Vumark ID
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;
    int cameraMonitorViewId;
    VuforiaLocalizer.Parameters parameters;
    RelicRecoveryVuMark vuMark;


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

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXqihmr/////AAAAGfBzFvn3mUp2pArXwOs50RaJ5JQdpAr4rsjsH+U8jWqZz9IZH657T+p7j4SgiRhOxlbMsoXP43dcRWb953uxv1Pd9ykpvITS8R0LGB8w8DIEYElzCWAvx0qxFO/6mUq2nuWvAhSyGbVsQk3IgjC17DwijqO1i21E7bZtAp3LRfUaNjvwh38Q0EZkIY0ulaUChjb/sep2XzJ8/yoOxq3deuAVx6pSPcQwaLpdV7vSvLr7rDr1OIOZeb5DGjAEA4QLiV/t8/daIVi3AAWTpCi0kskgtT/KZMzzok8ACYE96pDMKn7Z5epuguKyZ4/6w9Mc7oMF68XMbtf60AhZvgUApJCakYrDT9MwT7IpGa03e+HC";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();

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

    public String readCryptograph()
    {
        String dejavu="";

        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN)
        {
            dejavu = vuMark.toString();
        }
        return dejavu;
    }

}
