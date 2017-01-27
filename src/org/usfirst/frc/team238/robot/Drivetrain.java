package org.usfirst.frc.team238.robot;


import org.usfirst.frc.team238.core.Logger;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Drivetrain {

	Solenoid shifterSolenoid;
	RobotDrive robotMotors;
	
	CANTalon leftFrontDrive;
	CANTalon rightFrontDrive;

	int encoderLeft;
	int encoderRight;
	
	int counter;
	double protoCounter;
	int delayCounter;
	// need turn left turn right
	int lastBtnPressed;
	int scalefactor;
	int scalefactorOnePercent;
	int btncounter;
	int btncounterDec;
	
	public Drivetrain(RobotDrive theRobotDrive)
	{
		this.robotMotors = theRobotDrive;
	}
	
	public void init(CANTalon leftFrontDriveTalon, CANTalon rightFrontDriveTalon)
	{
		//Have to find the values for Encoders- HS
		
		leftFrontDrive = leftFrontDriveTalon;
		rightFrontDrive = rightFrontDriveTalon;
		shifterSolenoid = new Solenoid (0);
		
		lastBtnPressed = 0;
		protoCounter = 0;
		scalefactor = 5;
		scalefactorOnePercent = 1;
		counter = 0;
		leftFrontDrive.configEncoderCodesPerRev(256);
		rightFrontDrive.configEncoderCodesPerRev(256);
		
		 btncounter = 0;
		btncounterDec = 0;
		
	}
	public int getEncoderTicks()
	{
		boolean debug;
		
		debug = SmartDashboard.getBoolean("Debug");
		
		if(debug == false)
		{
			encoderLeft = leftFrontDrive.getEncPosition();
			encoderRight = rightFrontDrive.getEncPosition();
			return encoderRight;
		}
		else 
		{
			
			counter++;
			encoderRight = counter * 1000;
			return encoderRight;
			
		}
		
		
	}
	
	public void resetEncoders(){
		counter=0;
		
		boolean debug;
		
			debug = SmartDashboard.getBoolean("Debug");
		
			if(debug == false)
			{
				leftFrontDrive.setPosition(0);
				rightFrontDrive.setPosition(0);
				
				encoderLeft = leftFrontDrive.getEncPosition();
				encoderRight = rightFrontDrive.getEncPosition();
			}
			else 
			{
				encoderLeft = 0;
				encoderRight = 0;
			}
		

		
		Logger.logTwoInt("ENCODER LEFT : " , encoderLeft , "ENCODER RIGHT : " , encoderRight);
	}
	
	public int getEncoderCount(int count)
	{
		counter++;
		return counter;
		
	}
	
	public void shiftHigh()
	{
		shifterSolenoid.set(CrusaderCommon.SHIFTER_HIGH_GEAR);
		Logger.logString("!!!!!!!!!!DEBUGHIGH!!!!!!!!!!");
	}
	
	public void shiftLow()
	{
		shifterSolenoid.set(CrusaderCommon.SHIFTER_LOW_GEAR);
		Logger.logString("!!!!!!!!!!DEBUGLOW!!!!!!!!!!");
	}
	
	public void driveForward(double leftMotorValue, double rightMotorValue)  {
		
		robotMotors.tankDrive(leftMotorValue, rightMotorValue);	
	}
	
	public void driveBackwards(double leftMotorValue, double rightMotorValue)  {
		
		robotMotors.tankDrive(leftMotorValue * -1, rightMotorValue * -1);
	}
	
	public void turnLeft (double leftJsValue, double rightJsValue){
		
		robotMotors.tankDrive(leftJsValue, rightJsValue * -1);
	}
	
	public void turnRight(double leftJsValue, double rightJsValue){
		
		robotMotors.tankDrive(leftJsValue * -1, rightJsValue);
	}
	
	public boolean complete() {
		// TODO Auto-generated method stub
		return false;
	}

	public void incrementMotorValue(int currentBtn)
	{
		//Logger.logDouble("Button = ", currentBtn);
		if(btncounter < 7)
		{
			btncounter++;
			return;
		}
		btncounter = 0;
		if((lastBtnPressed == currentBtn)) // || (0 == currentBtn))
		{
			lastBtnPressed = currentBtn;
			//Logger.logDouble("Button = ", currentBtn);
			SmartDashboard.putNumber("current btn = ", lastBtnPressed);
			return;
		}
		else
		{
			lastBtnPressed = currentBtn;
			
			if(protoCounter < 1) 
			{
				protoCounter = protoCounter * 100;
				protoCounter += scalefactor;
				protoCounter = protoCounter/100;
				robotMotors.tankDrive(protoCounter, 0);
				lastBtnPressed = currentBtn;
				SmartDashboard.putNumber("Motor vaslue = ", protoCounter);
			}
			Logger.logDouble("Increment", protoCounter);
		}
	}
	
	public void decrementMotorValue(int currentBtn)
	{
		if(btncounterDec < 7)
		{
			btncounterDec++;
			return;
		}
		btncounterDec = 0;
		if((lastBtnPressed == currentBtn)) // || (0 == currentBtn))
		{
			lastBtnPressed = currentBtn;
			return;
		}
		else
		{
			lastBtnPressed = currentBtn;
			if(protoCounter > -1)
			{
				protoCounter = protoCounter * 100;
				protoCounter -= scalefactor;
				protoCounter = protoCounter/100;
				robotMotors.tankDrive(protoCounter, 0);
				lastBtnPressed = currentBtn;
				SmartDashboard.putNumber("Motor vaslue = ", protoCounter);
			}
			Logger.logDouble("decrenment", protoCounter);
		}
	}
	
	public void incrementMotorValueOnePercent(int currentBtn)
	{
		//Logger.logDouble("Button = ", currentBtn);
		if(btncounter < 7)
		{
			btncounter++;
			return;
		}
		btncounter = 0;
		if((lastBtnPressed == currentBtn)) // || (0 == currentBtn))
		{
			lastBtnPressed = currentBtn;
			//Logger.logDouble("Button = ", currentBtn);
			SmartDashboard.putNumber("current btn = ", lastBtnPressed);
			return;
		}
		else
		{
			lastBtnPressed = currentBtn;
			
			if(protoCounter < 1) 
			{
				protoCounter = protoCounter * 100;
				protoCounter += scalefactorOnePercent;
				protoCounter = protoCounter/100;
				robotMotors.tankDrive(protoCounter, 0);
				lastBtnPressed = currentBtn;
				SmartDashboard.putNumber("Motor vaslue = ", protoCounter);
			}
			Logger.logDouble("Increment", protoCounter);
		}
	}
	
	public void decrementMotorValueOnePercent(int currentBtn)
	{
		if(btncounterDec < 7)
		{
			btncounterDec++;
			return;
		}
		btncounterDec = 0;
		if((lastBtnPressed == currentBtn)) // || (0 == currentBtn))
		{
			lastBtnPressed = currentBtn;
			return;
		}
		else
		{
			lastBtnPressed = currentBtn;
			if(protoCounter > -1)
			{
				protoCounter = protoCounter * 100;
				protoCounter -= scalefactorOnePercent;
				protoCounter = protoCounter/100;
				robotMotors.tankDrive(protoCounter, 0);
				lastBtnPressed = currentBtn;
				SmartDashboard.putNumber("Motor vaslue = ", protoCounter);
			}
			Logger.logDouble("decrenment", protoCounter);
		}
	}
	
	public void resetMotorValue()
	{
		protoCounter = 0;
		lastBtnPressed = 1;
		robotMotors.tankDrive(0, 0);
		Logger.logDouble("Reset", protoCounter);
	}
	public void nobtnPressed()
	{
		lastBtnPressed = 1;
		//Logger.logDouble("Reset", lastBtnPressed);
		}
	/*
	 * public void incrementMotorValue()
	{
		Logger.logDouble("Increment", protoCounter);
		
		if(protoCounter < 1)
		{
			
			if(delayCounter > 50)
			{
				robotMotors.tankDrive(protoCounter += .10, 0);
				delayCounter =0;
			}
			delayCounter++;
			Logger.logDouble("Increment", protoCounter);
		}
	}
	
	public void decrementMotorValue()
	{
		Logger.logDouble("decrement", protoCounter);
		
		if(protoCounter > -1)
		{
			if(delayCounter > 50)
			{
				protoCounter = protoCounter * 100;
				protoCounter -= 10;
				protoCounter = protoCounter/100;
				robotMotors.tankDrive(protoCounter, 0);
				delayCounter =0;
			}
			delayCounter++;
			
			Logger.logDouble("Decrement", protoCounter);
		}
	}
	 */
}


















