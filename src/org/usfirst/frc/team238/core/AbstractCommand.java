package org.usfirst.frc.team238.core;

public class AbstractCommand implements Command {

  
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
	
	public double pidCalc(double pValue, double deadStop, double targetValue, double maxError, double maxMotorValue)
	{
	  double motorValue = 0;
	  double error;
	  
	  
	  error = getError();
	  if(error < maxError)
	  {
	    
	    motorValue = (error * pValue) + deadStop;
	    
	  }
	  else
	  {
	    motorValue = maxMotorValue; //.7;
	  }
	  
	  return motorValue;
	  
	}
	
	public double getError()
	{
	  double error = 0;
	  return error;
	  
	}
	
  
}
