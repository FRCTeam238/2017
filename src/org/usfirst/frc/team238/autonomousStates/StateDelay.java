package org.usfirst.frc.team238.autonomousStates;

import org.usfirst.frc.team238.commands.CommandDelay;
import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateDelay implements AutonomousState
{
	
	CommandDelay delay;
	String parameters[];
	
	public void init(String params[], CommandController theMcp)
	{
		delay = (CommandDelay) theMcp.getAutoCmd("CommandDelay");
		parameters = params;
	}
	
	int count;
	int targetValue;

	public void process() 
	{
		delay.execute();
		Logger.logString("delaying");
	}
	public boolean done()  
	{
		if(delay.done() == true)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		delay.setParams(Integer.parseInt(parameters[0]));
		delay.prepare();
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showParams() {
		
		SmartDashboard.putString("Param 1 - targetValue", parameters[0]);
		SmartDashboard.putString("Param 2 - motorSpeed", "0");
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
			if(parameters == null||parameters.length-1 < value){
			output="";
		}else{
			output=parameters[value];
		}
		return output;
	}


}

