package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.ControlBoard;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {

  CANTalon climberMotorOne;
  CANTalon climberMotorTwo;
  ControlBoard controlBoard;
  
  public boolean currentlyClimbing;
  
  PowerDistributionPanel myPowerDistributionPanel;
  
  public void init(){
    climberMotorOne = new CANTalon(CrusaderCommon.CLIMBER_TALON_ONE);
    climberMotorTwo = new CANTalon(CrusaderCommon.CLIMBER_TALON_TWO);
    myPowerDistributionPanel = new PowerDistributionPanel();
  }
  
  /**
   * Starts the climber
   */
  public void StartClimbing(){
    
    //note original code had a negative sign but practice robot was wired differently
    climberMotorOne.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    climberMotorTwo.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    
    currentlyClimbing = true;
    
    /*if(currentOverLoad()){
      StopClimbing();
    }
    */
  }
  
  public void reverseClimbing()
  {
    if(SmartDashboard.getBoolean("CLIMBDEBUG", false))
    {
      if(controlBoard.overRide())
      {
      
        climberMotorOne.set(-1);
        climberMotorTwo.set(-1);
      }
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
      
    if(count < CrusaderCommon.TEST_COUNT_CONDITION){
       
      if(count >= CrusaderCommon.TEST_COUNT_CONDITION || currentOverLoad()){
        
        StopClimbing();
      }
        count++;
    }
   
    }catch(Exception e){
        e.printStackTrace();
        Logger.Log("Climber() : Test() : Climber has failed!");
    }
    
    Logger.Log("Climber() : Test() : Climber standing by!");    
    
  }
  
  /**
  * A function meant to check if the robot is running into a wall by checking the current output
  * @return
  */
  private boolean currentOverLoad(){ 
   boolean currentOverload = false;
   
   double TotalCurrentDraw = 0;
   
   double ClimberMotorCurrentDrawOne = myPowerDistributionPanel.getCurrent(CrusaderCommon.PDP_CLIMBER_MOTOR_ONE_ID); 
   double ClimberMotorCurrentDrawTwo = myPowerDistributionPanel.getCurrent(CrusaderCommon.PDP_CLIMBER_MOTOR_TWO_ID);

   TotalCurrentDraw = (ClimberMotorCurrentDrawOne+ClimberMotorCurrentDrawTwo)/2;
   
   Logger.Log("Climber() : currentOverLoad() : TotalCurrentDraw is = "+TotalCurrentDraw);
  
   if( TotalCurrentDraw > CrusaderCommon.CURRENT_DRAW_LIMIT) {
   
     currentOverload = true; 
    
   }
   
  return currentOverload; 
  }
  
  public boolean complete(){
    return true;
  }
  
}
