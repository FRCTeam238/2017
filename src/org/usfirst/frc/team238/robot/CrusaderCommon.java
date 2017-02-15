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
	public static final double SHOOTER_MAX_RPM = 1500;
	

	
	/*
	 * The AUTO_DRIVE_* constants are the joystick positions when moving in the
	 * expected direction. These values are passed to the RobotDrive.tankDrive
	 * method.
	 */
	/*DO NOT NUKE*/
	public static final double AUTO_DRIVE_FORWARD = 0.75;
	public static final double AUTO_DRIVE_BACKWARD = -0.75;
	public static final double AUTO_DRIVE_IDLE = 0.0;
	public static final int AUTO_JSON_CREATOR_PARAM_LIMIT = 4;
	
	//Test values
  public static final int TEST_COUNT_CONDITION = 150;
	
	//Power Distribution Panel ID's UPDATE THESE USING CONTRACT!!!!!!!!!!!!!!
  public static final int PDP_SERIALIZER_MOTOR_ID = 13;
  public static final int PDP_ELEVATOR_MOTOR_ID = 3;
	public static final int PDP_CLIMBER_MOTOR_ONE_ID = 11;
  public static final int PDP_CLIMBER_MOTOR_TWO_ID = 10;
	
	//PDP Overload values
	public static final int PDP_CURRENT_DRAW_LIMIT = 20;
	
	//Servo power setting
  public static final int SERVO_ON = 1;
  public static final int SERVO_OFF = 0;
	
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
	
	/*THESE ARE THE ALL THE TALON IDS FOR THE 2017 ROBOT*/
	public final static int SHOOTER_MASTER_TALON = 1;
  public final static int SHOOTER_SLAVE_TALON = 2;
  public final static int ELEVATOR_TALON = 3;
  public final static int INTAKE_TALON = 4;
	public final static int DRIVE_TRAIN_MASTER_RIGHT = 5;
	public final static int DRIVE_TRAIN_SLAVE_RIGHT = 6;
	public final static int DRIVE_TRAIN_MASTER_LEFT = 7;
	public final static int DRIVE_TRAIN_SLAVE_LEFT = 8;
	public final static int SERIALIZER_TALON = 9;
//RENAME THIS IF IT IS USED!!!
  public final static int UNASSIGNED_TALON = 10;
	public final static int CLIMBER_TALON_ONE = 11;
	public final static int CLIMBER_TALON_TWO = 12;
	
	//FPID VALUES FOR SHOOTER
	public final static double SHOOTER_TALON_F_VALUE = 0;
	public final static double SHOOTER_TALON_P_VALUE = 0;
	public final static double SHOOTER_TALON_I_VALUE = 0;
	public final static double SHOOTER_TALON_D_VALUE = 0;
	
	//SERVOS
	public final static int HOOD_SERVO_ID = 13;
	public final static int HOPPER_DOOR_ID = 14;
	public final static int SPROCKET_ONE = 15;
	public final static int SPROCKET_TWO = 16;
	
}
