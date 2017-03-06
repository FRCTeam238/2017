package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Shooter;
import org.usfirst.frc.team238.robot.FuelHandler;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Elevator;
import org.usfirst.frc.team238.robot.Vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class CommandRunShooter extends AbstractCommand {
  
  FuelHandler myFuelHandler;
  Elevator myElevator;
  Vision myVision;
  
  double shooterSpeed;
  double hoodAngle;
  double visionAngle;
  double ringLightTime = 0;

  double start;
  double elapsed;
  double current;
  double maxShootTime;
  int count = 0;
  
  double shooterDelayTime = 1000;
  
  
  public CommandRunShooter(FuelHandler theFuelHandler, Vision theVision) 
  {
    // TODO Auto-generated constructor stub
    this.myFuelHandler = theFuelHandler;
    this.myVision = theVision;
    
  }
  
  public void execute()
  {
 
    
    if(!myFuelHandler.isRingLightOn())
    {
      myFuelHandler.turnOnRingLight();
     
    }
    
    if(count == 0)
    {
      
      start = System.currentTimeMillis();
      count++;
    }
    else
    {
      current = System.currentTimeMillis();
    }
    
    elapsed = current - start;
    
    //Logger.Log("Elapsed Time is : " + elapsed);
    
    if(Timer.getFPGATimestamp() - ringLightTime > CrusaderCommon.RING_LIGHT_DELAY)
    {
      
      //shooterSpeed = calculateRPM();
    
      myFuelHandler.shoot(SmartDashboard.getNumber("TESTING RPM",1600)/*shooterSpeed*/,shooterDelayTime);//shooterSpeed);
    }
    
  }
  
  public void prepare()
  {
    
    if(!myFuelHandler.isRingLightOn())
    {
      
      ringLightTime = Timer.getFPGATimestamp();
      myFuelHandler.turnOnRingLight();
      
    }
    else
    {
      ringLightTime = 0;
    }

    
  }
  
  public void setParams(String params[])
  {
    
    if ((params[0] != null) || (!params[0].isEmpty())) {
      shooterDelayTime = Double.parseDouble(params[0]);
    } else {
      shooterDelayTime = 1000;
    }

    if ((params[1] != null) || (!params[1].isEmpty())) {
      maxShootTime = Double.parseDouble(params[1]);
    } else {
      maxShootTime = 5000;
    }
    
    
    
  }
  
  /**
   * This is the function that calculates our RPM based on our distance in inches
   * @return
   */
  public double calculateRPM()
  {
    
    double newRPM;
    double distance;
    
    distance = myVision.getShooterDistance();
    if(distance != Double.MAX_VALUE)
    {
      newRPM = (((0.597 * (distance * distance)) - (0.4533 * distance) + (2751)) * 0.5);
    }
    else
    {
      Logger.Log("We Can't Find A Distance!");
      newRPM = (3250 * 0.5);
    }
      
    
    
    return newRPM;
    
  }
  
  
  
  
  public boolean done()
  {
    
    
    
    if(elapsed >= maxShootTime)
    {
      myFuelHandler.shoot(0, 0);
      myFuelHandler.stopEverything();+
      return true;
    }
    else
    {
      return false;
    }
    
    
  }

}
