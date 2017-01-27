package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.ControlBoard;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandIncrementOnePercent extends AbstractCommand {
	
	Drivetrain myRobotDrive;
	
	double motorValue;
	double targetValue;
	double newTargetYaw;
	int count;

	public CommandIncrementOnePercent(Drivetrain theRobotDrive) {
		// TODO Auto-generated constructor stub
		
		this.myRobotDrive = theRobotDrive;
		count = 0;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Logger.logTwoString("!!!!!DEBUG!!!!!!!!!!!!" , "Increment");
		myRobotDrive.incrementMotorValue(8);
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParams() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return true;
	}

}
