package org.firstinspires.ftc.teamcode;

public class Error404JewelAutonomous extends Error404_Hardware_Tier2

{
    ///////////////////////////////////////////////////////////////////
    private int state = 0;
    private int encoder=0;
    private double timer=0;
    private int cryptoboxDriveDistance;
    private int stoneToMarket;
    private int turnToFrontCryptobox;
    private int turnToRearCryptobox;
    private int cryptoboxSlide;
    private int location=0;

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
        if(fieldSide.equals("BLUE")){
            if(sideLocation.equals("FRONT")) {
                cryptoboxDriveDistance = 350;
                stoneToMarket = 0;
                cryptoboxSlide=0;
                turnToRearCryptobox=0;
                turnToFrontCryptobox=85;
                location=0;
                driveStraight("RUE",0,"r",0);
            }
            if(sideLocation.equals("REAR")){
                cryptoboxDriveDistance = 0;
                stoneToMarket = 600;
                cryptoboxSlide=320;
                turnToRearCryptobox=170;
                turnToFrontCryptobox=0;
                location=1;
                driveStraight("RUE",0,"r",0);
            }
        }
        if(fieldSide.equals("RED")){
            if(sideLocation.equals("FRONT")) {
                cryptoboxDriveDistance = 370;
                stoneToMarket = 0;
                cryptoboxSlide=0;
                turnToRearCryptobox=0;
                turnToFrontCryptobox=85;
                location=2;
                driveStraight("RUE",0,"f",0);
            }
            if(sideLocation.equals("REAR")){
                cryptoboxDriveDistance = 0;
                stoneToMarket = 560;
                cryptoboxSlide=305;
                turnToRearCryptobox=0;
                turnToFrontCryptobox=0;
                location=3;
                driveStraight("RUE",0,"f",0);
            }
        }
        encoder=leftFront.getCurrentPosition();
    }
    @Override public void start(){
        super.start();
    }

    public void setLocation(String frontOrBack, String redOrBlue){
        fieldSide = redOrBlue;
        sideLocation = frontOrBack;
}

    @Override public void loop ()
    {
        switch (state)
        {
            case 0:
                arm.setPosition(0.75);
                    state++;
                break;
            case 1:
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
            case 2:
                if(((int)(getRuntime()-timer))>1) {
                    swivel.setPosition(0.7);
                    state=4;
                    timer =getRuntime();
                }
                break;
            case 3:
                if(((int)(getRuntime()-timer))>1) {
                    swivel.setPosition(0.4);
                    state=4;
                    timer =getRuntime();
                }
                break;
            case 4:
                if(((int)(getRuntime()-timer))>1) {
                    arm.setPosition(0);
                    swivel.setPosition(0.5);
                    state=6;
                    timer =getRuntime();
                }
                break;
            case 6:
                if(readCryptograph().equals("LEFT")){
                    switch (location){
                        case 0:
                            cryptoboxDriveDistance=110;
                            cryptoboxSlide=0;
                            break;
                        case 1:
                            cryptoboxDriveDistance=0;
                            cryptoboxSlide=135;
                            break;
                        case 2:
                            cryptoboxDriveDistance=130;
                            cryptoboxSlide=0;
                            break;
                        case 3:
                            cryptoboxDriveDistance=0;
                            cryptoboxSlide=460;
                            break;
                    }
                    state=7;
                }
                else if(readCryptograph().equals("RIGHT")){
                    switch (location){
                        case 0:
                            cryptoboxDriveDistance=475;
                            cryptoboxSlide=0;
                            break;
                        case 1:
                            cryptoboxDriveDistance=0;
                            cryptoboxSlide=500;
                            break;
                        case 2:
                            cryptoboxDriveDistance=475;
                            cryptoboxSlide=0;
                            break;
                        case 3:
                            cryptoboxDriveDistance=0;
                            cryptoboxSlide=115;
                            break;
                    }
                    state=7;
                }
                else if(readCryptograph().equals("CENTER")){
                    switch (location){
                        case 0:
                            cryptoboxDriveDistance=305;
                            cryptoboxSlide=0;
                            break;
                        case 1:
                            cryptoboxDriveDistance=0;
                            cryptoboxSlide=318;
                            break;
                        case 2:
                            cryptoboxDriveDistance=300;
                            cryptoboxSlide=0;
                            break;
                        case 3:
                            cryptoboxDriveDistance=0;
                            cryptoboxSlide=305;
                            break;
                    }
                    state=7;
                }
                if(((int)(getRuntime()-timer))>2) {
                    switch (location){
                        case 0:
                            cryptoboxDriveDistance=300;
                            cryptoboxSlide=0;
                            break;
                        case 1:
                            cryptoboxDriveDistance=0;
                            cryptoboxSlide=318;
                            break;
                        case 2:
                            cryptoboxDriveDistance=300;
                            cryptoboxSlide=0;
                            break;
                        case 3:
                            cryptoboxDriveDistance=0;
                            cryptoboxSlide=305;
                            break;
                    }
                    state=7;
                }
                break;
            case 7:
                if(cryptoboxDriveDistance>0) {
                    switch (location){
                        case 0:
                            driveStraight("RUE", 0.3, "r", 0);
                            if (leftFront.getCurrentPosition() - encoder > 400 + cryptoboxDriveDistance) {
                                pointTurn("RUE", 0, "r", 0);
                                state++;
                                encoder = leftFront.getCurrentPosition();
                            }
                            break;
                        case 2:
                            driveStraight("RUE", 0.3, "f", 0);
                            if (leftFront.getCurrentPosition() - encoder > 400 + cryptoboxDriveDistance) {
                                pointTurn("RUE", 0, "l", 0);
                                state++;
                                encoder = leftFront.getCurrentPosition();
                            }
                            break;
                    }
                }
                else{
                    switch (location){
                        case 1:
                            driveStraight("RUE", 0.3, "r", 0);
                            if (leftFront.getCurrentPosition() - encoder > stoneToMarket) {
                                pointTurn("RUE", 0, "r", 0);
                                state++;
                                encoder = leftFront.getCurrentPosition();
                            }
                            break;
                        case 3:
                            driveStraight("RUE", 0.3, "f", 0);
                            if (leftFront.getCurrentPosition() - encoder > stoneToMarket) {
                                slide_sideways("RUE", 0, "l", 0);
                                state++;
                                encoder = leftFront.getCurrentPosition();
                            }
                            break;
                    }
                }
                break;
            case 8:
                if(sideLocation.equals("FRONT")){
                    pointTurn("RUE",0.3,"r",0);
                    if(Math.abs(getHeading())>turnToFrontCryptobox){
                        state++;
                        driveStraight("RUE",0,"f",0);
                        encoder=leftFront.getCurrentPosition();
                    }
                }
                    if(location==1){
                        pointTurn("RUE",0.3,"r",0);
                        if(Math.abs(getHeading())>turnToRearCryptobox){
                            state++;
                            driveStraight("RUE",0,"f",0);
                            encoder=leftFront.getCurrentPosition();
                        }
                    }
                    if(location==3){
                        state++;
                    }
                        break;
            case 9:
                if(location==3){
                    slide_sideways("RUE",0.3,"l",0);
                    if(leftFront.getCurrentPosition()-encoder>cryptoboxSlide) {
                        driveStraight("RUE",0,"f",0);
                        state++;
                        encoder=leftFront.getCurrentPosition();
                    }
                }
                else if(location==12){
                    slide_sideways("RUE",0.3,"r",0);
                    if(leftFront.getCurrentPosition()-encoder>cryptoboxSlide) {
                        driveStraight("RUE",0,"f",0);
                        state++;
                        encoder=leftFront.getCurrentPosition();
                    }
                }
                else{
                    state++;
                }
                break;
            case 10:
                driveStraight("RUE",0.3,"f",0);
                if(leftFront.getCurrentPosition()-encoder>150) {
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 11:
                glyph.setPosition(0.75);
                timer =getRuntime();
                state++;
                break;
            case 12:
                if(((int)(getRuntime()-timer))>4){
                    driveStraight("RUE",0,"r",0);
                    state++;
                    encoder=leftFront.getCurrentPosition();
                }
                break;
            case 13:
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
        switch (location){
            case 0:
                telemetry.addData("Blue Front","");
                break;
            case 1:
                telemetry.addData("Blue Rear","");
                break;
            case 2:
                telemetry.addData("Red Front","");
                break;
            case 3:
                telemetry.addData("Red Rear","");
                break;
        }



    } // loop
} //
