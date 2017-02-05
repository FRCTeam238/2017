package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.robot.Shooter;

import org.usfirst.frc.team238.commands.CommandResetPcm;
import org.usfirst.frc.team238.commands.CommandTrackTargetRight;
import org.usfirst.frc.team238.commands.CommandTrackTarget;
import org.usfirst.frc.team238.commands.CommandRunShooter;


public class OperatorCmdFactory {

	CommandResetPcm commandResetPcm;
	
	CommandTrackTargetRight commandTrackRight;
	
	CommandTrackTarget commandTrackLeft;
	
	CommandRunShooter commandRunShooter;
	
	HashMap <Integer, Command> operatorCommands;

	
	
	public void init(){
	
	operatorCommands = new HashMap<Integer, Command>(16);
	
	}
	
	public HashMap<Integer, Command> createOperatorCommands(Drivetrain driveTrain,
	    Navigation theNavigation, Vision theVision, Shooter theShooter){
	
		commandRunShooter = new CommandRunShooter(theShooter);
		operatorCommands.put(1, commandRunShooter);
		
		commandTrackRight = new CommandTrackTargetRight(driveTrain, theNavigation, theVision);
		operatorCommands.put(9, commandTrackRight);
		
		commandTrackLeft = new CommandTrackTarget(driveTrain, theNavigation, theVision);
		operatorCommands.put(8, commandTrackLeft);
		
		return operatorCommands;
	
	
	}
	
}
