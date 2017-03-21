package org.usfirst.frc.team238.robot;


import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.AutonomousController;
import org.usfirst.frc.team238.core.AutonomousDataHandler;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.core.TargetingDataHandler;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.robot.FuelHandler;
import org.usfirst.frc.team238.robot.Climber;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DriverStation;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

// @SuppressWarnings("deprecation")
public class Robot extends IterativeRobot {

	private static int count = 1;
	double[] dataFromVision;
	//private static boolean AUTO_STARTED = false;
	
	CANTalon leftFrontDrive; //id = 1
	CANTalon leftRearDrive; //id = 2
	CANTalon rightFrontDrive; //id = 3
	CANTalon rightRearDrive; //id = 4
	
	Robot myRobot;
	Preferences myPreferences;
	ControlBoard myControlBoard;
	CommandController theMCP;
	RobotDrive myRobotDrive;
	Navigation myNavigation;
	Drivetrain myDriveTrain;
	DriverStation myDriverstation;
	Vision theVision;
	Shooter theShooter;
	FuelHandler theFuelHandler;
	Climber theClimber;
  Logger myLogger;
  DriverStation myDriverStation;
  Alliance myAllianceTeam;
	
	// Autonomous Mode Support
	String autoMode;
	/*AutonomousDrive autonomousDrive;*/
	private AutonomousDataHandler myAutonomousDataHandler;
	private TargetingDataHandler myTargetingData;
	private AutonomousController theMACP;
	
  //SendableChooser<String> autonomousChooser;
  SendableChooser<String> autonomousSaveChooser;
	//SendableChooser<String> targetingStateParamsUpdate;
  //SendableChooser<String> targetingSaveChooser;
  //SendableChooser<String> aModeSelector;
  SendableChooser<String> autonomousStateParamsUpdate;
	
	Alliance teamColor;
	
	
	/**
	 * Called when the robot is disabled
	 */
	public void disabledInit() {
		try {
		  
			// only use checkForSmartDashboardChanges function in init methods
			// or you will smoke the roborio into a useless pile of silicon
			//checkForSmartDashboardChanges(CrusaderCommon.PREFVALUE_OP_AUTO, CrusaderCommon.PREFVALUE_OP_AUTO_DEFAULT);
			
			Logger.Log("Robot(): disabledInit:");
		
		} catch (Exception e) {
		  e.printStackTrace();
			Logger.Log("Robot(): disabledInit Exception: "+e);
		}
	}

