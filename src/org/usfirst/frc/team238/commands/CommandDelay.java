/**
 * 
 */
package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

/**
 * @author Crusader
 *
 */
public class CommandDelay extends AbstractCommand {

  int        count;
  int        targetValue;
  Drivetrain myRobotdrive;
  Navigation myNavigation;

  /**
   * 
   */
  public CommandDelay(Drivetrain myRobotDrive, Navigation theNavigation) {
    // TODO Auto-generated constructor stub
    this.myRobotdrive = myRobotDrive;
    this.myNavigation = theNavigation;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.usfirst.frc.team238.core.Command#execute()
   */
  @Override
  public void execute() {
    count++;
    myRobotdrive.resetEncoders();
    myNavigation.resetNAVX();
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
    myNavigation.resetNAVX();
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
