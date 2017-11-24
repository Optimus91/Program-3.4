package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by kalee1 on 11/24/17.
 */

public class GlyphMechanism
{
    protected Servo glyph;


    public GlyphMechanism(HardwareMap hardwareMap, Telemetry telemetry)
    {
        try {
            glyph = hardwareMap.get(Servo.class, "glyph");
        } catch (Exception p_exeception) {
            telemetry.addData("glyph servo not found in config file", 0);
            glyph = null;
        }
    }


    public void pushOut() { glyph.setPosition(0.75);}
    public void pullIn() { glyph.setPosition(0.25);}

}