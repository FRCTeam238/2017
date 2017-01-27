package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;

public class CommandTrackTargetLeft extends AbstractCommand {

	Drivetrain myRobotDrive;
	Navigation myNavigation;
	Vision myVision;
	
	double motorValue = 1;
	
	
	
	
	
	public CommandTrackTargetLeft(Drivetrain robotDrive, Navigation myNavigationForTarget, Vision theVision) {
		// TODO Auto-generated constructor stub
		this.myNavigation = myNavigationForTarget;
		this.myRobotDrive = robotDrive;
		this.myVision = theVision;
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		double[] visionAngle;
		double realAngle;
		double slowSide;
		double slowSideCalc;
		
		visionAngle = myVision.getTheData();

		
		
		realAngle = visionAngle[CrusaderCommon.VISION_ANGLE_SLOT ] / 2;
		//Logger.logDouble("Real Angle Is = ", realAngle);
		
		
		
		myNavigation.setTargetValues(realAngle);
		
		Logger.logDouble("Target Value is = ", realAngle);
		
		
		if(Math.abs(realAngle) > 10)
		{
			
			slowSide = 0.5;
			
		}
		else
		{
			
			slowSideCalc = (realAngle / 100 * 5);
			slowSide = 1 - slowSideCalc;
			
		}
		Logger.logDouble("The Slow Side is = ", slowSide);
		
		myRobotDrive.driveForward(-slowSide, -motorValue);
		
		
		
		myNavigation.resetNAVX();
		
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		myNavigation.resetNAVX();
	}

	public void setParams(String params[]) {
		// TODO Auto-generated method stub
		 
		 
		 	

			if ((params[0] != null) || (!params[0].isEmpty())){
				motorValue = Double.parseDouble(params[1]);
			}
			else {
				motorValue = 1;
			}
		 
			
		 
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		
		if(myNavigation.areWeThereYet() == true)
		{
			
			myRobotDrive.driveForward(0, 0); 
			
			Logger.logString("We Have Aligned!");
			
			return true;
			
		}
		
		else
		{
			
			return false;
			
		}
		
		
	}

}
