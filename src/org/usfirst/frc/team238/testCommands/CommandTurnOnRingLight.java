package org.usfirst.frc.team238.testCommands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.Intake;
import org.usfirst.frc.team238.robot.Shooter;

public class CommandTurnOnRingLight implements Command {

  Shooter myShooter;
  
  public CommandTurnOnRingLight (Shooter theShooter){
    
    this.myShooter = theShooter;
  
  }
  
  @Override
  public void execute() {
    // TODO Auto-generated method stub
    myShooter.turnOnRingLight();
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
    
    return true;

  }

  @Override
  public void execute(int btnPressed) {
    // TODO Auto-generated method stub
    
  }

}
