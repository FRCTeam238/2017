package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Elevator {
 
  CANTalon elevatorMotor;
  
  boolean elevatorInUse;
  
  PowerDistributionPanel myPowerDistributionPanel;
  
  public void init(){
    elevatorMotor = new CANTalon(CrusaderCommon.ELEVATOR_TALON);
    myPowerDistributionPanel = new PowerDistributionPanel();
  }
  
  /**
   * Runs the elevator
   */
  public void runElevator(){
    elevatorMotor.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    elevatorInUse = true;
    if(currentOverLoad()){
      stopElevator();
    }
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
  
  /**
   * A function meant to check if the robot is running into a wall by checking the current output
   * @return
   */
   private boolean currentOverLoad(){ 
    boolean currentOverload = false;
    
    double TotalCurrentDraw = 0;
    
    double ElevatorMotorCurrentDrawOne = myPowerDistributionPanel.getCurrent(CrusaderCommon.PDP_ELEVATOR_MOTOR_ID);

    TotalCurrentDraw = ElevatorMotorCurrentDrawOne;
    
    Logger.Log("Climber: TotalCurrentDraw is = "+TotalCurrentDraw);
   
    if( TotalCurrentDraw > CrusaderCommon.PDP_CURRENT_DRAW_LIMIT) {
    
      currentOverload = true; 
     
    }
    
   return currentOverload; 
  }
   
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
