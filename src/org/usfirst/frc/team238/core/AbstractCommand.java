package org.usfirst.frc.team238.core;

public class AbstractCommand implements Command {

  int theCount = 0;
  double theStart = 0;
  double theCurrent = 0;
  double theElapsed = 0;
  
	public AbstractCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
  public void execute(int buttonPressed) {
    // TODO Auto-generated method stub

  }
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParams() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public double pidCalc(double pValue, double deadStop, double targetValue, 
	    double maxError, double maxMotorValue, double iValue)
	{
	  double motorValue = 0;
	  double error;
	  
	  if(theCount == 0)
    {
      
      theStart = System.currentTimeMillis();
      theCount++; 
    }
    else
    {
      theCurrent = System.currentTimeMillis();
    }
    
    theElapsed = (theCurrent - theStart) * 1000;//TIMES 1000 TO CONVERT TO SECONDS
	  //double currentTime = System.currentTimeMillis() * 1000; // make this seconds
	  error = getError();
	  if(error < maxError)
	  {
	    Logger.Log("AbstractCommand(): pidCalc(): We Are Using motorValue");
	    motorValue = (error * pValue) + (iValue * integrate(0, theElapsed) * error);//deadStop;
	    
	  }
	  else
	  {
	    Logger.Log("AbstractCommand(): pidCalc(): We Are Using maxMotorValue!");
	    motorValue = maxMotorValue; //.7;
	  }
	  
	  Logger.Log("ERROR = " + error);
	  Logger.Log("PVALUE = " + pValue);
	  Logger.Log("IVALUE = " + iValue);
	  Logger.Log("INTEGRATE VALUE = " + integrate(0, theElapsed));
	  
	  /*Logger.writeToUSB("AbstractCommand(): pidCalc(): error = " + error, "usblogger");
	  Logger.writeToUSB("AbstractCommand(): pidCalc(): maxError = " + maxError,"usblogger");
	  Logger.writeToUSB("AbstractCommand(): pidCalc(): targetValue = " + targetValue, "usblogger");
	  Logger.writeToUSB("AbstractCommand(): pidCalc(): deadStop = " + deadStop, "usblogger");
	  Logger.writeToUSB("AbstractCommand(): pidCalc(): pValue = " + pValue, "usblogger");
	  Logger.writeToUSB("AbstractCommand(): pidCalc(): maxMotorValue = " + maxMotorValue, "usblogger");
	  Logger.writeToUSB("AbstractCommand(): pidCalc(): motorValue = " + motorValue, "usblogger");
	  */
	  
	  
	  return motorValue;
	  
	}
	
	public double getError()
	{
	  double error = 0;
	  return error;
	  
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 * 
	 * Standard normal distribution function
	 * 
	 */
	public static double f(double x)
	{
	  return Math.exp(- x * x / 2) / Math.sqrt(2 * Math.PI);
	}
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 * 
	 * Integrate f from a to b using Simpson's rule.
	 * Increase N for more precision.
	 */
	public static double integrate(double a, double b)
	{
	  int N = 10000;              //Precision parameter
	  double h = (b - a) / (N - 1); //step size
	  
	  //1/3 terms
	  double sum = 1.0 / 3.0 * (f(a) + f(b));
	  
	  //4/3 terms
	  for (int i = 1; i < N - 1; i += 2)
	  {
	    
	    double x = a + h * i;
	    sum += 4.0 / 3.0 * f(x);
	    
	  }
	  
	  //2/3 terms
	  for (int i = 2; i < N - 1; i += 2)
	  {
	    double x = a + h * i;
	    sum += 2.0 / 3.0 * f(x);
	  }
	  
	  return sum * h;
	}
	
	public void resetVals()
	{
	  theCount = 0;
	  theStart = 0;
	  theCurrent = 0;
	  theElapsed = 0;
	}
  
}
