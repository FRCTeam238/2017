package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

public class CommandTurnLeft extends AbstractCommand {

  Drivetrain myRobotDrive;
  Navigation myNavigation; // Pass variable here

  double motorValue;
  double targetValue;
  double newTargetYaw;
  double currentYaw;
  int    count;

  public CommandTurnLeft(Drivetrain theRobotDrive, Navigation myNavigationForTarget) {

    this.myRobotDrive = theRobotDrive;
    this.myNavigation = myNavigationForTarget;
    count = 0;

  }

  public void prepare() {

    myNavigation.resetNAVX();

  }

  public void execute() {
    // Using -motorValues to spin the left motors backwards
    // If that's how it works lol Maybe change this
    currentYaw = myNavigation.getYaw();
    motorValue = myNavigation.turningMotorValue(targetValue, currentYaw, motorValue);
    myRobotDrive.turnLeft(motorValue, motorValue);
    myNavigation.navxValues();

  }

  public void setParams(String params[]) {
    Logger.logTwoString("!!!!!DEBUG!!!!!!!!PARAMETERS!!!!", params[0]);

    if ((params[0] != null) || (!params[0].isEmpty())) {
      targetValue = Double.parseDouble(params[0]) * -1;
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