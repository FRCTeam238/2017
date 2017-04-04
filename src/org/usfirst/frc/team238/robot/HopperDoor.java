package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;


public class HopperDoor {

 // Servo hopperServo;
  
  Solenoid hopperSolenoid;
  Solenoid gearSolenoid;
  
  public HopperDoor() {
    // TODO Auto-generated constructor stub
  }
  
  public void init()
  {
    
    hopperSolenoid = new Solenoid(4);
    gearSolenoid = new Solenoid(5);
    
    openDoor();
  }
  
  /*THIS WILL NEED TO BE A SOLENOID!!!*/
  public void openDoor()
  {
    
    //hopperServo.set(CrusaderCommon.SERVO_ON);
   hopperSolenoid.set(false);
  }
  
  public void closeDoor()
  {
   // hopperServo.set(CrusaderCommon.SERVO_OFF);
    hopperSolenoid.set(true);
    //Logger.Log("We Are CLOSED!!!!");
  }
  
  public void deployGear()
  {
    //Deploys the gear
    gearSolenoid.set(true);
  }
  
  public void undeployGear()
  {
    //this "undeploys" the gear, it just retracts the piston
    gearSolenoid.set(false);
    
  }

}
