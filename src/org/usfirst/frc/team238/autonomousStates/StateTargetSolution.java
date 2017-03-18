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
    count++;
    Logger.Log("StateTargetSolution.Process() "+count);
  }

  public boolean done() {
      count = 0;
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

    SmartDashboard.putString("Tracking Param 1 - Angle", parameters[0]);
    SmartDashboard.putString("Tracking Param 2 - Distance", parameters[1]);
    SmartDashboard.putString("Tracking Param 3 - ...", "0");
    SmartDashboard.putString("Tracking Param 4 - ...", "0");
  }

  @Override
  public void updateParams() {
    // TODO Auto-generated method stub
    String param1;
    String param2;
    String param3;
    String param4;

    param1 = SmartDashboard.getString("Tracking Param 1 - Angle");
    parameters[0] = param1;
    param2 = SmartDashboard.getString("Tracking Param 2 - Distance");
    parameters[1] = param2;
    param3 = SmartDashboard.getString("Tracking Param 3 - ...");
    parameters[2] = param3;
    param4 = SmartDashboard.getString("Tracking Param 4 - ...");
    parameters[3] = param4;

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
