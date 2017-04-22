package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.commands.CommandCurlForward;
import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateCurlForward implements AutonomousState {

  CommandCurlForward commandCurlForward;
  CommandController theMCP;
  String parameters[];
  int count = 0;
 

  @Override
  public void prepare() {
    // TODO Auto-generated method stub
    commandCurlForward.setParams(parameters);
    commandCurlForward.prepare();
    
  }

  @Override
  public void init(String[] params, CommandController theMcp) {
    // TODO Auto-generated method stub
    commandCurlForward = (CommandCurlForward) theMcp.getAutoCmd("CommandCurlForward");
    parameters = params;

  }

  @Override
  public void process() {
    // TODO Auto-generated method stub
    Logger.Log("StateCurlForward.Process() "+ count);
    count++;
    commandCurlForward.execute();
    
  }

  @Override
  public boolean done() {
    
      if(commandCurlForward.done()){
        count = 0;
        return true;
      }else{
        return false;
      }
      
  }

  @Override
  public void showParams() {
    // TODO Auto-generated method stub
    SmartDashboard.putString("Param 1 - targetValue", parameters[0]);
    SmartDashboard.putString("Param 2 - motorSpeed", parameters[1]);
    SmartDashboard.putString("Param 3 - newTargetYaw", parameters[2]);
    SmartDashboard.putString("Param 4 - ultrasonicTarget", parameters[3]);
    SmartDashboard.putString("Param 5 - collisionToggle", parameters[4]);

  }

  @Override
  public void updateParams() {
    // TODO Auto-generated method stub
    String param1;
    String param2;
    String param3;
    String param4;
    String param5;

    param1 = SmartDashboard.getString("Param 1 - targetValue");
    parameters[0] = param1;
    param2 = SmartDashboard.getString("Param 2 - motorSpeed");
    parameters[1] = param2;
    param3 = SmartDashboard.getString("Param 3 - newTargetYaw");
    parameters[2] = param3;
    param4 = SmartDashboard.getString("Param 4 - ultrasonicTarget");
    parameters[3] = param4;
    param5 = SmartDashboard.getString("Param 5 - collisionToggle");
    parameters[4] = param5;
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

  @Override
  public void reset() {
    // TODO Auto-generated method stub

  } 
  
  @Override
  public void init() {
    // TODO Auto-generated method stub
    
  }
  
}
