package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.Logger;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;

public class Navigation {
	
	AHRS ahrs;
	double currentYaw;
	double currentRoll;
	double targetYaw;
	double ultrasonicDistance;
	
	Ultrasonic myUltrasonic;
	
	public void init()
	{
		
		ahrs = new AHRS(SerialPort.Port.kMXP);
		currentYaw = ahrs.getYaw();
		
		currentRoll = ahrs.getRoll();
		myUltrasonic = new Ultrasonic(9,8);
		myUltrasonic.setEnabled(true);
		myUltrasonic.setAutomaticMode(true);
		
	}
	
	public double getDistanceFromUltrasonic()
	{
		
		ultrasonicDistance = myUltrasonic.getRangeInches();
		SmartDashboard.putNumber("Ultrasonic Distance", ultrasonicDistance);
		
		return ultrasonicDistance;
	
	}
	
	public void resetNAVX(){
		
		ahrs.reset();
		
	}
	
	public double getRoll()
	{
		currentRoll = ahrs.getRoll();
		
		return currentRoll;
	}
	public double getYaw()
	{
		currentYaw = ahrs.getYaw();
		
		return currentYaw;
	}
	
	//NOTE: make a set target for yaw and roll	
	public void setTargetValues(double targetValue)
	{
		
		targetYaw = targetValue;
		
	}
	
	//Values being read from the NavX board
	public void navxValues()
	{
		Timer.delay(0.020);
		
		/*SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
        
		SmartDashboard.putNumber("Gyro_X", ahrs.getRawGyroX());
		SmartDashboard.putNumber("Gyro_Y", ahrs.getRawGyroY());
		SmartDashboard.putNumber("Gyro_Z", ahrs.getRawGyroZ());
		
		SmartDashboard.putNumber("Accel_X", ahrs.getRawAccelX());
		SmartDashboard.putNumber("Accel_Y", ahrs.getRawAccelY());
		SmartDashboard.putNumber("Accel_Z", ahrs.getRawAccelZ());*/
		
        SmartDashboard.putNumber("IMU_Yaw", ahrs.getYaw());
        SmartDashboard.putNumber("IMU_Pitch", ahrs.getPitch());
        SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());
	}
	//Tells us if we are at our target yaw
	public boolean areWeThereYet()
	{
		currentYaw = ahrs.getYaw();
		
		Logger.logTwoDouble("Current Yaw is : ", currentYaw, " \n Target is : ", targetYaw);
		
		if(currentYaw > (targetYaw - 5) && currentYaw < (targetYaw + 5))
		{
			return true;
		}
		else
		{

			return false;
		}
	}
	
}
