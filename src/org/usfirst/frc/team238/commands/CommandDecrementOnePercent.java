package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.ControlBoard;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.FuelHandler;
import org.usfirst.frc.team238.robot.Navigation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandDecrementOnePercent extends AbstractCommand {
	
	Drivetrain myRobotDrive;
	
	FuelHandler myFuelHandler;
	
	double motorValue = 0;
	double targetValue;
	double newTargetYaw;
	int count;
	int increaseCount = 0;

	public CommandDecrementOnePercent(Drivetrain theRobotDrive, FuelHandler theFuelHandler) {
		// TODO Auto-generated constructor stub
		
		this.myRobotDrive = theRobotDrive;
		this.myFuelHandler = theFuelHandler;
		count = 0;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//Logger.Log("!!!!!DEBUG!!!!!!!!!!!!   " + motorValue);
		
    if(increaseCount > 40)
    {
      myFuelHandler.decreaseOne();
      increaseCount = 0;
    }
    else
    {
      increaseCount++;
    }
		
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
