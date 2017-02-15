package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateTargetSolution implements AutonomousState {

  String       parameters[];

  public void init(String params[], CommandController theMcp) {
    parameters = params;
  }

  int count;
  int targetValue;

  public void process() {
    
    Logger.Log("target state is in process");
  }

  public boolean done() {
   
      return true;
  }

  @Override
  public void init() {
    // TODO Auto-generated method stub

  }

  @Override
  public void prepare() {
    
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub

  }

  @Override
  public void showParams() {

    SmartDashboard.putString("Param 1 - targetValue", parameters[0]);
    SmartDashboard.putString("Param 2 - motorSpeed", parameters[1]);
    SmartDashboard.putString("Param 3 - rollValue", "0");
    SmartDashboard.putString("Param 4 - ultrasonicTarget", "0");
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
