package org.usfirst.frc.team238.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team238.robot.CrusaderCommon;

public class Serializer {
  
  CANTalon serializerMotor;
  
  public void init(){
    
    serializerMotor = new CANTalon (5);
    
  }

}
