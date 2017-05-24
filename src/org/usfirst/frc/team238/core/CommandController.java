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

public class CommandController 
{
  //Command factories
  AutonomousCmdFactory theRouge;
	DriverCommandFactory  theDriverCommandFactory;
	OperatorCmdFactory theOperatorCmdFactory;
	
	//Command lists
	HashMap<String, Command> autoCmdList;
	HashMap<Integer, Command> driverCmdList; 
	HashMap<Integer, Command> driverLeftCmdList;
	HashMap<Integer, Command> driverRightCmdList;
	HashMap<Integer[], Command[]> operatorCmdList;
	
	//Controller inputs
	//HashMap<Integer, Command> commandValue;
	
	/**
	 * Populates the command factories with their respective objects
	 * @param myRobotDrive
	 * @param driveTrain
	 * @param myNavigation
	 * @param myVision
	 * @param theFuelHandler
	 * @param myClimber
	 * @param myRobot
	 */
	public void  init(RobotDrive myRobotDrive,/* AutonomousDrive autonomousDrive,*/ 
	    Drivetrain driveTrain, Navigation myNavigation, Vision myVision, 
	    FuelHandler theFuelHandler, Climber myClimber, Robot myRobot)
	{

		setupOperatorCommands(myNavigation, driveTrain, myVision, theFuelHandler, myClimber, myRobot);
		setupDriverCommands(myRobotDrive, driveTrain, myNavigation,myVision,theFuelHandler);
		setupAutonomousCommands(driveTrain, myNavigation, myVision, myRobot, theFuelHandler);
		
		//Doesn't get used
		//commandValue = new HashMap<Integer, Command>(8);
	}
	
	
	/**
	 * Gets an AutoCommand by key name
	 * @param cmdName
	 * @return
	 */
	public Command getAutoCmd(String cmdName)
	{
		return autoCmdList.get(cmdName);
	}
	
	/**
	 * Creates the autonomous command factory and gets the command list that's created in that factory
	 * @param driveTrain
	 * @param myNavigation
	 * @param myVision
	 * @param myRobot
	 * @param theFuelHandler
	 */
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
	
	/**
	 * Creates the driver command factory and gets the command lists that are in the factory
	 * @param myRobotDrive
	 * @param driveTrain
	 * @param myNavigation
	 * @param myVision
	 * @param myFuelHandler
	 */
	private void setupDriverCommands( RobotDrive myRobotDrive, Drivetrain driveTrain,Navigation myNavigation, Vision myVision, FuelHandler myFuelHandler)
	{
		theDriverCommandFactory = new DriverCommandFactory();
		theDriverCommandFactory.init();
		
		driverLeftCmdList = theDriverCommandFactory.createDriverLeftCommands(driveTrain,myNavigation,myVision,myFuelHandler);
		driverRightCmdList = theDriverCommandFactory.createDriverRightCommands(driveTrain,myNavigation,myVision,myFuelHandler);
		driverCmdList = theDriverCommandFactory.createDriverCommands(myRobotDrive);
	}
	
	//Never gets called
	
	//gets Operator Commands by name
	//public Command getOperatorCmd(int cmdName)
	//{
	//	return operatorCmdList.get(cmdName);
	//}
	
	/**
	 * Creates the operator command factory and gets the command list that's created in that factory
	 * @param myNavigation
	 * @param driveTrain
	 * @param myVision
	 * @param theFuelHandler
	 * @param theClimber
	 * @param myRobot
	 */
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
	
	/**
	 * Cycles through the hashmap, checking if a buttonis pressed.
	 * If a button is pressed, grab the command associated with that button and executes it.
	 * @param commandValues
	 */
	public void joyStickCommandExecution(HashMap<Integer, HashMap<Integer, Boolean>> commandValues)
	{
			Command commandForTheButtonPressed;
			Command operatorCommandsForTheButtonPressed[];
			HashMap<Integer, Boolean> buttonPressed;
			
			//Checks for inputs on the left driver joystick
			buttonPressed = commandValues.get(CrusaderCommon.INPUT_DRIVER_LEFT_JS);
			commandForTheButtonPressed = driverLeftCmdList.get(buttonPressed.containsValue(true));
			
			if(commandForTheButtonPressed != null)
			{
				commandForTheButtonPressed.execute();
			}
			
			//Checks for inputs on the right driver joystick
			buttonPressed = commandValues.get(CrusaderCommon.INPUT_DRIVER_RIGHT_JS);
			commandForTheButtonPressed = driverRightCmdList.get(buttonPressed.containsValue(true));
			
			if(commandForTheButtonPressed != null)
			{
				commandForTheButtonPressed.execute();
			}
			
			//Checks for y inputs on both of the driver joysticks
			buttonPressed = commandValues.get(CrusaderCommon.DT_CMD_LIST);
			commandForTheButtonPressed = driverCmdList.get(buttonPressed.containsValue(true)); 
			
			if(commandForTheButtonPressed != null)
			{
				commandForTheButtonPressed.execute();
			}
			
			//Check for inputs on the operator joystick
			buttonPressed = commandValues.get(CrusaderCommon.OPR_CMD_LIST);		
			operatorCommandsForTheButtonPressed = operatorCmdList.get(buttonPressed.containsValue(true)); 
			
			if(commandForTheButtonPressed != null)
			{
			  
			  for(int i = 0; i < operatorCommandsForTheButtonPressed.length; i++)
			  {
			    
			    //Because the shooter command takes button inputs in order to switch between a dynamic and static shooter
			    if(buttonPressed.get(1) == true) 
			    {
			      operatorCommandsForTheButtonPressed[0].execute(1);
			    }
			    else if(buttonPressed.get(5) == true)
			    {
			      operatorCommandsForTheButtonPressed[0].execute(5);
			    } 
			    else
			    {
			      operatorCommandsForTheButtonPressed[i].execute();
			    }
			  }
			}	
	 }
}