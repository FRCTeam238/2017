package org.usfirst.frc.team238.core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team238.robot.CrusaderCommon;

/**
 * The goal of this is to be able to read the target solution table JSON file for it's data, 
 * and to be able to create, edit, and even save the data to a new JSON file.
 * This also creates a chooser that's meant for selecting specific targetSolutions.
 * It is leveraging the autonomous subsystem to read in a set of target coordinates that 
 * correspond to an angle. The vision system returns an angle which we'll use to look up 
 * the correct motor speed and hood position from the target solution table.  
 * @author Mike :D
 */
public class TargetingDataHandler implements AutonomousState{
	
	//Every state obj from every tagetsolution (In order the they were read)
	ArrayList<AutonomousState> targetSolutionStates;
	
	//Holds all of the commands in each targetSolution in order
	ArrayList<AutonomousState>[] targetSolutionCommandList;
	
	//Holds the names for each targetSolution
	ArrayList<String> targetSolutionNames;
	
	
	//Put's out all the targetSolution for the user to choose from
	SendableChooser<String> targetChooser; 
	
	
	/**
	 * @return An Integer on what AutoMode was selected on the Smartdashboard 
	 */
	public Integer getTargetChooserSelection(){
		
		String selection = (String) targetChooser.getSelected();
		
		if(selection == null)
		{
			
			return 0;
			
		}
		else
		{
			
			int modeSelection = Integer.parseInt(selection);
			
			if(modeSelection < 0)
			{
			  
			  return 0;
			  
			}
			else
			{
			  
			  return modeSelection;
			  
			}
		}
	}
	
	
	/**
	 * Starts up the JSONHandler
	 * @param theMCP (The control scheme that's being used)
	 */
	public void init(CommandController theMCP)
	{
		targetChooser = new SendableChooser();
		//readJson(theMCP);
	}
	
	
	/**
	 * Test function for JSONHandler
	 * @param theMCP (The control scheme that's being used)
	 */
	public void Test(CommandController theMCP){
		try{
		
		//Reads the data, prints out the data, and saves it
		readJson(theMCP);
		dump();
		save();
		
		Logger.Log("TargetingDataHandler() : Test() : TargetingDataHandler Standing by!");
		
		}catch(Exception e){
		  
			e.printStackTrace();
			
			Logger.Log("TargetingDataHandler() : Test() : TargetingDataHandler Test Failed!");
			
		}
	}
	
	
	/**
	 * Reads the JSON file and creates a list of autonmousStates for each autonomousMode
	 * @param theMCP: Uses theMCP to create new state objects
	 */
	@SuppressWarnings("unchecked")
	public void readJson(CommandController theMCP) {
		try {
			
			//Something to read the JSON File
			JSONParser parser = new JSONParser();
			
			//Path in which all states will be created
			String classPath = "org.usfirst.frc.team238.autonomousStates.State";
			
			FileReader TargetSolutionFile = new FileReader("/home/lvuser/TargetSolutionTable.txt");
			
			//Creates an object in which the file will be read
			Object obj = parser.parse(TargetSolutionFile);

			//Converts the object into a JSONObject
			JSONObject jsonObject = (JSONObject) obj;
			
			//Put's all TargetSolutions into an Array
			JSONArray targetSolutions = (JSONArray) jsonObject.get("TargetSolutionTable");

			//Creates an iterator out of that array
			Iterator<JSONObject> targetSolutionsIterator = targetSolutions.iterator();
			
			//Get's the number of target solutions
			int numSolutions = targetSolutions.size();
			Logger.Log("TargetingDataHandler() : readJson() : Number of Targeting Solutions : " + numSolutions);
			
			//create a list of commandsteps for each targetSolution
			targetSolutionCommandList = new ArrayList[numSolutions];
			
			//Creates an Arraylist of autonomousStates for each targetSolution
			for(int i = 0; i < numSolutions; i++){
				targetSolutionCommandList [i]= new ArrayList<AutonomousState>();
			}
			
			//Keeps track of the names of each AutonomousMode
			targetSolutionNames = new ArrayList<String>(numSolutions);
			
			//A counter meant for outputing each autonomousMode to the targetChooser
			int targetSolutionsIteratorIndexCounter = 0;
			
			//Iterates through each AutonomousMode
			while (targetSolutionsIterator.hasNext()) {
            	
				//Gets the name of the targetSolution
            	JSONObject targetSolution = targetSolutionsIterator.next();
            	String name = (String) targetSolution.get("Name");
            	Logger.Log("TargetingDataHandler() : readJson() : TargetSolution Name: " + name);
            	
            	//Add the name of this targetSolution to the arrayList
            	targetSolutionNames.add(name);
            	
            	//Start building the list of selectable targetSolutions on the dashboard
            	if(targetSolutionsIteratorIndexCounter == 1){
            		targetChooser.addDefault(name, String.valueOf(targetSolutionsIteratorIndexCounter));
            	}
            	else{
            		targetChooser.addObject(name,String.valueOf(targetSolutionsIteratorIndexCounter));
            	}
            	
            	//Create an array/iterator of commands from the AutoMode it's currently cycling through
            	JSONArray commandArrayList = (JSONArray) targetSolution.get("Commands");
            	Iterator<JSONObject> commandIterator = commandArrayList.iterator();
            	
            	//Iterates through each command
            	while (commandIterator.hasNext()) {
            		
            		//Gets the command as a JSONObject
            		JSONObject aCommand = commandIterator.next();
            		
            		//Debug stuff
            		String cmdName = (String) aCommand.get("Name");
            		Logger.Log("TargetingDataHandler() : readJson() :  	Command Name = " + cmdName);
            		String cmdClass = classPath + cmdName; 
            		Logger.Log("TargetingDataHandler() : readJson() :  	Class = " + cmdClass);

            		//Gets the array of params in the command
            		JSONArray paramArrayList = (JSONArray) aCommand.get("Parameters");
            		Iterator<String> paramIterator = paramArrayList.iterator();
            		
            		//Creates a new string array to hold the params in
            		String params[] = new String[paramArrayList.size()];
            		
            		//Adds every parameter into a String Array
            		int i = 0;
            		while (paramIterator.hasNext()) {
            			params[i++] = (String) paramIterator.next();
            			Logger.Log("TargetingDataHandler() : readJson() :   	Param:" + i + " = " + params[i -1]);
            		}
            		
            		try {
    					
            			//Use reflection to create state object (Naming it, while also giving it params and the control scheme on initiation)
    					AutonomousState xxx = (AutonomousState) Class.forName(cmdClass).newInstance();
    					
    					//Initiate the state object with the params and control scheme
    					xxx.init(params, theMCP);
    					
    					//add it to the steps for this autonomous mode   					
    					targetSolutionCommandList[targetSolutionsIteratorIndexCounter].add(xxx);
    					
    				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
    					e.printStackTrace();
    				}
          }
            	targetSolutionsIteratorIndexCounter++;
        }
			
			TargetSolutionFile.close();
			
			//Push the list of Amodes to the dashboard
			SmartDashboard.putData("Choose Target", targetChooser);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This function creates a string with the parameters given, and creates a new file from that given string
	 */
	public void save(){

		//Creates the new json string with params given
		String newTargetSolutionTable = BuildNewJson();
		
		try {
			
			//get the fileName instead of declaring it here.
			String fileName = "TargetSolutionTable";
			
			//Specify a directory for this new file
			FileWriter file = new FileWriter("/home/lvuser/"+fileName+".txt");
			
			//Write out a the new file
			file.write(newTargetSolutionTable);
			file.flush();
			
			//Close it
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FileWriter Failed!");
		} 
	}


	/**
	 * Builds a new JSON file using the targetSolutionArrayList and the targetSolutionNames and returns it as a String
	 * @returns A new JSON String Object
	 */
	@SuppressWarnings("unchecked")
	public String BuildNewJson () {	
		
		//Create a new string for the JSON
		String newTargetSolutionString = "";
		
		try{
			
			//Used to increment through each set of commands in the targetSolutionArrayList
			ArrayList<AutonomousState> StateArrayList;
			
			//Incrementor for the targetSolution Names
			int targetSolutionNameInc = 0;
			
			//All targetSolution data is placed within a JsonObject
			JSONObject newTargetSolutionJSONObject = new JSONObject();
			
			//Array for every targetSolutions to be put into
			JSONArray jsonTargetSolutionArray = new JSONArray();
			
			//Put the array above into the JSONObject while also giving the array a name
			newTargetSolutionJSONObject.put("TargetSolutionTable",jsonTargetSolutionArray);
			
			//Create an iterator for the list of targetSolutions
			Iterator<String> targetSolutionIterator = targetSolutionNames.iterator();
			
			while(targetSolutionIterator.hasNext()){
				
				//Create a new string for naming this targetSolution
				String targetSolutionName = new String();
				targetSolutionName = targetSolutionIterator.next().toString();
				
				//Make the CommandArrayList equal the set of commands in the targetSolution it's currently cycling through
				StateArrayList = targetSolutionCommandList[targetSolutionNameInc];
				Iterator<AutonomousState> StateIterator = StateArrayList.iterator();
				
				//Create a JSONObject that will be an targetSolution
				JSONObject jsonTargetSolutionArrayElement = new JSONObject();
				
				//Create an array that will hold all of the states/commands for this targetSolution
				JSONArray jsonStatesArray = new JSONArray(); 
				
				//Adds this new AutoMode to the JSONAutoModeArray
				jsonTargetSolutionArray.add(jsonTargetSolutionArrayElement);
			
				while(StateIterator.hasNext()){
					
					//Get the CurrentCommandState it's cycling through in order to get the data from it
					AutonomousState CurrentCommandState = StateIterator.next();
					
					//Create a new JSONArray to hold the parameters of this state
					JSONArray jsonParams = new JSONArray();
					
					//Create a JSONObject that holds the name and params of the state
					JSONObject jsonStateObject = new JSONObject();
					
					//Create a new string in order to name this State
					String stateName = new String();
					stateName = CurrentCommandState.toString();
					
					//Make a substring for this State so that it has just the name, with no extra characters
					stateName = stateName.substring(46, stateName.indexOf("@"));
					
					//Put the state into the targetSolution element
					jsonTargetSolutionArrayElement.put("Commands", jsonStatesArray);
					
					//Put the name of the state in the stateObject
					jsonStateObject.put("Name", stateName); 
					
					//Increments through all the parameters in this command (If there's any)
					for(int i = 0; i < CrusaderCommon.AUTO_JSON_CREATOR_PARAM_LIMIT; i++){
						
							//Grab the parameter of this specific Command
							String paramString = CurrentCommandState.getParam(i);
							
							//If this paramString has nothing in it
							if(paramString==""){
								//Get out of this param loop
								break;
							}
							
							//Add this paramString into the state's jsonParams
							jsonParams.add(paramString);
					}
					
					//Put the parameter array into the stateObject
					jsonStateObject.put("Parameters", jsonParams);
					
					//Add the state Object into the State Array
					jsonStatesArray.add(jsonStateObject); 
					
				}

				//Put the name of this autoMode in
				jsonTargetSolutionArrayElement.put("Name", targetSolutionName); 
				
				//Increment through to the next mode
				targetSolutionNameInc++;
				
			}
			
			//Convert the JSON into a string
			newTargetSolutionString = newTargetSolutionJSONObject.toJSONString();

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("BuildNewJSON has failed!");
		}
		
		return newTargetSolutionString;
	}
	
	
	/**
	 * Goes through each AutonomousMode and prints out each state and it's parameters
	 */
	public void dump(){
		
		@SuppressWarnings("deprecation")
    int index = (int) SmartDashboard.getNumber("TargetStateCmdIndex");
		int count = 0;
		String selection = (String) targetChooser.getSelected();
		int targetSolutionSelection = Integer.parseInt(selection);
		String name;
		String targetStatesList = String.valueOf(targetChooser.getSelected() + ": ");
		
		//Substitutes for the 'steps' variable from the AutonomousController
		
		targetSolutionStates = targetSolutionCommandList[targetSolutionSelection];
		Iterator<AutonomousState> targetSolutionIterator = targetSolutionStates.iterator();
		
		while(targetSolutionIterator.hasNext()){
			
			//Outputs every state name to the dashboard
			AutonomousState thisState = targetSolutionIterator.next();
			name =  thisState.getClass().getName();
			name = name.substring(41);
			targetStatesList = "TargetStateList " + count + " ";
			SmartDashboard.putString( targetStatesList, name);
			Logger.Log("TargetingDataHandler() : dump() : TargetSolution Name " + name);
		
			//If this state was selected
			if ( count == index){
				
				//Show params of the selected state
				SmartDashboard.putString("TargetSolutionName", name);
				thisState.showParams();

			}
			
			count++;
		}

		
		while(count < targetSolutionStates.size()){ 
			
		  targetStatesList = "AutoStateList " + count + " ";
			SmartDashboard.putString( targetStatesList, " ");
			count++;
			
		}
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

    targetSolutionStates = targetSolutionCommandList[selectedMode];
    Iterator<AutonomousState> aModeIterator = targetSolutionStates.iterator();
    
    //Iterates through the mode's states until it finds the selected state
    while(aModeIterator.hasNext()){
      
      AutonomousState thisState = aModeIterator.next();
      Logger.Log("TargetingDataHandler() : updateStateParameters() :  State Updated!: " + thisState.getClass().getName());
      if ( count == index)
      {
        //Updates it (It grabs the data from the SmartDashboard and applies it)
        thisState.updateParams();
        
      }
      
      //Increment counter as it goes through each states
      count++;
    }
  }
	
	/**
	 * Used to keep track of when the JSON was last saved.
	 * @return The date is in the format of MM/dd/yyyy hh:mm:ss (PM/AM)
	 */
	public String getModificationDate(){
	  
		String ModificationDate="";
		
		File filedate = new File("/home/lvuser/TargetSolutionTable.txt");
		
		SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy     hh:mm:ss a");
		
		ModificationDate = DateFormat.format(filedate.lastModified());
		
		return ModificationDate;
		
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
	public void process() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
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

/**
 * Sets the array of steps to be processed during AutonomousMode
 * @param mode
 */
public void pickAMode(int mode){
  targetSolutionStates = targetSolutionCommandList [mode];
  
  AutonomousState currentTargetSolution = targetSolutionStates.get(0);
  
  String angle = currentTargetSolution.getParam(0);
  String motorSpeed = currentTargetSolution.getParam(1);
  
  

}

}