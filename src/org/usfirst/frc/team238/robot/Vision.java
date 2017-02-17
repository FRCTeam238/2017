package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.udpClient;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
	udpClient theClient;
	
	
	
	
	public void init()
	{
		
		theClient = new udpClient();
		/*theClient.init();
		theClient.enable();
		theClient.start();*/
		
	}
	
	public void startClient()
	{
		
		theClient.enable();
		
		
	}
	
	public void runClient()
	{
		
		theClient.run();
		
	}
	
	public void stopClient()
	{
		theClient.disable();
	}
	/*
	public double getDistance()
	{
		double distance;
		
		double[] data = theClient.getData();
		
		distance = data[1];
		
		return distance;
		
	}
	
	public double getAngle()
	{
		double angle;
		
		double[] data = theClient.getData();
				
		angle = data[0];
		
		return angle;
	}
	*/
	public double[] getTheData()
	{
		
		double[] data = null;
		
		data[0] = SmartDashboard.getNumber("Gear Horizontal");
		data[1] = SmartDashboard.getNumber("Gear Vertical");
		
		
		return data;
		
	}
	
	
	
}
