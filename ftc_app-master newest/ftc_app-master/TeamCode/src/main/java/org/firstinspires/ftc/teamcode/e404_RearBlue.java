package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Rear", group="Jewel")

/**
 * e404_RearBlue extends <code>Error404JewelAutonomous</code> class and has the encoder distances for this quadrant.
 *
 * @author Team 8668
 * @See Error404JewelAutonomous
 * */
public class e404_RearBlue extends Error404JewelAutonomous

{
    ///////////////////////////////////////////////////////////////////

    /** The quadrant the robot is in. */
    public e404_RearBlue()
    {
        setLocation("REAR", "BLUE");
    }

    /** The movement distances for this quadrant. */
    @Override public void init()
    {
        cryptoboxDriveDistance = -600;
        cryptoboxSlide=305;
        turnToCryptobox=170;
        setMultipleDirections("straight", "reverse");
        super.init();  //super.init() method is moved to bottom to not get in the way of the driveStraight() method
    }

    /** Overriding the start method to provide a place to put things for when this class is selected. */
    @Override public void start(){
        super.start();
    }

    /**
     * Reads the pictograph and uses the location to select the correct values for the robot movement.
     *
     * @param cryptoboxKey  records the pictograph key as a string */
    @Override protected boolean updateFromVuforia(String cryptoboxKey)
    {
        boolean result = false;

        if(cryptoboxKey.equals("LEFT"))
        {
            cryptoboxSlide = 150;
            result = true;
        }
        else if(cryptoboxKey.equals("RIGHT"))
        {
            cryptoboxSlide=490;
            result = true;
        }
        else if(cryptoboxKey.equals("CENTER"))
        {
            cryptoboxSlide=305;
            result = true;
        }
        return result;
    }

    /** Not used in this class. */
    @Override public void loop ()
    {
        super.loop();
    }  //loop

}