	/**
	 * Called periodically when the robot is disabled
	 */
	public void disabledPeriodic() {
		//boolean debug;m,
		try {
			if (count > 150) {
				
				count = 0;
				
				//Send the list of AutonomousModes into the AutonomousController for processing
        theMACP.setAutonomousControllerData(myAutonomousDataHandler);
				
				//myNavigation.getDistanceFromUltrasonic();
				
				//debug = SmartDashboard.getBoolean("Debug");
				//Logger.logBoolean("disabledPeriodic:Debug=  " , debug);
				
				int autoModeSelection = myAutonomousDataHandler.getModeSelectionFromDashboard();
				
				//int automousModeFromDS =  myAutonomousDataHandler.getAModeChooserSelection();
				Logger.Log("Robot(): disabledPeriodic(): The chosen AutoMode =  " + String.valueOf(autoModeSelection));
			
				//backups  in case sendable chooser disappear
				boolean updateBackup_BecauseTheSendableChooserSucks = false;
        boolean saveBackup_BecauseTheSendableChooserSucks = false;
        boolean  readAmode238DotTxt = false;
        
				//see if we need to modify the params on a state
				String updateParams = (String) autonomousStateParamsUpdate.getSelected();
				int update = Integer.parseInt(updateParams);
				
				if(updateParams == null)
				{
				  updateBackup_BecauseTheSendableChooserSucks = SmartDashboard.getBoolean("Update Params", false);
				  
				  if(updateBackup_BecauseTheSendableChooserSucks)
				  {
				    update = 1;
				  }
				}
				
				String saveParam = (String) autonomousSaveChooser.getSelected();
				int save = 0;
				if(saveParam == null)
				{
				  saveBackup_BecauseTheSendableChooserSucks = SmartDashboard.getBoolean("Save to Amode238", false);
          if(saveBackup_BecauseTheSendableChooserSucks)
          {
            save = CrusaderCommon.AUTONOMOUS_SAVE;            
          }
          
				  readAmode238DotTxt = SmartDashboard.getBoolean("Read Amode238", false);
				  if(readAmode238DotTxt)
				  {
				    save = CrusaderCommon.AUTONOMOUS_READ_FILE;
				  }
				}
				else
				{
				  save = Integer.parseInt(saveParam); 
				}
				 
				
				
				Logger.Log("Robot:DisablePeriodic: save = " + save);
				
//				String updateTargetingParams = (String) targetingStateParamsUpdate.getSelected();
//				int updateTargeting = Integer.parseInt(updateTargetingParams);
//				
//				String saveTargetingParam = (String) targetingSaveChooser.getSelected();
//				int saveTargeting = Integer.parseInt(saveTargetingParam);
				
				theMACP.pickAMode(autoModeSelection);
				
				//theMACP.pickAMode(automousModeFromDS);
				
//				int targetSolutionFromDS = myTargetingData.getTargetChooserSelection();
//				
//				SmartDashboard.putString("Chosen Targeting Data", String.valueOf(targetSolutionFromDS));
//				
//				if(updateTargeting != 0){
//				  
//				  myTargetingData.updateStateParameters(targetSolutionFromDS);
//				  
//				}
//				
//				if(saveTargeting != 0){
//				  
//				  myTargetingData.save();
//				  
//				}
				
				if(update == CrusaderCommon.AUTONOMOUS_UPDATE)
				{
					theMACP.updateStateParameters(autoModeSelection);
				}
				
				if(save == CrusaderCommon.AUTONOMOUS_SAVE) 
				{
				  myAutonomousDataHandler.save();	
				}
				
				
				if(save == CrusaderCommon.AUTONOMOUS_READ_FILE)
				{
				  myAutonomousDataHandler.readJson(theMCP);
				}
				
				myAutonomousDataHandler.dump();
				
				//myTargetingData.dump();
				
				//myNavigation.navxValues();
				
			  //dataFromVision = theVision.getTheData();
				
				//Logger.logDouble("Distance ", dataFromVision[CrusaderCommon.VISION_DISTANCE_SLOT]);		
				
				//Logger.Log("Angle "+ dataFromVision[CrusaderCommon.VISION_ANGLE_SLOT]);
				
				SmartDashboard.putString("Last Modified : ", myAutonomousDataHandler.getModificationDate());  
				
				//Logger.Log("Robot(): disabledPeriodic(): Vision Horizontal : " + dataFromVision[0]);
				//Logger.Log("Robot(): disabledPeriodic(): Vision Vertical : " + dataFromVision[1]);
				
			}
			
			count++;
			
		} catch (Exception e) {
		  e.printStackTrace();
			Logger.Log("Robot(): disabledPeriodic(): disabledPriodic exception: " + e);
		}

	}

	public void teleopInit() {
		try {
			Logger.Log("Robot(): TeleopInit()");
			myControlBoard.checkXboxController();
			
		} catch (Exception e) {
		  e.printStackTrace();
			Logger.Log("Robot(): TeleopInit() Exception: "+ e);
		}

	}

