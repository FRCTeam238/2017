package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.robot.Serializer;
import org.usfirst.frc.team238.robot.Elevator;
import org.usfirst.frc.team238.robot.Intake;

import org.usfirst.frc.team238.robot.Shooter;
import org.usfirst.frc.team238.robot.HopperDoor;

public class FuelHandler {

  public Serializer theSerializer;
  public Elevator theElevator;
  public Intake theIntake;
  public Shooter theShooter;
  public HopperDoor theHopperDoor;
  
  public void init(){
    
    theSerializer = new Serializer();
    theSerializer.init();
    
    theElevator = new Elevator();
    theElevator.init();
    
    theIntake = new Intake();
    theIntake.init();
    
    theShooter = new Shooter();
    theShooter.init();
    
    theHopperDoor = new HopperDoor();
    theHopperDoor.init();
    
  }
  
  public void test(){
    
    theSerializer.test();
    theElevator.test();
    theIntake.test();
    theShooter.test();
    
  }
  
  /**
   *  This is the function that has the logic for shooting*/
  public void shoot()
  {
        //check to see if we are aligned with the targer
        if(theShooter.targetAcquired())
        {
          //runs the shooter motors
          theShooter.execute();
          //Checks to see if the shooter is at speed before firing
          if(theShooter.isShooterAtSpeed())
          {
            //runs the elevator
            theElevator.runElevator();
            //makes sure the elevator is on before turning on the serializer
            if(theElevator.elevatorInUse)
            {
              
              //runs the serializer if we are not experiencing a current overload
              theSerializer.runSerializer();
              
            }
            
          }
        }
  }
  
  //Stops  all motors
  public void stopEverything()
  {
    
    theElevator.stopElevator();
    theIntake.IntakeStop();
    theShooter.stopShooter();
    theSerializer.stopSpinning();
    
  }
  
  public void openHopper()
  {
    
    theHopperDoor.openDoor();
    
  }
  
  public void closeHopper()
  {
    
    theHopperDoor.closeDoor();
    
  }
  
}
