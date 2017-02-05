package org.usfirst.frc.team238.robot;


import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.AutonomousController;
import org.usfirst.frc.team238.core.AutonomousDataHandler;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.robot.FuelHandler;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
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
	
	// Autonomous Mode Support
	String autoMode;
	/*AutonomousDrive autonomousDrive;*/
	private AutonomousDataHandler myAutonomousDataHandler;
	private AutonomousController theMACP;
	@SuppressWarnings("rawtypes")
  SendableChooser autonomousChooser;
	@SuppressWarnings("rawtypes")
  SendableChooser autonomousSaveChooser;
	Logger myLogger;
	@SuppressWarnings("rawtypes")
  SendableChooser autonomousStateParamsUpdate;
	//Holds all the autonomous states
	//and takes the data from the AutonomousController in order to
	//transfer it to the JSONFactory
	private ArrayList<AutonomousState>[] autonomousModeList;
	//This holds the names of each mode
	private ArrayList<String> AutoModeNames;
	
	public void disabledInit() {
		try {
			// only use checkForSmartDashboardChanges function in init methods
			// or you will smoke the roborio into a useless pile of silicon
			//checkForSmartDashboardChanges(CrusaderCommon.PREFVALUE_OP_AUTO, CrusaderCommon.PREFVALUE_OP_AUTO_DEFAULT);
			
			Logger.logString("disabledInit:");
		
		} catch (Exception ex) {
			Logger.logString("disabledInit exception");
		}
	}

	public void disabledPeriodic() {
		boolean debug;
		try {
			if (count > 150) {
				
				count = 0;
				
				//Send the list of AutonomousModes into the AutonomousController for processing
        theMACP.setAutonomousControllerData(myAutonomousDataHandler);
				
				myNavigation.getDistanceFromUltrasonic();
				
				debug = SmartDashboard.getBoolean("Debug");
				Logger.logBoolean("disabledPeriodic:Debug=  " , debug);
				
				int automousModeFromDS =  myAutonomousDataHandler.getAModeChooserSelection();
				Logger.logTwoString("The chosen One =  " , String.valueOf(automousModeFromDS));
			
				//see if we need to modify the params on a state
				String updateParams = (String) autonomousStateParamsUpdate.getSelected();
				int update = Integer.parseInt(updateParams);
				
				String saveParam = (String) autonomousSaveChooser.getSelected();
				int save = Integer.parseInt(saveParam);
				
				theMACP.pickAMode(automousModeFromDS);
				
				SmartDashboard.putString("Chosen Auto Mode", String.valueOf(automousModeFromDS));
				
				if(update != 0)
				{
					theMACP.updateStateParameters(automousModeFromDS);
				}
				
				if(save != 0)
				{
				  myAutonomousDataHandler.save();	
				}

				myAutonomousDataHandler.dump();
				
				myNavigation.navxValues();
				
				//Logger.logDouble("Distance ", dataFromVision[CrusaderCommon.VISION_DISTANCE_SLOT]);
				
				dataFromVision = theVision.getTheData();
				
				Logger.logDouble("Angle ", dataFromVision[CrusaderCommon.VISION_ANGLE_SLOT]);
				
				SmartDashboard.putString("Last Modified : ", myAutonomousDataHandler.getModificationDate());  
				
			}
			
			count++;
			
		} catch (Exception ex) {
			Logger.logString("disabledPriodic exception" );
			ex.printStackTrace();
		}

	}

	public void teleopInit() {
		try {
			Logger.logString("TeleopInit()");
			myControlBoard.checkXboxController();
			
		} catch (Exception ex) {
			Logger.logString("TeleopInit:Exception");
		}

	}

	public void autonomousInit() {
		try {

			Logger.logString("AutononousInit()");

			try {
			
				int automousModeFromDS =  myAutonomousDataHandler.getAModeChooserSelection(); //controller
				Logger.logTwoString("The chosen One =  " , String.valueOf(automousModeFromDS));
				theMACP.pickAMode(automousModeFromDS);
				
			} catch (Exception ex) {
				Logger.logString("AutononousInit:Something BAD happened");
			}
		} catch (Exception ex) {
			Logger.logString("AutononousInit:Exception");
		}
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public void robotInit() {

		try {
			System.out.println("RobotInit()");
			
			//SmartDashboard.putString(CrusaderCommon.PREFVALUE_OP_AUTO, "");
			
			SmartDashboard.putBoolean("Debug", true);
			
			SmartDashboard.putBoolean("Match Time Flag", false);
			
			SmartDashboard.putInt("AutoStateCmdIndex", 0);
			
			
			//Sendable Chooser for the state update function
			autonomousStateParamsUpdate = new SendableChooser();
			autonomousStateParamsUpdate.addDefault("As Received", "0");
			autonomousStateParamsUpdate.addObject("UPDATE", "1");
			
			//Create a new SendableChooser for the save function
			autonomousSaveChooser = new SendableChooser();
			autonomousSaveChooser.addDefault("DON'T Save", "0");
			autonomousSaveChooser.addObject("Save", "1");
			
			//Put sendableChoosers to the SmartDashboard
			SmartDashboard.putData("Edit State Params", autonomousStateParamsUpdate);
			SmartDashboard.putData("Save Changes", autonomousSaveChooser);
			
			myLogger = new Logger();
			
			//object that is the code representation for the physical control board
			myControlBoard = new ControlBoard();
			myControlBoard.controlBoardInit();

			//Create robot core objects 
												              // Test Robot | Actual Robot
			leftFrontDrive = new CANTalon(5);  //id =  1			 5
			leftRearDrive = new CANTalon(6);   //id =  2			 6
			rightFrontDrive = new CANTalon(7); //id =  3			 7
			rightRearDrive = new CANTalon(8);  //id =  4			 8
			
			//Setting the talons to follow master talons
			rightRearDrive.changeControlMode(TalonControlMode.Follower);
			leftRearDrive.changeControlMode(TalonControlMode.Follower);
			rightRearDrive.set(7);
			leftRearDrive.set(5);
			
			theFuelHandler = new FuelHandler();
			theFuelHandler.init();
			
			myRobotDrive = new RobotDrive(leftFrontDrive,leftRearDrive,rightFrontDrive,rightRearDrive);
			myRobotDrive.setSafetyEnabled(false);
			myRobotDrive.setMaxOutput(CrusaderCommon.DRIVETRAIN_MAX_RPM);
			/*autonomousDrive = new AutonomousDrive(myRobotDrive);
			autonomousDrive.init();*/
			
			myNavigation = new Navigation();
			myNavigation.init();
			
			myDriverstation = DriverStation.getInstance();
		
			SmartDashboard.putNumber("InityawValue", myNavigation.getYaw());
			
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
			theVision.startClient();
			
			theShooter = new Shooter();
			
			
			//Controller object for telop
			theMCP = new CommandController();
			theMCP.init(myRobotDrive, myDriveTrain, myNavigation, theVision, theShooter);
			
			//The handler that handles everything JSON related 
			myAutonomousDataHandler = new AutonomousDataHandler();
			
		  //Takes the CommandController in order to create AutonomousStates that work with the control scheme
			myAutonomousDataHandler.init(theMCP);
			
			//Controller Object for autonomous
			theMACP = new AutonomousController(); 
			
			//Gives the newly read JSON data to the AutonomousController for processing
      theMACP.setAutonomousControllerData(myAutonomousDataHandler);
			
			Logger.logString("Fully Initialized");

		} catch (Exception ex) {

			Logger.logString(ex.getMessage());
			ex.printStackTrace();

		}
	}

	public CommandController getTheMCP()
	{
		return theMCP;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		//SmartDashboard.putNumber("Left Encoder", leftFrontDrive.getEncPosition());
		//SmartDashboard.putNumber("Right Encoder", rightFrontDrive.getEncPosition());
		try {
			
			theMACP.process();
			myNavigation.navxValues();
			myNavigation.getDistanceFromUltrasonic();
			
		} catch (Exception ex) {
			Logger.logString("Autonomous exception");
			ex.printStackTrace();
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
			//myNavigation.navxValues();
			//myNavigation.ultrasonicSensor();
			//SmartDashboard.putNumber("Match Time", myDriverstation.getMatchTime());
			

		} catch (Exception e) {
			
			Logger.logString("telopperiodic: ");
			e.printStackTrace();
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
			Logger.logFourString("CheckSDChanges:valueFromPrefs : " , key , " = " , valueFromPrefs);
			String valueFromDS = null;
			
			try {
				valueFromDS = SmartDashboard.getString(key);
			} catch (Exception ex) {
				ex.printStackTrace();
				SmartDashboard.putString(key, valueFromPrefs);
			}

			Logger.logFourString("CheckSDChanges.ValFromDS : " , key , " = " , valueFromDS);

			// check for null and also if it's empty don't overwrite what's in
			// the preferences table
			if ((valueFromDS != null)  && (!valueFromDS.isEmpty())) {
								// if they are not the same then update the preferences
				if (!valueFromPrefs.equalsIgnoreCase(valueFromDS)) {
					
					Logger.logFourString("CheckSDChanges.UpdatePrefs" , key , " = " , valueFromDS);
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
