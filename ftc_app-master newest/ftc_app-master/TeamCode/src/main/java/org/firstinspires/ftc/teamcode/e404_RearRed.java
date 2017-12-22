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
        cryptoboxDriveDistance = 0;
       // stoneToMarket = 560;
        cryptoboxSlide=305;
       // turnToRearCryptobox=0;
       // turnToFrontCryptobox=0;
        turnToCryptobox=0;
        // location=3;
        driveStraight("RUE",0,"f",0);
        super.init();  //super.init() method is moved to bottom to not get in the way of the driveStraight() method

    }
    @Override public void start(){
        super.start();
    }

    @Override protected void updateFromVuforia(String cryptoboxKey)
    {
        if(cryptoboxKey.equals("LEFT"))
        {
            cryptoboxDriveDistance = 0;
            cryptoboxSlide = 460;
        }
        else if(cryptoboxKey.equals("RIGHT"))
        {
            cryptoboxDriveDistance=0;
            cryptoboxSlide=115;
        }
        else if(cryptoboxKey.equals("CENTER"))
        {
            cryptoboxDriveDistance=0;
            cryptoboxSlide=305;
        }

    }

    @Override public void loop ()
    {
        super.loop();
    } // loop



}
