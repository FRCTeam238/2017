package org.usfirst.frc.team238.robot;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Hood;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**THIS IS THE CLASS THAT THE SHOOTER MECHANISM IS USING
 * THIS IS DEPENDANT ON IF WE HAVE A TARGET LOCKED
 * WILL HAVE A MANUAL OVERRIDE MODE */
public class Shooter {
  
  CANTalon shooterMaster;
  CANTalon shooterSlave;
  
  Vision shooterVision;
  
  Hood theHood;
  
  public double talonSpeed;
  
  int count = 0;
  
  int encoderPosition;
  
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
   
   //sets up slave talon to follow master
   shooterSlave.changeControlMode(TalonControlMode.Follower);
   shooterSlave.set(CrusaderCommon.SHOOTER_MASTER_TALON);
   
   //sets the feedback device to Quad Encoders
   shooterMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
   
   //configures master talon to use PID values
   configShooterTalons(shooterMaster);
   
  }
  
  //This gets the encoder count from the master talon
  public int getShooterEncPosition()
  {
    
    encoderPosition = shooterMaster.getEncPosition();
    
    Logger.Log("Shooter Encoder Position"+ encoderPosition);
    
    return encoderPosition;
    
  }
  
  //Resets encoders to zero
  public void resetShooterEncoder()
  {
    shooterMaster.setEncPosition(0);
    
    encoderPosition = shooterMaster.getEncPosition();
    
    Logger.Log("Shooter Encoder Position"+ encoderPosition);
    
  }
  
  //Moves the shooter motors
  public void execute()
  {
    
    shooterMaster.set(1/*CrusaderCommon.SHOOTER_MAX_RPM*/);
    
  }
  
  public void stopShooter()
  {
    
    shooterMaster.set(0);
    
  }
  
  //configures the talons for velocity tuning code
  public void configShooterTalons(CANTalon talon)
  {
    
    /*This sets the voltage range the talon can use; should be 
     *set at +12.0f and -12.0f*/
     talon.configNominalOutputVoltage(+0.0f, -0.0f);
     talon.configPeakOutputVoltage(+12.0f, -12.0f);
     
     /*This sets the FPID values to correct error in the motor's velocity
      * */
    /* talon.setProfile(CrusaderCommon.TALON_NO_VALUE);
     talon.setF(CrusaderCommon.SHOOTER_TALON_F_VALUE); //.3113);
     talon.setP(CrusaderCommon.SHOOTER_TALON_P_VALUE); //.8);//064543);
     talon.setI(CrusaderCommon.SHOOTER_TALON_I_VALUE); 
     talon.setD(CrusaderCommon.SHOOTER_TALON_D_VALUE);*/
     
     
     
     //this set the talon to use speed mode instead of voltage mode
     talon.changeControlMode(TalonControlMode.PercentVbus);
     
    
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
      return false;
    }
    
  }
  
  public boolean isShooterAtSpeed()
  {
    
    double talonMasterSpeed;
    
    talonMasterSpeed = shooterMaster.getSpeed();
    
    if(talonMasterSpeed == CrusaderCommon.SHOOTER_MAX_RPM)
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
   

    
    Logger.Log("Begining Shooter Test");
    
    try
    {

      if(count < CrusaderCommon.TEST_COUNT_CONDITION)
      {
        
        execute();
        
        Logger.Log("Shooter Encoder Position"+ encoderPosition);
        
        count++;
        
      }
      
    }
    
    catch(Exception e)
    {
      
      e.printStackTrace();
      Logger.Log("Shooter Test Failed!");
      
    }
    
    Logger.Log("Shooter Test Sucessful!");
  }
  
  
  public void setTalonSpeed(double speed)
  {
    
    talonSpeed = speed;
    
    shooterMaster.set(talonSpeed);
    
  }
  

}
