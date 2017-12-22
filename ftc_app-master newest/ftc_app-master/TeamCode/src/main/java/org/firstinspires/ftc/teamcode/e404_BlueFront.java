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
        cryptoboxDriveDistance = 350;
       // stoneToMarket = 0;
        cryptoboxSlide=0;
       // turnToRearCryptobox=0;
       // turnToFrontCryptobox=85;
        turnToCryptobox=0;
       // location=0;
        driveStraight("RUE",0,"r",0);
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
