package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.Intake;

public class CommandStartIntake implements Command {

  Intake myIntake;
  
  public CommandStartIntake (Intake theIntake){
    
    this.myIntake = theIntake;
  
  }
  
  @Override
  public void execute() {
    // TODO Auto-generated method stub
    myIntake.startIntake();
  }

  @Override
  public void prepare() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setParams() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean done() {
    
    return myIntake.complete();

  }

}
