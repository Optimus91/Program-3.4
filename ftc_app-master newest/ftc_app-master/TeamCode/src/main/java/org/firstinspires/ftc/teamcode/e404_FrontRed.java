package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Front", group="Jewel")

public class e404_FrontRed extends Error404JewelAutonomous

{
    ///////////////////////////////////////////////////////////////////

    public e404_FrontRed()
    {
        setLocation("FRONT", "RED");
    }
    @Override public void init()
    {
        cryptoboxDriveDistance = 370;
       // stoneToMarket = 0;
        cryptoboxSlide=0;
       // turnToRearCryptobox=0;
       // turnToFrontCryptobox=85;
        turnToCryptobox=0;
       // location=2;
        driveStraight("RUE",0,"f",0);
        super.init();  //super.init() method is moved to bottom to not get in the way of the driveStraight() method
    }
    @Override public void start(){
        super.start();
    }

    @Override public void loop ()
    {
        super.loop();
    } // loop

}
