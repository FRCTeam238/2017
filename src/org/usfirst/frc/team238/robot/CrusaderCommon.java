package org.usfirst.frc.team238.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	public static final double SHOOTER_MAX_RPM = SmartDashboard.getNumber("SHOOTER RPM", 0);//1500;
	
	public static final double AUTO_JSON_CREATOR_PARAM_LIMIT = 4;
	
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
	public final static double SERIALIZER_MOTOR_ON = 0.87;//0.77;
	public final static double SERIALIZER_MOTOR_OFF = 0;
	public final static double SERIALIZER_MOTOR_ON_REVERSE = -0.77;
	
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
	public final static int SHOOTER_MASTER_TALON = 11;
	public final static int SHOOTER_SLAVE_TALON = 12;
	public final static int ELEVATOR_TALON = 3;
	public final static int INTAKE_TALON = 2;
	public final static int DRIVE_TRAIN_MASTER_RIGHT = 7;
	public final static int DRIVE_TRAIN_SLAVE_RIGHT = 8;
	public final static int DRIVE_TRAIN_MASTER_LEFT = 5;
	public final static int DRIVE_TRAIN_SLAVE_LEFT = 6;
	public final static int SERIALIZER_TALON = 4;
	public final static int UNASSIGNED_TALON = 1;
	public final static int CLIMBER_TALON_ONE = 9;
	public final static int CLIMBER_TALON_TWO = 10;
	
	public final static int HOOD_SERVO_ID = 13;
	public final static int HOPPER_DOOR_SERVO_ID = 14;
	public final static int SPROCKET_SERVO_ONE = 15;
	public final static int SPROCKET_SERVO_TWO = 16;
	
	//Servo power setting
	public static final int SERVO_ON = 1;
	public static final int SERVO_OFF = 0;
  
	//Power Distribution Panel (PDP) ID's
	public static final int PDP_SERIALIZER_MOTOR_ID = 2;
	public static final int PDP_ELEVATOR_MOTOR_ID = 3;
	public static final int PDP_CLIMBER_MOTOR_ONE_ID = 12;
	public static final int PDP_CLIMBER_MOTOR_TWO_ID = 13;
	
	//CurrentDraw variable
	public final static double CURRENT_DRAW_LIMIT = 20.0;
	
	//Test values
	public static final int TEST_COUNT_CONDITION = 150;
	
	//FPID VALUES FOR SHOOTER
	public final static double SHOOTER_TALON_F_VALUE = 0.0480;
	public final static double SHOOTER_TALON_P_VALUE = 0.2;
	public final static double SHOOTER_TALON_I_VALUE = 0;
	public final static double SHOOTER_TALON_D_VALUE = 1.333;

	public final static double NAVIGATION_P_VALUE = 0.1;
	public final static double NAVIGATION_MAX_MOTOR_INCREMENT = 0.2;
	public final static double NAVIGATION_TURNING_DEADZONE = 6;
	
	public final static double DRIVE_FORWARD_P_VALUE = 0.015;
	public final static double DRIVE_FORWARD_I_VALUE = 0;
	public final static double DRIVE_FORWARD_MAX_YAW_PERCENT = 0.1;
	
	public final static int DRIVE_FORWARD_ENCODER_TICKS_PER_FOOT = 3900;//4983;//3900;
	
	public final static int SONIC_SENSOR_ACTIVATION_DISTANCE = 6840;
	public final static int SONIC_INPUT_PORT = 8;
	public final static int SONIC_OUTPUT_PORT = 9;
	

	
	public final static double SHOOTER_VISION_DEADZONE = 1;
	
	public final static double TURN_P_VALUE = 0.003;//SmartDashboard.getNumber("Turn P Value",0.005);//0.005;
	public final static double TURN_DEAD_STOP = 0.25;//SmartDashboard.getNumber("Turn Dead Stop", 0.42);//0.42;
	public final static double TURN_MAX_ERROR = 80;//SmartDashboard.getNumber("Turn Max Error",45);//45;
	public final static double TURN_MAX_MOTOR_VALUE = .7;//SmartDashboard.getNumber("Turn Max Error",45);//45;
	  
	public final static double STRAIGHT_P_VALUE = 0.000055;//0.00003512;
	public final static double STRAIGHT_DEAD_STOP = 0.05;
	public final static double STRAIGHT_MAX_ERROR = 15000;//9966;
	 public final static double STRAIGHT_MAX_MOTOR_VALUE  = 1;//SmartDashboard.getNumber("Turn Max Error",45);//45;
	  
	public final static int SHOOTER_COUNT_DELAY = 5;
	public final static int SHOOTER_PROCESSING_COUNT=10;
	
	public final static int ACCEPTABLE_RPM_ERROR = 1500;
	
	public final static double RING_LIGHT_DELAY = 0.25;
	public final static double ALIGN_IMAGE_DELAY = 0.25;
	public final static double ALIGN_ANGLE_BUFFER = 2;
	
	
	public final static int BOILER_TARGET_SERIALIZER_DELAY = 2000;
	
	public final static double PERCENT_COMPLETE_BEFORE_SHOOTER_START = 0.75;
	
	public final static double RED_SHOOTER_SPEED = 2808;
	public final static double RED_CLOSE_SHOOTER_SPEED = 1738;
	public final static double BLUE_SHOOTER_SPEED = 2900;
  
	
}
