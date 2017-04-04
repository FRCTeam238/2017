package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.commands.CommandUnDeployGear;
import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;

public class StateUnDeployGear implements AutonomousState {

  CommandUnDeployGear retractGearSolenoid;
  String parameters[];
  
  @Override
  public void init() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void prepare() {
    // TODO Auto-generated method stub
    retractGearSolenoid.prepare();
  }

  @Override
  public void init(String[] params, CommandController theMcp) {
    // TODO Auto-generated method stub
    retractGearSolenoid = (CommandUnDeployGear) theMcp.getAutoCmd("CommandUnDeployGear");
    parameters = params;
  }

  @Override
  public void process() {
    // TODO Auto-generated method stub
    retractGearSolenoid.execute();
  }

  @Override
  public boolean done() {
    // TODO Auto-generated method stub
    return retractGearSolenoid.done();
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
