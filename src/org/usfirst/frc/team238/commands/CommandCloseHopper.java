package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.FuelHandler;

public class CommandCloseHopper implements Command {

  FuelHandler theFuelHandler;
  
  boolean isDone = false;
  
  public CommandCloseHopper(FuelHandler theFuelHandler) {
    // TODO Auto-generated constructor stub
    
    this.theFuelHandler = theFuelHandler;
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    theFuelHandler.closeHopper();
    isDone = true;
  }

  @Override
  public void prepare() {
    // TODO Auto-generated method stub
    isDone = false;
  }

  @Override
  public void setParams() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean done() {
    // TODO Auto-generated method stub
    return isDone;
  }

  @Override
  public void execute(int btnPressed) {
    // TODO Auto-generated method stub
    
  }

}
