package org.usfirst.frc.team238.core;


import java.util.HashMap;
//import org.usfirst.frc.team238.robot.AutonomousDrive;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Robot;

import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team238.robot.FuelHandler;
import org.usfirst.frc.team238.robot.Climber;
import org.usfirst.frc.team238.robot.Vision;

public class CommandController {
	AutonomousCmdFactory theRouge;
	DriverCommandFactory  theDriverCommandFactory;
	OperatorCmdFactory theOperatorCmdFactory;
	
	HashMap<String, Command> autoCmdList;
	HashMap<Integer, Command> driverCmdList; 
	HashMap<Integer, Command> driverLeftCmdList;
	HashMap<Integer, Command> driverRightCmdList;
	HashMap<Integer, Command> operatorCmdList;
	
	HashMap<Integer, Command> commandValue;
	
	public void  init(RobotDrive myRobotDrive,/* AutonomousDrive autonomousDrive,*/ 
	    Drivetrain driveTrain, Navigation myNavigation, Vision myVision, 
	    FuelHandler theFuelHandler, Climber myClimber, Robot myRobot)
	{
		// populate the command lists
		setupOperatorCommands(myNavigation, driveTrain, myVision, theFuelHandler, myClimber, myRobot);
		setupDriverCommands(myRobotDrive, driveTrain, myNavigation,myVision,theFuelHandler);
		setupAutonomousCommands(driveTrain, myNavigation, myVision, myRobot, theFuelHandler);
		
		commandValue = new HashMap<Integer, Command>(8);
	}
	
	//gets an AutoCommand by key name
	public Command getAutoCmd(String cmdName)
	{
		return autoCmdList.get(cmdName);
		
	}
	
	//loads all the autonomous commands from the auto factory
	private void setupAutonomousCommands(Drivetrain driveTrain, Navigation myNavigation, Vision myVision, Robot myRobot,FuelHandler theFuelHandler)
	{
		theRouge = new AutonomousCmdFactory();
		theRouge.init();
		autoCmdList = theRouge.createAutonomousCommands(driveTrain, myNavigation, myVision, myRobot,theFuelHandler);
		
	}
	
	//gets a Driver Command by key name
	public Command getDriverCmd(String cmdName)
	{
		return driverCmdList.get(cmdName);
	}
	
	private void setupDriverCommands( RobotDrive myRobotDrive, Drivetrain driveTrain,Navigation myNavigation, Vision myVision, FuelHandler myFuelHandler)
	{
		theDriverCommandFactory = new DriverCommandFactory();
		theDriverCommandFactory.init();
		
		driverLeftCmdList = theDriverCommandFactory.createDriverLeftCommands(driveTrain,myNavigation,myVision);
		driverRightCmdList = theDriverCommandFactory.createDriverRightCommands(driveTrain,myNavigation,myVision,myFuelHandler);
		driverCmdList = theDriverCommandFactory.createDriverCommands(myRobotDrive);
	}
	
	//gets Operator Commands by name
	public Command getOperatorCmd(int cmdName)
	{
		return operatorCmdList.get(cmdName);
	}
	
	private void setupOperatorCommands(Navigation myNavigation, Drivetrain driveTrain, Vision myVision,
	    FuelHandler theFuelHandler, Climber theClimber, Robot myRobot)
	{
		theOperatorCmdFactory = new OperatorCmdFactory();
		theOperatorCmdFactory.init();
		
		operatorCmdList = theOperatorCmdFactory.createOperatorCommands(driveTrain, myNavigation, myVision, 
		                                              theFuelHandler, theClimber, myRobot);
	}

	/*
	 * Gets the buttons that are pressed or switches that are set from the controls (joysticks or custom)  
	 * which the values  ( button1 = 1 etc) are the key into a Map of commands that have been pre-loaded 
	 * in the setup methods,  then "get"s the command associated with the key in the hashmap and calls the execute function on that command.
	 */
	/*public void buttonPressed(int[] commandValue){
		
		Command commandForTheButtonPressed;
		Integer buttonPressed = commandValue(CrusaderCommon.INPUT_DRIVER_LEFT_JS);
		
		commandForTheButtonPressed = driverLeftCmdList.get(buttonPressed);
		if(commandForTheButtonPressed != null){
			commandForTheButtonPressed.execute();
		}
		
		buttonPressed = commandValue[CrusaderCommon.INPUT_DRIVER_RIGHT_JS];
		commandForTheButtonPressed = driverRightCmdList.get(buttonPressed);
		if(commandForTheButtonPressed != null){
			commandForTheButtonPressed.execute();
		}
		
		buttonPressed = commandValue[CrusaderCommon.DT_CMD_LIST];		
		commandForTheButtonPressed = driverCmdList.get(buttonPressed); 
		if(commandForTheButtonPressed != null){
			commandForTheButtonPressed.execute();
		}
		
		buttonPressed = commandValue[CrusaderCommon.OPR_CMD_LIST];		
		commandForTheButtonPressed = operatorCmdList.get(buttonPressed); 
		if(commandForTheButtonPressed != null){
			commandForTheButtonPressed.execute();
		}
	}*/
	
	public void buttonPressed(HashMap<Integer, Integer> commandValue){
			
			Command commandForTheButtonPressed;
			Integer buttonPressed = commandValue.get(CrusaderCommon.INPUT_DRIVER_LEFT_JS);
			
			
			
			commandForTheButtonPressed = driverLeftCmdList.get(buttonPressed);
			if(commandForTheButtonPressed != null){
				
				commandForTheButtonPressed.execute();
			}
			
			
			
			buttonPressed = commandValue.get(CrusaderCommon.INPUT_DRIVER_RIGHT_JS);
			commandForTheButtonPressed = driverRightCmdList.get(buttonPressed);
			if(commandForTheButtonPressed != null){
				//Logger.logInt("buttonPressed driver right : ", buttonPressed);
				commandForTheButtonPressed.execute();
			}
			
			
			
			buttonPressed = commandValue.get(CrusaderCommon.DT_CMD_LIST);
			//Logger.logInt("buttonPressed : ", buttonPressed);
			commandForTheButtonPressed = driverCmdList.get(buttonPressed); 
			if(commandForTheButtonPressed != null){
				//Logger.logInt("buttonPressed(in if statement) : ", buttonPressed);
				commandForTheButtonPressed.execute();
			}
			
			
			
			buttonPressed = commandValue.get(CrusaderCommon.OPR_CMD_LIST);		
			commandForTheButtonPressed = operatorCmdList.get(buttonPressed); 
			if(commandForTheButtonPressed != null){
				commandForTheButtonPressed.execute();
			}
			
			
		}
	
	//Edit to send to github

	
}
