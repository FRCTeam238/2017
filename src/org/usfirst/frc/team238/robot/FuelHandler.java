package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.robot.Serializer;
import org.usfirst.frc.team238.core.Logger;
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
  
  
  int counter;
  double protoCounter;
  int delayCounter;
  // need turn left turn right
  int lastBtnPressed;
  int scalefactor;
  int scalefactorOnePercent;
  int btncounter;
  int btncounterDec;
  double talonSpeed = 0;
  double serializerSpeed = 0;
  
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
    
    theShooter.execute();
    theSerializer.runSerializer();
        //check to see if we are aligned with the targer
       /* if(theShooter.targetAcquired())
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
        }*/
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
  
  public void increaseTen()
  {
    
    
    talonSpeed = talonSpeed + 0.1;
    
    theShooter.setTalonSpeed(talonSpeed);
    
    Logger.Log("SHOOTER SPEED IS   " + talonSpeed);
    
    
  }
  
  public void increaseOne()
  {
    
    talonSpeed = talonSpeed + 0.01;
    
    theShooter.setTalonSpeed(talonSpeed);
    
    Logger.Log("SHOOTER SPEED IS   " + talonSpeed);
    
  }
  
  public void decreaseTen()
  {
    
    
    talonSpeed = talonSpeed - 0.1;
    
    theShooter.setTalonSpeed(talonSpeed);
    
    Logger.Log("SHOOTER SPEED IS   " + talonSpeed);
    
    
  }
  
  public void decreaseOne()
  {
    
    talonSpeed = talonSpeed - 0.01;   
    
    theShooter.setTalonSpeed(talonSpeed);
    
    Logger.Log("SHOOTER SPEED IS   " + talonSpeed);
  }
  
  public void serialDecreaseTen()
  {
    
    serializerSpeed = serializerSpeed - 0.7;
    
    theSerializer.setTalonSpeed(serializerSpeed);
    
    Logger.Log("SERIALIZER SPEED IS   " + serializerSpeed);
    
  }
  
  public void serialIncreaseTen()
  {
    
    serializerSpeed = serializerSpeed + 0.7;
    
    theSerializer.setTalonSpeed(serializerSpeed);
    
    Logger.Log("SERIALIZER SPEED IS   " + serializerSpeed);
    
  }
  
  public void resetMotor()
  {
    
    talonSpeed = 0;
    serializerSpeed = 0;
    
    theShooter.setTalonSpeed(talonSpeed);
    theSerializer.setTalonSpeed(serializerSpeed);
    
    Logger.Log("SHOOTER SPEED IS   " + talonSpeed);
    Logger.Log("SERIALIZER SPEED IS   " + serializerSpeed);
    
    
  }
  
}
