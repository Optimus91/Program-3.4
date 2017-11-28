package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;


public class Drivetrain {

	protected DcMotor leftFront;
	protected DcMotor rightFront;
	protected DcMotor leftRear;
	protected DcMotor rightRear;
    protected IntegratingGyroscope gyro;
    protected NavxMicroNavigationSensor navxMicro;
    protected int wheelDiam;
    protected double initialPosition;
    protected double finalPosition;

    static final int ROT_DIAM = 4;
    static final int STRAFE_DIAM = 4;
    static final int GEAR_RATIO = 1;
    static final int ENCODER_TICKS_PER_ROT = 280;

	public  Drivetrain(HardwareMap hardwareMap, Telemetry telemetry)
    {
        wheelDiam = ROT_DIAM;
        try {
            navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
            gyro = (IntegratingGyroscope)navxMicro;
        } catch (Exception p_exeception) {
            telemetry.addData("navx not found in config file", 0);
            navxMicro = null;
        }
		try {
			leftFront = hardwareMap.dcMotor.get("leftFront");
			if ( leftFront != null )
			{
			    leftFront.setMode(RUN_USING_ENCODER );
			}
		} catch (Exception p_exeception) {
			telemetry.addData("leftFront not found in config file", 0);
			leftFront = null;
		}
		try {
			rightFront = hardwareMap.dcMotor.get("rightFront");
			if ( rightFront!= null  )
			{
			    rightFront.setMode(RUN_USING_ENCODER);
			}

		} catch (Exception p_exeception) {
			telemetry.addData("rightFront not found in config file", 0);
			rightFront = null;
		}
		try {
			leftRear = hardwareMap.dcMotor.get("leftRear");
			if( leftRear != null )
			{
			    leftRear.setMode(RUN_USING_ENCODER);
			}
		} catch (Exception p_exeception) {
			telemetry.addData("leftRear not found in config file", 0);
			leftRear = null;
		}
		try {
			rightRear = hardwareMap.dcMotor.get("rightRear");
			if ( rightRear != null )
			{
			    rightRear.setMode(RUN_USING_ENCODER);
            }
		} catch (Exception p_exeception) {
			telemetry.addData("rightRear not found in config file", 0);
			rightRear = null;
		}

	}

