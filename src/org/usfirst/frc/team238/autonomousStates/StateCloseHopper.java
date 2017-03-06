package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.commands.CommandCloseHopper;
import org.usfirst.frc.team238.commands.CommandOpenHopper;
import org.usfirst.frc.team238.commands.CommandRunShooter;
import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;

public class StateCloseHopper implements AutonomousState {

  CommandCloseHopper closeHopper;
  String parameters[];
  
  @Override
  public void init() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void prepare() {
    // TODO Auto-generated method stub
    closeHopper.prepare();
  }

  @Override
  public void init(String[] params, CommandController theMcp) {
    // TODO Auto-generated method stub
    closeHopper = (CommandCloseHopper) theMcp.getAutoCmd("CommandCloseHopper");
    parameters = params;
  }

  @Override
  public void process() {
    // TODO Auto-generated method stub
    closeHopper.execute();
  }

  @Override
  public boolean done() {
    // TODO Auto-generated method stub
    return closeHopper.done();
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
