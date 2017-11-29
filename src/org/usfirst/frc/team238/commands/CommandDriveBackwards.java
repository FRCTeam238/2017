package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;

public class CommandDriveBackwards extends AbstractCommand {

  Drivetrain myRobotDrive;

  double motorValue;
  double targetValue;
  // boolean debug;

  public CommandDriveBackwards(Drivetrain robotDrive) {
    this.myRobotDrive = robotDrive;
    // this.debug = SmartDashboard.getBoolean("Debug");

  }

  public void prepare() {

    myRobotDrive.resetEncoders();
    //Logger.Log("CommandDriveBackwards.prepare");

  }

  public void execute() {

    myRobotDrive.shiftLow();
    myRobotDrive.driveBackwards(motorValue, motorValue);

  }

  public void setParams(String params[]) {

    if ((params[0] != null) || (!params[0].isEmpty())) {
      targetValue = Double.parseDouble(params[0]) * CrusaderCommon.DRIVE_FORWARD_ENCODER_TICKS_PER_INCH;
    } else {
      targetValue = 0;
    }

    if ((params[1] != null) || (!params[1].isEmpty())) {
      motorValue = Double.parseDouble(params[1]);
    } else {
      motorValue = 1;
    }

  }

  public boolean done() {

    boolean isDone = false;
    double amountOfTicks;

    amountOfTicks = myRobotDrive.getEncoderTicks();
    Logger.Log("Target Value = "+ targetValue+ " Amount Of Ticks = "+ amountOfTicks);

    if (amountOfTicks > targetValue) {

      isDone = true;
      myRobotDrive.driveForward(0, 0);

    } else {
      isDone = false;
    }

    return isDone;
  }

}
