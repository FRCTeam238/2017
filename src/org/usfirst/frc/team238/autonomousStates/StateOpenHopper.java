package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.commands.CommandOpenHopper;
import org.usfirst.frc.team238.commands.CommandRunShooter;
import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;

public class StateOpenHopper implements AutonomousState {

  CommandOpenHopper openHopper;
  String parameters[];
  
  @Override
  public void init() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void prepare() {
    // TODO Auto-generated method stub
    openHopper.prepare();
  }

  @Override
  public void init(String[] params, CommandController theMcp) {
    // TODO Auto-generated method stub
    openHopper = (CommandOpenHopper) theMcp.getAutoCmd("CommandOpenHopper");
    parameters = params;
  }

  @Override
  public void process() {
    // TODO Auto-generated method stub
    openHopper.execute();
  }

  @Override
  public boolean done() {
    // TODO Auto-generated method stub
    return openHopper.done();
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void showParams() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void updateParams() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getParam(int value) {
    // TODO Auto-generated method stub
    return null;
  }

}
