package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.commands.CommandDepositGear;
import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;

public class StateDepositGear implements AutonomousState {

  CommandDepositGear spitOutGear;
  String parameters[];
  
  @Override
  public void init() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void prepare() {
    // TODO Auto-generated method stub
    spitOutGear.prepare();
  }

  @Override
  public void init(String[] params, CommandController theMcp) {
    // TODO Auto-generated method stub
    spitOutGear = (CommandDepositGear) theMcp.getAutoCmd("CommandDepositGear");
    parameters = params;
  }

  @Override
  public void process() {
    // TODO Auto-generated method stub
    spitOutGear.execute();
  }

  @Override
  public boolean done() {
    // TODO Auto-generated method stub
    return spitOutGear.done();
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
