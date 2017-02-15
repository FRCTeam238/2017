package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.Elevator;

public class CommandRunElevator implements Command {

  Elevator myElevator;
  
  public CommandRunElevator(Elevator theElevator){
    
    this.myElevator = theElevator;
    
  }
  
  @Override
  public void execute() {
    // TODO Auto-generated method stub
    myElevator.runElevator();
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
    return myElevator.complete();
  }

}