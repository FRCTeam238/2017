package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.udpClient;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
	udpClient theClient;
	
	
	double[] data = null;

	
	public void init()
	{
		
		//theClient = new udpClient();
		/*theClient.init();
		theClient.enable();
		theClient.start();*/
		data = new double[2];
		
	}
	
//	public void startClient()
//	{
//		
//		theClient.enable();
//		
//		
//	}
//	
//	public void runClient()
//	{
//		
//		theClient.run();
//		
//	}
//	
//	public void stopClient()
//	{
//		theClient.disable();
//	}
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
	
	/**
	 * this returns an array with gear angles, pretty bad name for this function
	 * considering we have similar functions that get boiler data that
	 * are named appropriately 
	 * @return
	 */
	public double[] getTheData()
	{
		
		
		
		data[0] = SmartDashboard.getNumber("Gear Horizontal", 0);
		data[1] = SmartDashboard.getNumber("Gear Vertical", 0);
		
		
		return data;
		
	}
	
	public double getShooterDistance()
	{
	  
	  double shooterDistance;
	  
	  shooterDistance = SmartDashboard.getNumber("Shooter Distance",0);
	  
	  return shooterDistance;
	  
	  
	}
	
	public double shooterHorizontal()
	{
	  double horizontal;
	  
	  horizontal = SmartDashboard.getNumber("Shooter Horizontal",0);
	  
	  return horizontal;
	  
	}
	
	public double shooterVertical()
	{
	  
	  double vertical;
	  
	  vertical = SmartDashboard.getNumber("Shooter Vertical",0);
	  
	  return vertical;
	  
	}
	
	public double getBoilerAngle()
	{
	  
	  double boilerAngle;
	  
	  boilerAngle = 0;//SmartDashboard.getNumber("Boiler Angle",0);
	  
	  return boilerAngle;
	  
	  
	}

	
	
	
}
