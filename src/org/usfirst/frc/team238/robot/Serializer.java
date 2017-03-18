package org.usfirst.frc.team238.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.core.Logger;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Serializer {

  CANTalon serializerMotor;

  PowerDistributionPanel myPowerDistributionPanel;

  public boolean spinning;
  
  double talonSpeed;
  
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
    
    if(count < CrusaderCommon.TEST_COUNT_CONDITION){
     
      if(count >= CrusaderCommon.TEST_COUNT_CONDITION || currentOverLoad()){
      
        stopSpinning();
        
      }
      count++;
    }
 
    }catch(Exception e){
      Logger.Log("Serializer(): test(): Exception:"+e);
    }
    
    Logger.Log("Serializer(): test(): Serializer standing by!");
    
  }
  
  /**
   * Starts spinning the serializer
   */
  public void startSpinning(){
    
    serializerMotor.set(CrusaderCommon.SERIALIZER_MOTOR_ON);
    spinning = true;
    
    /*if(currentOverLoad() && timer> currentTimeLimit){
      
      
      
      stopSpinning();
      
    }*/
    
  }
  
  /**
   * Stops spinning the serializer
   */
  public void stopSpinning(){
    
    serializerMotor.set(CrusaderCommon.SERIALIZER_MOTOR_OFF);
    spinning = false;
    
    //timer = 0;
  }
  
  public void runSerializer()
  {
    
//    if(currentOverLoad())
//    {
//      stopSpinning();
//    }
//    else
//    {
      startSpinning();
//   }
    
    
  }
  
  /**
  * A function meant to check if the robot is running into a wall by checking the current output
  * @return
  */
  private boolean currentOverLoad(){ 
   boolean currentOverload = false;
   
   double SerializerCurrentDraw = myPowerDistributionPanel.getCurrent(CrusaderCommon.PDP_SERIALIZER_MOTOR_ID); 
   
   Logger.Log("Serializer(): currentOverLoad(): CurrentDraw is = "+SerializerCurrentDraw);
   
   if( SerializerCurrentDraw > CrusaderCommon.CURRENT_DRAW_LIMIT) {
   
     currentOverload = true;
   
   }
   
   return currentOverload; 
  }
  
  public boolean complete(){
    return true;
  }
  
  public void setTalonSpeed(double speed)
  {
    
    talonSpeed = speed;
    
    serializerMotor.set(talonSpeed);
    
  }
  
}
