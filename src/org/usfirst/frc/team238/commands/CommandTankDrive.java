package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.ControlBoard;
import org.usfirst.frc.team238.robot.CrusaderCommon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    
    double tuningValue = SmartDashboard.getNumber("DRIVETRAIN TUNING", 0.2);
    leftJsValue = ControlBoard.getDriverLeftJs().getY();
    rightJsValue = ControlBoard.getDriverRightJs().getY();
    
    
    //This represents x = ax^3+(1-a)x where leftJsValue = x; tuningValue = a;
    leftJsValue = (tuningValue * (leftJsValue * leftJsValue * leftJsValue) + (1-tuningValue) * leftJsValue);
    rightJsValue = (tuningValue * (rightJsValue * rightJsValue * rightJsValue) + (1-tuningValue) * rightJsValue);
    
    Logger.Log("Left Motor Value in TELEOP = " + leftJsValue);
    Logger.Log("Right Motor Value in TELEOP = " + rightJsValue);
    
    myRobotDrive.tankDrive(leftJsValue, rightJsValue);

  }

  // @Override
  // public void execute(double overRideValue) {
  // TODO Auto-generated method stub

}
