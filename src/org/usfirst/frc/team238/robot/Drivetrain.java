package org.usfirst.frc.team238.robot;


import org.usfirst.frc.team238.core.Logger;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
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
		
		configTalon(leftFrontDrive);
		configTalon(rightFrontDrive);
		
		
		
		 btncounter = 0;
		btncounterDec = 0;
		
	}
	public int getEncoderTicks()
	{
		boolean debug;
		
		debug = SmartDashboard.getBoolean("Debug");
		
		if(debug == true)// normally false
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
		
			if(debug == true)
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
	/*These four functions are used in autonomous to drive the robot*/
	public void driveForward(double leftMotorValue, double rightMotorValue)  {
		
	  /*the joystick value is multiplied by a target RPM so the 
	  *robot works with the velocity tuning code*/
		robotMotors.tankDrive(-leftMotorValue, -rightMotorValue);	
	}
	
	public void driveBackwards(double leftMotorValue , double rightMotorValue)  {
		
		robotMotors.tankDrive(leftMotorValue, rightMotorValue);
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
	/*configTalon  is used to configure the master talons for velocity tuning
	 * so they can be set to go to a specific velocity rather than just 
	 * use a voltage percentage
	 * This can be found in the CTRE Talon SRX Software Reference Manual 
	 * Section 12.4: Velocity Closed-Loop Walkthrough Java */
	public void configTalon(CANTalon talon)
	{
	  /*This sets the voltage range the talon can use; should be 
	  *set at +12.0f and -12.0f*/
	  //talon.configNominalOutputVoltage(+0.0f, -0.0f);
	  //talon.configPeakOutputVoltage(+12.0f, -12.0f);
    
	  /*This sets the FPID values to correct error in the motor's velocity
	   * */
   /* talon.setProfile(CrusaderCommon.TALON_NO_VALUE);
    talon.setF(CrusaderCommon.TALON_F_VALUE); //.3113);
    talon.setP(CrusaderCommon.TALON_P_VALUE); //.8);//064543);
    talon.setI(CrusaderCommon.TALON_NO_VALUE); 
    talon.setD(CrusaderCommon.TALON_NO_VALUE);*/
    
    //this set the talon to use speed mode instead of voltage mode
    talon.changeControlMode(TalonControlMode.PercentVbus);
    
	}
	
}


















