package org.usfirst.frc.team238.core;

import java.io.File;
import java.io.FileWriter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger {
	
  private static final String newline = "\n"; 
	static Boolean isDebug;
	static Boolean outputToLog;
	
	public Logger() {
		// TODO Auto-generated constructor stub
	}
	
	public static Boolean isWriteToFile(){
	  
	  outputToLog = SmartDashboard.getBoolean("Output Log to File");
	  
	  return outputToLog;
	}
	
	public static Boolean isDebug()
	{
		
		isDebug = SmartDashboard.getBoolean("Debug");
		
		return isDebug;
	}
	
	public static void Log(String comment)
	{
		if(isDebug())
		{
			System.out.println(comment);
      
      if(isWriteToFile()){
        
        writeToLogFile(comment);
        
      }
      
		}
		
	}

	public static void writeToLogFile(String log){
    
	  try{
	  
    File logFile238 = new File("/home/lvuser/logFile238.txt");
	  
	    if(logFile238.exists()){
	      
	      FileWriter logFile = new FileWriter("/home/lvuser/logFile238.txt");
	      logFile.write(newline+log);
	      logFile.flush();
	      logFile.close();
	      
	    }else{
	     
	      logFile238.createNewFile();
	      FileWriter logFile = new FileWriter("/home/lvuser/logFile238.txt");
        logFile.write(newline+log);
        logFile.flush();
        logFile.close();
        
	    }
	  }catch(Exception e){
	    
	    e.printStackTrace();
	    Log("Logger: writeToLogFile has Failed!");
	  
	  }
	}
	
}
