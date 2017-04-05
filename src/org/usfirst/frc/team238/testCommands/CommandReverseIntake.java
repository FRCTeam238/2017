package org.usfirst.frc.team238.testCommands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.Intake;

public class CommandReverseIntake implements Command {

  Intake myIntake;
  
  public CommandReverseIntake (Intake theIntake){
    
    this.myIntake = theIntake;
  
  }
  
  @Override
  public void execute() {
    // TODO Auto-generated method stub
    myIntake.IntakeOut();
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

  @Override
  public void execute(int btnPressed) {
    // TODO Auto-generated method stub
    
  }

}
