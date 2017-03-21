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
 * The goal of this is to be able to read the amodeJSON file for it's data, 
 * and to be able to creates, edit, and even saves the data to a new JSON file.
 * This also creates a chooser that's meant for selecting specific Autonomous Modes.
 * @author Mike Frye :D
 */
public class AutonomousDataHandler implements AutonomousState{
	
	//Every state from every Amode (In order the they were read)
	ArrayList<AutonomousState> autonomousModeStates;
	
	//Holds all of the commands in each autonomousMode in order
	ArrayList<AutonomousState>[] autonomousModeCommandList;
	
	//Holds the names for each autonomousMode
	ArrayList<String> autonomousModeNames;
	
	//Put's out all the autonomousModes for the user to choose from
	//SendableChooser aModeChooser; 
	
	
	/**
	 * @return Returns an array of Arraylists that includes the autonomousStates in order. With the index being the AutoModes 'ID' 
	 */
	public ArrayList<AutonomousState>[] getAutonomousModeCommandList(){
		return autonomousModeCommandList;
	}
	
	public Integer getModeSelectionFromDashboard(){
	  
	  int selection;
	  
	  selection = (int) SmartDashboard.getNumber("Chosen Auto Mode", 0);
	  //selection = Integer.parseInt(SmartDashboard.getString("Choosen Auto Mode","0"));
	  
	  return selection;
	}
	
