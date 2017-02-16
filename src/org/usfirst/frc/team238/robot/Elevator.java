package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;

import com.ctre.CANTalon;

public class Elevator {

  CANTalon elevatorMotor;
  
  boolean elevatorInUse;
  
  public void init(){
    elevatorMotor = new CANTalon(CrusaderCommon.ELEVATOR_TALON);
  }
  
  /**
   * Runs the elevator
   */
  public void runElevator(){
    elevatorMotor.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    elevatorInUse = true;
  }
  
  /**
   * Stops the elevator
   */
  public void stopElevator(){
    elevatorMotor.set(CrusaderCommon.INTAKE_MOTOR_OFF);
    elevatorInUse = false;
  }
  
  /**
   * Tests what this object is meant to do
   */
  public void test(){
    
    try{
      
    int count = 0;
      
    runElevator();
      
    if(count < CrusaderCommon.TEST_COUNT_CONDITION){
       
      if(count >= CrusaderCommon.TEST_COUNT_CONDITION){
        
        stopElevator();
      }
        count++;
    }
   
    }catch(Exception e){
        e.printStackTrace();
        Logger.Log("Elevator has failed!");
    }
    
    Logger.Log("Elevator standing by!");    
    
  }
  
  //ADD OVERLOAD FUNCTION
  
  public boolean preFilled(){
    return false;
  }
  
  public boolean loaded(){
    return false;
  }
  
  public boolean complete(){
    return true;
  }

}
