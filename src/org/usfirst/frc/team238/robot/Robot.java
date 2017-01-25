package org.usfirst.frc.team238.robot;

import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team238.core.AutonomousController;
import org.usfirst.frc.team238.core.AutonomousJSONFactory;
import org.usfirst.frc.team238.core.AutonomousState;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

// @SuppressWarnings("deprecation")
public class Robot extends IterativeRobot {

  private static int count = 0;
  // private static boolean AUTO_STARTED = false;

  CANTalon leftFrontDrive;  // id = 1
  CANTalon leftRearDrive;   // id = 2
  CANTalon rightFrontDrive; // id = 3
  CANTalon rightRearDrive;  // id = 4

  Preferences       myPreferences;
  ControlBoard      myControlBoard;
  CommandController theMCP;
  RobotDrive        myRobotDrive;

  Navigation myNavigation;
  Drivetrain myDriveTrain;

  DriverStation myDriverstation;

  // Autonomous Mode Support
  String autoMode;
  /* AutonomousDrive autonomousDrive; */
  private AutonomousJSONFactory jSONFileWriter;
  private AutonomousController  theMACP;
  SendableChooser               autonomousChooser;
  SendableChooser               autonomousSaveChooser;
  Logger                        myLogger;
  SendableChooser               autonomousStateParamsUpdate;
  // Holds all the autonomous states
  // and takes the data from the AutonomousController in order to
  // transfer it to the JSONFactory
  private ArrayList<AutonomousState>[] autonomousModeList;
  // This holds the names of each mode
  private ArrayList<String> AutoModeNames;

  public void disabledInit() {
    try {
      // only use checkForSmartDashboardChanges function in init methods
      // or you will smoke the roborio into a useless pile of silicon

      // ^ Thanks for the heads up bro! ^

      // checkForSmartDashboardChanges(CrusaderCommon.PREFVALUE_OP_AUTO,
      // CrusaderCommon.PREFVALUE_OP_AUTO_DEFAULT);

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

        myNavigation.getDistanceFromUltrasonic();

        debug = SmartDashboard.getBoolean("Debug");
        Logger.logBoolean("disabledPeriodic:Debug=  ", debug);

        int automousModeFromDS = theMACP.getAutonomousSelection();// autonomousChooser.getSelected();
        Logger.logTwoString("The chosen One =  ", String.valueOf(automousModeFromDS));

        // see if we need to modify the params on a state
        String updateParams = (String) autonomousStateParamsUpdate.getSelected();
        int update = Integer.parseInt(updateParams);

        String saveParam = (String) autonomousSaveChooser.getSelected();
        int save = Integer.parseInt(saveParam);

        theMACP.pickAMode(automousModeFromDS);

        // Get the list of autonomousModes
        autonomousModeList = theMACP.getAutoModeList();
        // Get the names of each set of autonomousModes
        AutoModeNames = theMACP.getAutoModeNames();

        SmartDashboard.putString("Chosen Auto Mode", String.valueOf(automousModeFromDS));

        // Could combine update and save so it's just save

        if (update != 0) {
          theMACP.updateStateParameters();
        }

        if (save != 0) {
          jSONFileWriter.save(autonomousModeList, AutoModeNames);
        }

        theMACP.dump();

        myNavigation.navxValues();

      }
      count++;
    } catch (Exception ex) {
      Logger.logString("disabledPriodic exception");
      ex.printStackTrace();
    }

  }

  public void teleopInit() {
    try {
      Logger.logString("TeleopInit()");

    } catch (Exception ex) {
      Logger.logString("TeleopInit:Exception");
    }

  }

  public void autonomousInit() {
    try {

      Logger.logString("AutononousInit()");

      try {

        int automousModeFromDS = theMACP.getAutonomousSelection();// autonomousChooser.getSelected();
        Logger.logTwoString("The chosen One =  ", String.valueOf(automousModeFromDS));
        theMACP.pickAMode(automousModeFromDS);
      } catch (Exception ex) {
        Logger.logString("AutononousInit:Something BAD happened");
      }
    } catch (Exception ex) {
      Logger.logString("AutononousInit:Exception");
    }
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @SuppressWarnings("deprecation")
  public void robotInit() {

    try {
      System.out.println("RobotInit()");

      SmartDashboard.putBoolean("Debug", false);

      SmartDashboard.putInt("AutoStateCmdIndex", 0);

      autonomousStateParamsUpdate = new SendableChooser();
      autonomousStateParamsUpdate.addDefault("As Received", "0");
      autonomousStateParamsUpdate.addObject("UPDATE", "1");

      // Create a new SendableChooser for the save function
      autonomousSaveChooser = new SendableChooser();
      autonomousSaveChooser.addDefault("DON'T Save", "0");
      autonomousSaveChooser.addObject("Save", "1");

      SmartDashboard.putData("Edit State Params", autonomousStateParamsUpdate);
      // put it on the SmartDashboard
      SmartDashboard.putData("Save Changes", autonomousSaveChooser);

      myLogger = new Logger();

      // object that is the code representation for the physical control board
      myControlBoard = new ControlBoard();
      myControlBoard.controlBoardInit();

      // Create robot core objects
      // Test Robot | Actual Robot
      leftFrontDrive = new CANTalon(CrusaderCommon.LEFT_FRONT_TALON); // id = 1
                                                                      // 5
      leftRearDrive = new CANTalon(CrusaderCommon.LEFT_REAR_TALON); // id = 2 6
      rightFrontDrive = new CANTalon(CrusaderCommon.RIGHT_FRONT_TALON); // id =
                                                                        // 3 7
      rightRearDrive = new CANTalon(CrusaderCommon.RIGHT_REAR_TALON); // id = 4
                                                                      // 8

      myRobotDrive = new RobotDrive(leftFrontDrive, leftRearDrive, rightFrontDrive, rightRearDrive);
      myRobotDrive.setSafetyEnabled(false);

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

      // Controller object for telop
      theMCP = new CommandController();
      theMCP.init(myRobotDrive, myDriveTrain, myNavigation);

      // Controller Object for autonomous
      theMACP = new AutonomousController();
      theMACP.init(theMCP);

      // The file writer to create new AutonomousModes
      jSONFileWriter = new AutonomousJSONFactory();

      Logger.logString("Fully Initialized");

    } catch (Exception ex) {

      Logger.logString(ex.getMessage());
      ex.printStackTrace();

    }
  }

  /**
   * This function is called periodically during autonomous
   */
  public void autonomousPeriodic() {

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

      // get the buttons (commands) that were pressed on the control board
      commandValue = myControlBoard.getCommands();
      // pass the array with the commands coming form the control to the
      // Controller object
      theMCP.buttonPressed(commandValue);

    } catch (Exception e) {

      Logger.logString("telopperiodic: ");
      e.printStackTrace();
    }
  }

  /**
   * This function is called periodically during test mode
   */
  public void testPeriodic() {

  }

}
