package org.usfirst.frc.team238.robot;

import edu.wpi.first.wpilibj.Servo;

public class HopperDoor {

  Servo hopperServo;
  
  public HopperDoor() {
    // TODO Auto-generated constructor stub
  }
  
  public void init()
  {
    
    //hopperServo = new Servo(CrusaderCommon.HOPPER_DOOR_SERVO_ID);
    
  }
  
  public void openDoor()
  {
    
    hopperServo.set(CrusaderCommon.SERVO_ON);
    
  }
  
  public void closeDoor()
  {
    hopperServo.set(CrusaderCommon.SERVO_OFF);
  }

}
