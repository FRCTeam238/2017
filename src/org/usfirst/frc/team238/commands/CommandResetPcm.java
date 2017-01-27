package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.robot.Drivetrain;




public class CommandResetPcm extends AbstractCommand {
	
	Drivetrain myDrivetrain;
	
	
	public CommandResetPcm(Drivetrain theDrivetrain)
	{
		this.myDrivetrain = theDrivetrain;
		
	}
	
	public void prepare(){
		
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//myDrivetrain.resetPCM();
	}


	//@Override
	public void execute(double overRideValue) {
		// TODO Auto-generated method stub

	}

	 
	public boolean complete()
	{
		 return myDrivetrain.complete();
	}
	
	
}
