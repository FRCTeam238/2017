package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.Climber;

public class CommandStopClimber implements Command {

  Climber myClimber;
  
  public CommandStopClimber (Climber theClimber){
    
    this.myClimber = theClimber;
    
  }
  
  @Override
  public void execute() {
    // TODO Auto-generated method stub
    myClimber.StopClimbing();
    
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

  @Override
  public void execute(int btnPressed) {
    // TODO Auto-generated method stub
    
  }

}