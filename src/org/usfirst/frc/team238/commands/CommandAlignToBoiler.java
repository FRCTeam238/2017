package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.FuelHandler;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandAlignToBoiler extends AbstractCommand {

	Drivetrain myDrivetrain;
	Vision myVision;
	Navigation myNavigation;
	FuelHandler myFuelHandler;

	boolean areWeThere = false;
	double realAngle;
	int count = 0;
	int checkCount = 0;
	boolean done;
	double visionAngle = 0;
	double ringLightTime;
	double imageDelayTimestamp = 0;

	public CommandAlignToBoiler(Drivetrain theDriveTrain, Vision theVision, Navigation theNavigation, FuelHandler theFuelHandler) {

		this.myDrivetrain = theDriveTrain;
		this.myVision = theVision;
		this.myNavigation = theNavigation;
		this.myFuelHandler = theFuelHandler;

	}

	@Override
	public void execute() {
	  if(!myFuelHandler.isRingLightOn())
	  {
	    myFuelHandler.turnOnRingLight();
	  }
		if (Timer.getFPGATimestamp() - ringLightTime < CrusaderCommon.RING_LIGHT_DELAY) {
			return;
		}

		double calculatedMotorValue;

		if (myNavigation.areWeThereYet()) {
			if (imageDelayTimestamp == 0) {
				imageDelayTimestamp = Timer.getFPGATimestamp();
			} else if (Timer.getFPGATimestamp() - imageDelayTimestamp > CrusaderCommon.ALIGN_IMAGE_DELAY)
			// ALIGN_IMAGE_DELAY should be a constant, start with .25?
			{
				imageDelayTimestamp = 0;
				if (myVision.shooterHorizontal() != Double.MAX_VALUE) {
					visionAngle = myVision.shooterHorizontal();
					myNavigation.zeroYaw();
					myNavigation.setTargetValues(visionAngle);
					SmartDashboard.putString("Do We See the Target?", "YES");
				} else {
					Logger.Log("We are NOT seeing the target!");
					SmartDashboard.putString("Do We See the Target?", "NO");
				}
			}

		} else {
			calculatedMotorValue = pidCalc(CrusaderCommon.TURN_P_VALUE, CrusaderCommon.TURN_DEAD_STOP, visionAngle,
					CrusaderCommon.TURN_MAX_ERROR);

			Logger.Log("Turning to vision angle: " + realAngle);

			// If we are at an angle higher than 0, we will turn left to go to 0
			if (visionAngle > 0) {
				myDrivetrain.turnLeft(0.4, 0.4);
			}

			// If we are at an angle lower than 0, we will turn right to go to 0
			else if (visionAngle < 0) {
				myDrivetrain.turnRight(0.4, 0.4);
			}
			// if we are at zero, we will stop
			else {
				myDrivetrain.turnLeft(0, 0);
			}
			// If we dont see the target, we will need to put something on th
			// dashboard that tells us we cant in teleop
			// not sure what to do if we cant see in auto yet :P
		}
	}

	@Override
	public void prepare() {
		// Turn ring light on
		if (!myFuelHandler.isRingLightOn()) {
			ringLightTime = Timer.getFPGATimestamp();
			myFuelHandler.turnOnRingLight();
		} else {
			ringLightTime = 0;
		}
		myNavigation.zeroYaw();
		myNavigation.setTargetValues(0);
		visionAngle = Double.MAX_VALUE;
	}

	@Override
	public void setParams() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done() {
		if (Math.abs(visionAngle) < CrusaderCommon.ALIGN_ANGLE_BUFFER) {
			myDrivetrain.driveForward(0, 0);
			return true;
		} else {
			return false;
		}
	}
}