	public void autonomousInit() {
		try {

			  Logger.Log("Robot(): AutononousInit()");
			
				//int automousModeFromDS =  myAutonomousDataHandler.getAModeChooserSelection(); //controller
				int automousModeFromDS =  myAutonomousDataHandler.getModeSelectionFromDashboard(); //controller
			
				Logger.Log("Robot(): AutonomousInit(): The chosen One =  " + String.valueOf(automousModeFromDS));
				theMACP.pickAMode(automousModeFromDS);
				
				myDriveTrain.getEncoderTicks();
				
				teamColor = DriverStation.getInstance().getAlliance();
				SmartDashboard.putString("Alliance Color", teamColor.toString());
				
				theFuelHandler.setAlliance(teamColor);
				
		} catch (Exception ex) {
		  ex.printStackTrace();
			Logger.Log("Robot(): AutononousInit() Exception: "+ex);
		}
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@SuppressWarnings({ "deprecation"})
	public void robotInit() {

		try {
			System.out.println("RobotInit()");
			
			myRobot = Robot.this; 
		
			InitSmartDashboardObjects();
			
			myLogger = new Logger();
			
			//object that is the code representation for the physical control board
			myControlBoard = new ControlBoard();
			myControlBoard.controlBoardInit();

			//Create robot core objects                                          // Test Robot | Actual Robot
			leftFrontDrive = new CANTalon(CrusaderCommon.DRIVE_TRAIN_MASTER_LEFT);   //id =  1			 5
			leftRearDrive = new CANTalon(CrusaderCommon.DRIVE_TRAIN_SLAVE_LEFT);     //id =  2			 6
			rightFrontDrive = new CANTalon(CrusaderCommon.DRIVE_TRAIN_MASTER_RIGHT); //id =  3			 7
			rightRearDrive = new CANTalon(CrusaderCommon.DRIVE_TRAIN_SLAVE_RIGHT);   //id =  4			 8
			
			//Setting the talons to follow master talons
			rightRearDrive.changeControlMode(TalonControlMode.Follower);
			leftRearDrive.changeControlMode(TalonControlMode.Follower);
			rightRearDrive.set(CrusaderCommon.DRIVE_TRAIN_MASTER_RIGHT);
			leftRearDrive.set(CrusaderCommon.DRIVE_TRAIN_MASTER_LEFT);
			
			//theFuelHandler = new FuelHandler();
			//theFuelHandler.init();
			
			myRobotDrive = new RobotDrive(leftFrontDrive,rightFrontDrive);
			myRobotDrive.setSafetyEnabled(false);
			//myRobotDrive.setMaxOutput(CrusaderCommon.DRIVETRAIN_MAX_RPM);
			/*autonomousDrive = new AutonomousDrive(myRobotDrive);
			autonomousDrive.init();*/
			
			myNavigation = new Navigation();
			myNavigation.init();
			
			myDriveTrain = new Drivetrain(myRobotDrive);
			myDriveTrain.init(leftFrontDrive, rightFrontDrive);
			
			leftFrontDrive.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			rightFrontDrive.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			
			leftFrontDrive.enableBrakeMode(true);
			rightFrontDrive.enableBrakeMode(true);
			leftRearDrive.enableBrakeMode(true);
			rightRearDrive.enableBrakeMode(true);
			
			theVision = new Vision();
			theVision.init();
			//theVision.startClient();
			
			theFuelHandler = new FuelHandler();
			theFuelHandler.init();
			
			theClimber = new Climber();
			theClimber.init();
			
			myDriveTrain.resetEncoders();
			
			//Controller object for telop
			theMCP = new CommandController();
			theMCP.init(myRobotDrive, myDriveTrain, myNavigation, theVision, 
			    theFuelHandler, theClimber, myRobot);
			
			//The handler that handles everything JSON related 
			myAutonomousDataHandler = new AutonomousDataHandler();
			
		  //Takes the CommandController in order to create AutonomousStates that work with the control scheme
			myAutonomousDataHandler.init(theMCP); //, aModeSelector);
			
			//Controller Object for autonomous
			theMACP = new AutonomousController(); 
			
			//Gives the newly read JSON data to the AutonomousController for processing
      theMACP.setAutonomousControllerData(myAutonomousDataHandler);
      
     // myTargetingData = new TargetingDataHandler();
      //myTargetingData.init(theMCP);
      
			Logger.Log("Robot(): robotInit(): Fully Initialized");

		} catch (Exception ex) {
		  ex.printStackTrace();
			Logger.Log("Robot(): robotInit() Exception : "+ex);

		}
	}

	/**
	 * Creates all the objects to be used on the SmartDashboard
	 */
	public void InitSmartDashboardObjects(){
	  
	  SmartDashboard.putNumber("Shooter F Value", CrusaderCommon.SHOOTER_TALON_F_VALUE); //0.0427);
	  SmartDashboard.putNumber("Shooter P Value", CrusaderCommon.SHOOTER_TALON_P_VALUE); // 0.2);
	  SmartDashboard.putNumber("Shooter I Value",  CrusaderCommon.SHOOTER_TALON_I_VALUE); //0);
	  SmartDashboard.putNumber("Shooter D Value",  CrusaderCommon.SHOOTER_TALON_D_VALUE); //1.33);
  
	  SmartDashboard.putNumber("Chosen Auto Mode", 0);
  
	  SmartDashboard.putBoolean("Output Log to File", true);
  
	  SmartDashboard.putBoolean("Debug", true);
  
	  SmartDashboard.putBoolean("Match Time Flag", false);
  
	  SmartDashboard.putInt("Select Auto State", 0);
  
	  //  SmartDashboard.putInt("TargetStateCmdIndex", 0);
	  //  
	  //  targetingStateParamsUpdate = new SendableChooser<String>();
	  //  targetingStateParamsUpdate.addDefault("Don't update", "0");
	  //  targetingStateParamsUpdate.addObject("Update State Target", "1");
	  //  
	  //  targetingSaveChooser = new SendableChooser<String>();
	  //  targetingSaveChooser.addDefault("Don't Save Target Data", "0");
	  //  targetingSaveChooser.addObject("Save Target State Data", "1");
  
	  //Sendable Chooser for the state update function
	  autonomousStateParamsUpdate = new SendableChooser<String>();
	  autonomousStateParamsUpdate.addDefault("As Received", "0");
	  autonomousStateParamsUpdate.addObject("UPDATE", "1");
  
	  //Create a new SendableChooser for the save function
	  autonomousSaveChooser = new SendableChooser<String>();
	  autonomousSaveChooser.addDefault("DON'T Save", "0");
	  autonomousSaveChooser.addObject("Save", "1");
	  autonomousSaveChooser.addObject("Read", "2");
  
	  //aModeSelector = new SendableChooser<String>();
  
	  //  SmartDashboard.putData("Edit Target Params", targetingStateParamsUpdate);
	  //  SmartDashboard.putData("Save Target Data", targetingSaveChooser);
  
	  //Put sendableChoosers to the SmartDashboard
	  SmartDashboard.putData("Edit State Params", autonomousStateParamsUpdate);
	  SmartDashboard.putData("Save Changes", autonomousSaveChooser);
  
	  SmartDashboard.putNumber("Blue TESTING RPM", CrusaderCommon.BLUE_SHOOTER_SPEED);//2900 is blue side
    SmartDashboard.putNumber("Red Hopper 2 TESTING RPM", CrusaderCommon.RED_SHOOTER_SPEED);//2808 is blue side
    //SmartDashboard.putNumber("Red Hopper 1 TESTING RPM", CrusaderCommon.RED_CLOSE_SHOOTER_SPEED);
    SmartDashboard.putBoolean("Update Params", false);
    SmartDashboard.putBoolean("Save to Amode238", false);
    SmartDashboard.putBoolean("Read Amode238", false);
    //SmartDashboard.putNumber("InityawValue", myNavigation.getYaw());
  
    SmartDashboard.putBoolean("CLIMBDEBUG", false);
  
    //SmartDashboard.putNumber("DRIVETRAIN TUNING", 0.2);
  
    //SmartDashboard.putString("Team Side :",getAllianceTeam());
    //SmartDashboard.putNumber("SHOOTER RPM",0);
	  
	}
	
	public CommandController getTheMCP()
	{
		return theMCP;
	}
  
  public Alliance getAllianceTeam()
  {
    return teamColor; 
  }

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("Left Encoder", leftFrontDrive.getEncPosition());
		SmartDashboard.putNumber("Right Encoder", rightFrontDrive.getEncPosition());
		try {
			
			theMACP.process();
			myNavigation.navxValues();
			myNavigation.getDistanceFromUltrasonic();
			
			int currentYaw = (int) myNavigation.getYaw();
			
			SmartDashboard.putNumber("AutonomousPeriodic: The CurrentYaw ", currentYaw);
			
		} catch (Exception ex) {
		  ex.printStackTrace();
			Logger.Log("Robot(): autonomousPeriodic() Exception: "+ex);
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {

		HashMap<Integer, Integer> commandValue;
		
		SmartDashboard.putNumber("Left Encoder", leftFrontDrive.getEncPosition());
		SmartDashboard.putNumber("Right Encoder", rightFrontDrive.getEncPosition());
		
		try {

			//get the buttons (commands) that were pressed on the control board
			commandValue = myControlBoard.getCommands();
			//pass the array with the commands coming form the control to the Controller object 
			theMCP.buttonPressed(commandValue);
			myNavigation.navxValues();
			//myNavigation.ultrasonicSensor();
			//SmartDashboard.putNumber("Match Time", myDriverstation.getMatchTime());
			

		} catch (Exception e) {
		  e.printStackTrace();
			Logger.Log("Robot(): teleopPeriodic() Exception: "+ e);
			
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	  try{
	    
	    theFuelHandler.test();
	    
	  }catch(Exception e){
	    
	    e.printStackTrace();
	  }
	  
	}
	
	
	/**
	 * This should ONLY be called from an init function
	 */
	private void checkForSmartDashboardChanges(String key, String value) {
		myPreferences = Preferences.getInstance();

		String valueFromPrefs = myPreferences.getString(key, value);
		if (valueFromPrefs != null) {
			Logger.Log("Robot(): CheckSDChanges(): valueFromPrefs : " + key + " = " + valueFromPrefs);
			String valueFromDS = null;
			
			try {
				valueFromDS = SmartDashboard.getString(key);
			} catch (Exception ex) {
				ex.printStackTrace();
				SmartDashboard.putString(key, valueFromPrefs);
			}

			Logger.Log("Robot(): CheckSDChanges(): ValFromDS : " + key + " = " + valueFromDS);

			// check for null and also if it's empty don't overwrite what's in
			// the preferences table
			if ((valueFromDS != null)  && (!valueFromDS.isEmpty())) {
								// if they are not the same then update the preferences
				if (!valueFromPrefs.equalsIgnoreCase(valueFromDS)) {
					
					Logger.Log("Robot(): CheckSDChanges(): UpdatePrefs" + key + " = " + valueFromDS);
					myPreferences.putString(key, valueFromDS);

					// NEVER NEVER use this save() in a periodic function or you
					// will destroy your RoboRio
					// making it an expensive chunk of useless plastic and
					// silicon
					
					//Thanks for the heads up bro!
					
					myPreferences.save();
				}
			}
			
			if(( valueFromDS != null) && (valueFromDS.isEmpty()) && (!valueFromPrefs.isEmpty())) {
			
				SmartDashboard.putString(key, valueFromPrefs);
			
			}
		}
	}
}
