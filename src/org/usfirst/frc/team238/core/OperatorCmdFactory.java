package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.robot.Climber;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Robot;
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
import org.usfirst.frc.team238.commands.CommandTrackTargetBoiler;
import org.usfirst.frc.team238.commands.CommandMultiButtonTest;
import org.usfirst.frc.team238.commands.CommandRunShooter;
import org.usfirst.frc.team238.commands.CommandStartClimber;
import org.usfirst.frc.team238.commands.CommandStartIntake;
import org.usfirst.frc.team238.commands.CommandStartSerializer;
import org.usfirst.frc.team238.commands.CommandStopEverything;
import org.usfirst.frc.team238.commands.CommandTargetBoiler;
import org.usfirst.frc.team238.commands.CommandAlignToBoiler;
import org.usfirst.frc.team238.commands.CommandCloseHopper;
import org.usfirst.frc.team238.commands.CommandDeployGear;
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
	
	CommandDeployGear commandDepositGear;
	
	CommandIncrementTestDriveWithButtons commandIncreaseTen;
	
	CommandIncrementOnePercent commandIncreaseOne;
	
	CommandDeccrementTestDriveWithButtons commandDecreaseTen;
	
	CommandDecrementOnePercent commandDecreaseOne;
	
	CommandResetTestDriveWithButtons commandReset;
	
	CommandDeccrementSerializer commandDecSerial;
	
	CommandIncrementSerialaizer commandIncSerial;
	
	CommandAlignToBoiler commandAlignToBoiler;
	
	CommandReverseIntake commandReverseIntake;
	
	CommandTargetBoiler commandTargetBoiler;
	
	CommandTrackTargetBoiler commandTrackTargetBoiler;
	
	CommandMultiButtonTest commandMultiButtonsTest;

	
	HashMap<Integer, Command> operatorCommands;
	
	
	public void init()
	{
	
	  operatorCommands = new HashMap<Integer, Command>(16);
	
	}
	
	/**
	 * Operator controls get binded here. Assigning a series of buttons and commands to a HashMap
	 * @param driveTrain
	 * @param theNavigation
	 * @param theVision
	 * @param theFuelHandler
	 * @param theClimber
	 * @param theRobot
	 * @return
	 */
	public HashMap<Integer, Command> createOperatorCommands(Drivetrain driveTrain,
	    Navigation theNavigation, Vision theVision, FuelHandler theFuelHandler,
	    Climber theClimber, Robot theRobot)
	{
	  //Inputs get defined in CrusaderCommon
	  Integer[] multiButtonTestInput = {1,2,3,4,5}; //Test : Button input
	  
	  //Create command objects, passing objects into each of them
	  commandStopEverything = new CommandStopEverything(theFuelHandler, theClimber);
	  commandRunShooter = new CommandRunShooter(theFuelHandler, theVision);
	  commandDepositGear = new CommandDeployGear(theFuelHandler);
	  commandTrackTargetBoiler = new CommandTrackTargetBoiler(driveTrain,theNavigation,theVision,theFuelHandler);
	  commandReverseIntake = new CommandReverseIntake(theFuelHandler.theIntake);
	  commandRunIntake = new CommandStartIntake(theFuelHandler.theIntake);
	  commandRunClimber = new CommandStartClimber(theClimber);
	  commandOpenHopper = new CommandOpenHopper(theFuelHandler);
	  commandCloseHopper = new CommandCloseHopper(theFuelHandler);
	  
	  commandMultiButtonsTest = new CommandMultiButtonTest();	//Test : Just outputs a simple log statement  
	  
	  //Define the command sequences here
	  /*Command[] stopEverythingCommandArray = {commandStopEverything};
	  Command[] runStaticShooterCommandArray = {commandRunShooter};
	  Command[] depositGearCommandArray = {commandDepositGear};
	  Command[] trackTargetBoilerCommandArray = {commandTrackTargetBoiler};
	  Command[] runDynamicShooterCommandArray = {commandRunShooter};
	  Command[] reverseIntakeCommandArray = {commandReverseIntake};
	  Command[] runIntakeCommandArray = {commandRunIntake};
	  Command[] runClimberCommandArray = {commandRunClimber};
	  Command[] openHopperCommandArray = {commandOpenHopper};
	  Command[] closeHopperCommandArray = {commandCloseHopper};
	     
		Command[] twoButtonTestCommandArray = {commandMultiButtonsTest,commandMultiButtonsTest,commandMultiButtonsTest,commandMultiButtonsTest}; //Test : Should output the log statement four times 
	  */
	  //Assigns all command arrays and their specific inputs to the HashMap
	  operatorCommands.put(CrusaderCommon.stopEverythingInput, commandStopEverything);
	  operatorCommands.put(CrusaderCommon.runStaticShooterInput, commandRunShooter);
		operatorCommands.put(CrusaderCommon.depositGearInput, commandDepositGear);
		operatorCommands.put(CrusaderCommon.trackTheBoilerInput, commandTrackTargetBoiler);
    //operatorCommands.put(CrusaderCommon.runDynamicShooterInput, runDynamicShooterCommandArray);
    operatorCommands.put(CrusaderCommon.reverseIntakeInput, commandReverseIntake);
    operatorCommands.put(CrusaderCommon.runIntakeInput, commandRunIntake);
    operatorCommands.put(CrusaderCommon.runClimberInput, commandRunClimber);
    operatorCommands.put(CrusaderCommon.openHopperInput, commandOpenHopper);
    operatorCommands.put(CrusaderCommon.closeHopperInput, commandCloseHopper);
    
    //operatorCommands.put(multiButtonTestInput, twoButtonTestCommandArray); //Test : Command put
		
	  
		return operatorCommands;
		
	}

	/* Older testCommands
	 
	commandOpenSprocket = new CommandOpenSprocket(theSprocket);
  operatorCommands.put(9, commandOpenSprocket);
  
  commandTrackTarget = new CommandTrackTarget(driveTrain, theNavigation, theVision);
  operatorCommands.put(10, commandTrackTarget);
  
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
	
}
