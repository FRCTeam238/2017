package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class CommandTurnToBoiler extends AbstractCommand {

  String direction="";
  Alliance teamColor;
  Robot theRobot;
  Drivetrain myRobotDrive;
  Navigation myNavigation;
  
  double motorValue;
  double targetValue;
  double newTargetYaw;
  
  public CommandTurnToBoiler(Drivetrain theRobotDrive, Navigation myNavigationForTarget, Robot myRobot){
    
    this.myRobotDrive = theRobotDrive;
    this.myNavigation = myNavigationForTarget;
    this.theRobot = myRobot;
    
  }
  
  @Override
  public void execute() {

    switch(direction){
      
      case "Left":
        myRobotDrive.turnLeft(motorValue, motorValue);
        break;
     
      case "Right":
        myRobotDrive.turnRight(motorValue, motorValue);
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
      direction = "Right";
    }else{
      direction = "Left";
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
    if (myNavigation.areWeThereYet() == true) {
      myRobotDrive.driveForward(0, 0);
      return true;
    }

    else {
      return false;
    }
  }

}
