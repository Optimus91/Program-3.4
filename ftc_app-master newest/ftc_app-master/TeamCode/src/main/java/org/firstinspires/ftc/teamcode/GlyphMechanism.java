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

    /**
     * Checks to make sure the servo(s) used in the glyph mechanism are
     * registered in the hardware map and ready to be used
     *
     * @param hardwareMap  accesses the hardware map which contains the information
     *                     about the motors the code requires to uyse the motors
     * @param telemetry  Dunno
     */
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
