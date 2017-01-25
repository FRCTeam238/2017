/**
 * 
 */
package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.robot.Drivetrain;

/**
 * @author Crusader
 *
 */
public class CommandDelay extends AbstractCommand {

  int        count;
  int        targetValue;
  Drivetrain myRobotdrive;

  /**
   * 
   */
  public CommandDelay(Drivetrain myRobotDrive) {
    // TODO Auto-generated constructor stub
    myRobotdrive = myRobotDrive;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.usfirst.frc.team238.core.Command#execute()
   */
  @Override
  public void execute() {
    count++;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.usfirst.frc.team238.core.Command#prepare()
   */
  @Override
  public void prepare() {
    // TODO Auto-generated method stub
    myRobotdrive.resetEncoders();
    count = 0;
  }

  public void setParams(int params) {
    targetValue = params;
  }

  public boolean done() {
    boolean isDone = false;

    if (count > targetValue) {
      isDone = true;
    }

    return isDone;
  }

}
