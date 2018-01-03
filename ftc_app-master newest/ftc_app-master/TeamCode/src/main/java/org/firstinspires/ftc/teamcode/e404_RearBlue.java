package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Rear", group="Jewel")

public class e404_RearBlue extends Error404JewelAutonomous

{
    ///////////////////////////////////////////////////////////////////

    public e404_RearBlue()
    {
        setLocation("REAR", "BLUE");
    }
    @Override public void init()
    {
        cryptoboxDriveDistance = -600;
        cryptoboxSlide=320;
        turnToCryptobox=170;
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
            cryptoboxSlide = 135;
            result = true;
        }
        else if(cryptoboxKey.equals("RIGHT"))
        {
            cryptoboxSlide=500;
            result = true;
        }
        else if(cryptoboxKey.equals("CENTER"))
        {
            cryptoboxSlide=318;
            result = true;
        }
        return result;
    }

    @Override public void loop ()
    {
        super.loop();
    }  //loop

}
