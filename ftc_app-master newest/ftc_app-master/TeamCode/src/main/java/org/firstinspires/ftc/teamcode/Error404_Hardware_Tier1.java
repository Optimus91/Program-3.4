package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RESET_ENCODERS;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODERS;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODERS;

public class Error404_Hardware_Tier1 extends OpMode {
    protected DcMotor leftFront;
    protected DcMotor rightFront;
    protected DcMotor leftRear;
    protected DcMotor rightRear;
    protected DcMotor ballCollector;
    protected DcMotor balllauncher1;
    protected DcMotor balllauncher2;
    protected ColorSensor RGB;
    protected ColorSensor beacon;
    protected ColorSensor beacon2;
    protected GyroSensor gyro;
    protected TouchSensor touch;
    protected TouchSensor touch2;
    protected Servo leftPush;
    protected Servo rightPush;

    @Override public void init() {
        /////////////////////////////////////////////////////////////////
        /* Attempting a hardware map of the motors, servos, and sensors//
        //      If the device cannot be found in the config file,      //
        //    an error message shows on the driver station telemetry.  //
        *////////////////////////////////////////////////////////////////
        try {
            touch2 = hardwareMap.touchSensor.get("touch2");
        } catch (Exception p_exeception) {
            telemetry.addData("Touch 2 not found in config file", 0);
            touch2 = null;
        }
        try {
            leftPush = hardwareMap.servo.get("leftPush");   ///beacon pusher
        } catch (Exception p_exeception) {
            telemetry.addData("leftPush not found in config file", 0);
            leftPush = null;
        }
        try {
            rightPush = hardwareMap.servo.get("rightPush");
        } catch (Exception p_exeception) {
            telemetry.addData("rightPush not found in config file", 0);
            rightPush = null;
        }
        try {
            balllauncher2 = hardwareMap.dcMotor.get("balllauncher2");
        } catch (Exception p_exeception) {
            telemetry.addData("ball launcher 2 not found in config file", 0);
            balllauncher2 = null;
        }
        try {
            balllauncher1 = hardwareMap.dcMotor.get("balllauncher1");
        } catch (Exception p_exeception) {
            telemetry.addData("ball launcher 1 not found in config file", 0);
            balllauncher1 = null;
        }
        try {
            touch = hardwareMap.touchSensor.get("touch");
        } catch (Exception p_exeception) {
            telemetry.addData("Touch not found in config file", 0);
            touch = null;
        }
        try {
            gyro = hardwareMap.gyroSensor.get("gyro");
        } catch (Exception p_exeception) {
            telemetry.addData("Gyro not found in config file", 0);
            gyro = null;
        }

        try {
            RGB = hardwareMap.colorSensor.get("mr");
        } catch (Exception p_exeception) {
            telemetry.addData("Color Sensor not found in config file", 0);
            RGB = null;
        }
        try {
            beacon = hardwareMap.colorSensor.get("beacon");
        } catch (Exception p_exeception) {
            telemetry.addData("Beacon Color Sensor not found in config file", 0);
            beacon = null;
        }
        try {
            leftFront = hardwareMap.dcMotor.get("leftFront");
        } catch (Exception p_exeception) {
            telemetry.addData("leftFront not found in config file", 0);
            leftFront = null;
        }
        try {
            rightFront = hardwareMap.dcMotor.get("rightFront");
        } catch (Exception p_exeception) {
            telemetry.addData("rightFront not found in config file", 0);
            rightFront = null;
        }
        try {
            leftRear = hardwareMap.dcMotor.get("leftRear");
        } catch (Exception p_exeception) {
            telemetry.addData("leftRear not found in config file", 0);
            leftRear = null;
        }
        try {
            rightRear = hardwareMap.dcMotor.get("rightRear");
        } catch (Exception p_exeception) {
            telemetry.addData("rightRear not found in config file", 0);
            rightRear = null;
        }
        try {
            ballCollector = hardwareMap.dcMotor.get("ballcollector");
        } catch (Exception p_exeception) {
            telemetry.addData("ballcollector not found in config file", 0);
            leftFront = null;
        }
        try {
            beacon2 = hardwareMap.colorSensor.get("beacon2");
        } catch (Exception p_exeception) {
            telemetry.addData("Beacon 2 Color Sensor not found in config file", 0);
            beacon2 = null;
        }
        RGB.setI2cAddress(I2cAddr.create8bit(0x3C));       //30 is the decimal conversion from 7 bit hexadecimal value 0x1e converted from 8 bit hexadecimal 0x3c
        beacon.setI2cAddress(I2cAddr.create8bit(0x2C));
        beacon2.setI2cAddress(I2cAddr.create8bit(0x5C));
        RGB.enableLed(false); //not sure why these are needed here.  Seems to help reset the LEDS so the next enable commands are obeyed.
        beacon.enableLed(false);
        beacon2.enableLed(false);

    }

    public DcMotor convert(int mtr) {
        if (mtr == 1) {
            telemetry.addData("test",mtr);
            return leftFront;
        }
        if (mtr == 2) return rightFront;
        if (mtr == 3) return leftRear;
        if (mtr == 4) return rightRear;
        return null;
    }

    public void start() {}
    public void loop() { }
    public void stop() { }

    ////////////////////////////////////////////
    /*   Methods that return raw data use     //
    //       for decision making and          //
    //  and String versions for printing to   //
    //        Driver's Station phone          //
    *///////////////////////////////////////////
    public double get_power(DcMotor motor)
    {
        double motorReturn = 0;
        if(motor != null)
        {
            motorReturn = motor.getPower();
            return motorReturn;
        }
        return motorReturn;
    }

