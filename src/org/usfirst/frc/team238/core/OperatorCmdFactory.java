package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.robot.Climber;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.robot.FuelHandler;

import org.usfirst.frc.team238.commands.CommandResetPcm;
import org.usfirst.frc.team238.commands.CommandRunElevator;
import org.usfirst.frc.team238.commands.CommandTrackTargetRight;
import org.usfirst.frc.team238.commands.CommandTrackTarget;
import org.usfirst.frc.team238.commands.CommandRunShooter;
import org.usfirst.frc.team238.commands.CommandStartClimber;
import org.usfirst.frc.team238.commands.CommandStartIntake;
import org.usfirst.frc.team238.commands.CommandStartSerializer;
import org.usfirst.frc.team238.commands.CommandStopClimber;
import org.usfirst.frc.team238.commands.CommandStopElevator;
import org.usfirst.frc.team238.commands.CommandStopIntake;
import org.usfirst.frc.team238.commands.CommandStopSerializer;


public class OperatorCmdFactory {

	CommandResetPcm commandResetPcm;
	
	CommandTrackTargetRight commandTrackRight;
	
	CommandTrackTarget commandTrackLeft;
	
	CommandRunShooter commandRunShooter;

	CommandStartClimber commandRunClimber;
	
	CommandStopClimber commandStopClimber;
	
	CommandStartIntake commandRunIntake;
	
	CommandStopIntake commandStopIntake;
	
	CommandRunElevator commandRunElevator;
	
	CommandStopElevator commandStopElevator;
	
	CommandStartSerializer commandRunSerializer;
	
	CommandStopSerializer commandStopSerializer;
	
	HashMap <Integer, Command> operatorCommands;
	
	
	public void init(){
	
	operatorCommands = new HashMap<Integer, Command>(16);
	
	}
	
	public HashMap<Integer, Command> createOperatorCommands(Drivetrain driveTrain,
	    Navigation theNavigation, Vision theVision, FuelHandler myFuelHandler, Climber theClimber){
	
		commandRunShooter = new CommandRunShooter(myFuelHandler);
		operatorCommands.put(1, commandRunShooter);
		
		commandRunClimber = new CommandStartClimber(theClimber);
		operatorCommands.put(2, commandRunClimber);
		
		commandStopClimber = new CommandStopClimber(theClimber);
		operatorCommands.put(3, commandStopClimber);
		
		commandRunIntake = new CommandStartIntake(myFuelHandler.theIntake);
		operatorCommands.put(4, commandRunIntake);
		
		commandStopIntake = new CommandStopIntake(myFuelHandler.theIntake);
		operatorCommands.put(5, commandStopIntake);
		
		commandRunElevator = new CommandRunElevator(myFuelHandler.theElevator);
		operatorCommands.put(6, commandRunElevator);

		commandStopElevator = new CommandStopElevator(myFuelHandler.theElevator);
		operatorCommands.put(7, commandStopElevator);
		
		commandRunSerializer = new CommandStartSerializer(myFuelHandler.theSerializer);
		operatorCommands.put(8, commandRunSerializer);
	
		commandStopSerializer = new CommandStopSerializer(myFuelHandler.theSerializer);
		operatorCommands.put(9, commandStopSerializer);
		
		commandTrackLeft = new CommandTrackTarget(driveTrain, theNavigation, theVision);
		operatorCommands.put(10, commandTrackLeft);
		
		commandTrackRight = new CommandTrackTargetRight(driveTrain, theNavigation, theVision);
		operatorCommands.put(11, commandTrackRight);
		
		return operatorCommands;
	
	
	}
	
}
