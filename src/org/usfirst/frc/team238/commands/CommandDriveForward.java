
package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandDriveForward extends AbstractCommand {

  Drivetrain myRobotDrive;
  Navigation myNavigation;
  PowerDistributionPanel myPowerDistributionPanel;
  
  double motorValue;
  double targetValue;
  double stallValue;
  double yawValue;
  double yawErrorTotal;
  double ultrasonicTarget;
  double CurrentDrawLimit = 20.0; // Limit for CurrentDraw
  double yawPConstant = CrusaderCommon.DRIVE_FORWARD_P_VALUE; // Proportional constant for drive
  double yawIConstant = CrusaderCommon.DRIVE_FORWARD_I_VALUE;
  double yawCorrectionMaxPercent = CrusaderCommon.DRIVE_FORWARD_MAX_YAW_PERCENT;   // percent of motorValue for max yaw
  double previousEncoderTicks;
  double okToCheckForCollision = 0;
  
  // boolean debug;

  public CommandDriveForward(Drivetrain robotDrive, Navigation myNav) {
    
    // this.debug = SmartDashboard.getBoolean("Debug");
    this.myRobotDrive = robotDrive;
    this.myNavigation = myNav;
    this.myPowerDistributionPanel = new PowerDistributionPanel();

  }

  public void prepare() {

    yawErrorTotal = 0;
    
    myNavigation.resetNAVX();
    myNavigation.zeroYaw();
    myRobotDrive.resetEncoders();
    yawValue = myNavigation.getYaw();
    previousEncoderTicks = 0;
    SmartDashboard.putNumber("Starting Yaw", yawValue);
    
    //Logger.Log("CommandDriveForward.prepare");

  }

  public void execute() {

    myRobotDrive.shiftLow();
    
    motorValue = pidCalc(CrusaderCommon.STRAIGHT_P_VALUE, CrusaderCommon.STRAIGHT_DEAD_STOP,
    targetValue, CrusaderCommon.STRAIGHT_MAX_ERROR, CrusaderCommon.STRAIGHT_MAX_MOTOR_VALUE);
    
    double currentYaw = myNavigation.getYaw();
    double yawError = currentYaw - yawValue; // Positive yaw is right turn so positive error is right turn
    double yawCorrection = yawPConstant * yawError * motorValue;
    
    yawCorrection = Math.min(yawCorrection, yawCorrectionMaxPercent * motorValue); 

    double finalMotorValueLeft = motorValue - yawCorrection;
    double finalMotorValueRight = motorValue + yawCorrection;
    
    Logger.Log("CommandDriveForward(): LeftMotorValue = "+ finalMotorValueLeft); 
    Logger.Log("CommandDriveForward(): RightMotorValue = " + finalMotorValueRight);
    Logger.Log("CommandDriveForward(): CurrentYaw: "+ currentYaw+ "  YawError: "+ yawError+ "  YawCorrection: "+ yawCorrection);
    
    myRobotDrive.driveForward(finalMotorValueLeft, finalMotorValueRight); 
    
    
    /*
     * SmartDashboard.putNumber("YawError", yawError);
     * SmartDashboard.putNumber("CurrenTYaw", currentYaw);
     * //SmartDashboard.putNumber("YawErrorTotal", yawErrorTotal);
     * SmartDashboard.putNumber("YawCorrection", yawCorrection);
     * SmartDashboard.putNumber("finalMotorValueLeft", finalMotorValueLeft);
     * SmartDashboard.putNumber("finalMotorValueRight", finalMotorValueRight);
     */

   
    //myRobotDrive.driveForward(motorValue, motorValue);
    
    // myRobotDrive.driveForward(motorValue, motorValue);

  }

  public void setParams(String params[]) {

    if ((params[0] != null) || (!params[0].isEmpty())) {
      targetValue = Double.parseDouble(params[0]) * CrusaderCommon.DRIVE_FORWARD_ENCODER_TICKS_PER_FOOT; //4560;
    } else {
      targetValue = 0;
    }

    if ((params[1] != null) || (!params[1].isEmpty())) {
      motorValue = Double.parseDouble(params[1]);
    } else {
      motorValue = 1;
    }

    if ((params[2] != null) || (!params[2].isEmpty())) {
      stallValue = Double.parseDouble(params[2]);
    } else {
      stallValue = 0;
    }
    if ((params[3] != null) || (!params[3].isEmpty())) {
      ultrasonicTarget = Double.parseDouble(params[3]);
    } else {
      ultrasonicTarget = 0;
    }

    okToCheckForCollision = targetValue *.25; // .25 should be in CC
  }

  public boolean done() {
  
    double amountOfTicks;
   
    amountOfTicks = myRobotDrive.getEncoderTicks();
    
    Logger.Log("CommandDriveForward() : Target Value = "+ targetValue+ " Amount Of Ticks = "+ amountOfTicks);
    
    boolean areWeDone = (amountOfTicks > targetValue);

    if((!areWeDone) && (stallValue != 0)) 
    {
      //if we run into a wall and still arent There yet consider it done 
     
      if (amountOfTicks > okToCheckForCollision)
      {
        areWeDone = myNavigation.haveWeCollided();
      }
         
    }

    if (areWeDone) 
    {
      //isDone = true;
      myRobotDrive.driveForward(0, 0);
      SmartDashboard.putNumber("WE STOPPED AT", amountOfTicks);
    }
    
      
    return areWeDone;
  }
  
  /*
   * A function meant to check if the robot is running into a wall by checking
   * the current output private boolean currentOverLoad(){ boolean
   * currentOverload = false;
   * 
   * double totalCurrentDraw; //CurrentDraw of all Talons below double
   * leftFrontCurrentDraw = myPowerDistributionPanel.getCurrent(5);
   * //CurrentDraw of Left Front Talon CurrentDraw double leftRearCurrentDraw =
   * myPowerDistributionPanel.getCurrent(6); //CurrentDraw of Left Rear Talon
   * CurrentDraw double rightFrontCurrentDraw =
   * myPowerDistributionPanel.getCurrent(7); //CurrentDraw of Right Front Talon
   * CurrentDraw double rightRearCurrentDraw =
   * myPowerDistributionPanel.getCurrent(8); //CurrentDraw of Right Rear Talon
   * CurrentDraw
   * 
   * 
   * totalCurrentDraw =
   * (leftFrontCurrentDraw+leftRearCurrentDraw+rightFrontCurrentDraw+
   * rightRearCurrentDraw)/4;
   * 
   * Logger.logString("COMMANDDRIVEFORWARD: leftFrontCurrentDraw: "+
   * leftFrontCurrentDraw);
   * Logger.logString("COMMANDDRIVEFORWARD: leftRearCurrentDraw: "+
   * leftRearCurrentDraw);
   * Logger.logString("COMMANDDRIVEFORWARD: rightFrontCurrentDraw: "+
   * rightFrontCurrentDraw);
   * Logger.logString("COMMANDDRIVEFORWARD: rightRearCurrentDraw: "+
   * rightRearCurrentDraw);
   * Logger.logString("COMMANDDRIVEFORWARD: Total CurrentDraw is = "+
   * totalCurrentDraw);
   * 
   * if( totalCurrentDraw > CurrentDrawLimit) { currentOverload = true; }
   * 
   * return currentOverload; }
   */
  public double getError()
  {
    double error;
    double amountOfTicks = myRobotDrive.getEncoderTicks();
    
    error = targetValue - amountOfTicks;
    
    return error;
  }
}
