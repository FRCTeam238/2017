package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;


public class HopperDoor {

 // Servo hopperServo;
  
  Solenoid hopperSolenoid;
  
  public HopperDoor() {
    // TODO Auto-generated constructor stub
  }
  
  public void init()
  {
    
    hopperSolenoid = new Solenoid(4);
    //hopperServo = new Servo(CrusaderCommon.HOPPER_DOOR_SERVO_ID);
    
  }
  
  /*THIS WILL NEED TO BE A SOLENOID!!!*/
  public void openDoor()
  {
    
    //hopperServo.set(CrusaderCommon.SERVO_ON);
   hopperSolenoid.set(true);
  }
  
  public void closeDoor()
  {
   // hopperServo.set(CrusaderCommon.SERVO_OFF);
    hopperSolenoid.set(false);
    Logger.Log("We Are CLOSED!!!!");
  }

}
