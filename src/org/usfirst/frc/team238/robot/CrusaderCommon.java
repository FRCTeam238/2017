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
	
	public static final int LEFT_FRONT_TALON = 5;
	public static final int LEFT_REAR_TALON = 6;
	public static final int RIGHT_FRONT_TALON = 7;
	public static final int RIGHT_REAR_TALON = 8;
	

		// this element in the commandValue array is only
		// used by the AutoMode1Impl class. CommandController
		// is unaware of it


	/*DO NOT NUKE*/
	public static final boolean SHIFTER_HIGH_GEAR = true;
	public static final boolean SHIFTER_LOW_GEAR = false;
	
	
	
	/*
	 * The AUTO_DRIVE_* constants are the joystick positions when moving in the
	 * expected direction. These values are passed to the RobotDrive.tankDrive
	 * method.
	 */
	/*DO NOT NUKE*/
	/*public static final double AUTO_DRIVE_FORWARD = 0.75;
	public static final double AUTO_DRIVE_BACKWARD = -0.75;
	public static final double AUTO_DRIVE_IDLE = 0.0;*/
	
	
	public final static int VISION_ANGLE_SLOT = 0;
	public final static int VISION_DISTANCE_SLOT = 1;
	
	/*DO NOT NUKE*/
	public static final int DRIVE_TRAIN_CMD_IDX = 0;
	
	/*DO NOT NUKE*/
	/*public static final int AUTO_DRIVE_LIMIT = 15000;*/
	
}
