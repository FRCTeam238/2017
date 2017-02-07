package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.robot.Serializer;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Elevator;
import org.usfirst.frc.team238.robot.Intake;
import org.usfirst.frc.team238.robot.Shooter;

/**
 * Inits and holds public objects
 * 
 * Objects include:
 * Serializer,
 * Elevator,
 * Intake,
 * Shooter
 * 
 * @author Mike Frye
 *
 */
public class FuelHandler {

  public Serializer theSerializer;
  public Elevator theElevator;
  public Intake theIntake;
  public Shooter theShooter;
  
  public void init(){
    
    try{
      
    theSerializer = new Serializer();
    theSerializer.init();
    
    theElevator = new Elevator();
    theElevator.init();
    
    theIntake = new Intake();
    theIntake.init();
    
    theShooter = new Shooter();
    theShooter.shooterInit();
    
    }catch(Exception e){
      
      Logger.logString("FuelHandler Init has failed!");
      e.printStackTrace();
      
    }
  }
  
  /**
   * Test function for everything relating to any fuel handling
   */
  public void test(){
    
    theSerializer.test();
    theElevator.test();
    theIntake.test();
    theShooter.test();
    
  }
  
}
