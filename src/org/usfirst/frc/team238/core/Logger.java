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
	
	
	/**
	 * Checks on the SmartDashboard on if debug is true
	 * @return
	 */
	public static Boolean isDebug()
	{
		
		isDebug = SmartDashboard.getBoolean("Debug");
		
		return isDebug;
	}
	
	
	/**
	 * Checks on the SmartDashboard on if "Output Log to File" is true
	 * @return
	 */
  public static Boolean writeToFile(){
    
    outputToLog = SmartDashboard.getBoolean("Output Log to File");
    
    return outputToLog;
  }
  
  
  //getCurrentDate()??
  
  //clearLog()??
  
  
  /**
   * Logs a string to the system log
   * Writes the comment to logFile238 by default
   * Also writes it to a file if writeToFile is true on the SmartDashboard
   * @param comment
   */
  public static void Log(String comment)
  {
    if(isDebug())
    {
      System.out.println(comment);
      
      if(writeToFile()){
        
        writeToLogFile(comment);
        
      }
      
    }
    
  }

  
    /**
   * Logs a string to the system log and creates a new file for it
   * Can be used for categorization
   * Also writes it to a file if writeToFile is true on the SmartDashboard
   * @param comment
   */
  public static void Log(String comment,String fileName)
  {
    if(isDebug())
    {
      //System.out.println(comment);
      
      if( writeToFile() ){
        
        writeToNewLogFile(comment,fileName);
        
      }
      
    }
    
  }
  
  
   /**
   * Writes the log into a custom file
   * @param log
   */
  public static void writeToNewLogFile(String log,String logFileName){
    
    try{
    
    File customFile = new File("/home/lvuser/"+logFileName+".txt");
    
    //If the file already exists, open the file and write the string to it 
      if(customFile.exists())
      {
        
        FileWriter logFile = new FileWriter("/home/lvuser/"+logFileName+".txt",true);
        logFile.write(newline+log);
        logFile.flush();
        logFile.close();
     
    //If the file doesn't already exists, create a new one and write the string to it
      }
      else
      {
       
        customFile.createNewFile();
        FileWriter logFile = new FileWriter("/home/lvuser/"+logFileName+".txt",true);
        logFile.write(newline+log);
        logFile.flush();
        logFile.close();
        
      }
    }
    catch(Exception e)
    {
      
      e.printStackTrace();
      Log("Logger: writeToNewLogFile has Failed!");
    
    }
  }
  
  
  /**
   * Writes the log into a file
   * @param log
   */
  public static void writeToLogFile(String log)
  {
    
    try
    {
    
    File logFile238 = new File("/home/lvuser/logFile238.txt");
    
    //If the file already exists, open the file and write the string to it 
      if(logFile238.exists()){
        
        FileWriter logFile = new FileWriter("/home/lvuser/logFile238.txt",true);
        logFile.write(newline+log);
        logFile.flush();
        logFile.close();
     
    //If the file doesn't already exists, create a new one and write the string to it
      }else{
       
        logFile238.createNewFile();
        FileWriter logFile = new FileWriter("/home/lvuser/logFile238.txt",true);
        logFile.write(newline+log);
        logFile.flush();
        logFile.close();
        
      }
    }catch(Exception e){
      
      e.printStackTrace();
      Log("Logger: writeToLogFile has Failed!");
    
    }
  }
  
  /*public static void writeToUSB(String log,String logFileName){
    // /U or media/svb?
    try{
    
    File customFile = new File("/U"+logFileName+".txt");
    
    //If the file already exists, open the file and write the string to it 
      if(customFile.exists())
      {
        
        FileWriter logFile = new FileWriter("/U"+logFileName+".txt",true);
        logFile.write(newline+log);
        logFile.flush();
        logFile.close();
     
    //If the file doesn't already exists, create a new one and write the string to it
      }
      else
      {
       
        customFile.createNewFile();
        FileWriter logFile = new FileWriter("/U"+logFileName+".txt",true);
        logFile.write(newline+log);
        logFile.flush();
        logFile.close();
        
      }
    }
    catch(Exception e)
    {
      
      e.printStackTrace();
      Log("Logger: writeToNewLogFile has Failed!");
    
    }
  }*/
}