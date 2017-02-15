package org.usfirst.frc.team238.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.core.Logger;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Serializer {

  CANTalon serializerMotor;

  PowerDistributionPanel myPowerDistributionPanel;
  
  double CurrentDrawLimit = CrusaderCommon.CURRENT_DRAW_LIMIT; // Limit for CurrentDraw

  public boolean spinning;
  
  public void init(){
    
    serializerMotor = new CANTalon (CrusaderCommon.SERIALIZER_TALON);
    myPowerDistributionPanel = new PowerDistributionPanel();
    
  }
  
  /**
   * Tests what this object is meant to do
   */
  public void test(){
    try{
      
    int count = 0;
    
    startSpinning();
    
    if(count < 150){
     
      if(count >= 150 || currentOverLoad()){
      
        stopSpinning();
        
      }
      count++;
    }
 
    }catch(Exception e){
      e.printStackTrace();
      Logger.logString("Serializer has failed!");
    }
    
    Logger.logString("Serializer standing by!");
    
  }
  
  /**
   * Starts spinning the serializer
   */
  public void startSpinning(){
    
    serializerMotor.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    spinning = true;
    
    if(currentOverLoad()){
      
      stopSpinning();
      
    }
    
  }
  
  /**
   * Stops spinning the serializer
   */
  public void stopSpinning(){
    
    serializerMotor.set(CrusaderCommon.INTAKE_MOTOR_OFF);
    spinning = false;
    
  }
  
  public void runSerializer()
  {
    
    if(currentOverLoad())
    {
      stopSpinning();
    }
    else
    {
      startSpinning();
    }
    
  }
  
  /**
  * A function meant to check if the robot is running into a wall by checking the current output
  * @return
  */
  private boolean currentOverLoad(){ 
   boolean currentOverload = false;
   
   double SerializerCurrentDraw = myPowerDistributionPanel.getCurrent(9); 

   Logger.logString("Serializer: CurrentDraw is = "+SerializerCurrentDraw);
  
   if( SerializerCurrentDraw > CurrentDrawLimit) {
   
     currentOverload = true; 
    
   }
   
  return currentOverload; 
  }
  
  public boolean complete(){
    return true;
  }
  
}
