/**
 * 
 */
package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

/**
 * @author Crusader
 *
 */
public class CommandDelay extends AbstractCommand {

  int        count;
  int        targetValue;
  double     start;
  double     current;
  double     elapsed;
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
    
    myRobotdrive.resetEncoders();
    myNavigation.zeroYaw();
    //This if else is used to have this delay work in time rather than iterations
    if(count == 0)
    {
      
      start = System.currentTimeMillis();
      count++;
      
    }
    else
    {
      current = System.currentTimeMillis();
    }
    
    elapsed = current - start;
    
    Logger.Log("DELAY Elapsed Time (millis): "+elapsed);
    
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
    myNavigation.zeroYaw();
    count = 0;
  }

  public void setParams(int params) {
    targetValue = params;
  }

  public boolean done() {
    boolean isDone = false;

    if (elapsed > targetValue) {
      isDone = true;
    }

    return isDone;
  }

}