	public int getHeading()
    {
		Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
		return (int)AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle));
	}

	public double getPosition()
    {
        double distance;
        distance = encoder2Distance(leftFront.getCurrentPosition());

        if ( leftFront.getDirection() == DcMotor.Direction.REVERSE )
        {
            distance *= -1.0;
        }
        return distance;
    }

    public void driveStraight( int distance )
    {
        driveStraightAtPower( distance, 0.3 );
    }

	public void driveStraightAtPower( int distance, double power )
    {
        wheelDiam = ROT_DIAM;
        String dir = "F";
        if ( distance < 0)
        {
            dir = "B";
            //distance = Math.abs( distance );
        }
        //finalPosition = encoder2Distance(leftFront.getCurrentPosition()) + distance;
        driveImpl( distance, power, dir);
    }


    public void pivotTurnCW( double targetAngle, double power )
    {
        wheelDiam = ROT_DIAM;
        finalPosition = targetAngle;
        driveImpl( 0, power, "CW");
    }

    public void pivotTurnCCW( double targetAngle, double power )
    {
        wheelDiam = ROT_DIAM;
        finalPosition = targetAngle;
        driveImpl( 0, power, "CCW");
    }

    public void slideLeft( double distance, double power )
    {
        wheelDiam = STRAFE_DIAM;
        driveImpl( distance, power, "L");
    }

    public void slideRight( double distance, double power )
    {
        wheelDiam = STRAFE_DIAM;
        driveImpl( distance, power, "R");

    }

    public void stop()
    {
        setPower( 0 );
    }

    public boolean finishedDriving()
    {
        int busyCount = 0;

        //if (leftFront  != null && leftFront.isBusy() ){ busyCount++; }
        //if (rightFront != null && rightFront.isBusy() ){ busyCount++; }
        //if (leftRear   != null && leftRear.isBusy() ){ busyCount++; }
        //if (rightRear  != null && rightRear.isBusy() ){ busyCount++; }

        boolean positionReached = Math.abs( getPosition() - finalPosition ) < 0.25;

        return (busyCount == 0) && positionReached;
    }

    public boolean finishedPivoting()
    {
        int busyCount = 0;

        if (leftFront  != null && leftFront.isBusy() ){ busyCount++; }
        if (rightFront != null && rightFront.isBusy() ){ busyCount++; }
        if (leftRear   != null && leftRear.isBusy() ){ busyCount++; }
        if (rightRear  != null && rightRear.isBusy() ){ busyCount++; }

        boolean  positionReached = Math.abs( getHeading() - finalPosition ) < 0.25;

        return (busyCount == 0) && positionReached;

    }

    protected void driveImpl( double distance, double power, String direction )
    {
        initialPosition = encoder2Distance( leftFront.getCurrentPosition() );
        finalPosition = initialPosition + distance;

	    int encoderPosition = distance2Encoder( distance );
        setDirection( direction );
        encoderPosition=Math.abs(encoderPosition);
        setPosition( encoderPosition );
        setPower( power );
    }


    protected void setDirection( String direction )
    {
        String theDirection = direction.toLowerCase();

        switch ( theDirection )
        {
            case ("f"):
                if (leftFront  != null) {  leftFront.setDirection(DcMotor.Direction.FORWARD); }
                if (rightFront != null) { rightFront.setDirection(DcMotor.Direction.FORWARD); }
                if (leftRear   != null) {   leftRear.setDirection(DcMotor.Direction.REVERSE); }
                if (rightRear  != null) {  rightRear.setDirection(DcMotor.Direction.REVERSE); }
                break;

            case ("b"):
                if (leftFront  != null) {  leftFront.setDirection(DcMotor.Direction.REVERSE); }
                if (rightFront != null) { rightFront.setDirection(DcMotor.Direction.REVERSE); }
                if (leftRear   != null) {   leftRear.setDirection(DcMotor.Direction.FORWARD); }
                if (rightRear  != null) {  rightRear.setDirection(DcMotor.Direction.FORWARD); }
                break;

            case ( "cw" ):
                if (leftFront  != null) {  leftFront.setDirection(DcMotor.Direction.FORWARD); }
                if (rightFront != null) { rightFront.setDirection(DcMotor.Direction.REVERSE); }
                if (leftRear   != null) {   leftRear.setDirection(DcMotor.Direction.REVERSE); }
                if (rightRear  != null) {  rightRear.setDirection(DcMotor.Direction.FORWARD); }
                break;

            case ("ccw"):
                if (leftFront  != null) {  leftFront.setDirection(DcMotor.Direction.REVERSE); }
                if (rightFront != null) { rightFront.setDirection(DcMotor.Direction.FORWARD); }
                if (leftRear   != null) {   leftRear.setDirection(DcMotor.Direction.FORWARD); }
                if (rightRear  != null) {  rightRear.setDirection(DcMotor.Direction.REVERSE); }
                break;

            case ("l"):
                if (leftFront  != null) {  leftFront.setDirection(DcMotor.Direction.REVERSE); }
                if (rightFront != null) { rightFront.setDirection(DcMotor.Direction.FORWARD); }
                if (leftRear   != null) {   leftRear.setDirection(DcMotor.Direction.FORWARD); }
                if (rightRear  != null) {  rightRear.setDirection(DcMotor.Direction.REVERSE); }
                break;

            case ("r"):
                if (leftFront  != null) {  leftFront.setDirection(DcMotor.Direction.FORWARD); }
                if (rightFront != null) { rightFront.setDirection(DcMotor.Direction.REVERSE); }
                if (leftRear   != null) {   leftRear.setDirection(DcMotor.Direction.FORWARD); }
                if (rightRear  != null) {  rightRear.setDirection(DcMotor.Direction.REVERSE); }
                break;

            default:
                break;
        }
    }

    protected void setPosition( int position )
    {
        if (leftFront  != null){  leftFront.setTargetPosition(position); }
        if (rightFront != null){ rightFront.setTargetPosition(position); }
        if (leftRear   != null){   leftRear.setTargetPosition(position); }
        if (rightRear  != null){  rightRear.setTargetPosition(position); }

    }


    protected void setPower( double power )
    {
        if (leftFront  != null){  leftFront.setPower(power); }
        if (rightFront != null){ rightFront.setPower(power); }
        if (leftRear   != null){   leftRear.setPower(power); }
        if (rightRear  != null){  rightRear.setPower(power); }

    }

    //////////////////////////////////////////////////
    //In this method, you input the desired distance//
    //in inches, the wheel diameter, and the gear   //
    //ratio. This method will then calculate the    //
    //needed number of encoder ticks needed to      //
    //drive the distance input.                     //
    //////////////////////////////////////////////////
    protected int distance2Encoder( double distance )
    {
        double circumference = Math.PI * wheelDiam;
        double wheelRotations = distance / circumference;
        double motorRotations = wheelRotations * GEAR_RATIO;

        return (int) ( ENCODER_TICKS_PER_ROT * motorRotations );
    }

    protected double encoder2Distance ( int encoder )
    {
        double motorRotations = encoder / ENCODER_TICKS_PER_ROT;
        double circumference = Math.PI * wheelDiam;

        return motorRotations * circumference;
    }
    protected int getTargetPosition(){
        return (int)finalPosition;
    }

}
