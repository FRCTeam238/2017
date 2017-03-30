package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandCurlForward extends AbstractCommand {

  String direction="";
  Alliance teamColor;
  Robot theRobot;
  Drivetrain myRobotDrive;
  Navigation myNavigation;
  
  double motorValue;
  double targetValue;
  double newTargetYaw;
  
  double stallValue;
  int autonomousCount;
  double yawValue;
  double yawErrorTotal;
  double ultrasonicTarget;
  double CurrentDrawLimit = 20.0; // Limit for CurrentDraw
  double yawPConstant = CrusaderCommon.DRIVE_FORWARD_P_VALUE; // Proportional constant for drive
  double yawIConstant = CrusaderCommon.DRIVE_FORWARD_I_VALUE;
  double yawCorrectionMaxPercent = CrusaderCommon.DRIVE_FORWARD_MAX_YAW_PERCENT;   // percent of motorValue for max yaw
  double previousEncoderTicks;
  
  double finalMotorValueLeft;
  double finalMotorValueRight;
  
  double turnValue = 0;
  
  int stage = 0;
  
  public CommandCurlForward(Drivetrain theRobotDrive, Navigation myNavigationForTarget, Robot myRobot){
    
    this.myRobotDrive = theRobotDrive;
    this.myNavigation = myNavigationForTarget;
    this.theRobot = myRobot;
    
  }
  
  @Override
  public void execute() {
    
    double calculatedMotorValue;
    calculatedMotorValue = pidCalc(CrusaderCommon.TURN_P_VALUE, CrusaderCommon.TURN_DEAD_STOP,
        targetValue, CrusaderCommon.TURN_MAX_ERROR, CrusaderCommon.TURN_MAX_MOTOR_VALUE);
    
    driveStraightCalc();
    
    turnValue = SmartDashboard.getNumber("Curl Turn Value", 0.5);
    
    Logger.Log("CommandCurlForward(): Calculated Motor Value is " + calculatedMotorValue);
    
    switch(stage)
    {
      case 1:
      {
        
        myRobotDrive.driveForward(finalMotorValueLeft, finalMotorValueRight);
        
        break;
        
      }
      
      case 2:
      {
        
        switch(direction){
          
          
          case "Left":
            myRobotDrive.driveForward(turnValue, motorValue);
            Logger.Log("CommandCurlForward(): We Are Going Left","CommandCurlForwardLog");
            break;
         
          case "Right":
            myRobotDrive.driveForward(motorValue, turnValue);
            Logger.Log("CommandCurlForward(): We Are Going Right","CommandCurlForwardLog");
            break;
            
          default:
              //do nothing
            break;
          }
        
        break;
        
      }
        
      case 3:
      {
          
        myRobotDrive.driveForward(finalMotorValueLeft, finalMotorValueRight);
        break;
          
      }
        
    }
      
  }
    

  @Override
  public void prepare() {
    
    myNavigation.zeroYaw();
  
  }

  public void setParams(String params[]) {
    
    teamColor = theRobot.getAllianceTeam();
    
    Logger.Log("CommandCurlForward(): Team Color is : " + teamColor,"CommandCurlForwardLog");
    
    if (teamColor == Alliance.Red)
    {
      
      direction = "Right";
      
    }else{
      
      direction = "Left";
      
    }
    
    Logger.Log("CommandCurlForward(): Direction is: "+direction,"CommandCurlForwardLog");
    
    if ((params[0] != null) || (!params[0].isEmpty())) {
      
      targetValue = Double.parseDouble(params[0]);
      
    } else {
      
      targetValue = 0;
    }

    if ((params[1] != null) || (!params[1].isEmpty())) {
      
      motorValue = Double.parseDouble(params[1]);
      
    } else {
      
      motorValue = 1;
      
    }

    if ((params[2] != null) || (!params[2].isEmpty())) {
      
      newTargetYaw = Integer.parseInt(params[2]);

    } else {
      
      newTargetYaw = 0; // Don't turn if there's no input

    }

    if(direction.equals("Left")){
      
      myNavigation.setTargetValues(targetValue*-1);
      
    }else{
      
      myNavigation.setTargetValues(targetValue);
      
    }
  }

  @Override
  public boolean done() {
    
    boolean Doness= false;
    double currentYaw;
    double amountOfTicks;
    
    currentYaw = myNavigation.getYaw();
    
    amountOfTicks = myRobotDrive.getEncoderTicks();
    
    switch(stage){
      
      case 0:
        stage++;
        break;
        
      case 1:
        
        boolean areWeDone = (amountOfTicks > targetValue);
        
        if(areWeDone){
          stage++;
        }
        
       break; 
      
      case 2:

        if(myNavigation.areWeThereYet()){
          stage++;
        }
        
       break;
      
      case 3:
        
        boolean areWeDoneAgain = (amountOfTicks > targetValue);
        
        if(areWeDoneAgain){
          Doness = true;
        }
        
        break;
       
    }
    
    return Doness;
    
  }
  
  //THIS ALSO EXISTS IN TURN AWAY FROM BOILER
  public double getError()
  {
    double error;
    double currentYaw = Math.abs(myNavigation.getYaw());
    
    error = targetValue - currentYaw;
    
    return error;
  }
  
  public void driveStraightCalc()
  {
    
    double currentYaw = myNavigation.getYaw();
    double yawError = currentYaw - yawValue; // Positive yaw is right turn so positive error is right turn
    double yawCorrection = yawPConstant * yawError * motorValue;
    
    yawCorrection = Math.min(yawCorrection, yawCorrectionMaxPercent * motorValue); 

    finalMotorValueLeft = motorValue - yawCorrection;
    finalMotorValueRight = motorValue + yawCorrection;
    
    
  }

}
