
package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandDriveForward extends AbstractCommand {

  Drivetrain myRobotDrive;
  Navigation myNavigation;

  double motorValue;
  double targetValue;
  // boolean debug;
  double rollValue;
  double yawValue;
  double yawErrorTotal;
  double ultrasonicTarget;

  PowerDistributionPanel myPowerDistributionPanel;
  double                 CurrentDrawLimit = 20.0; // Limit for CurrentDraw

  double yawPConstant            = 0.015; // Proportional constant for drive
                                          // straight controller
  double yawIConstant            = 0;
  double yawCorrectionMaxPercent = 0.1;   // percent of motorValue for max yaw
                                          // correction

  public CommandDriveForward(Drivetrain robotDrive, Navigation myNav) {
    this.myRobotDrive = robotDrive;
    this.myNavigation = myNav;
    // this.debug = SmartDashboard.getBoolean("Debug");
    this.myPowerDistributionPanel = new PowerDistributionPanel();

  }

  public void prepare() {

    myNavigation.resetNAVX();
    myRobotDrive.resetEncoders();
    yawValue = myNavigation.getYaw();
    SmartDashboard.putNumber("Starting Yaw", yawValue);
    yawErrorTotal = 0;
    Logger.logString("CommandDriveForward.prepare");

  }

  public void execute() {

    double currentYaw = myNavigation.getYaw();
    double yawError = currentYaw - yawValue; // Positive yaw is right turn so
                                             // positive error is right turn
    // yawErrorTotal += yawError;

    double yawCorrection = yawPConstant * yawError * motorValue;// +
                                                                // yawIConstant*yawErrorTotal*motorValue;
    yawCorrection = Math.min(yawCorrection, yawCorrectionMaxPercent * motorValue); // Constrain
                                                                                   // yaw
                                                                                   // correction
                                                                                   // factor
                                                                                   // to
                                                                                   // max
                                                                                   // value

    double finalMotorValueLeft = motorValue - yawCorrection;
    double finalMotorValueRight = motorValue + yawCorrection;
    
    Logger.logDouble("FINAL LEFT MOTOR", finalMotorValueLeft);
    Logger.logDouble("FINAL RIGHT MOTOR", finalMotorValueRight);
    
    myRobotDrive.driveForward(finalMotorValueLeft, finalMotorValueRight); // If
                                                                          // yaw
                                                                          // error
                                                                          // is
                                                                          // positive,
                                                                          // slow
                                                                          // down
                                                                          // left
                                                                          // side
                                                                          // to
                                                                          // turn
                                                                          // yaw
                                                                          // back
                                                                          // to
                                                                          // left

    /*
     * SmartDashboard.putNumber("YawError", yawError);
     * SmartDashboard.putNumber("CurrenTYaw", currentYaw);
     * //SmartDashboard.putNumber("YawErrorTotal", yawErrorTotal);
     * SmartDashboard.putNumber("YawCorrection", yawCorrection);
     * SmartDashboard.putNumber("finalMotorValueLeft", finalMotorValueLeft);
     * SmartDashboard.putNumber("finalMotorValueRight", finalMotorValueRight);
     */

    Logger.logThreeDoubles("CurrentYaw: ", currentYaw, "  YawError: ", yawError, "  YawCorrection: ", yawCorrection);
    
    //myRobotDrive.driveForward(motorValue, motorValue);
    
    // myRobotDrive.driveForward(motorValue, motorValue);

  }

  public void setParams(String params[]) {

    if ((params[0] != null) || (!params[0].isEmpty())) {
      targetValue = Double.parseDouble(params[0]) * 3900; //4560;
    } else {
      targetValue = 0;
    }

    if ((params[1] != null) || (!params[1].isEmpty())) {
      motorValue = Double.parseDouble(params[1]);
    } else {
      motorValue = 1;
    }

    if ((params[2] != null) || (!params[2].isEmpty())) {
      rollValue = Double.parseDouble(params[2]);
    } else {
      rollValue = 0;
    }
    if ((params[3] != null) || (!params[3].isEmpty())) {
      ultrasonicTarget = Double.parseDouble(params[3]);
    } else {
      ultrasonicTarget = 0;
    }

  }

  public boolean done() {
    boolean isDone = false;
    double amountOfTicks;
    double currnetRollValue = myNavigation.getRoll();
    double currentUltrasonicDistance;

    amountOfTicks = myRobotDrive.getEncoderTicks();
    Logger.logTwoDouble("Target Value = ", targetValue, " Amount Of Ticks = ", amountOfTicks);
    Logger.logTwoDouble("RollValue : ", rollValue, "CurrentRollValue : ", currnetRollValue);
    Logger.logDouble("Ultrasonic : ", ultrasonicTarget);

    if (rollValue > 0) {
      if ((currnetRollValue >= rollValue) && (amountOfTicks > 9000)) // why is
                                                                     // this &&
                                                                     // here?
                                                                     // MJF?
      {
        isDone = true;
        myRobotDrive.driveForward(0, 0);

      } else {
        isDone = false;
      }
    }
    /*
     * else if(currentOverLoad()){
     * 
     * Logger.
     * logString("COMMANDDRIVEFORWARD: We must've hit a wall, stopping...");
     * isDone = true; myRobotDrive.driveForward(0, 0);
     * 
     * }
     */
    else if (ultrasonicTarget > 0) {
      isDone = false;
      /*
       * This (amountOfTicks >= targetValue - 6840) is here so the sonic sensor
       * doesn't kick in until 1.5 feet away from the target distance
       */
      if ((amountOfTicks >= targetValue - 6840)) {
        currentUltrasonicDistance = myNavigation.getDistanceFromUltrasonic();
        
        

        if (currentUltrasonicDistance <= ultrasonicTarget) {
          isDone = true;
          myRobotDrive.driveForward(0, 0);
        }

      }
    } 
    else 
    {
      boolean areWeDone = (amountOfTicks > targetValue);
      
      //Logger.logBoolean("DONE : ", areWeDone);
      
      if (areWeDone) {
        isDone = true;
        myRobotDrive.driveForward(0, 0);

      } else {
        isDone = false;
      }
    }
    return isDone;
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

}
