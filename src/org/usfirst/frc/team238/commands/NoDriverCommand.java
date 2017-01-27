package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.Drivetrain;

public class NoDriverCommand extends AbstractCommand {
	
	Drivetrain myRobotDrive;
	
	public void prepare(){
		
		
		
	}
	
	public NoDriverCommand(Drivetrain theRobotDrive){
		
		// TODO Auto-generated constructor stub
		myRobotDrive = theRobotDrive;
	}

	
	public void execute() {
		myRobotDrive.nobtnPressed();
	}

	
	public void execute(double overRideValue) {
		
		
	}

}
