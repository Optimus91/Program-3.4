package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Rear", group="Jewel")

public class e404_RearRed extends Error404JewelAutonomous

{
    ///////////////////////////////////////////////////////////////////

    public e404_RearRed()
    {
        setLocation("REAR", "RED");
    }
    @Override public void init()
    {
        cryptoboxDriveDistance = 560;
        cryptoboxSlide=305;
        turnToCryptobox=0;
        setMultipleDirections("straight", "forward");
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
            cryptoboxSlide = -465;
            result = true;
        }
        else if(cryptoboxKey.equals("RIGHT"))
        {
            cryptoboxSlide=-115;
            result = true;
        }
        else if(cryptoboxKey.equals("CENTER"))
        {
            cryptoboxSlide=-290;
            result = true;
        }
        return result;
    }

    @Override public void loop ()
    {
        super.loop();
    } // loop



}
