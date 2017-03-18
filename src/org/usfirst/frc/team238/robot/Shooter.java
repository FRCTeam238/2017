package org.usfirst.frc.team238.robot;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.VelocityMeasurementPeriod;
import com.ctre.CANTalon;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Hood;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;



/**THIS IS THE CLASS THAT THE SHOOTER MECHANISM IS USING
 * THIS IS DEPENDANT ON IF WE HAVE A TARGET LOCKED
 * WILL HAVE A MANUAL OVERRIDE MODE */
public class Shooter {
  
  CANTalon shooterMaster;
  CANTalon shooterSlave;
  
  Vision shooterVision;
  
  Solenoid shooterRingLight;
  
  Hood theHood;
  
  public double talonSpeed;
  
  int count = 0;
  
  int encoderPosition;
  public boolean isRingLightOn = false;
  
  public Shooter() {
    // TODO Auto-generated constructor stub
  }
  
  //Init method, initializes everything, called in robot init
  public void init()
  {
    
   shooterMaster = new CANTalon(CrusaderCommon.SHOOTER_MASTER_TALON);
   shooterSlave = new CANTalon(CrusaderCommon.SHOOTER_SLAVE_TALON);
   
   shooterVision = new Vision();
   
   theHood = new Hood();
   shooterRingLight = new Solenoid(3);
   
   //sets up slave talon to follow master
   shooterSlave.changeControlMode(TalonControlMode.Follower);
   shooterSlave.set(CrusaderCommon.SHOOTER_MASTER_TALON);
   
   //sets the feedback device to Quad Encoders
   shooterMaster.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
   
   
   
   //configures master talon to use PID values
   configShooterTalons(shooterMaster);
   
  }
  
  //This gets the encoder count from the master talon
  public int getShooterEncPosition()
  {
    
    encoderPosition = shooterMaster.getEncPosition();
    
    Logger.Log("Shooter(): getShooterEncPosition(): Encoder Position"+ encoderPosition);
    
    return encoderPosition;
    
  }
  
  //Resets encoders to zero
  public void resetShooterEncoder()
  {
    shooterMaster.setEncPosition(0);
    
    encoderPosition = shooterMaster.getEncPosition();
    
    Logger.Log("Shooter(): resetShooterEncoder(): Encoder Position"+ encoderPosition);
    
  }
  
  //Moves the shooter motors
  public void execute(double shooterRPM)
  {
    shooterMaster.enableControl();
   
    //smahtDashboard();
    
    shooterMaster.set(shooterRPM);//(CrusaderCommon.SHOOTER_MAX_RPM);
    
  }
  
  /*private void smahtDashboard(){
    double test = SmartDashboard.getNumber("Shooter F Value",0.427);
    Logger.Log("test: " +  test);
    shooterMaster.setF(test); 
    shooterMaster.setP(SmartDashboard.getNumber("Shooter P Value", 0.2)); 
    shooterMaster.setI(SmartDashboard.getNumber("Shooter I Value", 0)); 
    shooterMaster.setD(SmartDashboard.getNumber("Shooter D Value", 1.33));
    
  }*/
  
  public void stopShooter()
  {
    
    //THIS LINE DOES NOT WORK DONT KNOW WHY!!!!!!!!
    //shooterMaster.set(0);
    shooterMaster.disableControl();
    //shooterSlave.disableControl();
    //Logger.Log("We Are STOPPING!!!!!!!");
    shooterRingLight.set(false); 
    isRingLightOn = false;
    
  }
  
  //configures the talons for velocity tuning code
  public void configShooterTalons(CANTalon talon)
  {
    
    talon.reverseSensor(true);
    
    /*This sets the voltage range the talon can use; should be 
     *set at +12.0f and -12.0f*/
     talon.configNominalOutputVoltage(+0.0f, -0.0f);
     talon.configPeakOutputVoltage(+12.0f, -12.0f);
     
     /*This sets the FPID values to correct error in the motor's velocity
      * */
     talon.setProfile(CrusaderCommon.TALON_NO_VALUE);
     
     talon.setF(SmartDashboard.getNumber("Shooter F Value",0.427)); //.3113);
     talon.setP(SmartDashboard.getNumber("Shooter P Value", 0.2)); //.8);//064543);
     talon.setI(SmartDashboard.getNumber("Shooter I Value", 0)); 
     talon.setD(SmartDashboard.getNumber("Shooter D Value", 1.33));
     
     talon.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_10Ms);
     talon.SetVelocityMeasurementWindow(20);
     
     //this set the talon to use speed mode instead of voltage mode
     talon.changeControlMode(TalonControlMode.Speed);
     
    
  }
  
  //Moves the hood up
  public void moveHoodUp()
  {
    
    theHood.moveHoodUp();
    
  }
  
  //Moves the hood up
  public void moveHoodDown()
  {
    
    theHood.moveHoodDown();
    
  }
  
  public boolean targetAcquired()
  {
    
    double angle = shooterVision.getTheData()[CrusaderCommon.VISION_ANGLE_SLOT];
    
    if(angle > -CrusaderCommon.SHOOTER_VISION_DEADZONE && angle < CrusaderCommon.SHOOTER_VISION_DEADZONE)
    {
      
      return true;
      
    }
    else
    {
      return true;
    }
    
  }
  
  public boolean isShooterAtSpeed(double shooterRPM)
  {
    
    double talonMasterSpeed;
    double talonMasterError;
    talonMasterSpeed = shooterMaster.getSpeed();
    talonMasterError = shooterMaster.getError();
    
    SmartDashboard.putNumber("Shooter: Shooter Error", talonMasterError);
    
    if(talonMasterError <= CrusaderCommon.ACCEPTABLE_RPM_ERROR)
    {
      
      return true;
      
    }
    else
    {
      return false;
    }
    
    
    
  }
  
  //This will be called in test periodic and will test the functionality of the system
  public void test()
  {
   

    
    Logger.Log("Shooter(): test(): Begining Shooter Test");
    
    try
    {

      if(count < CrusaderCommon.TEST_COUNT_CONDITION)
      {
        
        execute(3500);
        
        Logger.Log("Shooter(): test(): Shooter Encoder Position"+ encoderPosition);
        
        count++;
        
      }
      
    }
    
    catch(Exception e)
    {
      
      e.printStackTrace();
      Logger.Log("Shooter(): test(): Exception: "+e);
      
    }
    
    Logger.Log("Shooter(): test(): Shooter Standing by!");
  }
  
  
  public void setTalonSpeed(double speed)
  {
    
    talonSpeed = speed;
    
    shooterMaster.set(talonSpeed);
    
  }
  
  public void turnOnRingLight()
  {
    
   shooterRingLight.set(true); 
   isRingLightOn = true;
    
  }
  

}
