package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.robot.Serializer;
import org.usfirst.frc.team238.robot.Elevator;
import org.usfirst.frc.team238.robot.Intake;
import org.usfirst.frc.team238.robot.Climber;
import org.usfirst.frc.team238.robot.Shooter;

public class FuelHandler {

  public Serializer theSerializer;
  public Elevator theElevator;
  public Intake theIntake;
  public Climber theClimber;
  public Shooter theShooter;
  
  public void init(){
    
    theSerializer = new Serializer();
    theSerializer.init();
    
    theElevator = new Elevator();
    theElevator.init();
    
    theIntake = new Intake();
    theIntake.init();
    
    theClimber = new Climber();
    theClimber.init();
    
    theShooter = new Shooter();
    theShooter.init();
    
  }
  
  public void test(){
    
    theSerializer.test();
    theElevator.test();
    theIntake.test();
    theClimber.test();
    
  }
  
  public void shoot()
  {
    
    
        if(theShooter.targetAcquired())
        {
         
          theShooter.execute();
          //Checks to see if the shooter is at speed before firing
          if(theShooter.isShooterAtSpeed())
          {
            
            theElevator.runElevator();
            
            if(theElevator.elevatorInUse)
            {
              
              theSerializer.startSpinning();
              
              theSerializer.areWeOverloaded();
              
            }
            
          }
        }
  }
  
}
