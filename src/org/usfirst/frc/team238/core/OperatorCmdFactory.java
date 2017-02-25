package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.robot.Climber;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.SprocketDoor;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.robot.FuelHandler;

import org.usfirst.frc.team238.commands.CommandResetPcm;
import org.usfirst.frc.team238.commands.CommandResetTestDriveWithButtons;
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
import org.usfirst.frc.team238.commands.CommandStopEverything;
import org.usfirst.frc.team238.commands.CommandCloseHopper;
import org.usfirst.frc.team238.commands.CommandCloseSprocket;
import org.usfirst.frc.team238.commands.CommandDeccrementSerializer;
import org.usfirst.frc.team238.commands.CommandOpenHopper;
import org.usfirst.frc.team238.commands.CommandOpenSprocket;
import org.usfirst.frc.team238.commands.CommandIncrementTestDriveWithButtons;
import org.usfirst.frc.team238.commands.CommandIncrementOnePercent;
import org.usfirst.frc.team238.commands.CommandIncrementSerialaizer;
import org.usfirst.frc.team238.commands.CommandDeccrementTestDriveWithButtons;
import org.usfirst.frc.team238.commands.CommandDecrementOnePercent;

public class OperatorCmdFactory {

	CommandResetPcm commandResetPcm;
	
	CommandTrackTarget commandTrackTarget;
	
	CommandRunShooter commandRunShooter;

	CommandStartClimber commandRunClimber;
	
	CommandStopClimber commandStopClimber;
	
	CommandStartIntake commandRunIntake;
	
	CommandStopIntake commandStopIntake;
	
	CommandRunElevator commandRunElevator;
	
	CommandStopElevator commandStopElevator;
	
	CommandStartSerializer commandRunSerializer;
	
	CommandStopSerializer commandStopSerializer;
	
	CommandStopEverything commandStopEverything;
	
	CommandOpenHopper commandOpenHopper;
	
	CommandCloseHopper commandCloseHopper;
	
	CommandOpenSprocket commandOpenSprocket;
	
	CommandCloseSprocket commandCloseSprocket;
	
	CommandIncrementTestDriveWithButtons commandIncreaseTen;
	
	CommandIncrementOnePercent commandIncreaseOne;
	
	CommandDeccrementTestDriveWithButtons commandDecreaseTen;
	
	CommandDecrementOnePercent commandDecreaseOne;
	
	CommandResetTestDriveWithButtons commandReset;
	
	CommandDeccrementSerializer commandDecSerial;
	
	CommandIncrementSerialaizer commandIncSerial;
	
	HashMap <Integer, Command> operatorCommands;
	
	
	public void init(){
	
	operatorCommands = new HashMap<Integer, Command>(16);
	
	}
	
	public HashMap<Integer, Command> createOperatorCommands(Drivetrain driveTrain,
	    Navigation theNavigation, Vision theVision, FuelHandler myFuelHandler,
	    Climber theClimber, SprocketDoor theSprocket){
	
		commandStopEverything = new CommandStopEverything(myFuelHandler, theClimber);
		operatorCommands.put(0, commandStopEverything);
	  
	  /*commandRunShooter = new CommandRunShooter(myFuelHandler);
		operatorCommands.put(1, commandRunShooter);*/
		
		commandRunClimber = new CommandStartClimber(theClimber);
		operatorCommands.put(2, commandRunClimber);
		
		/*commandStopClimber = new CommandStopClimber(theClimber);
		operatorCommands.put(3, commandStopClimber);*/
		
		commandRunIntake = new CommandStartIntake(myFuelHandler.theIntake);
		operatorCommands.put(4, commandRunIntake);
		
		/*commandOpenHopper = new CommandOpenHopper(myFuelHandler);
		operatorCommands.put(5, commandOpenHopper);*/
		
		/*commandRunElevator = new CommandRunElevator(myFuelHandler.theElevator);
		operatorCommands.put(6, commandRunElevator);*/

		/*commandCloseHopper = new CommandCloseHopper(myFuelHandler);
		operatorCommands.put(7, commandCloseHopper);*/
		
		/*commandRunSerializer = new CommandStartSerializer(myFuelHandler.theSerializer);
		operatorCommands.put(8, commandRunSerializer);*/
	
		/*commandOpenSprocket = new CommandOpenSprocket(theSprocket);
		operatorCommands.put(9, commandOpenSprocket);*/
		
		/*commandTrackTarget = new CommandTrackTarget(driveTrain, theNavigation, theVision);
		operatorCommands.put(10, commandTrackTarget);*/
		
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
		
		return operatorCommands;
	
	
	}
	
}
