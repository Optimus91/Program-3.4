package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by kalee1 on 11/16/17.
 */

public class e404_Autonomous extends OpMode
{
    // Alliance info to that
    protected String allianceColor = "BLUE";
    protected String alliancePos = "FRONT";

    // Distances used to tell the robot where to go during the autonomous portion.  They
    // are named with s# in front of them to indicate step numbers in the sequence.
    private double s1_safeZoneTranslation;
    private double s2_safeZoneSlide;
    private double s3_cryptoBoxClearance;
    private double s4_cryptoBoxSnuggle;
    private double s5_cryptoBoxBackup;
    private double  glyphOffset = 0.0;

    // The items needed to accomplish the tasks
    private Drivetrain bilbo;
    private VisionSystem palantir;
    private Arm sting;
    private GlyphMechanism precious;

    // Used for managing the state machine
    private int state = 0;  // What state should I execute now?
    private int nextState = 0;  // If I have gone to a wait state (99 or 100), what state do I go to next?
    private double timeValue = 0; // Used for timed states

    @Override public void init()
    {
        bilbo = new Drivetrain( hardwareMap, telemetry );
        sting = new Arm( hardwareMap, telemetry);
        palantir = new VisionSystem( hardwareMap, telemetry, allianceColor );
        precious = new GlyphMechanism( hardwareMap, telemetry );

        telemetry.addData("","V 1");

        // Set up my specific positions
        String keyColumn = palantir.readCryptograph();

        if (keyColumn.equals("LEFT"))
        {
            glyphOffset = 6.0;
        }
        else if (keyColumn.equals("CENTER"))
        {
            glyphOffset = 10.0;
        }
        else if (keyColumn.equals("RIGHT"))
        {
            glyphOffset = 14.0;
        }
        if (alliancePos.equals("REAR") )
        {
            s1_safeZoneTranslation = -54.0;
            s2_safeZoneSlide = 12.0 + glyphOffset;
            s3_cryptoBoxClearance = 6.0;
        }
        else
        {
            s1_safeZoneTranslation = -27.0 + glyphOffset;
            s2_safeZoneSlide = 0.0;
            s3_cryptoBoxClearance = 0.0;
        }
        s4_cryptoBoxSnuggle = 3.0;
        s5_cryptoBoxBackup = 6.0;
    }


    @Override public void loop ()
    {
        switch (state)
        {
            case 0: // Knock the jewel off
                sting.plungDown();

                if ( palantir.keepJewelOnRight() )
                {
                    sting.swingLeft();
                }
                else
                {
                    sting.swingRight();
                }
                state++;
                nextState = state;
                timeValue = getRuntime();
                break;
            case 1: // Wait 3 seconds to make sure the jewel sword has done its thing
                if ( (getRuntime() - timeValue) > 3 )
                {
                    sting.pullUp();
                    state++;
                }
                break;
            case 2: // Drive towards safe zone
                if ( Math.abs(s1_safeZoneTranslation) >  0.01 )
                {
                    bilbo.driveStraight(s1_safeZoneTranslation);
                    state = 99;
                    nextState = 3;
                }
                else
                {
                    state++;
                    nextState = state;
                }
                break;
            case 3: // If I need to slide over to the box, do that
                if ( Math.abs(s2_safeZoneSlide) >  0.01 )
                {
                    bilbo.slideLeft(s2_safeZoneSlide, 0.3);
                    state = 99;
                    nextState = 4;
                }
                else
                {
                    state++;
                    nextState = state;
                }
                break;
            case 4: // Make sure I have enough clearance to pivot
                if (Math.abs(s3_cryptoBoxClearance) > 0.01 )
                {
                    bilbo.driveStraight(s3_cryptoBoxClearance);
                    state = 99;
                    nextState = 5;
                }
                else
                {
                    state++;
                    nextState = state;
                }
                break;
            case 5: // Pivot to get the glyph pointed at the box
                bilbo.pivotTurnCCW(178, 0.3);
                state = 100;
                nextState = 6;
                break;
            case 6: // Snuggle up to the box so that I can push the glyph in
                if ( Math.abs(s4_cryptoBoxSnuggle) >  0.01 )
                {
                    bilbo.driveStraight(s4_cryptoBoxSnuggle);
                    state = 99;
                    nextState = 7;
                }
                else
                {
                    state++;
                    nextState = state;
                }
                break;
            case 7:  //Do Glyph
                precious.pushOut();
                timeValue = getRuntime();
                state++;
                break;
            case 8:
                if ( (getRuntime() - timeValue) > 8 )
                {
                    precious.pullIn();
                    state++;
                }
                break;
            case 9: // Back up a little from box so tha the robot is not touching the glyph
                if ( Math.abs(s5_cryptoBoxBackup) >  0.01 )
                {
                    bilbo.driveStraight(s5_cryptoBoxBackup);
                    state = 99;
                    nextState = 10;
                }
                else
                {
                    state++;
                    nextState = state;
                }
                break;
            case 99:
                if (bilbo.finishedDriving() )
                {
                    bilbo.stop();
                    state = nextState;
                }
                break;
            case 100:
                if ( bilbo.finishedPivoting() )
                {
                    bilbo.stop();
                    state = nextState;
                }
                break;
            default:
                break;

        }
        telemetry.addData("1. State: ", state);
        telemetry.addData("2. Gyro: ", bilbo.getHeading() );
        telemetry.addData("3. Camera:  ", palantir.getVoltage() );
        telemetry.addData("4. Left Front Position: ", bilbo.getPosition() );
        telemetry.update();

    } // loop


}