package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateFinished implements AutonomousState {

  String[] parameters;

  @Override
  public void prepare() {

  }

  @Override
  public void init() {
    // TODO Auto-generated method stub

  }

  // used when autonomous is interrupted
  @Override
  public void reset() {

  }

  @Override
  public void init(String params[], CommandController theMcp) {
    // TODO Auto-generated method stub

  }

  @Override
  public void process() {
    //Logger.Log("AutonomousMode Done ");

  }

  @Override
  public boolean done() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void showParams() {
    // TODO Auto-generated method stub
    SmartDashboard.putString("Param 1 - targetValue", "0");
    SmartDashboard.putString("Param 2 - motorSpeed", "0");
    SmartDashboard.putString("Param 3 - targetYaw", "0");
    SmartDashboard.putString("Param 4 - ultrasonicTarget", "0");
    SmartDashboard.putString("Param 5 - collisionToggle", "0");
  }

  @Override
  public void updateParams() {
    // TODO Auto-generated method stub

  }

  @Override
  public String getParam(int value) {
    String output = "";
    if (parameters == null || parameters.length - 1 < value) {
      output = "";
    } else {
      output = parameters[value];
    }
    return output;
  }

}
