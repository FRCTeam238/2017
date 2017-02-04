package org.usfirst.frc.team238.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team238.robot.CrusaderCommon;

public class Intake {
  
  CANTalon intakeMotor;
  
  public boolean intakeIsOn;
  
  public void init(){
    
    intakeMotor = new CANTalon(9);
    
  }
  
  public void startIntake(){
    intakeMotor.set(CrusaderCommon.INTAKE_MOTOR_ROTATE_IN);
    intakeIsOn = true;
  }
  
  public void stopIntake(){
    intakeMotor.set(CrusaderCommon.INTAKE_MOTOR_OFF);
    intakeIsOn = false;
  }
  
  public boolean complete(){
    return true;
  }

}
