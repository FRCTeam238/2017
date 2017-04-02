package org.usfirst.frc.team238.core;

import java.util.ArrayList;
import java.util.Iterator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousController implements AutonomousState{

	//Current state being processed
	private AutonomousState currentState;
	
	//Index for going through the autonomousModeList
	private int index = 0;
	
	//The states that it's currently processing through
	ArrayList<AutonomousState> steps;
	
	//Holds all autocommands in order (does not include the automode names)
	ArrayList<AutonomousState>[] autonomousModeList;

	/**
	 * Allows the JSONHandler to pass it's data to the autonomousController for processing
	 * @param autonomousModeCommandList
	 */
	public void setAutonomousControllerData(AutonomousDataHandler myJsonHandler){
		autonomousModeList = myJsonHandler.getAutonomousModeCommandList();
	}
	
	/**
	 * Sets the array of steps to be processed during AutonomousMode
	 * @param mode
	 */
	public void pickAMode(int mode){
		steps = autonomousModeList [mode];
		setState(steps.get(0));
		index = 0;
	}
	
	/**
	 * Sets the current state to the given state parameter
	 * (Also calls the prepare method of the given state)
	 * @param state
	 */
	public void setState(AutonomousState state){
		this.currentState = state;
		state.prepare();
		Logger.Log("AutonomousController(): State: " + currentState);
	}
	
	/**
	 * Process the currentState and then continues onto the next state when done
	 */
	public void process() { 
		
		this.currentState.process();
		
		if(this.currentState.done() == true){
			
			setState(getNextState());
		}

	}

	/**
	 * Gets the next state in the sequence of steps
	 * @return
	 */
	private AutonomousState getNextState(){
		Logger.Log("AutonomousController(): getNextState(): index = " + index);
		AutonomousState nextState = steps.get(++index);
		
		return(nextState);
	}
	
	/**
	 * This cycles through every state until it gets to the selected state to be changed.
	 * Then applies the changes to the state object.
	 * THIS ISN'T THE SAME AS SAVING! THIS JUST UPDATES THE STATES WITH NEW PARAMS!
	 */
	public void updateStateParameters(int selectedMode)
	{
		//Get the index of the state that's being modified
		int index = (int) SmartDashboard.getNumber("Select Auto State");
		int count = 0;

		steps = autonomousModeList[selectedMode];
		Iterator<AutonomousState> aModeIterator = steps.iterator();
		
		//Iterates through the mode's states until it finds the selected state
		while(aModeIterator.hasNext()){
			
			AutonomousState thisState = aModeIterator.next();
			Logger.Log("AutonomousController(): updateStateParameters(): State Updated! :" + thisState.getClass().getName());
			if ( count == index)
			{
				//Updates it (It grabs the data from the SmartDashboard and applies it)
				thisState.updateParams();
				
			}
			
			//Increment counter as it goes through each states
			count++;
		}
	}

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    index = 0;
  }
  
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(String[] params, CommandController theMcp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showParams() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateParams() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getParam(int value) {
		// TODO Auto-generated method stub
		return null;
	}

}