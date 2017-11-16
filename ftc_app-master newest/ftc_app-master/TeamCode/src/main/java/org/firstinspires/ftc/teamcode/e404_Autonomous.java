package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by kalee1 on 11/16/17.
 */

public class e404_Autonomous extends OpMode
{

    private static final String ALLIANCE_COLOR = "BLUE";
    private static final String ALLIANCE_POS = "FRONT";

    private double safeZoneSlide;
    private double safeZoneTranlation;

    private Telemetry telemetry;

    private Drivetrain bilbo = new Drivetrain( hardwareMap, telemetry );
    private VisionSystem palantir = new VisionSystem( hardwareMap, telemetry, ALLIANCE_COLOR );
    private Arm sting = new Arm( hardwareMap, telemetry);

    private int state = 0;

    @Override public void init()
    {
        //telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");

        if (ALLIANCE_POS.equals("FRONT") )
        {
            safeZoneSlide = 12.0;
            safeZoneTranlation = 54.0;
        }
        else
        {
            safeZoneSlide = 0.0;
            safeZoneTranlation = 27.0;
        }
    }


    @Override public void loop ()
    {
        switch (state)
        {
            case 0: // Drive off balancing stone
                bilbo.slideRight( 3.65, 0.3 );
                if ( bilbo.finishedDriving() )
                {
                    bilbo.stop();
                    state++;
                }
                break;
            case 1: // Determine which way to bump the correct jewel
                sting.down();

                if ( palantir.keepJewelOnRight() )
                {
                    bilbo.driveForward(8.08, 0.2 );
                }
                else
                {
                    bilbo.driveBackwards( 3.36, 0.2 );
                }
                state++;
                break;
            case 2:
                if (bilbo.finishedDriving() )
                {
                    bilbo.stop();
                    state++;
                }
                break;
            case 3:
                sting.up();
                if ( ALLIANCE_COLOR.equals("BLUE" ))
                {
                    bilbo.driveBackwards(safeZoneTranlation, 0.3);
                }
                else
                {
                    bilbo.driveForward(safeZoneTranlation, 0.3);
                }
                state++;
                break;
            case 4:
                if (bilbo.finishedDriving() )
                {
                    bilbo.stop();
                    state++;
                }
                break;
            case 5:
                if ( Math.abs(safeZoneSlide) >  0.01 )
                {
                    bilbo.slideLeft(safeZoneSlide, 0.3);
                }
                state++;
                break;
            case 6:
                if (bilbo.finishedDriving() )
                {
                    bilbo.stop();
                    state++;
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
