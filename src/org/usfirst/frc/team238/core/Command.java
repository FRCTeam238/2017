package org.usfirst.frc.team238.core;

public interface Command {
	
	public void execute();
	public void prepare();
	public void setParams();
	public boolean done();
	//public void execute(double overRideValue);

}
