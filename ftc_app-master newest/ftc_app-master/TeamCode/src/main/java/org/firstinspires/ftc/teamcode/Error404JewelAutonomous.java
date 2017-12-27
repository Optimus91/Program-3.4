package org.firstinspires.ftc.teamcode;

public class Error404JewelAutonomous extends Error404_Hardware_Tier2

{
    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private double timer=0;
    protected int cryptoboxDriveDistance;
    // protected int stoneToMarket;
    // protected int turnToFrontCryptobox;
    // protected int turnToRearCryptobox;
    protected int turnToCryptobox;
    protected int cryptoboxSlide;
    // protected int location=0;

    private String fieldSide;
    private String sideLocation;

    public Error404JewelAutonomous()
    {
    }
    @Override public void init(){
        super.init();
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
        arm.setPosition(0);
        swivel.setPosition(0.52);
        glyph.setPosition(0.25);
        encoder=leftFront.getCurrentPosition();

//        if(fieldSide.equals("BLUE")){
//            if(sideLocation.equals("FRONT")) {
//                cryptoboxDriveDistance = 350;
//                stoneToMarket = 0;
//                cryptoboxSlide=0;
//                turnToRearCryptobox=0;
//                turnToFrontCryptobox=85;
//                location=0;
//                driveStraight("RUE",0,"r",0);
//            }
//            if(sideLocation.equals("REAR")){
//                cryptoboxDriveDistance = 0;
//                stoneToMarket = 600;
//                cryptoboxSlide=320;
//                turnToRearCryptobox=170;
//                turnToFrontCryptobox=0;
//                location=1;
//                driveStraight("RUE",0,"r",0);
//            }
//        }
//        if(fieldSide.equals("RED")){
//            if(sideLocation.equals("FRONT")) {
//                cryptoboxDriveDistance = 370;
//                stoneToMarket = 0;
//                cryptoboxSlide=0;
//                turnToRearCryptobox=0;
//                turnToFrontCryptobox=85;
//                location=2;
//                driveStraight("RUE",0,"f",0);
//            }
//            if(sideLocation.equals("REAR")){
//                cryptoboxDriveDistance = 0;
//                stoneToMarket = 560;
//                cryptoboxSlide=305;
//                turnToRearCryptobox=0;
//                turnToFrontCryptobox=0;
//                location=3;
//                driveStraight("RUE",0,"f",0);
//            }
//        }
        encoder=leftFront.getCurrentPosition();
    }
    @Override public void start(){
        super.start();
    }

    public void setLocation(String frontOrBack, String redOrBlue){
        fieldSide = redOrBlue;
        sideLocation = frontOrBack;
    }

    protected boolean updateFromVuforia(String cryptoboxKey)
    {
        return true;
    }


    @Override public void loop ()
    {
        switch (state)
        {
            case 0: // Lower Arm
                arm.setPosition(0.75);
                    state++;
                break;

            case 1:  //Find Jewel
                timer =getRuntime();
                if(camera.getVoltage()<1.2){
                    telemetry.addData("On left","");
                    if(fieldSide.equals("BLUE")){
                        state=3;
                    }
                    if(fieldSide.equals("RED")){
                        state=2;
                    }
                    break;
                }
                else if(camera.getVoltage()>1.2){
                    telemetry.addData("On right","");
                    if(fieldSide.equals("BLUE")){
                        state=2;
                    }
                    if(fieldSide.equals("RED")){
                        state=3;
                    }
                    break;
                }

            case 2:  //Swivel Right
                if(((int)(getRuntime()-timer))>1) {
                    swivel.setPosition(0.7);
                    state=4;
                    timer =getRuntime();
                }
                break;

            case 3:  //Swivel Left
                if(((int)(getRuntime()-timer))>1) {
                    swivel.setPosition(0.4);
                    state=4;
                    timer =getRuntime();
                }
                break;

            case 4:  //Reset Arm
                if(((int)(getRuntime()-timer))>1) {
                    arm.setPosition(0);
                    swivel.setPosition(0.5);
                    state=6;
                    timer =getRuntime();
                }
                break;

            case 6:  //Read Pictograph
                if(updateFromVuforia(readCryptograph()))
                {
                    state= 7;
                }


                if(((int)(getRuntime()-timer))>2)
                {
                    updateFromVuforia("CENTER");
                    state=7;
                }
                break;


            case 7:  //Drive to Cryptobox
                if(cryptoboxDriveDistance!=0)
                {
                    //driveStraight("RUE", 0.3, "f", 0);
                    if(cryptoboxDriveDistance>0)
                    {
                        driveStraightCombo(0.3);
                    }
                    else
                    {
                        driveStraightCombo(-0.3);
                    }

                    if (leftFront.getCurrentPosition() - encoder > Math.abs(cryptoboxDriveDistance)) {
                        //slide_sideways("RUE", 0, "l", 0);
                        stopEverything();
                        setMultipleDirections("turn", "right");
                        state++;
                        encoder = leftFront.getCurrentPosition();
                    }
                }
                else
                {
                    setMultipleDirections("turn", "right");
                    state++;
                    encoder = leftFront.getCurrentPosition();
                }

                break;

            case 8:  //Face Cryptobox
                if(turnToCryptobox!=0)
                {
                    pointTurn("RUE", 0.3, "r", 0);
                    if (Math.abs(getHeading()) > turnToCryptobox) {
                        state++;
                        stopEverything();
                        setMultipleDirections("straight", "forward");
                        encoder = leftFront.getCurrentPosition();
                    }
                }
                else
                {
                    setMultipleDirections("straight", "forward");
                    state++;
                    encoder = leftFront.getCurrentPosition();
                }
                break;

            case 9:  // Slide to Cryptobox
                if(cryptoboxSlide!=0)
                {
                    if(cryptoboxSlide>0)
                    {
                        slide_sideways("RUE",0.3,"r",0);
                    }
                    else
                    {
                        slide_sideways("RUE",0.3,"l",0);
                    }

                    if(leftFront.getCurrentPosition()-encoder>cryptoboxSlide) {
                        stopEverything();
                        setMultipleDirections("straight", "forward");
                        state++;
                        encoder=leftFront.getCurrentPosition();
                    }
                }
                else
                {
                    setMultipleDirections("straight", "forward");
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;

            case 10:  //Drive into Cryptobox
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder>150) {
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;

            case 11:  //Deploy Glyph
                glyph.setPosition(0.75);
                timer =getRuntime();
                state++;
                break;

            case 12:  //Wait
                if(((int)(getRuntime()-timer))>4){
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;

            case 13:  //Finish Glyph
                glyph.setPosition(0.75);
                driveStraight("RUE",0.1,"r",0);
                if(leftFront.getCurrentPosition()-encoder>90) {
                    driveStraight("RUE",0,"f",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            default:
                break;


        }
        telemetry.addData("1. State: ", state);
        telemetry.addData("2. Gyro: ", getHeading());
        telemetry.addData("3. Camera:  ", camera.getVoltage());
        telemetry.addData("4. Left Front Position: ", leftFront.getCurrentPosition());
        telemetry.addData("5. Delta Position: ", encoder);
        telemetry.addData("Pattern: ", readCryptograph());
//        switch (location){
//            case 0:
//                telemetry.addData("Blue Front","");
//                break;
//            case 1:
//                telemetry.addData("Blue Rear","");
//                break;
//            case 2:
//                telemetry.addData("Red Front","");
//                break;
//            case 3:
//                telemetry.addData("Red Rear","");
//                break;
//        }



    } // loop
} //
