package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.robot.Shooter;
import org.usfirst.frc.team238.robot.FuelHandler;
import org.usfirst.frc.team238.robot.Elevator;

public class CommandRunShooter extends AbstractCommand {
  
  FuelHandler myFuelHandler;
  Elevator myElevator;
  
  double targetValue;
  double motorValue;
  double visionAngle;
  
  public CommandRunShooter(FuelHandler theFuelHandler) 
  {
    // TODO Auto-generated constructor stub
    this.myFuelHandler = theFuelHandler;
    
  }
  
  public void execute()
  {
    
    myFuelHandler.shoot();
    
  }
  
  public void prepare()
  {
    
    
    
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
