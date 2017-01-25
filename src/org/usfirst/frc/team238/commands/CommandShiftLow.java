package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.robot.Drivetrain;

public class CommandShiftLow extends AbstractCommand {

  Drivetrain myDrivetrain;

  public CommandShiftLow(Drivetrain driveTrain) {
    this.myDrivetrain = driveTrain;
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    myDrivetrain.shiftLow();
  }

  public void prepare() {

  }

  // @Override
  public void execute(double overRideValue) {

    // TODO Auto-generated method stub
  }

  public void setParams() {

  }

  public boolean complete() {
    return myDrivetrain.complete();
  }

}
