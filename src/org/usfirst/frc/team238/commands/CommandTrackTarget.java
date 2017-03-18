package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;

public class CommandTrackTarget extends AbstractCommand {

	Drivetrain myRobotDrive;
	Navigation myNavigation;
	Vision myVision;
	double realAngle = 0;
	
	double slowSide = 0;
	
	double paramMotorValue = 0;
	
	double pickASide;
	
	double leftMotor = 0;
	double rightMotor = 0;
	
	int count = 0;
	
	
	
	public CommandTrackTarget(Drivetrain robotDrive, Navigation myNavigationForTarget, Vision theVision) {
		// TODO Auto-generated constructor stub
		this.myNavigation = myNavigationForTarget;
		this.myRobotDrive = robotDrive;
		this.myVision = theVision;
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		double[] visionAngle;
		
		visionAngle = myVision.getTheData();
		
		realAngle = visionAngle[CrusaderCommon.VISION_ANGLE_SLOT ] / 2;
		//Logger.logDouble("Real Angle Is = ", realAngle);
		
		myNavigation.setTargetValues(realAngle);
		 
		Logger.Log("CommandTrackTarget(): Target Value is = "+ realAngle);
		
		if(visionAngle[CrusaderCommon.VISION_ANGLE_SLOT] == Double.MAX_VALUE)
		{
			//If we don't see the target, a param will tell us which way to look
			if(pickASide == 0)
			{
				//scan right to find the target
				realAngle = 30;
				
			}
			else
			{
				//scan left to find the target
				realAngle = -30;
				
			}
			
		}
		
		if(realAngle > 0)
		{
			// Turn Right
			
			if(realAngle > 10)
			{
			  
				slowSide = 0.5;
				
			}else if(realAngle > 0){
			  
				getSlowSide();
				
			}
			
			leftMotor = paramMotorValue;
			rightMotor = slowSide;
			
		}else if(realAngle < 0){
			// Turn Right
			
			if(realAngle < -10)
			{
			  
				slowSide = 0.5;
				
			}else if(realAngle < 0){
			  
				getSlowSide();
				
			}
			
			leftMotor = slowSide;
			rightMotor = paramMotorValue;
			
		}
		
		else
		{
			//If we are at zero, we stop
			leftMotor = 0;
			rightMotor = 0;
			
		}
		
		if(slowSide > 1){
			
			//If the motor value goes over one we stop the motors
			rightMotor = 0;
			leftMotor = 0;
			
		}
		
		Logger.Log("CommandTrackTarget(): The Slow Side is = "+ slowSide);
		
		myRobotDrive.driveForward(leftMotor, rightMotor);
		
		myNavigation.zeroYaw();
		
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		myNavigation.zeroYaw();
	}
	
	public double getSlowSide()
	{
		double slowSideCalc;
		
		slowSideCalc = ((realAngle / 100) * 5);
		slowSide = paramMotorValue - Math.abs(slowSideCalc);
		
		return slowSide;
	}

	public void setParams(String params[]) throws Exception {
		// TODO Auto-generated method stub
		 
			if ((params[0] != null) || (!params[0].isEmpty())){
				pickASide = Double.parseDouble(params[0]);
			}
			else 
			{
			
				throw new Exception("CommandTrackTarget(): Bad Param in trackTarget, Please Fix");
				
			}
			if ((params[1] != null) || (!params[1].isEmpty())){
				paramMotorValue = Double.parseDouble(params[1]);
			}
			else {
				paramMotorValue = 1;
			}
			
			// 0 = you are on the left, want to turn right
			// 1 = you are on the right, want to turn left
		 
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		
		if(realAngle == 0)
		{
			
			count++;
			
			if(count >20)
			{
			  
				Logger.Log("CommandTrackTarget(): We Have Finished!");
				return true;
			
			}
			
		}
		
		return false;
	}

}
