package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.commands.CommandShiftHigh;
import org.usfirst.frc.team238.commands.CommandShiftLow;
import org.usfirst.frc.team238.commands.CommandTankDrive;
import org.usfirst.frc.team238.commands.NoDriverCommand;
import org.usfirst.frc.team238.robot.Drivetrain;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriverCommandFactory {

	NoDriverCommand NoDriveCommand;
	CommandShiftHigh commandShiftHigh;
	CommandShiftLow commandShiftLow;
	HashMap <Integer, Command> driverLeftCommands;
	HashMap <Integer, Command> driverRightCommands;
	HashMap <Integer, Command> driverCommands;
	
	public void init(){
		driverLeftCommands = new HashMap<Integer, Command>(10);
		driverRightCommands = new HashMap<Integer, Command>(10);
		driverCommands = new HashMap<Integer, Command>(10);
	}
	
	
	public HashMap<Integer, Command> createDriverLeftCommands(Drivetrain driveTrain){
		
		NoDriveCommand = new NoDriverCommand();
		
		driverLeftCommands.put(0, NoDriveCommand);
		
		commandShiftLow = new CommandShiftLow(driveTrain);
		
		driverLeftCommands.put(1, commandShiftLow);
		
		return driverLeftCommands;
		
	}

	public HashMap<Integer, Command> createDriverRightCommands(Drivetrain driveTrain){
		
		NoDriveCommand  = new NoDriverCommand();
		
		driverRightCommands.put(0, NoDriveCommand);
		
		commandShiftHigh = new CommandShiftHigh(driveTrain);
				
		driverRightCommands.put(1, commandShiftHigh);
		
		return driverRightCommands;
		
	}
	
public HashMap<Integer, Command> createDriverCommands(RobotDrive myRobotDrive){
		
	 	CommandTankDrive cmdToDriveTheRobot = new CommandTankDrive(myRobotDrive);
		
		
		driverCommands.put(0, cmdToDriveTheRobot);
		
		return driverCommands;
		
	}
}
