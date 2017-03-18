package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.robot.Serializer;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Intake;

import org.usfirst.frc.team238.robot.Shooter;
import org.usfirst.frc.team238.robot.HopperDoor;

/**
 * this is the FuelHandler class.
 * @author Crusader
 */
public class FuelHandler {

  public Serializer theSerializer;
  public Intake theIntake;
  public Shooter theShooter;
  public HopperDoor theHopperDoor;
  
  //------------------ Instance Variables------------------//
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
  int count = 0;
  double delayMotorStart = 0;
  /**
   * 
   */
  public void init(){
    
    theSerializer = new Serializer();
    theSerializer.init();
    
    theIntake = new Intake();
    theIntake.init();
    
    theShooter = new Shooter();
    theShooter.init();
    
    theHopperDoor = new HopperDoor();
    theHopperDoor.init();
    

    
  }
  /**
   * this method tests the intake, the serializer, and the shooter.
   */
  public void test(){
    
    theSerializer.test();
    theIntake.test();
    theShooter.test();
    
  }
  
  /**
   *  This is the function that has the logic for shooting
   *  @param shooterRPM double.
   *  @param serializerDelayy double.
   */
  public void shoot(double shooterRPM,double serializerDelay)
  {
    
    double counterCurrentTime;
    double delayCount;
    
      //runs the shooter motors
      theShooter.execute(shooterRPM);
      
      //Checks to see if the shooter is at speed before firing      
      if (delayMotorStart == 0)
      {
        delayMotorStart = System.currentTimeMillis();
      }

      counterCurrentTime = System.currentTimeMillis();
      delayCount = counterCurrentTime - delayMotorStart;
      
      //if(theShooter.isShooterAtSpeed(shooterRPM))
      //{
        //runs the serializer if we are not experiencing a current overload
        
        Logger.Log("FuelHandler() : Shoot() : Delay Count Is : " + delayCount);
        Logger.Log("FuelHandler() : Shoot() : SerializerDelay Is : "+ serializerDelay);
        
        boolean delayTimer = delayCount >= serializerDelay;
        
        
        if(delayTimer)
        {
          
          theSerializer.runSerializer();
          Logger.Log("FuelHandler() : Shoot() : We Are Serializing!");
          
        }
        
          
      //}
    //}
  }
  
  /**
   * Stops  all motors
   */
  public void stopEverything()
  {
    
    //theElevator.stopElevator();
    theIntake.IntakeStop();
    theShooter.stopShooter();
    theSerializer.stopSpinning();
    openHopper();
    delayMotorStart = 0;
   
    
  }
  
  /** 
   * opens the hopper door.
   */
  public void openHopper()
  {
    
    theHopperDoor.openDoor();
    
  }
  
  /**
   * closes the hopper door.
   */
  public void closeHopper()
  {
    
    theHopperDoor.closeDoor();
    
  }
  
  public void increaseTen()
  {
    
    
    talonSpeed = talonSpeed + 0.1;
    
    theShooter.setTalonSpeed(talonSpeed);
    
    Logger.Log("FuelHandler() : increaseTen() : SHOOTER SPEED IS   " + talonSpeed);
    
    
  }
  
  public void increaseOne()
  {
    
    talonSpeed = talonSpeed + 0.01;
    
    theShooter.setTalonSpeed(talonSpeed);
    
    Logger.Log("FuelHandler() : increaseOne() : SHOOTER SPEED IS   " + talonSpeed);
    
  }
  
  public void decreaseTen()
  {
    
    
    talonSpeed = talonSpeed - 0.1;
    
    theShooter.setTalonSpeed(talonSpeed);
    
    Logger.Log("FuelHandler() : decreaseTen() : SHOOTER SPEED IS   " + talonSpeed);
    
    
  }
  
  public void decreaseOne()
  {
    
    talonSpeed = talonSpeed - 0.01;   
    
    theShooter.setTalonSpeed(talonSpeed);
    
    Logger.Log("FuelHandler() : decreaseOne() : SHOOTER SPEED IS   " + talonSpeed);
  }
  
  public void serialDecreaseTen()
  {
    
    serializerSpeed = serializerSpeed - 0.7;
    
    theSerializer.setTalonSpeed(serializerSpeed);
    
    Logger.Log("FuelHandler() : serialDecreaseTen() : SERIALIZER SPEED IS   " + serializerSpeed);
    
  }
  
  public void serialIncreaseTen()
  {
    
    serializerSpeed = serializerSpeed + 0.7;
    
    theSerializer.setTalonSpeed(serializerSpeed);
    
    Logger.Log("FuelHandler() : serialIncreaseTen() : SERIALIZER SPEED IS   " + serializerSpeed);
    
  }
  
  /**
   * this method resets the motor.
   */
  public void resetMotor()
  {
    
    talonSpeed = 0;
    serializerSpeed = 0;
    
    theShooter.setTalonSpeed(talonSpeed);
    theSerializer.setTalonSpeed(serializerSpeed);
    
    Logger.Log("FuelHandler() : resetMotor() : SHOOTER SPEED IS   " + talonSpeed);
    Logger.Log("FuelHandler() : resetMotor() : SERIALIZER SPEED IS   " + serializerSpeed);
    
    
  }
  
  /**
   * this method checks if the ring light is on.
   * @return
   */
  public boolean isRingLightOn()
  {
    
    if(theShooter.isRingLightOn == true)
    {
      
      return true;
      
    }else{
      
      return false;
    }
    
  }
  
  /**
   * this method turns the ring light on.
   */
  public void turnOnRingLight()
  {
    
    theShooter.turnOnRingLight();
    
    
  }
  
}
