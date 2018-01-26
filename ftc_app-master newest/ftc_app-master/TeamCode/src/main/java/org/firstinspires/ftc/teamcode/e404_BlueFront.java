package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Front", group="Jewel")

/**
 * e404_BlueFront extends <code>Error404JewelAutonomous</code> class and has the encoder distances for this quadrant.
 *
 * @author Team 8668
 * @See Error404JewelAutonomous
 * */
public class e404_BlueFront extends Error404JewelAutonomous

{
    ///////////////////////////////////////////////////////////////////

    /** The quadrant the robot is in. */
    public e404_BlueFront()
    {
        setLocation("FRONT", "BLUE");
    }

   /** The movement distances for this quadrant. */
    @Override public void init()
    {
        cryptoboxDriveDistance = -780;
        cryptoboxSlide=0;
        turnToCryptobox=80;
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
            cryptoboxDriveDistance = -630;
            result = true;
        }
        else if(cryptoboxKey.equals("RIGHT"))
        {
            cryptoboxDriveDistance=-990;
            result = true;
        }
        else if(cryptoboxKey.equals("CENTER"))
        {
            cryptoboxDriveDistance=-805;
            result = true;
        }
        return result;
    }

    /** Not used in this class. */
    @Override public void loop ()
    {
        super.loop();
    } // loop


}
