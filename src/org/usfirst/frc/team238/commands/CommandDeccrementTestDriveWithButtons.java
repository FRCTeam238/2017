package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.ControlBoard;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandDeccrementTestDriveWithButtons extends AbstractCommand  {

	Drivetrain myRobotDrive;
	
	double motorValue;
	double targetValue;
	double newTargetYaw;
	int count;
	
	public CommandDeccrementTestDriveWithButtons(Drivetrain theRobotDrive)
	{
		
		this.myRobotDrive = theRobotDrive;
		count = 0;

	}

	public void prepare(){
		
		
	}
	
	public void execute()  {
		//Using -motorValues to spin the left motors backwards
		//If that's how it works lol Maybe change this
		Logger.logTwoString("!!!!!DEBUG!!!!!!!!!!!!" , "DECREMENT");
		myRobotDrive.decrementMotorValue(2);
		
	}
	
	public void setParams(String params[])
	{
		Logger.logTwoString("!!!!!DEBUG!!!!!!!!PARAMETERS!!!!" , params[0]);
		
		if ((params[0] != null) || (!params[0].isEmpty())){
			targetValue = Double.parseDouble(params[0])* -1;
		}
		else {
			targetValue = 0;
		}

		if ((params[1] != null) || (!params[1].isEmpty())){
			motorValue = Double.parseDouble(params[1]);
		}
		else {
			motorValue = 1;
		}

		if((params[2] != null) || (!params[2].isEmpty())){
			newTargetYaw = Integer.parseInt(params[2]);
			
		} else {
			newTargetYaw = 0; //Don't turn if there's no input
			
		}
		

		
	}
	
	
	
	public boolean done()
	{
		return true;

	}
	
}