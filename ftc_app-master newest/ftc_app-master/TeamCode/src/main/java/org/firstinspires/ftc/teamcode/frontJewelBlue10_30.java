//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.hardware.DcMotor;
//@Autonomous(name="Shoot Ball Center", group="Center")
//
//public class frontJewelBlue10_30 extends Error404_Hardware_Tier2
//
//{
//    ///////////////////////////////////////////////////////////////////
//    private int state = 0;
//    private int encoder=0;
//    private int test=0;
//    public frontJewelBlue10_30()
//    {
//    }
//    @Override public void init(){
//        super.init();
//        telemetry.addData("Out Red: ", beacon.red());
//        telemetry.addData("Out Blue: ", beacon.blue());
//        gyroCalibrate();
//        telemetry.addData("Gyro: ", gyro.getHeading());
//        telemetry.addData("","V 1");
//        rightPush.setPosition(0.5);
//        leftPush.setPosition(0.5);
//
//    }
//    @Override public void start(){
//        resetAllEncoders_withWait();
//        //gyroCalibrate();
//        RGB.enableLed(false); //not sure why these are needed here.  Seems to help reset the LEDS so the next enable commands are obeyed.
//        beacon.enableLed(false);
//
//
//    }
//    @Override public void loop ()
//    {
//        switch (state)
//        {
//            case 0:
//                balllauncher1.setDirection(DcMotor.Direction.FORWARD);
//                balllauncher2.setDirection(DcMotor.Direction.REVERSE);
//                ballCollector.setDirection(DcMotor.Direction.REVERSE);
//
//                encoder=leftFront.getCurrentPosition();
//                state++;
//                break;
//            case 1:
//                driveStright("RUE",0.05,"r",0);
//                if (is_encoder_reached((200+encoder), leftFront)) {
//                    driveStright("RUE", 0, "f", 0);
//                    state++;
//                    encoder=balllauncher1.getCurrentPosition();
//                }
//            case 2:
//                balllauncher1.setPower(0.55);
//                balllauncher2.setPower(0.55);
//                if (is_encoder_reached((4000+encoder), balllauncher1)) {
//                    state++;
//                }
//                break;
//            case 3:
//                ballCollector.setPower(0.2);
//
//                if (is_encoder_reached((5000+encoder), balllauncher1)) {
//                    state++;
//                }
//                break;
//            case 4:
//                ballCollector.setPower(0);
//                encoder=balllauncher1.getCurrentPosition();
//                state++;
//                break;
//            case 5:
//                balllauncher1.setPower(0.55);
//                balllauncher2.setPower(0.55);
//                if (is_encoder_reached((3000+encoder), balllauncher1)) {
//                    state++;
//                }
//                break;
//            case 6:
//                ballCollector.setPower(0.2);
//
//                if (is_encoder_reached((5000+encoder), balllauncher1)) {
//                    state++;
//                }
//                break;
//            case 7:
//                ballCollector.setPower(0);
//                balllauncher1.setPower(0);
//                balllauncher2.setPower(0);
//                state++;
//                break;
//            case 8:
//                driveStright("RUE",0.2,"r",0);
//
//                if (is_encoder_reached(3000, leftFront)) {
//
//                    state++;
//                    encoder=leftFront.getCurrentPosition();
//                }
//                break;
//            case 9:
//                set_power(0,rightFront);
//                set_power(0,leftFront);
//                set_power(0,rightRear);
//                set_power(0,leftRear);
//                state++;
//                break;
//            default:
//                break;
//
//
//        }
//        telemetry.addData("1. State: ", state);
//        telemetry.addData("2. loops: ", test);
//        telemetry.addData("3. Gyro: ", gyro.getHeading());
//    } // loop
//} //
