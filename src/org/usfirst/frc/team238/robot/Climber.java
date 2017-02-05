package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Climber {

  //Replace with CrusaderCommon values
  private static final double ClimbingValue = 0;
  private static final double StopClimbingValue = 0;

  double CurrentDrawLimit = 20.0; // Limit for CurrentDraw
  
  CANTalon climberMotorOne;
  CANTalon climberMotorTwo;
  
  public boolean currentlyClimbing;
  
  PowerDistributionPanel myPowerDistributionPanel;
  
  public void init(){
    climberMotorOne = new CANTalon(11);
    climberMotorTwo = new CANTalon(12);
    myPowerDistributionPanel = new PowerDistributionPanel();
  }
  
  /**
   * Starts the climber
   */
  public void StartClimbing(){
    
    climberMotorOne.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    climberMotorTwo.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    
    currentlyClimbing = true;
    
    if(currentOverLoad()){
      StopClimbing();
    }
    
  }
  
  /**
   * Stops the climber
   */
  public void StopClimbing(){
    
    climberMotorOne.set(CrusaderCommon.INTAKE_MOTOR_OFF);
    climberMotorTwo.set(CrusaderCommon.INTAKE_MOTOR_OFF);
    
    currentlyClimbing = false;
    
  }
  
  /**
   * Tests what this object is meant to do
   */
  public void test(){
    
    try{
      
    int count = 0;
      
    StartClimbing();
      
    if(count < 150){
       
      if(count >= 150 || currentOverLoad()){
        
        StopClimbing();
      }
        count++;
    }
   
    }catch(Exception e){
        e.printStackTrace();
        Logger.logString("Climber has failed!");
    }
    
    Logger.logString("Climber standing by!");    
    
  }
  
  /**
  * A function meant to check if the robot is running into a wall by checking the current output
  * @return
  */
  private boolean currentOverLoad(){ 
   boolean currentOverload = false;
   
   double TotalCurrentDraw = 0;
   
   double ElevatorMotorCurrentDrawOne = myPowerDistributionPanel.getCurrent(11); 
   double ElevatorMotorCurrentDrawTwo = myPowerDistributionPanel.getCurrent(12);

   TotalCurrentDraw = (ElevatorMotorCurrentDrawOne+ElevatorMotorCurrentDrawTwo)/2;
   
   Logger.logString("Elevator: TotalCurrentDraw is = "+TotalCurrentDraw);
  
   if( TotalCurrentDraw > CurrentDrawLimit) {
   
     currentOverload = true; 
    
   }
   
  return currentOverload; 
  }
  
  public boolean complete(){
    return true;
  }
  
}
