package org.usfirst.frc.team238.robot;

import com.ctre.CANTalon;

import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.CrusaderCommon;

public class Intake {
  
  //Check CrusaderCommon values
  
  CANTalon intakeMotor;
  
  //If intake is currently running
  public boolean intakeIsOn;
  
  public void init(){
    
    intakeMotor = new CANTalon(CrusaderCommon.INTAKE_TALON);
    
  }
  
  /**
   * Starts the intake
   */
  public void IntakeIn(){
    intakeMotor.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    intakeIsOn = true;
  }
  
  /**
   * Stops the intake
   */
  public void IntakeStop(){
    intakeMotor.set(CrusaderCommon.INTAKE_MOTOR_OFF);
    intakeIsOn = false;
  }
  
  public void IntakeOut(){
    intakeMotor.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_OUT);
    intakeIsOn = true;
  }
  
  /**
   * Tests what this object is meant to do
   */
  public void test(){
    try{
      
    int count = 0;
    
    IntakeIn();
    
    if(count < 150){
     
      if(count >= 150){
      
        IntakeStop();
      }
      count++;
    }
    
    }catch(Exception e){
      e.printStackTrace();
      Logger.logString("Intake has failed!");
    }
    
    Logger.logString("Intake standing by!");
    
  }
  
  public boolean complete(){
    return true;
  }

}
