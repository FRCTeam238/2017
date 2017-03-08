package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.robot.Climber;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.testCommands.CommandDeccrementSerializer;
import org.usfirst.frc.team238.testCommands.CommandDeccrementTestDriveWithButtons;
import org.usfirst.frc.team238.testCommands.CommandDecrementOnePercent;
import org.usfirst.frc.team238.testCommands.CommandIncrementOnePercent;
import org.usfirst.frc.team238.testCommands.CommandIncrementSerialaizer;
import org.usfirst.frc.team238.testCommands.CommandIncrementTestDriveWithButtons;
import org.usfirst.frc.team238.testCommands.CommandResetTestDriveWithButtons;
import org.usfirst.frc.team238.testCommands.CommandReverseIntake;
import org.usfirst.frc.team238.robot.FuelHandler;

import org.usfirst.frc.team238.commands.CommandTrackTarget;
import org.usfirst.frc.team238.commands.CommandRunShooter;
import org.usfirst.frc.team238.commands.CommandStartClimber;
import org.usfirst.frc.team238.commands.CommandStartIntake;
import org.usfirst.frc.team238.commands.CommandStartSerializer;
import org.usfirst.frc.team238.commands.CommandStopEverything;
import org.usfirst.frc.team238.commands.CommandAlignToBoiler;
import org.usfirst.frc.team238.commands.CommandCloseHopper;
import org.usfirst.frc.team238.commands.CommandOpenHopper;

public class OperatorCmdFactory {

	CommandTrackTarget commandTrackTarget;
	
	CommandRunShooter commandRunShooter;

	CommandStartClimber commandRunClimber;
	
	CommandStartIntake commandRunIntake;
	
	CommandStartSerializer commandRunSerializer;
	
	CommandStopEverything commandStopEverything;
	
	CommandOpenHopper commandOpenHopper;
	
	CommandCloseHopper commandCloseHopper;
	
	CommandIncrementTestDriveWithButtons commandIncreaseTen;
	
	CommandIncrementOnePercent commandIncreaseOne;
	
	CommandDeccrementTestDriveWithButtons commandDecreaseTen;
	
	CommandDecrementOnePercent commandDecreaseOne;
	
	CommandResetTestDriveWithButtons commandReset;
	
	CommandDeccrementSerializer commandDecSerial;
	
	CommandIncrementSerialaizer commandIncSerial;
	
	CommandAlignToBoiler commandAlignToBoiler;
	
	CommandReverseIntake commandReverseIntake;
	
	HashMap <Integer, Command> operatorCommands;
	
	
	public void init(){
	
	operatorCommands = new HashMap<Integer, Command>(16);
	
	}
	
	public HashMap<Integer, Command> createOperatorCommands(Drivetrain driveTrain,
	    Navigation theNavigation, Vision theVision, FuelHandler myFuelHandler,
	    Climber theClimber){
	
		commandStopEverything = new CommandStopEverything(myFuelHandler, theClimber);
		operatorCommands.put(0, commandStopEverything);
	  
	  commandRunShooter = new CommandRunShooter(myFuelHandler, theVision);
		operatorCommands.put(1, commandRunShooter);
		
    commandAlignToBoiler = new CommandAlignToBoiler(driveTrain, theVision, theNavigation, myFuelHandler);
    operatorCommands.put(3, commandAlignToBoiler);
    
    commandRunIntake = new CommandStartIntake(myFuelHandler.theIntake);
    operatorCommands.put(6, commandRunIntake);
    
    commandReverseIntake = new CommandReverseIntake(myFuelHandler.theIntake);
    operatorCommands.put(7, commandReverseIntake);
    
    commandRunSerializer = new CommandStartSerializer(myFuelHandler.theSerializer);
    operatorCommands.put(8, commandRunSerializer);
		
		commandRunClimber = new CommandStartClimber(theClimber);
		operatorCommands.put(10, commandRunClimber);
		
		/*commandReverseClimber = new CommandReverseClimber(theClimber);
		operatorCommands.put(11, commandReverseClimber);
		*/
		/*commandStopClimber = new CommandStopClimber(theClimber);
		operatorCommands.put(3, commandStopClimber);*/
		

		
		commandOpenHopper = new CommandOpenHopper(myFuelHandler);
		operatorCommands.put(11, commandOpenHopper);
		
		

		/*commandRunElevator = new CommandRunElevator(myFuelHandler.theElevator);
		operatorCommands.put(6, commandRunElevator);*/
/*
		commandCloseHopper = new CommandCloseHopper(myFuelHandler);
		operatorCommands.put(7, commandCloseHopper);
	*/	

	
		/*commandOpenSprocket = new CommandOpenSprocket(theSprocket);
		operatorCommands.put(9, commandOpenSprocket);*/
		
		/*commandTrackTarget = new CommandTrackTarget(driveTrain, theNavigation, theVision);
		operatorCommands.put(10, commandTrackTarget);*/
		
		/*
		commandIncreaseOne = new CommandIncrementOnePercent(driveTrain, myFuelHandler);
		operatorCommands.put(3, commandIncreaseOne);
		
		commandIncreaseTen = new CommandIncrementTestDriveWithButtons(driveTrain, myFuelHandler);
		operatorCommands.put(5, commandIncreaseTen);
		
		commandDecreaseOne = new CommandDecrementOnePercent(driveTrain, myFuelHandler);
		operatorCommands.put(6, commandDecreaseOne);
		
		commandDecreaseTen = new CommandDeccrementTestDriveWithButtons(driveTrain, myFuelHandler);
		operatorCommands.put(7, commandDecreaseTen);
		
		commandDecSerial = new CommandDeccrementSerializer(driveTrain, myFuelHandler);
		operatorCommands.put(8, commandDecSerial);
		
		commandIncSerial = new CommandIncrementSerialaizer(driveTrain, myFuelHandler);
		operatorCommands.put(9, commandIncSerial);
		
		commandReset = new CommandResetTestDriveWithButtons(driveTrain, myFuelHandler);
		operatorCommands.put(10, commandReset);
		*/
		
		return operatorCommands;
	
	
	}
	
}
