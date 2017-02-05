package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.Climber;

public class CommandStartClimber implements Command {

  Climber myClimber;
  
  public CommandStartClimber (Climber theClimber){
    
    this.myClimber = theClimber;
    
  }
  
  @Override
  public void execute() {
    // TODO Auto-generated method stub
    myClimber.StartClimbing();
    
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
   
    return myClimber.complete();
    
  }

}
