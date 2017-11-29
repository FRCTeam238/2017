package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandTurnAwayFromBoiler extends AbstractCommand {

  String direction="";
  Alliance teamColor;
  Robot theRobot;
  Drivetrain myRobotDrive;
  Navigation myNavigation;
  
  double motorValue;
  double targetValue;
  double newTargetYaw;
  
  public CommandTurnAwayFromBoiler(Drivetrain theRobotDrive, Navigation myNavigationForTarget, Robot myRobot){
    
    this.myRobotDrive = theRobotDrive;
    this.myNavigation = myNavigationForTarget;
    this.theRobot = myRobot;
    
  }
  
  @Override
  public void execute() {
    
    double calculatedMotorValue;
    calculatedMotorValue = pidCalc(CrusaderCommon.TURN_P_VALUE, CrusaderCommon.TURN_DEAD_STOP,
        targetValue, CrusaderCommon.TURN_MAX_ERROR,CrusaderCommon.TURN_MAX_MOTOR_VALUE, CrusaderCommon.TURN_I_VALUE);
    
    Logger.Log("CommandTurnAwayFromBoiler(): Calculated Motor Value is " + calculatedMotorValue);
    
    switch(direction){
      
      //THIS NEEDS TO BE FIXED
      case "Right":
        myRobotDrive.turnRight(calculatedMotorValue, calculatedMotorValue);
        Logger.Log("CommandTurnAwayFromBoiler(): We Are Turning Right");
        break;
     
      case "Left":
        myRobotDrive.turnLeft(calculatedMotorValue, calculatedMotorValue);
        Logger.Log("CommandTurnAwayFromBoiler(): We Are Turning Left");
        break;
        
      default:
          //do nothing
        break;
        
    }
    
  }

  @Override
  public void prepare() {

    myNavigation.zeroYaw();
    
  }


  public void setParams(String params[]) {

    teamColor = theRobot.getAllianceTeam();
    
    if (teamColor == Alliance.Red)
    {
      direction = "Left";
    }else{
      direction = "Right";
    }
    
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
    double currentYaw;
    currentYaw = myNavigation.getYaw();
    if (myNavigation.areWeThereYet() == true) {
      myRobotDrive.driveForward(0, 0);
      
      SmartDashboard.putNumber("FINAL YAW", currentYaw);
      
      return true;
    }

    else {
      return false;
    }
  }
  
//THIS ALSO EXISTS IN TURN TO FROM BOILER
  public double getError()
  {
    double error;
    double currentYaw = Math.abs(myNavigation.getYaw());
    
    error = targetValue - currentYaw;
    
    return error;
  }

}
