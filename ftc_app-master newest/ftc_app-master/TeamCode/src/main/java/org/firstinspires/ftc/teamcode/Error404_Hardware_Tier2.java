package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Error404_Hardware_Tier2 extends Error404_Hardware_Tier1 { //VERSION 2.1.2

    /// Beacon pusher method  //
    public void setServoPos(Servo servomotor, Double position){
        servomotor.setPosition(position);
    }

    public void gyroCalibrate()
    {
        double before = gyro.getHeading();
        gyro.calibrate();
        while (gyro.getHeading() != 0) {
            telemetry.addData("Gyro: ", gyro.getHeading());
        }
        telemetry.addData("Gyro Calibrated", "");
        telemetry.addData("Before: ", before);
        telemetry.addData("After: ", gyro.getHeading());

    }
    public void driveStright(String mode, double power, String direction, int position) {
        position=distance2encoder(position,6,1);
        if (direction.toLowerCase().equals("f")) {
            set_direction(leftFront, "f");
            set_direction(leftRear, "f");
            set_direction(rightFront, "r");
            set_direction(rightRear, "r");
        } else {
            set_direction(leftFront, "r");
            set_direction(leftRear, "r");
            set_direction(rightFront, "f");
            set_direction(rightRear, "f");
        }
        set_mode(leftFront, mode);
        set_mode(leftRear, mode);
        set_mode(rightFront, mode);
        set_mode(rightRear, mode);
        set_position(leftFront, position);
        set_position(leftRear,position);
        set_position(rightFront,position);
        set_position(rightRear,position);
        left_set_power(power);
        right_set_power(power);

    }
    public void left_set_power(double power)
    {
        set_power(power, leftFront);
        set_power(power, leftRear);
    }

    public void right_set_power(double power)
    {
        set_power(power, rightFront);
        set_power(power, rightRear);
    }
    public void resetAllEncoders_withWait(){
        int count=0;
        reset_encoder(rightFront);
        reset_encoder(rightRear);
        reset_encoder(leftFront);
        reset_encoder(leftRear);
        while (get_position(rightFront)!= 0 && get_position(rightRear)!= 0 && get_position(leftFront)!= 0 && get_position(leftRear)!= 0){
            count++;
            telemetry.addData("count: ", count);
        }
    }
    public void resetAllEncoders_noWait(){
        reset_encoder(rightFront);
        reset_encoder(rightRear);
        reset_encoder(leftFront);
        reset_encoder(leftRear);
    }


    //Direction is either l "L" for left or r for right, instead of F for forward and B for backward
    public void pointTurn(String mode, double power, String direction, int position){
        position=distance2encoder(position,6,1);
        if (direction.toLowerCase().equals("r")) {
            set_direction(leftFront, "f");
            set_direction(leftRear, "f");
            set_direction(rightFront, "f");
            set_direction(rightRear, "f");
        } else {
            set_direction(leftFront, "r");
            set_direction(leftRear, "r");
            set_direction(rightFront, "r");
            set_direction(rightRear, "r");
        }
        //sets mode to what is sent in with the "mode" parameter
        set_mode(leftFront, mode);
        set_mode(leftRear, mode);
        set_mode(rightFront, mode);
        set_mode(rightRear, mode);
        //sets target position to parameter "position"
        set_position(leftFront, position);
        set_position(leftRear,position);
        set_position(rightFront,position);
        set_position(rightRear,position);

        left_set_power(power);
        right_set_power(power);
    }

//Unused at current time
    /*
    public void swing_turn(String mode, double powerLeft, double powerRight, String direction, int position)
        {
            position = distance2encoder(position, 6, 1);
            set_direction(leftFront, "f");
            set_direction(leftRear, "f");
            set_direction(rightFront, "r");
            set_direction(rightRear, "r");
            set_mode(leftFront, mode);
            set_mode(leftRear, mode);
            set_mode(rightFront, mode);
            set_mode(rightRear, mode);
            if(direction.toLowerCase().equals("r"))
                {
                    set_position(rightFront, position);
                    set_position(rightRear, position);
                    double temp = powerLeft - powerRight;
                    temp += 1;
                    position *= temp;
                    set_position(leftFront, position);
                    set_position(leftRear, position);
                } else {
                    set_position(leftFront, position);
                    set_position(leftRear, position);
                    double temp = powerRight - powerLeft;
                    temp += 1;
                    position *= temp;
                    set_position(rightFront, position);
                    set_position(rightRear, position);
                }
            left_set_power(powerLeft);
            right_set_power(powerRight);
    }
*/
    //Unused at current time
    /*
    public void pivot_turn(String mode, double power, String direction, int position){
        position = distance2encoder(position, 6, 1);
        set_direction(leftFront, "f");
        set_direction(leftRear, "f");
        set_direction(rightFront, "r");
        set_direction(rightRear, "r");
        set_mode(leftFront, mode);
        set_mode(leftRear, mode);
        set_mode(rightFront, mode);
        set_mode(rightRear, mode);
        if (direction.toLowerCase().equals("l")) {
            set_position(rightFront, position);
            set_position(rightRear, position);
            right_set_power(power);
        } else {
            set_position(leftFront, position);
            set_position(leftRear, position);
            left_set_power(power);
        }
    }
    */

    public void slide_sideways(String mode, double power, String direction, int position){
        position = distance2encoder(position, 4, 1);
        position=position*2; //because the wheels on the meccanum wheels are at 45', multiply the encoder counts by 2
        set_mode(leftFront, mode);
        set_mode(leftRear, mode);
        set_mode(rightFront, mode);
        set_mode(rightRear, mode);
        if (direction.toLowerCase().equals("r")) {
            set_direction(leftFront, "f");
            set_direction(rightRear, "r");
            set_direction(rightFront, "f");
            set_direction(leftRear, "r");
            set_position(rightFront, position);
            set_position(rightRear, position);
            set_position(leftFront, position);
            set_position(leftRear, position);
            set_power(power, rightRear);
            set_power(power, rightFront);
            set_power(power, leftFront);
            set_power(power, leftRear);

        } else if (direction.toLowerCase().equals("l")) {  // added else tim
            set_direction(leftFront, "r");
            set_direction(rightRear, "f");
            set_direction(rightFront, "r");
            set_direction(leftRear, "f");
            set_position(rightFront, position);
            set_position(rightRear, position);
            set_position(leftFront, position);
            set_position(leftRear, position);
            set_power(power, rightRear);
            set_power(power, rightFront);
            set_power(power, leftFront);
            set_power(power, leftRear);
        }
    }

    public void slide_sideways_gyro(String mode, double power, String direction, int zeropoint){
        int maxDrift=5;
        int drift=0;
        int current=gyro.getHeading();
        set_mode(leftFront, mode);
        set_mode(leftRear, mode);
        set_mode(rightFront, mode);
        set_mode(rightRear, mode);
        if (direction.toLowerCase().equals("r")) {
            drift=(zeropoint-current);
            telemetry.addData("Drift in direction R: ",drift);
            if(Math.abs(drift)>=maxDrift){
                telemetry.addData("Max Drift Achieved","");
                left_set_power(0);
                right_set_power(0);
                if(drift>0 && current<180){
                    turn_gyro_power_new(zeropoint,0.2, 0.6, "r");
                    telemetry.addData("adjusting right","");
                }else {
                    turn_gyro_power_new(zeropoint,0.2, 0.6, "l");
                    telemetry.addData("adjusting left","");
                }
            }
            else {
                set_direction(leftFront, "f");
                set_direction(rightRear, "r");
                set_direction(rightFront, "f");
                set_direction(leftRear, "r");
                set_power(power, rightRear);
                set_power(power, rightFront);
                set_power(power, leftFront);
                set_power(power, leftRear);
            }

        } else {       // added else tim
            drift=(zeropoint-current);
            telemetry.addData("Drift in direction L: ",drift);
            if(Math.abs(drift)>=maxDrift){
                telemetry.addData("Max Drift Achieved","");
                left_set_power(0);
                right_set_power(0);
                if(drift>0 && current>180){
                    turn_gyro_power_new(zeropoint,0.2, 0.6, "l");
                    telemetry.addData("adjusting left","");
                }else {
                    turn_gyro_power_new(zeropoint,0.2, 0.6, "r");
                    telemetry.addData("adjusting right","");
                }
            }
            else {
                set_direction(leftFront, "r");
                set_direction(rightRear, "f");
                set_direction(rightFront, "r");
                set_direction(leftRear, "f");
                set_power(power, rightRear);
                set_power(power, rightFront);
                set_power(power, leftFront);
                set_power(power, leftRear);
            }
        }
    }

    public void turn_gyro_power_new(int desired_gyro, double starting_power, double fraction_to_change_power, String direction){
        double powervalue=0;
        int heading = gyro.getHeading();
        double last_part=(desired_gyro*fraction_to_change_power);
        if(direction.toLowerCase().equals("r")) {
            if (heading <= last_part) {
                powervalue = starting_power;
            }
            else if (heading> last_part) {
                powervalue = (desired_gyro - heading) / 50;
            }

            if(powervalue < 0.03) {
                powervalue = 0.03;
            }

            pointTurn("RUE", powervalue, "r", 0); //turn towards line
        } // end if direction = r
        else {
            if (heading>180)
            {
                heading=-360+heading;
            }
            telemetry.addData("Left Turn Heading: ",heading);

            if (heading >= -last_part) {
                powervalue = starting_power;
            }
            else// if (heading < -last_part)
            {
                powervalue = (desired_gyro - Math.abs(heading)) / 50;
            }

            if (powervalue < 0.03) {
                powervalue = 0.03;
            }

            pointTurn("RUE", powervalue, "l", 0); //turn towards line
        } // end if direction = l

    }

    public void turn_gyro_power(int desired_gyro, double starting_power, double fraction_to_change_power, String direction){
        double powervalue=0;
        double last_part=(desired_gyro*fraction_to_change_power);
        if(direction.toLowerCase()=="r") {
            if (gyro.getHeading() <= last_part) {
                powervalue = starting_power;
            }
            if (gyro.getHeading() > last_part && gyro.getHeading() < desired_gyro) {
                powervalue = (desired_gyro - gyro.getHeading()) / 200;

            }
            if(powervalue <0.03) {
                powervalue = 0.03;
            }

            pointTurn("RUE", powervalue, "r", 0); //turn towards line
        } // end if direction = r
        if(direction.toLowerCase()=="l") {
            int heading = gyro.getHeading();
            if (heading>180)
            {
                heading=-360+heading;
            }
            if (heading > last_part) {
                powervalue = starting_power;
            }
            if (heading < last_part && heading > desired_gyro) {
                powervalue = (desired_gyro - gyro.getHeading()) / 200;
            }
            if (powervalue < 0.03) {
                powervalue = 0.03;
            }

            pointTurn("RUE", powervalue, "l", 0); //turn towards line
        } // end if direction = l

    }

    public double ramp_up(double powerBegin, double powerEnd, double powerToWrite){
        if (powerBegin<powerEnd)
        {
            powerToWrite+=0.001;
        }
        else if (powerBegin>powerEnd)
        {
            powerToWrite-=0.001;
        }
        else if (powerEnd==0)
        {
            powerToWrite=0;
        }
        return powerToWrite;
    }

    double rampUpMethod (double motorOut, double analogIn, double slewRate) {
        if (slewRate < (Math.abs(motorOut - analogIn))) {
            if (motorOut - analogIn < 0) return (motorOut + slewRate);
            else return (motorOut - slewRate);
        }
        else return analogIn;
    }

    public void motorTelemetry(DcMotor motor)
    {
        if(motor != null)
        {
            telemetry.addData("00", get_direction(motor));
            telemetry.addData("01", get_mode(motor));
            telemetry.addData("02", get_power_tele(motor));
            telemetry.addData("03", get_position_tele(motor));
        }
    }

}