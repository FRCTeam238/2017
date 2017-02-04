package org.usfirst.frc.team238.robot;

public class CrusaderCommon {
	
	/*DO NOT NUKE*/
	//two types of command lists
	public static final int OPR_CMD_LIST = 1;
	public static final int LEFTDRIVER_CMD_LIST = 2;
	public static final int RIGHTDRIVER_CMD_LIST = 3;
	/*DO NOT NUKE*/
	public static final int DT_CMD_LIST = 4;
	
	/*DO NOT NUKE*/
	public static final int INPUT_DRIVER_LEFT_JS = 2;
	public static final int INPUT_DRIVER_RIGHT_JS = 3;
	

		// this element in the commandValue array is only
		// used by the AutoMode1Impl class. CommandController
		// is unaware of it


	/*DO NOT NUKE*/
	public static final boolean SHIFTER_HIGH_GEAR = true;
	public static final boolean SHIFTER_LOW_GEAR = false;
	
	public static final double DRIVETRAIN_MAX_RPM = 1500;
	

  public static final int AUTO_JSON_CREATOR_PARAM_LIMIT = 4;
	
	/*
	 * The AUTO_DRIVE_* constants are the joystick positions when moving in the
	 * expected direction. These values are passed to the RobotDrive.tankDrive
	 * method.
	 */
	/*DO NOT NUKE*/
	public static final double AUTO_DRIVE_FORWARD = 0.75;
	public static final double AUTO_DRIVE_BACKWARD = -0.75;
	public static final double AUTO_DRIVE_IDLE = 0.0;
	
	
	
	
	/*DO NOT NUKE*/
	public static final int DRIVE_TRAIN_CMD_IDX = 0;
	
	/*DO NOT NUKE*/
	public static final int AUTO_DRIVE_LIMIT = 15000;
	
	//Intake Motor Values
	
	public final static double INTAKE_MOTOR_ROTATE_IN = 1.0;
	public final static double INTAKE_MOTOR_ROTATE_OUT = -1.0;
	public final static double INTAKE_MOTOR_OFF = 0;
	
	public final static int VISION_ANGLE_SLOT = 0;
	public final static int VISION_DISTANCE_SLOT = 1;
	
	public final static double TRACKING_MOTOR_VALUE = 1;
	
	public final static double TALON_F_VALUE = 0.3510;
	public final static double TALON_P_VALUE = 1;
	public final static int TALON_NO_VALUE = 0;
}
