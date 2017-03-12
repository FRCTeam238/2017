package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandTargetBoiler extends AbstractCommand {

  String direction="";
  Alliance teamColor;
  Robot theRobot;
  Drivetrain myRobotDrive;
  Navigation myNavigation;
  
  double motorValue;
  double targetValue;
  double newTargetYaw;
  
  public CommandTargetBoiler(Drivetrain theRobotDrive, Navigation myNavigationForTarget, Robot myRobot){
    
    this.myRobotDrive = theRobotDrive;
    this.myNavigation = myNavigationForTarget;
    this.theRobot = myRobot;
    
  }
  
  @Override
  public void execute() {
    
    double calculatedMotorValue;
    calculatedMotorValue = pidCalc(CrusaderCommon.TURN_P_VALUE, CrusaderCommon.TURN_DEAD_STOP,
        targetValue, CrusaderCommon.TURN_MAX_ERROR, CrusaderCommon.TURN_MAX_MOTOR_VALUE);
    
    Logger.Log("Calculated Motor Value is " + calculatedMotorValue);
    
    //this is different than turning to/from boiler cmd as it's the 
    //opposite direction but only on one side so the robot pivots on the side that doesn't move
    
    switch(direction){
       case "Left":
        myRobotDrive.turnLeft(calculatedMotorValue, 0);
        Logger.Log("We Are Going Left");
        break;
     
      case "Right":
        myRobotDrive.turnRight(0, calculatedMotorValue);
        Logger.Log("We Are Going Right");
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
    
    Logger.Log("Team Color is : " + teamColor);
    
    //this is different than turning to/from boiler cmd as it's the 
    //opposite direction but only on one side so the robot pivots on the side that doesn't move
    if (teamColor == Alliance.Red)
    {
      direction = "Left";
    }else{
      direction = "Right";
    }
    
    Logger.Log("CommandTurnToBoiler: Direction is: "+direction);
    
    
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
  
  //THIS ALSO EXISTS IN TURN AWAY FROM BOILER
  public double getError()
  {
    double error;
    double currentYaw = Math.abs(myNavigation.getYaw());
    
    error = targetValue - currentYaw;
    
    return error;
  }

}
