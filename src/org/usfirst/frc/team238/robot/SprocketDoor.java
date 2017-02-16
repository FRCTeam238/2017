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
    
    sprocketServoOne = new Servo(CrusaderCommon.SPROCKET_SERVO_ONE);
    sprocketServoTwo = new Servo(CrusaderCommon.SPROCKET_SERVO_TWO);
    
  }
  
  public void openDoor()
  {
    
    sprocketServoOne.set(CrusaderCommon.SERVO_ON);
    sprocketServoOne.set(CrusaderCommon.SERVO_ON);
    
  }
  
  public void closeDoor()
  {
    
    sprocketServoOne.set(CrusaderCommon.SERVO_OFF);
    sprocketServoTwo.set(CrusaderCommon.SERVO_OFF);
    
  }

}

