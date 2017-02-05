package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.robot.Shooter;

public class CommandRunShooter extends AbstractCommand {

  Shooter myShooter;
  
  double targetValue;
  double motorValue;
  
  public CommandRunShooter(Shooter theShooter) 
  {
    // TODO Auto-generated constructor stub
    this.myShooter = theShooter;
    
  }
  
  public void execute()
  {
    
    myShooter.execute();
    
  }
  
  public void prepare()
  {
    
    myShooter.resetShooterEncoder();
    
  }
  
  public void setParams(String params[])
  {
    
    if ((params[0] != null) || (!params[0].isEmpty())) {
      targetValue = Double.parseDouble(params[0]) * 4560;
    } else {
      targetValue = 0;
    }

    if ((params[1] != null) || (!params[1].isEmpty())) {
      motorValue = Double.parseDouble(params[1]);
    } else {
      motorValue = 1;
    }
    
    
  }
  
  public boolean done()
  {
    
    boolean isDone = false;
    
    return isDone;
    
  }

}
