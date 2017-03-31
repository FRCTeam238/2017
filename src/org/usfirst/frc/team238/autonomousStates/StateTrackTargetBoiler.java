/**
 * 
 */
package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.commands.CommandTrackTargetBoiler;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateTrackTargetBoiler implements AutonomousState {

	
	//parameter[1] = motorValue (Speed)
	//parameter[0] = direction
	
	CommandTrackTargetBoiler trackerBoilerCommand;
	String parameters[];		// 0 = Left
								          // 1 = Right
	int count = 0;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() {
		
	  //For some reason this demands a try/catch here
			try {
        trackerBoilerCommand.setParams(parameters);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        Logger.Log("StateTrackTarget: Exception: "+ e);
      }
			trackerBoilerCommand.prepare();
	}

	@Override
	public void init(String[] params, CommandController theMcp) {

	  trackerBoilerCommand = (CommandTrackTargetBoiler) theMcp.getAutoCmd("CommandTrackTargetBoiler");
		parameters = params;
		
	}

	@Override
	public void process() {
		
	  count++;
	  trackerBoilerCommand.execute();
		Logger.Log("StateTrackTarget.Process(): "+count);
		
	}

	@Override
	public boolean done() {
		
		 if(trackerBoilerCommand.done() == true)
		 {
		   count = 0;
		 	return true;
		 }
		 else
		 {
		 	return false;
		 }
		 
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showParams() {
		SmartDashboard.putString("Param 1 - targetValue", parameters[0]);
		SmartDashboard.putString("Param 2 - motorSpeed", parameters[1]);
		SmartDashboard.putString("Param 3 - newTargetYaw", "0");
		SmartDashboard.putString("Param 4 - ultrasonicTarget", "0");
	}

	@Override
	public void updateParams() {

		String param1;
		String param2;
		param1 = SmartDashboard.getString("Param 1 - targetValue");
		param2 = SmartDashboard.getString("Param 2 - motorSpeed");
		parameters[0] = param1;
		parameters[1] = param2;
		
	}

	@Override
	public String getParam(int value) {
		String output = null;
		if(parameters.length-1 < value||parameters == null){
			output="";
		}else{
			output=parameters[value];
		}
		return output;
	}

}
