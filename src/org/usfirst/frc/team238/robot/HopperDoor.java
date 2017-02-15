package org.usfirst.frc.team238.robot;

import edu.wpi.first.wpilibj.Servo;

public class HopperDoor {

  Servo hopperServo;
  
  public HopperDoor() {
    // TODO Auto-generated constructor stub
  }
  
  public void init()
  {
    
    //hopperServo = new Servo(CrusaderCommon.HOPPER_DOOR_ID);
    
  }
  
  public void openDoor()
  {
    
    hopperServo.set(1);
    
  }
  
  public void closeDoor()
  {
    hopperServo.set(0);
  }

}
