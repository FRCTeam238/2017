package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.commands.CommandDriveForward;
import org.usfirst.frc.team238.commands.CommandDriveBackwards;
import org.usfirst.frc.team238.commands.CommandTurnLeft;
import org.usfirst.frc.team238.commands.CommandTurnRight;
import org.usfirst.frc.team238.commands.CommandDelay;
import org.usfirst.frc.team238.commands.CommandTrackTarget;

public class AutonomousCmdFactory {
	
	CommandDriveForward autoDriveForward;
	CommandDriveBackwards autoDriveBackwards;
	CommandTurnLeft autoTurnLeft;
	CommandTurnRight autoTurnRight;
	CommandDelay delayCommand;
	CommandTrackTarget trackTargetCommand;
	
	HashMap <String, Command> autonomousCommands;
	//TODO change that static 10
	public void init(){
		autonomousCommands = new HashMap<String, Command>(10);
	}
	
	
	public HashMap<String, Command> createAutonomousCommands(Drivetrain robotDrive, Navigation myNavigation, Vision myVision){
		
		autoDriveForward = new CommandDriveForward(robotDrive, myNavigation);
		autonomousCommands.put("CommandDriveForward", autoDriveForward);
		autoDriveBackwards = new CommandDriveBackwards(robotDrive);
		autonomousCommands.put("CommandDriveBackwards", autoDriveBackwards);
		autoTurnLeft = new CommandTurnLeft(robotDrive, myNavigation);
		autonomousCommands.put("CommandTurnLeft", autoTurnLeft);
		autoTurnRight = new CommandTurnRight(robotDrive, myNavigation);
		autonomousCommands.put("CommandTurnRight", autoTurnRight);
		delayCommand = new CommandDelay(robotDrive);
		autonomousCommands.put("CommandDelay", delayCommand);
		trackTargetCommand = new CommandTrackTarget(robotDrive, myNavigation, myVision);
		autonomousCommands.put("CommandTrackTarget", trackTargetCommand);
		
		
		return autonomousCommands;
		
	}

}
