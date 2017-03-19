package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Shooter;
import org.usfirst.frc.team238.robot.FuelHandler;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class CommandRunShooter extends AbstractCommand {
  
  FuelHandler myFuelHandler;
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
  /**
   * this is the execute method.
   */
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
      
      if (myFuelHandler.getAlliance() == Alliance.Red)
      {
        shooterSpeed = SmartDashboard.getNumber("Red TESTING RPM", CrusaderCommon.RED_SHOOTER_SPEED); //2900); 
      //shooterSpeed = calculateRPM(Alliance.Red);
      }
      else
      {
        shooterSpeed = SmartDashboard.getNumber("Blue TESTING RPM", CrusaderCommon.BLUE_SHOOTER_SPEED); //2808);
      //shooterSpeed = calculateRPM(Alliance.Blue);
      }
      
      //shooterSpeed = calculateRPM
      myFuelHandler.theShooter.isShooterAtSpeed(shooterSpeed);
      
      SmartDashboard.putNumber("CommandRunShooter: Calculated Shooter Speed", shooterSpeed);
      
      myFuelHandler.theIntake.IntakeIn();
      myFuelHandler.shoot(shooterSpeed, shooterDelayTime);
    }

    
  }
  /**
   * this is the prepare method.
   */
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
  
  
  //AUTO NOTE!!!!!
  //RPM 2808 at hopper 2
  
  
  
  /**
   * this is the setParams method.
   * @param params String
   */
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
   * @return newRPM
   */
  public double calculateRPM()
  {
    
    double newRPM;
    double distance;
    
    distance = myVision.getShooterDistance();
    if(distance != Double.MAX_VALUE)
    {
      newRPM = (((0.0579 * (distance * distance)) - (0.4533 * distance) + (2751)) * 0.5);
    }
    else
    {
      Logger.Log("CommandRunShooter(): calculateRPM(): We Can't Find A Distance!");
      newRPM = (3250 * 0.5);
    }
      
    
    
    return newRPM;
    
  }
  
  
  
  
  public boolean done()
  {
    
    
    
    if(elapsed >= maxShootTime)
    {
      myFuelHandler.shoot(0, 0);
      myFuelHandler.stopEverything();
      return true;
    }
    else
    {
      return false;
    }
    
    
  }

}
