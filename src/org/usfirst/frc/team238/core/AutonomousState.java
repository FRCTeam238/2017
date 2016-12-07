package org.usfirst.frc.team238.core;


public interface AutonomousState {
	
	public void init();
	public void prepare();
	public void init(String params[], CommandController theMcp);
	public void process();
	public boolean done();
	public void reset();
	public void showParams();
	public void updateParams();
	public String getParam(int value);
	
}
