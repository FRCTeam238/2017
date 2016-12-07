package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team238.commands.CommandDriveBackwards;

public class StateDriveBackwards implements AutonomousState {

	CommandDriveBackwards driveBackwards;
	int count = 0;
	/*PURGE*/
	double howfar = 1.0; //This is probably not right, lol
	String parameters[];
	
	@Override
	public void prepare()
	{
		driveBackwards.setParams(parameters);
		driveBackwards.prepare();
		
	}
	
	@Override
	public void init(String params[], CommandController theMcp)
	{
		
		driveBackwards = (CommandDriveBackwards) theMcp.getAutoCmd("CommandDriveBackwards");
		parameters = params;
		
	}
	
	@Override
	public void process() {
		Logger.logInt("StateDriveBackwards.Process()  ", count);
		count++;
		driveBackwards.execute();
	}

	@Override
	public boolean done() {
		if(driveBackwards.done())
		{
			count=0;
			return true;
		}
		
		return false;
	}

	//used when autonomous is interrupted
	@Override
	public void reset() {
		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showParams() {

		SmartDashboard.putString("Param 1 - targetValue", parameters[0]);
		SmartDashboard.putString("Param 2 - motorSpeed", parameters[1]);
		SmartDashboard.putString("Param 3 - rollValue", parameters[2]);
		SmartDashboard.putString("Param 4 - ultrasonicTarget", parameters[3]);
		
	}

	@Override
	public void updateParams() {
		
		String param1;
		String param2;
		String param3;
		String param4;
		
		param1 = SmartDashboard.getString("Param 1 - targetValue");
		parameters[0] = param1;
		param2 = SmartDashboard.getString("Param 2 - motorSpeed");
		parameters[1] = param2;
		param3 = SmartDashboard.getString("Param 3 - rollValue");
		parameters[2] = param3;
		param4 = SmartDashboard.getString("Param 4 - ultrasonicTarget");
		parameters[3] = param4;
	}

	@Override
	public String getParam(int value) {
			String output = "";
			if(parameters == null||parameters.length-1 < value){
			output="";
		}else{
			output=parameters[value];
		}
		return output;
	}

}
