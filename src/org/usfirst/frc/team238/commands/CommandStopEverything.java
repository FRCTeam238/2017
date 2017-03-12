package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.FuelHandler;
import org.usfirst.frc.team238.robot.Climber;

public class CommandStopEverything implements Command {

  FuelHandler myFuelHandler;
  Climber myClimber;
  
  public CommandStopEverything(FuelHandler theFuelHandler, Climber theClimber) {
    // TODO Auto-generated constructor stub
    
    this.myFuelHandler = theFuelHandler;
    this.myClimber = theClimber;
    
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    myFuelHandler.stopEverything();
    myClimber.StopClimbing();
    myFuelHandler.openHopper();
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
    // TODO Auto-generated method stub
    return false;
  }

}
