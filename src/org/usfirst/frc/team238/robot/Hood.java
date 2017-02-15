package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.Servo;

public class Hood {
  
  Servo hoodServo;
  
  int count = 0;
  
  public Hood() {
    // TODO Auto-generated constructor stub
  }
  
  public void hoodInit()
  {
    
    hoodServo = new Servo(CrusaderCommon.HOOD_SERVO_ID);
    
  }
  
  
  //Moves the hood up, currently set to one for testing
  public void moveHoodUp()
  {
    
    hoodServo.set(CrusaderCommon.SERVO_ON);
    
  }
  
  
  //Moves the hood donw, currently set to zero for testing
  public void moveHoodDown()
  {
    
    hoodServo.set(CrusaderCommon.SERVO_OFF);
    
  }
  
  //Test function
  public void test()
  {
    
    int count = 0;
        
        try
        {
          
          Logger.Log("Moving Hood Up");
          if(count < CrusaderCommon.TEST_COUNT_CONDITION)
          {
             
            moveHoodUp();
          
            count++;
            
          }
          
          Logger.Log("Moving Hood Down");
          if(count > CrusaderCommon.TEST_COUNT_CONDITION && count < (CrusaderCommon.TEST_COUNT_CONDITION*2))
          {
            
            moveHoodDown();
            
            count++;
            
          }
        }
        
        catch(Exception e)
        {
          
          e.printStackTrace();
          Logger.Log("Hood Test Failed!");
          
        }
        
        Logger.Log("Hood Test Sucessful!");
    
  }

}
