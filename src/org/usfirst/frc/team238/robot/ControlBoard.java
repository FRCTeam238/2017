package org.usfirst.frc.team238.robot;

import java.util.HashMap;

import org.usfirst.frc.team238.core.Logger;

import edu.wpi.first.wpilibj.Joystick;

public class ControlBoard {
  // Here are the joysticks for controlling the robot
  static Joystick         manualOverrideJs; // operator manual overide
  static Joystick         operatorJs;       // operator control board
  private static Joystick driverLeftJs;     // driveTrain left
  private static Joystick driverRightJs;    // driveTrain right

  HashMap<Integer, Integer> controllers; /*
                                          * This is the list that contains the
                                          * locations for the controllers
                                          */

  // look at using more sophisticated collection classes
  static int commands[];

  public void controlBoardInit() {
    try {
      manualOverrideJs = new Joystick(0);
      operatorJs = new Joystick(1);
      setDriverLeftJs(new Joystick(2));
      setDriverRightJs(new Joystick(3));

      // array that holds the command sent by each control device
      commands = new int[5];

      controllers = new HashMap<Integer, Integer>(5);

    }

    catch (Exception ex) {
      Logger.logString("ControlBoard Init Failed");
    }
  }

  /**
   * loops thru all the buttons on the joystick until it gets to the one that is
   * pressed works as long as we only need one button pressed at a time, if we
   * need more than one button we'll need to create an array of commands.... int
   * command[]
   * 
   * @return command value
   */
  public int getCommand(Joystick theJoyStick) {
    int command;
    boolean jsButtonValue = false;

    int interator = theJoyStick.getButtonCount();

    // interator = 11 and buttons do not count from zero
    for (command = 1; command <= interator; command++) {
      jsButtonValue = theJoyStick.getRawButton(command);
      if (jsButtonValue) {
        break;
      }
    }
    if (!jsButtonValue) {
      command = 0;
    }

    return command;
  }

  /**
   * 
   * @param theJoyStick
   * @return
   */
  public int getDriverCommand(Joystick theJoyStick) {
    int command = 0;

    if (theJoyStick.getRawButton(1)) {
      command = 1;
    } else if (theJoyStick.getRawButton(2)) {
      command = 2;
    } else if (theJoyStick.getRawButton(3)) {
      command = 3;
    } else if (theJoyStick.getRawButton(4)) {
      command = 4;
    }

    return command;
  }

  // populates each array element with the corresponding value for the joy stick
  public HashMap<Integer, Integer> getCommands() {

    controllers.put(0, getCommand(manualOverrideJs));
    controllers.put(CrusaderCommon.OPR_CMD_LIST, getCommand(operatorJs));
    controllers.put(CrusaderCommon.INPUT_DRIVER_RIGHT_JS, getCommand(getDriverRightJs()));
    controllers.put(CrusaderCommon.INPUT_DRIVER_LEFT_JS, getCommand(getDriverLeftJs()));
    controllers.put(CrusaderCommon.DT_CMD_LIST, CrusaderCommon.DRIVE_TRAIN_CMD_IDX);

    return controllers;

  }

  // gets the y value of the manual overide joy stick to feed to the command
  // controller

  public static boolean resetEncoderValue() {
    boolean resetEncoderValue = operatorJs.getRawButton(8);
    Logger.logString("Reset Encoder = " + resetEncoderValue);
    return resetEncoderValue;
  }

  public static Joystick getDriverLeftJs() {
    return driverLeftJs;
  }

  public static void setDriverLeftJs(Joystick driverLeftJs) {
    ControlBoard.driverLeftJs = driverLeftJs;
  }

  public static Joystick getDriverRightJs() {
    return driverRightJs;
  }

  public static void setDriverRightJs(Joystick driverRightJs) {
    ControlBoard.driverRightJs = driverRightJs;
  }

  public static Joystick getOperatorLeftJs() {
    return manualOverrideJs;
  }

  public static Joystick getOperatorRightJs() {
    return operatorJs;
  }

}
