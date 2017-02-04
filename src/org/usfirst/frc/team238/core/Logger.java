package org.usfirst.frc.team238.core;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger {
	
	static Boolean isDebug;
	
	public Logger() {
		// TODO Auto-generated constructor stub
	}
	public static Boolean isDebug()
	{
		
		isDebug = SmartDashboard.getBoolean("Debug");
		
		return isDebug;
	}
	
	public static void logDouble(String comment, double value)
	{
		if(isDebug())
		{
			System.out.println(comment + value);
		}
		
	}
	public static void logString(String comment)
	{
		if(isDebug())
		{
			System.out.println(comment);
		}
		
	}
	
	public static void logInt(String comment, int value)
	{
		if(isDebug())
		{
			System.out.println(comment + value);
		}
	}
	
	public static void logTwoInt(String comment, int value, String comment2, int value2)
	{
		if(isDebug())
		{
			System.out.println(comment + value + " " + comment2 + value2);
		}
	}
	
	public static void logTwoDouble(String comment, double value, String comment2, double value2)
	{
		if(isDebug())
		{
			System.out.println(comment + value + " " + comment2 + value2);
		}
		
	}
	
	public static void logTwoString(String comment, String comment2)
	{
		if(isDebug())
		{
			System.out.println(comment + " " + comment2);
		}
	}
	
	public static void logFourString(String comment, String comment2, String comment3, String comment4)
	{
		if(isDebug())
		{
			System.out.println(comment + " " + comment2 + " " + comment3 + " " + comment4 + " ");
		}
	}
	
	public static void logBoolean(String comment, boolean Boolean)
	{
		if(isDebug())
		{
			System.out.println(comment + Boolean);
		}
	}
	
	public static void logTwoBoolean(String comment, boolean Boolean, String comment2, boolean Boolean2)
	{
		if(isDebug())
		{
			System.out.println(comment + Boolean + " " + comment2 + Boolean2);
		}
	}
	
	public static void logStringIntString(String comment, int value, String comment2)
	{
		if(isDebug())
		{
			System.out.println(comment + value + comment2);
		}
		
	}
	
	public static void logThreeDoubles(String comment, double value, String comment2, double value2, String comment3, double value3)
	{
		if(isDebug())
		{
			System.out.println(comment + value + comment2 + value2 + comment3 + value3);
		}
	}
	
}