    public String get_power_tele(DcMotor motor)
    {
        String motorReturn = "";
        if(motor != null){
            motorReturn += motor.getPower();
            return motorReturn;
        }
        motorReturn += "NULL";
        return motorReturn;
    }

    public int get_position(DcMotor motor)
    {
        int motorReturn = 0;
        if (motor != null)
        {
            motorReturn = motor.getCurrentPosition();
            return motorReturn;
        }
        return motorReturn;
    }

    public String get_position_tele(DcMotor motor)
    {
        String motorReturn = "";
        if(motor != null)
        {
            motorReturn += motor.getCurrentPosition();
            return motorReturn;
        }
        motorReturn += "NULL";
        return motorReturn;
    }

    public String get_mode(DcMotor motor) {
        String motorReturn = "";
        if (motor != null) {
            motorReturn += motor.getMode();
            return motorReturn;
        }
        motorReturn += "NULL";
        return motorReturn;
    }

    public String get_direction(DcMotor motor) {
        String motorReturn = "";
        if (motor != null) {
            motorReturn += motor.getDirection();
            return motorReturn;
        }
        motorReturn += "NULL";
        return motorReturn;
    }

    //////////////////////////////////////////
    /*method that checks if the motor has   //
    // reached it's goal if found, else     //
    //          returns false.              //
    */////////////////////////////////////////
//Sorry Josh. I had to change the if(encoderCount==goal) to
// if(encoderCount>=goal) because otherwise, the method has to be called at the exact moment
//the encoder value is at the desired position. ;-)
    public boolean is_encoder_reached(int goal, DcMotor motor)
    {
        int encoderCount = get_position(motor);
        // encoderCount=Math.abs(encoderCount);
        if(encoderCount >= goal)
        {return true;}
        else if((encoderCount > (goal - 10)) && (encoderCount < (goal + 10)))
        {return true;}
        else
        {return false;}
    }

    //////////////////////////////////////////////
    /*  method that checks if motors are reset  //
    //      if found, else returns false        //
    */////////////////////////////////////////////

    public boolean is_encoder_reset(DcMotor motor)
    {
        if(get_position(motor) == 0)
        {return true;}
        else
        {return false;}
    }

    ///////////////////////////////////////////
    /* methods that resets encoders if found, //
    //          else does nothing.           //
    *//////////////////////////////////////////

    public void reset_encoder(DcMotor motor)
    {
        if(motor != null)
        {
            motor.setMode(RESET_ENCODERS);
        }
    }



    ////////////////////////////////////////
    // In these set power methods, the    //
    //method checks to see if the motor   //
    //is null. If so, it skips that motor.//
    //If it is not null, the power is set //
    //to that motor.                      //
    ////////////////////////////////////////
    public void set_power(double power, DcMotor motor){
        if (motor != null) {
            motor.setPower(power);
        }
    }

    ///////////////////////////////////////////////////////
    // This set mode method uses two parameters:         //
    // motor and a 3-4 letter mode abbreviation.         //
    // If the motor is not null, the mode will be set to://
    //RTP= Run to Position       //////////////////////////
    //RUE= Run using encoders    //
    //RWOE= Run without encoders //
    ///////////////////////////////
    public void set_mode(DcMotor motor, String modetoset){
        modetoset=modetoset.toUpperCase();
        if (motor != null){
            if (modetoset.equals("RTP")){
                motor.setMode(RUN_TO_POSITION);
            }
            if (modetoset.equals("RUE")){
                motor.setMode(RUN_USING_ENCODERS);
            }
            if (modetoset.equals("RWOE")){
                motor.setMode(RUN_WITHOUT_ENCODERS);
            }
        }
    }


    ///////////////////////////////////////////////////////////
    //This set direction method takes two parameters: Motor  //
    // and direction. The direction is set as F for forward  //
    // and R for reversed. If the motor is not null, the     //
    //direction is set.                                      //
    ///////////////////////////////////////////////////////////
    public void set_direction(DcMotor motor, String direction) {
        if (motor != null) {
            direction=direction.toLowerCase();
            if (direction.equals("r")) {
                motor.setDirection(DcMotor.Direction.REVERSE);
            }
            if (direction.equals("f")) {
                motor.setDirection(DcMotor.Direction.FORWARD);
            }
        }
    }

    //////////////////////////////////////////
    //This method takes two parameters, one //
    //for the motor and one for the desired //
    //position. It then sets the position   //
    //to the motor if the motor is not null.//
    //////////////////////////////////////////
    public void set_position(DcMotor motor, int position)
    {
        if (motor != null){
            motor.setTargetPosition(position);
        }
    }

    //////////////////////////////////////////////////
    //In this method, you input the desired distance//
    //in inches, the wheel diameter, and the gear   //
    //ratio. This method will then calculate the    //
    //needed number of encoder ticks needed to      //
    //drive the distance input.                     //
    //////////////////////////////////////////////////
    public int distance2encoder(int desiredDistance, double wheel_diameter, double gear_ratio) {
        return (int) ( 1140*(desiredDistance/(((3.14159265)*(wheel_diameter))*gear_ratio)));}

    ///////////////////////////////////
    //This scale motor power method  //
    //takes the raw power input from //
    //joysticks and converts it to   //
    //floats. With these set values, //
    //the motor power will ramp up   //
    //instead of being sudden & jerky//
    //////////////////////////////// //
    public float scale_motor_power (float p_power)
    {
        float l_scale = 0.0f;
        float l_power = Range.clip(p_power, -1, 1);
        float[] l_array =
                { 0.00f, 0.05f, 0.09f, 0.10f, 0.12f
                        , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
                        , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
                        , 1.00f, 1.00f
                };
        int l_index = (int)(l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    }
}