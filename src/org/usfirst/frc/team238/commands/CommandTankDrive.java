package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.robot.ControlBoard;

import edu.wpi.first.wpilibj.RobotDrive;

public class CommandTankDrive extends AbstractCommand {

  RobotDrive myRobotDrive;

  public CommandTankDrive(RobotDrive robotDrive) {
    this.myRobotDrive = robotDrive;
  }

  public void prepare() {

  }

  @Override
  public void execute() {
    double leftJsValue = 0;
    double rightJsValue = 0;
    leftJsValue = ControlBoard.getDriverLeftJs().getY();
    rightJsValue = ControlBoard.getDriverRightJs().getY();

    myRobotDrive.tankDrive(-leftJsValue, -rightJsValue);

  }

  // @Override
  // public void execute(double overRideValue) {
  // TODO Auto-generated method stub

}
