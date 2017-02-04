package org.usfirst.frc.team238.robot;
import org.usfirst.frc.team238.core.Logger;

import java.util.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ControlBoard { 
	//Here are the joysticks for controlling  the robot
	static Joystick manualOverrideJs; 	// operator manual overide
	static Joystick operatorJs;  	// operator control board
	private static Joystick driverLeftJs; 	// driveTrain left
	private static Joystick driverRightJs; 	// driveTrain right
	static Joystick xboxController;
	
	HashMap<Integer, Integer> controllers; /*This is the list that contains the locations
						for the controllers*/
	
	boolean isXBoxController;
	//look at using more sophisticated collection classes
	static int commands[];
	static int [] xBoxToJsCmdMapping= { 0, 11, 10, 2, 3, 7, 6};
	
	public void controlBoardInit()
	{
		try
		{
			manualOverrideJs = new Joystick(0);
			operatorJs = new Joystick(1);
			setDriverLeftJs(new Joystick(2));
			setDriverRightJs(new Joystick(3));
		
			//array that holds the command sent by each control device
			commands = new int[5];
			
			controllers = new HashMap<Integer, Integer>(5);
			
			
		}
		
		catch(Exception ex)
		{
			Logger.logString("ControlBoard Init Failed");
		}
	}
	
	
	
	/**
	 * loops thru all the buttons on the joystick until it gets to the one that is pressed
	 * works as long as we only need one button pressed at a time, if we need  more than one
	 * button we'll need to create an array of commands.... int command[]
	 * @return command value
	 */
	public int getCommand(Joystick theJoyStick){
		int command;
		boolean jsButtonValue = false;
		
		int interator = theJoyStick.getButtonCount(); 
		
		//interator = 11 and buttons do not count from zero
		for(command = 1; command <= interator; command++){
			jsButtonValue = theJoyStick.getRawButton(command);
			if(jsButtonValue){
				break;
			}
		}
		if(!jsButtonValue){
			command = 0;
		}
		
		if(isXBoxController)
		{
			command =  xBoxToJsCmdMapping[command];
		}
		
		return command;
	}
	
	/**
	 * 
	 * @param theJoyStick
	 * @return
	 */
	public int getDriverCommand(Joystick theJoyStick){
		int command = 0;
		double zPos =  0.0;
		
		if(theJoyStick.getRawButton(1))
		{
			command = 1;
		}
		else if (theJoyStick.getRawButton(2))
		{
			command = 2;
		}
		else if(theJoyStick.getRawButton(3))
		{
			command = 3;
		}
		else if(theJoyStick.getRawButton(4))
		{
			command = 4;
		}
		

		
		return command;
	}
	
	//populates each array element with the corresponding value for the joy stick
	public HashMap<Integer, Integer> getCommands(){
		
		/*commands[0] = getCommand(manualOverrideJs);
		commands[1] = getCommand(operatorJs);
		commands[2] = getDriverCommand(getDriverRightJs());
		commands[3] = getDriverCommand(getDriverLeftJs());
		commands[4] = CrusaderCommon.DRIVE_TRAIN_CMD_IDX;*/
		
		controllers.put(0, getCommand(manualOverrideJs));
		controllers.put(CrusaderCommon.OPR_CMD_LIST, getCommand(operatorJs));
		controllers.put(CrusaderCommon.INPUT_DRIVER_RIGHT_JS, getDriverCommand(getDriverRightJs()));
		controllers.put(CrusaderCommon.INPUT_DRIVER_LEFT_JS, getDriverCommand(getDriverLeftJs()));
		controllers.put(CrusaderCommon.DT_CMD_LIST, CrusaderCommon.DRIVE_TRAIN_CMD_IDX);
		
		return controllers;
		
		//return commands;
		
	}
	
	//gets the y value of the manual overide joy stick to feed to the command controller
	public static double getManualCommandValue()
	{
		return manualOverrideJs.getY();
	}
	
	public static boolean canWeReleaseTheHounds()
	{
		boolean  secondButton = manualOverrideJs.getRawButton(1); //this button may need to change
		return secondButton;
	}
	
	public static boolean resetEncoderValue()
	{
		boolean resetEncoderValue = operatorJs.getRawButton(8);
		Logger.logString("Reset Encoder = " + resetEncoderValue);
		return resetEncoderValue;
	}
	
	
	
	
	public static boolean overRide(){
		boolean overRide = operatorJs.getRawButton(10);
		
		return overRide;
					
	}

	public static Joystick getDriverLeftJs() {
		return driverLeftJs;
	}

	public static void setDriverLeftJs(Joystick driverLeftJs) {
		ControlBoard.driverLeftJs = driverLeftJs;
	}


	public static Joystick getDriverRightJs() {
		return driverRightJs;
	}

	public static void setDriverRightJs(Joystick driverRightJs) {
		ControlBoard.driverRightJs = driverRightJs;
	}
	
	public void checkXboxController()
	{
		isXBoxController = operatorJs.getIsXbox();
	}

	public static Joystick getOperatorLeftJs() {
		return manualOverrideJs;
	}
	
	public static Joystick getOperatorRightJs() {
		return operatorJs;
	}
	
	public static double getHangerRightSide() {
		
		double value = 0;
		
		if(operatorJs.getRawButton(5))
		{
			value = operatorJs.getY();
		}
			
		return value;
	}
	
	public static double getHangerLeftSide() {
		
		double value = 0;
		
		if(operatorJs.getRawButton(4))
		{
			value = operatorJs.getY();
		}
			
		return value;
	}
}
