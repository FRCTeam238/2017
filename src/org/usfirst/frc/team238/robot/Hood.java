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
    
    hoodServo.set(1);
    
  }
  
  
  //Moves the hood donw, currently set to zero for testing
  public void moveHoodDown()
  {
    
    hoodServo.set(0);
    
  }
  
  //Test function
  public void test()
  {
    
    int count = 0;
        
        try
        {
          
          Logger.logString("Moving Hood Up");
          if(count < 150)
          {
             
            moveHoodUp();
          
            count++;
            
          }
          
          Logger.logString("Moving Hood Down");
          if(count > 150 && count < 300)
          {
            
            moveHoodDown();
            
            count++;
            
          }
        }
        
        catch(Exception e)
        {
          
          e.printStackTrace();
          Logger.logString("Hood Test Failed!");
          
        }
        
        Logger.logString("Hood Test Sucessful!");
    
  }

}
