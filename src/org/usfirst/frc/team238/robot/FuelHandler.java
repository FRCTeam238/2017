package org.usfirst.frc.team238.robot;

import org.usfirst.frc.team238.robot.Serializer;
import org.usfirst.frc.team238.robot.Elevator;
import org.usfirst.frc.team238.robot.Intake;
import org.usfirst.frc.team238.robot.Climber;

public class FuelHandler {

  Serializer theSerializer;
  Elevator theElevator;
  Intake theIntake;
  Climber theClimber;
  
  public void init(){
    
    theSerializer = new Serializer();
    theSerializer.init();
    
    theElevator = new Elevator();
    theElevator.init();
    
    theIntake = new Intake();
    theIntake.init();
    
    theClimber = new Climber();
    theClimber.init();
    
  }
  
  public void test(){
    
    theSerializer.test();
    theElevator.test();
    theIntake.test();
    theClimber.test();
    
  }
  
}
