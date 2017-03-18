package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandTurnRight extends AbstractCommand {

  Drivetrain myRobotDrive;
  Navigation myNavigation;

  double motorValue;
  double finalMotorValue;
  double targetValue;
  double newTargetYaw;
  double currentYaw;
  int    count;

  public CommandTurnRight(Drivetrain robotDrive, Navigation myNavigationForTarget) {

    this.myRobotDrive = robotDrive;
    this.myNavigation = myNavigationForTarget;
    count = 0;
  }

  public void prepare() {

    myNavigation.resetNAVX();
    myNavigation.zeroYaw();

  }

  public void execute() {

   // finalMotorValue = myNavigation.turningMotorValue(targetValue, currentYaw, motorValue);
    
    double yaw = myNavigation.getYaw();
    
    myNavigation.navxValues();
    currentYaw = myNavigation.getYaw();
    myRobotDrive.turnRight(motorValue, motorValue);
    
    Logger.Log("CommandTurnRight(): Our yaw = "+yaw);
    Logger.Log("CommandTurnRight(): Our Target yaw is = "+ targetValue);
    
  }

  public void setParams(String params[]) {

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

    myNavigation.setTargetValues(targetValue);

  }

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
