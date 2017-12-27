package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Front", group="Jewel")

public class e404_BlueFront extends Error404JewelAutonomous

{
    ///////////////////////////////////////////////////////////////////

    public e404_BlueFront()
    {
        setLocation("FRONT", "BLUE");
    }
    @Override public void init()
    {
        cryptoboxDriveDistance = -350;
        cryptoboxSlide=0;
        turnToCryptobox=0;
        setMultipleDirections("straight", "reverse");
        super.init();  //super.init() method is moved to bottom to not get in the way of the driveStraight() method

    }
    @Override public void start(){
        super.start();
    }

    @Override protected boolean updateFromVuforia(String cryptoboxKey)
    {
        boolean result = false;

        if(cryptoboxKey.equals("LEFT"))
        {
            cryptoboxDriveDistance = -110;
            result = true;
        }
        else if(cryptoboxKey.equals("RIGHT"))
        {
            cryptoboxDriveDistance=-475;
            result = true;
        }
        else if(cryptoboxKey.equals("CENTER"))
        {
            cryptoboxDriveDistance=-305;
            result = true;
        }
        return result;
    }

    @Override public void loop ()
    {
        super.loop();
    } // loop


}
