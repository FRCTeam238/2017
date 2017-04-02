package org.usfirst.frc.team238.robot;


import org.usfirst.frc.team238.core.Logger;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class Drivetrain {

	Solenoid shifterSolenoid;
	RobotDrive robotMotors;
	
	CANTalon leftFrontDrive;
	CANTalon rightFrontDrive;

	//Encoder tick variables
	int encoderLeft;
	int encoderRight;
	
	//A function that 
	int counter;
	
	//Test function variables
	double protoCounter;
	int delayCounter;
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

		leftFrontDrive = leftFrontDriveTalon;
		rightFrontDrive = rightFrontDriveTalon;
		shifterSolenoid = new Solenoid (0);
		
		leftFrontDrive.configEncoderCodesPerRev(256);
		rightFrontDrive.configEncoderCodesPerRev(256);
		
		configTalon(leftFrontDrive);
		configTalon(rightFrontDrive);
		
		btncounter = 0;
		btncounterDec = 0;
		lastBtnPressed = 0;
		protoCounter = 0;
		scalefactor = 5;
		scalefactorOnePercent = 1;
		counter = 0;
		
		shiftLow();
	}
	
	
	/**
	 * Gets the average number of encoder ticks and 
	 * only returns one encoder if one of them returns 0
	 * @return
	 */
	public int getEncoderTicks()
	{
	  
	  //What if we're going backwards?
	  //What if an encoder is not 0 but hasn't changed?
	  
	  int encoderNumber=0;
		int encoderAverage=0;
		
		encoderLeft = leftFrontDrive.getEncPosition();
		encoderRight = rightFrontDrive.getEncPosition();
		
		encoderLeft = Math.abs(encoderLeft);
		
		Logger.Log("DriveTrain(): Left Encoder = " + encoderLeft);
		Logger.Log("DriveTrain(): Right Encoder = " + encoderRight);
		
		//If the left encoder isn't reading, return the right encoder
		if((encoderLeft == 0) && (encoderRight != 0))
		{

		  encoderNumber = encoderRight;
		  
		}
		//If the right encoder isn't reading, return the left encoder
		else if((encoderLeft != 0) && (encoderRight == 0))
		{

		  encoderNumber = encoderLeft;
		  
		}
		//If neither of them are reading, return 0
		else if((encoderLeft == 0) && (encoderRight == 0))
		{
		  Logger.Log("DriveTrain(): Encoders are broken!!!! HELP!!");
		  encoderNumber = 0;
		  
		}
		else//If none of the above conditions are true, average both of the encoders
		{
		  
		  encoderAverage = Math.abs(encoderRight) + Math.abs(encoderLeft);
		  encoderNumber = encoderAverage / 2;
		  
		}

		
		SmartDashboard.putNumber("DriveTrain: EncoderAverage", encoderAverage);

		
		return encoderNumber;
	}
	
	
	/**
	 * Resets the encoders by setting them to 0
	 */
	public void resetEncoders(){
		
	  leftFrontDrive.setPosition(0);
    rightFrontDrive.setPosition(0);
    
    encoderLeft = leftFrontDrive.getEncPosition();
    encoderRight = rightFrontDrive.getEncPosition();
		
	}
	
	
	
	public int getEncoderCount(int count)
	{
		counter++;
		return counter;
		
	}
	
	
	/**
	 * Shifts solenoids into high gear
	 */
	public void shiftHigh()
	{
	  
		shifterSolenoid.set(CrusaderCommon.SHIFTER_HIGH_GEAR);
	//	Logger.Log("Drivetrain() : shiftHigh(): !!!!!!!!!!WE'RE IN HIGH GEAR!!!!!!!!!!");
		
	}
	
	
	/**
	 * Shifts solenoids into low gear
	 */
	public void shiftLow()
	{
	  
		shifterSolenoid.set(CrusaderCommon.SHIFTER_LOW_GEAR);
	//	Logger.Log("Drivetrain() : shiftLow(): !!!!!!!!!!WE'RE IN LOW GEAR!!!!!!!!!!");
		
	}
	
	
	
	/*These four functions are used in autonomous to drive the robot*/
	
	/**
	 * A drive forward function used in Autonomous
	 * @param leftMotorValue
	 * @param rightMotorValue
	 */
	public void driveForward(double leftMotorValue, double rightMotorValue)  {
		
	  /*the joystick value is multiplied by a target RPM so the 
	  *robot works with the velocity tuning code*/
		//robotMotors.tankDrive(-leftMotorValue, -rightMotorValue);
		leftFrontDrive.set(-leftMotorValue);
    rightFrontDrive.set(rightMotorValue);
	}
	
	
	/**
	 * A drive backwards function used in Autonomous
	 * @param leftMotorValue
	 * @param rightMotorValue
	 */
	public void driveBackwards(double leftMotorValue , double rightMotorValue)  {
		
		//robotMotors.tankDrive(leftMotorValue, rightMotorValue);
    leftFrontDrive.set(leftMotorValue);
    rightFrontDrive.set(-rightMotorValue);
	}
	
	
	/**
	 * A turn left function used in Autonomous
	 * @param leftJsValue
	 * @param rightJsValue
	 */
	public void turnLeft (double leftJsValue, double rightJsValue){
		
		//robotMotors.tankDrive(leftJsValue * -1, rightJsValue);
	  
    leftFrontDrive.set(leftJsValue);
    rightFrontDrive.set(rightJsValue);
    
    //Logger.Log("DriveTrain() : turnLeft() : LeftMotorValue = " + leftJsValue);
    //Logger.Log("DriveTrain() : turnLeft() : RightMotorValue = " + rightJsValue);
    
	}
	
	
	/**
	 * A turn right function used in Autonomous
	 * @param leftJsValue
	 * @param rightJsValue
	 */
	public void turnRight(double leftJsValue, double rightJsValue){
		
		//robotMotors.tankDrive(leftJsValue, rightJsValue * -1);
    leftFrontDrive.set(-leftJsValue);
    rightFrontDrive.set(-rightJsValue);
    
	}
	
	
	/**configTalon  is used to configure the master talons for velocity tuning
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
	
  
  //A complete function that only gets used in CommandShiftHigh and CommandShiftLow
	public boolean complete() {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * Motor Test function
	 * @param currentBtn
	 */
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
			Logger.Log("DriveTrain() : incrementMotorValue() : Increment"+ protoCounter);
		}
	}
	
	
	/**
   * Motor Test function
   * @param currentBtn
   */
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
			Logger.Log("DriveTrain() : decrementMotorValue() : decrenment "+ protoCounter);
		}
	}
	
	
	/**
   * Motor Test function
   * @param currentBtn
   */
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
			Logger.Log("DriveTrain() : incrementMotorValueOnePercent() : Increment "+ protoCounter);
		}
	}
	
	
	/**
   * Motor Test function
   * @param currentBtn
   */
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
			Logger.Log("DriveTrain() : incrementMotorValueOnePercent() : decrenment "+ protoCounter);
		}
	}
	
	
	/**
   * Motor Test function
   * @param currentBtn
   */
	public void resetMotorValue()
	{
		protoCounter = 0;
		lastBtnPressed = 1;
		robotMotors.tankDrive(0, 0);
		Logger.Log("DriveTrain() : resetMotorValue() : Reset Counter "+ protoCounter);
	}
	
	
	/**
   * Motor Test function
   * @param currentBtn
   */
	public void nobtnPressed()
	{
		lastBtnPressed = 1;
		shiftLow();
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
