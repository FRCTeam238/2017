package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.commands.CommandResetPcm;
import org.usfirst.frc.team238.commands.CommandTrackTargetRight;
import org.usfirst.frc.team238.commands.CommandTrackTargetLeft;



public class OperatorCmdFactory {

	
	
	CommandResetPcm commandResetPcm;
	
	CommandTrackTargetRight commandTrackRight;
	
	CommandTrackTargetLeft commandTrackLeft;
	
	HashMap <Integer, Command> operatorCommands;

	
	
	public void init(){
	
	operatorCommands = new HashMap<Integer, Command>(16);
	
	}
	
	public HashMap<Integer, Command> createOperatorCommands(Drivetrain driveTrain, Navigation theNavigation, Vision theVision){
	
		
		//commandResetPcm = new CommandResetPcm(theIntake);
		//operatorCommands.put(4, commandResetPcm);
		
		commandTrackRight = new CommandTrackTargetRight(driveTrain, theNavigation, theVision);
		operatorCommands.put(8, commandTrackRight);
		
		commandTrackLeft = new CommandTrackTargetLeft(driveTrain, theNavigation, theVision);
		operatorCommands.put(9, commandTrackLeft);
		
		return operatorCommands;
	
	
	}
	
}
