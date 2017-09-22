package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

public class ColorDisplaytest extends Error404_Hardware_Tier2

{
  ///////////////////////////////////////////////////////////////////
    double rightVal=0.15;
    double leftVal=0.15;

    public ColorDisplaytest()
  {
  }
   @Override public void init(){
       super.init();
       RGB.enableLed(true); //not sure why these are needed here.  Seems to help reset the LEDS so the next enable commands are obeyed.
       setServoPos(rightPush, rightVal);  // initialize servos to initial positions
       setServoPos(leftPush, leftVal);

   }
  @Override public void start(){
     RGB.enableLed(false); //not sure why these are needed here.  Seems to help reset the LEDS so the next enable commands are obeyed.
      beacon.enableLed(false);
    //  float[] hsvValues = {0F, 0F, 0F};
   //   final float values[] = hsvValues;


  }


  @Override public void loop ()
  {
    RGB.enableLed(true);
    beacon.enableLed(false);
      float[] hsvValues = {0F, 0F, 0F};
      Color.RGBToHSV(beacon.red()*8, beacon.green()*8, beacon.blue()*8, hsvValues);
      telemetry.addData("2. Red out: ", beacon.red());
      telemetry.addData("3. Blue out: ", beacon.blue());
      telemetry.addData("4. Green out: ", beacon.green());
      telemetry.addData("5. White out: ", beacon.alpha());

      telemetry.addData("6: Hue: ", hsvValues[0]);
      telemetry.addData("7: Saturation: ", hsvValues[1]);
      telemetry.addData("8: Value: ", hsvValues[2]);

  } // loop
} //