package org.usfirst.frc.team238.robot;

import edu.wpi.first.wpilibj.Servo;

public class SprocketDoor {

  public SprocketDoor() {
    // TODO Auto-generated constructor stub
  }
  
  Servo sprocketServoOne;
  Servo sprocketServoTwo;
  
  public void init()
  {
    
    sprocketServoOne = new Servo(CrusaderCommon.SPROCKET_ONE);
    sprocketServoTwo = new Servo(CrusaderCommon.SPROCKET_TWO);
    
  }
  
  public void openDoor()
  {
    
    sprocketServoOne.set(1);
    sprocketServoOne.set(1);
    
  }
  
  public void closeDoor()
  {
    
    sprocketServoOne.set(0);
    sprocketServoTwo.set(0);
    
  }

}

