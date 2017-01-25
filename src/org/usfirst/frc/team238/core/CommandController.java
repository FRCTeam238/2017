package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

import edu.wpi.first.wpilibj.RobotDrive;

public class CommandController {
  AutonomousCmdFactory theRouge;
  DriverCommandFactory theDriverCommandFactory;
  OperatorCmdFactory   theOperatorCmdFactory;

  HashMap<String, Command>  autoCmdList;
  HashMap<Integer, Command> driverCmdList;
  HashMap<Integer, Command> driverLeftCmdList;
  HashMap<Integer, Command> driverRightCmdList;
  HashMap<Integer, Command> operatorCmdList;

  HashMap<Integer, Command> commandValue;

  public void init(RobotDrive myRobotDrive, Drivetrain driveTrain, Navigation myNavigation) {
    // populate the command lists
    setupOperatorCommands(driveTrain);
    setupDriverCommands(myRobotDrive, driveTrain);
    setupAutonomousCommands(driveTrain, myNavigation);

    commandValue = new HashMap<Integer, Command>(8);
  }

  // gets an AutoCommand by key name
  public Command getAutoCmd(String cmdName) {
    return autoCmdList.get(cmdName);

  }

  // loads all the autonomous commands from the auto factory
  private void setupAutonomousCommands(Drivetrain driveTrain, Navigation myNavigation) {
    theRouge = new AutonomousCmdFactory();
    theRouge.init();
    autoCmdList = theRouge.createAutonomousCommands(driveTrain, myNavigation);

  }

  // gets a Driver Command by key name
  public Command getDriverCmd(String cmdName) {
    return driverCmdList.get(cmdName);
  }

  private void setupDriverCommands(RobotDrive myRobotDrive, Drivetrain driveTrain) {
    theDriverCommandFactory = new DriverCommandFactory();
    theDriverCommandFactory.init();

    driverLeftCmdList = theDriverCommandFactory.createDriverLeftCommands(driveTrain);
    driverRightCmdList = theDriverCommandFactory.createDriverRightCommands(driveTrain);
    driverCmdList = theDriverCommandFactory.createDriverCommands(myRobotDrive);
  }

  // gets Operator Commands by name
  public Command getOperatorCmd(int cmdName) {
    return operatorCmdList.get(cmdName);
  }

  private void setupOperatorCommands(Drivetrain theDrivetrain) {
    theOperatorCmdFactory = new OperatorCmdFactory();
    theOperatorCmdFactory.init();

    operatorCmdList = theOperatorCmdFactory.createOperatorCommands(theDrivetrain);
  }

  /*
   * Gets the buttons that are pressed or switches that are set from the
   * controls (joysticks or custom) which the values ( button1 = 1 etc) are the
   * key into a Map of commands that have been pre-loaded in the setup methods,
   * then "get"s the command associated with the key in the hashmap and calls
   * the execute function on that command.
   */

  public void buttonPressed(HashMap<Integer, Integer> commandValue) {

    Command commandForTheButtonPressed;
    Integer buttonPressed = commandValue.get(CrusaderCommon.INPUT_DRIVER_LEFT_JS);

    commandForTheButtonPressed = driverLeftCmdList.get(buttonPressed);
    if (commandForTheButtonPressed != null) {
      commandForTheButtonPressed.execute();
    }

    buttonPressed = commandValue.get(CrusaderCommon.INPUT_DRIVER_RIGHT_JS);
    commandForTheButtonPressed = driverRightCmdList.get(buttonPressed);
    if (commandForTheButtonPressed != null) {
      commandForTheButtonPressed.execute();
    }

    buttonPressed = commandValue.get(CrusaderCommon.DT_CMD_LIST);
    Logger.logInt("buttonPressed : ", buttonPressed);
    commandForTheButtonPressed = driverCmdList.get(buttonPressed);
    if (commandForTheButtonPressed != null) {
      Logger.logInt("buttonPressed(in if statement) : ", buttonPressed);
      commandForTheButtonPressed.execute();
    }

    buttonPressed = commandValue.get(CrusaderCommon.OPR_CMD_LIST);
    commandForTheButtonPressed = operatorCmdList.get(buttonPressed);
    if (commandForTheButtonPressed != null) {
      commandForTheButtonPressed.execute();
    }

  }

}
