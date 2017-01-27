package org.usfirst.frc.team238.core;

import java.io.FileReader;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.usfirst.frc.team238.robot.Robot;

//import edu.wpi.first.wpilibj.buttons.NetworkButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousController implements AutonomousState {

	private AutonomousState currentState;
	private int index = 0;
	Robot the238Robot;
	ArrayList<AutonomousState> steps;
	//Holds all autocommands in order (does not include the automode names)
	ArrayList<AutonomousState>[] autonomousModeList;
	//Holds the names for each autonomousMode
	ArrayList<String> autoModeNames;
	SendableChooser aModeChooser; 
	//NetworkButton testBtn;
	int MAX_NUM_IN_A_LIST =  20; //mjf
	 
	//Returns a list of each set of AutonomousMode States
	public ArrayList<AutonomousState>[] getAutoModeList (){
		return autonomousModeList;
	}
		
	//Returns the name of each AutonomousMode
	public ArrayList<String> getAutoModeNames (){
		return autoModeNames;
	}
	
	@Override
	public void prepare()
	{
	}
	
	public AutonomousController()
	{
	}
	
	public void init(CommandController theMCP)
	{
		aModeChooser = new SendableChooser();
		
		readJson(theMCP);
	}
	
	public void init(String params[], CommandController theMcp)
	{	
		
	}
	public void reset()
	{	
		
	}
	
	public void pickAMode(int mode){
		steps = autonomousModeList [mode];
		setState(steps.get(0));
		index = 0;
	}
	
	public void setState(AutonomousState state)
	{
		this.currentState = state;
		state.prepare();
		Logger.logString("State: " + currentState);
	}
	/*
	 * (non-Javadoc)
	 * @see org.usfirst.frc.team238.core.AutonomousState#process()
	 *  Walks down the list of autonomous actions
	 */
	@Override
	public void process() {
		
		this.currentState.process();
		
		if(this.currentState.done() == true)
		{
			setState(getNextState());
		}

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

	private AutonomousState getNextState()
	{
		Logger.logInt("getNextState:index = " , index);
		AutonomousState nextState = steps.get(++index);
		
		return(nextState);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public void readJson(CommandController theMCP) {

		try {
			JSONParser parser = new JSONParser();
			
			String classPath = "org.usfirst.frc.team238.autonomousStates.State";
			
			Object obj = parser.parse(new FileReader("/home/lvuser/amode238.txt"));

			JSONObject jsonObject = (JSONObject) obj;
			JSONArray autonomousModes = (JSONArray) jsonObject.get("AutonomousModes");

			Iterator<JSONObject> aModeIterator = autonomousModes.iterator();
			int numModes = autonomousModes.size();
			Logger.logInt("NumModes : " , numModes);
			
			//create a list of commandsteps for each mode
			autonomousModeList = new ArrayList[numModes];
			
			for(int i=0;i<numModes;i++){
				autonomousModeList [i]= new ArrayList<AutonomousState>();
			}
			
			autoModeNames = new ArrayList<String>(numModes);
			
			//testBtn = new NetworkButton("SmartDashboard","UPDATEPARAM");
			
			int aModeIndexCounter = 0;
			while (aModeIterator.hasNext()) {
            	
            	JSONObject autoModeX = aModeIterator.next();
            
            	String name = (String) autoModeX.get("Name");
            	Logger.logString("Autonmous Name: " + name);
            	
            	//Add the name of this mode to the arrayList
            	autoModeNames.add(name);
            	
            	//Start building the list of Amodes available that will get pushed to the dashboard
            	if(aModeIndexCounter == 1){
            		aModeChooser.addDefault(name, String.valueOf(aModeIndexCounter));
            	}
            	else{
            		aModeChooser.addObject(name,String.valueOf(aModeIndexCounter));
            	}
            	
            	JSONArray companyList = (JSONArray) autoModeX.get("Commands");

            	Iterator<JSONObject> iterator = companyList.iterator();
            	
            	while (iterator.hasNext()) {
            		JSONObject aCommand = iterator.next();
            		String cmdName = (String) aCommand.get("Name");
            		Logger.logTwoString("	Command Name = " , cmdName);
            		String cmdClass = classPath + cmdName; 
            		Logger.logTwoString("	Class = " , cmdClass);

            		JSONArray paramList = (JSONArray) aCommand.get("Parameters");
            		Iterator<String> paramIterator = paramList.iterator();
            		
            		String params[] = new String[paramList.size()];
            		int i = 0;
            		while (paramIterator.hasNext()) {
            			params[i++] = (String) paramIterator.next();
            			Logger.logStringIntString("	Param:" , i , " = " + params[i -1]);
            		}
            		try {
    					//use reflection to create state object
    					AutonomousState xxx = (AutonomousState) Class.forName(cmdClass).newInstance();
    					
    					xxx.init(params, theMCP);
    					
    					//add it to the steps for this autonomous mode   					
    					autonomousModeList[aModeIndexCounter].add(xxx);
    					

    				} catch (InstantiationException | IllegalAccessException
    						| ClassNotFoundException e) {
    					
    					e.printStackTrace();
    				}           		

            	}
            	//this is used to index into the array of autonomous mode arrayLists
            	aModeIndexCounter++;
            }
			
			//Push the list of Amodes to the dashboard
			SmartDashboard.putData("Choose Auto", aModeChooser);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void dump(){
		
		int index = SmartDashboard.getInt("AutoStateCmdIndex");
		int count = 0;
		String name;
		String statesList = String.valueOf(getAutonomousSelection() + ": ");
		Iterator<AutonomousState> aModeIterator = steps.iterator();
		
		while(aModeIterator.hasNext()){
			
			AutonomousState thisState = aModeIterator.next();
			name =  thisState.getClass().getName();
			name = name.substring(41);
			statesList = "AutoStateList " + count + " ";
			SmartDashboard.putString( statesList, name);
			
			
			Logger.logString("AUTONOMOUS DUMP " + name);
		
			
			if ( count == index)
			{
				SmartDashboard.putString("AutoStateName", name);
				thisState.showParams();
				
			}
			
			count++;
		}
		
		//TODO Use statesList.size instead of MAX_NUM
		while(count < MAX_NUM_IN_A_LIST){ 
			statesList = "AutoStateList " + count + " ";
			SmartDashboard.putString( statesList, " ");
			count++;
		}
	}

	@Override
	public void showParams() {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("deprecation")
	public void updateStateParameters()
	{
		int index = SmartDashboard.getInt("AutoStateCmdIndex");
		int count = 0;
		
		Iterator<AutonomousState> aModeIterator = steps.iterator();
		
		while(aModeIterator.hasNext()){
			
			AutonomousState thisState = aModeIterator.next();
			Logger.logString("AUTONOMOUS Update State Params " + thisState.getClass().getName());
			if ( count == index)
			{
				thisState.updateParams();
				
			}
			
			count++;
		}
	}

	@Override
	public void updateParams() {
		// TODO Auto-generated method stub
		
	}
	
	public int getAutonomousSelection(){
		
		String automousModeFromDashBoard = (String) aModeChooser.getSelected();
		Logger.logTwoString("The chosen One =  " , automousModeFromDashBoard);
		
		return Integer.parseInt(automousModeFromDashBoard);
	}

	@Override
	public String getParam(int value) {
		// TODO Auto-generated method stub
		return null;
	}

}