	/**
	 * @return An Integer on what AutoMode was selected on the Smartdashboard 
	 */
//	public Integer getAModeChooserSelection(){
//		
//		String selection = (String) aModeChooser.getSelected();
//		
//		if(selection == null){
//			
//			return 0;
//			
//		}else{
//			
//			int modeSelection = Integer.parseInt(selection);
//			
//			if(modeSelection < 0){
//			  
//			  return 0;
//			  
//			}else{
//			  
//			  return modeSelection;
//			  
//			}
//		}
//	}
	
	
	/**
	 * Starts up the JSONHandler
	 * @param theMCP (The control scheme that's being used)
	 */
	public void init(CommandController theMCP)   //, SendableChooser theAutoChooser)
	{
		//aModeChooser = theAutoChooser;
		readJson(theMCP);
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
		
		Logger.Log("AutonomousDataHandler(): Test(): AutonomousDataHandler Standing by!");
		
		}catch(Exception e){
		  
			e.printStackTrace();
			Logger.Log("AutonomousDataHandler(): Test(): AutonomousDataHandler Test Failed!: Exception: "+ e);
			
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
			
			//Open 
			FileReader amodeFile = new FileReader("/home/lvuser/amode238.txt");
			
			//Use the parser to convert the file into an object
			Object obj = parser.parse(amodeFile);

			//Converts the object into a JSONObject
			JSONObject jsonObject = (JSONObject) obj;
			
			//Put's all AutonomousModes into an Array
			JSONArray autonomousModes = (JSONArray) jsonObject.get("AutonomousModes");

			//Creates an iterator out of that array
			Iterator<JSONObject> aModeIterator = autonomousModes.iterator();
			
			//Get's the number of modes
			int numModes = autonomousModes.size();
			Logger.Log("AutonomousDataHandler(): readJson(): Number of detected modes: " + numModes);
			
			//create a list of commandsteps for each mode
			autonomousModeCommandList = new ArrayList[numModes];
			
			//Creates an Arraylist of autonomousStates for each Mode
			for(int i=0;i<numModes;i++){
				autonomousModeCommandList [i]= new ArrayList<AutonomousState>();
			}
			
			//Keeps track of the names of each AutonomousMode
			autonomousModeNames = new ArrayList<String>(numModes);
			
			//A counter meant for outputing each autonomousMode to the aModeChooser
			int aModeIndexCounter = 0;
			
			//Iterates through each AutonomousMode
			while (aModeIterator.hasNext()) {
            	
				//Gets the name of the autonomousMode
            	JSONObject autoModeX = aModeIterator.next();
            	String name = (String) autoModeX.get("Name");
            	Logger.Log("AutonomousDataHandler(): readJson(): Autonomous Mode Name: " + name);
            	
            	//Add the name of this mode to the arrayList
            	autonomousModeNames.add(name);
            	
            	//Start building the list of selectable Amodes on the dashboard
//            	if(aModeIndexCounter == 1){
//            		aModeChooser.addDefault(name, String.valueOf(aModeIndexCounter));
//            	}
//            	else{
//            		aModeChooser.addObject(name,String.valueOf(aModeIndexCounter));
//            	}
//            	
            	SmartDashboard.putString("Amode "+aModeIndexCounter,name);
            	
            	//Create an array/iterator of commands from the AutoMode it's currently cycling through
            	JSONArray commandArrayList = (JSONArray) autoModeX.get("Commands");
            	Iterator<JSONObject> commandIterator = commandArrayList.iterator();
            	
            	//Iterates through each command
            	while (commandIterator.hasNext()) {
            		
            		//Gets the command as a JSONObject
            		JSONObject aCommand = commandIterator.next();
            		
            		//Debug stuff
            		String cmdName = (String) aCommand.get("Name");
            		Logger.Log("AutonomousDataHandler(): readJson(): 	Command Name = " + cmdName);
            		String cmdClass = classPath + cmdName; 
            		Logger.Log("AutonomousDataHandler(): readJson(): 	Class = " + cmdClass);

            		//Gets the array of params in the command
            		JSONArray paramArrayList = (JSONArray) aCommand.get("Parameters");
            		Iterator<String> paramIterator = paramArrayList.iterator();
            		
            		//Creates a new string array to hold the params in
            		String params[] = new String[paramArrayList.size()];
            		
            		//Adds every parameter into a String Array
            		int i = 0;
            		while (paramIterator.hasNext()) {
            			params[i++] = (String) paramIterator.next();
            			Logger.Log("AutonomousDataHandler(): readJson():    	Param:" + i + " = " + params[i -1]);
            		}
            		
            		try 
            		{
    					
            			//Use reflection to create state object (Naming it, while also giving it params and the control scheme on initiation)
        					AutonomousState xxx = (AutonomousState) Class.forName(cmdClass).newInstance();
        					
        					//Initiate the state object with the params and control scheme
        					xxx.init(params, theMCP);
        					
        					//add it to the steps for this autonomous mode   					
        					autonomousModeCommandList[aModeIndexCounter].add(xxx);
        					
        				} 
            		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
            		{
        					e.printStackTrace();
        				}
            		//Probably don't need this
            		//autonomousModeStates.add(arg0);
            }
            	aModeIndexCounter++;
            }
			
			amodeFile.close();
			
			//Push the list of Amodes to the dashboard
			//SmartDashboard.putData("Choose Auto", aModeChooser);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This function creates a string with the parameters given, and creates a new file from that given string
	 */
	public void save(){
		
		try {
			
			Logger.Log("AutonomousDataHandler(): save(): Saving...");
		
			//Creates the new json string with params given
			String newAmode = BuildNewJson();

			//get the fileName instead of declaring it here.
			String fileName = "amode238";
			
			//Specify a directory for this new file
			FileWriter file = new FileWriter("/home/lvuser/"+fileName+".txt");
			
			//Write out a the new file
			file.write(newAmode);
			file.flush();
			
			//Close it
			file.close();
			
			Logger.Log("AutonomousDataHandler(): save():  Saved!");
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FileWriter Failed!");
		} 
	}


	/**
	 * Builds a new JSON file using the AutoModeArrayList and the AutoModeNames and returns it as a String
	 * @returns A new JSON String Object
	 */
	@SuppressWarnings("unchecked")
	public String BuildNewJson () {	
		
		//Create a new string for the JSON
		String newAmode238String = "";
		
		try{
			
			//Used to increment through each set of commands in the AutoModeArrayList
			ArrayList<AutonomousState> StateArrayList;
			
			//Incrementor for the AutoMode Names
			int modeNameInc = 0;
			
			//All AutoMode data is placed within a JsonObject
			JSONObject newAmodeJSONObject = new JSONObject();
			
			//Array for every autoModes to be put into
			JSONArray jsonAutoModeArray = new JSONArray();
			
			//Put the array above into the JSONObject while also giving the array a name
			newAmodeJSONObject.put("AutonomousModes",jsonAutoModeArray);
			
			//Create an iterator for the list of modes
			Iterator<String> ModeIterator = autonomousModeNames.iterator();
			
			while(ModeIterator.hasNext()){
				
				//Create a new string for naming this automode
				String XmodeName = new String();
				XmodeName = ModeIterator.next().toString();
				
				//Make the CommandArrayList equal the set of commands in the mode it's currently cycling through
				StateArrayList = autonomousModeCommandList[modeNameInc];
				Iterator<AutonomousState> StateIterator = StateArrayList.iterator();
				
				//Create a JSONObject that will be an AutonomousMode
				JSONObject jsonAutoModeArrayElement = new JSONObject();
				
				//Create an array that will hold all of the states/commands for this mode
				JSONArray jsonStatesArray = new JSONArray(); 
				
				//Adds this new AutoMode to the JSONAutoModeArray
				jsonAutoModeArray.add(jsonAutoModeArrayElement);
			
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
					
					//Put the state into the AutoMode element
					jsonAutoModeArrayElement.put("Commands", jsonStatesArray);
					
					//Put the name of the state in the stateObject
					jsonStateObject.put("Name", stateName); 
					
					//Increments through all the parameters in this command (If there's any)
					for(int i=0; i<CrusaderCommon.AUTO_JSON_CREATOR_PARAM_LIMIT; i++){
						
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
				jsonAutoModeArrayElement.put("Name", XmodeName); 
				
				//Increment through to the next mode
				modeNameInc++;
				
			}
			
			//Convert the JSON into a string
			newAmode238String = newAmodeJSONObject.toJSONString();

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("BuildNewJSON has failed!");
		}
		
		return newAmode238String;
	}
	
	 /**
   * Goes through each AutonomousMode and prints out each state and it's parameters
   */
  public void dump(){
    
    int aModeSelection = (int) SmartDashboard.getNumber("Chosen Auto Mode");
    int index = (int) SmartDashboard.getNumber("Select Auto State");
    int count = 0;
    String name;
    String statesList;
    autonomousModeStates = autonomousModeCommandList[aModeSelection];
    Iterator<AutonomousState> aModeIterator = autonomousModeStates.iterator();
    
    while(aModeIterator.hasNext()){
      
      //Outputs every state name to the dashboard
      AutonomousState thisState = aModeIterator.next();
      name =  thisState.getClass().getName();
      name = name.substring(41);
      statesList = "AutoStateList " + count + " ";
      SmartDashboard.putString( statesList, name);
      Logger.Log("AutonomousDataHandler(): dump(): State Name" + name);
    
      //If this state was selected
      if ( count == index){
        
        //Show params of the selected state
        SmartDashboard.putString("AutoStateName", name);
        thisState.showParams();

      }
      
      count++;
    }

    //Kinda confused on what this is specifically used for - It clears out the rest of the autostatelist entries on teh DB
    while(count < 12){ 
      
      statesList = "AutoStateList " + count + " ";
      SmartDashboard.putString( statesList, " ");
      count++;
      
    }
  }

	/*
	 * Goes through each AutonomousMode and prints out each state and it's parameters
	 
	public void dump(){
		
		int index = (int) SmartDashboard.getNumber("Select Auto State");
		int count = 0;
		String selection = (String) aModeChooser.getSelected();
		int aModeSelection = Integer.parseInt(selection);
		
		
		String name;
		String statesList = String.valueOf(aModeChooser.getSelected() + ": ");
		
		//Substitutes for the 'steps' variable from the AutonomousController
		
		autonomousModeStates = autonomousModeCommandList[aModeSelection];
		Iterator<AutonomousState> aModeIterator = autonomousModeStates.iterator();
		
		while(aModeIterator.hasNext()){
			
			//Outputs every state name to the dashboard
			AutonomousState thisState = aModeIterator.next();
			name =  thisState.getClass().getName();
			name = name.substring(41);
			statesList = "AutoStateList " + count + " ";
			SmartDashboard.putString( statesList, name);
			Logger.Log("AUTONOMOUS DUMP " + name);
		
			//If this state was selected
			if ( count == index){
				
				//Show params of the selected state
				SmartDashboard.putString("AutoStateName", name);
				thisState.showParams();

			}
			
			count++;
		}

		//Kinda confused on what this is specifically used for
		while(count < autonomousModeStates.size()){ 
			
			statesList = "AutoStateList " + count + " ";
			SmartDashboard.putString( statesList, " ");
			count++;
			
		}
	}

	*/
  
  
	/**
	 * Used to keep track of when the JSON was last saved.
	 * @return The date is in the format of MM/dd/yyyy hh:mm:ss (PM/AM)
	 */
	public String getModificationDate(){
	  
		String ModificationDate="";
		
		File filedate = new File("/home/lvuser/amode238.txt");
		
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
}
