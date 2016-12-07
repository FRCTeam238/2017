package org.usfirst.frc.team238.core;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.usfirst.frc.team238.core.AutonomousState;

public class AutonomousJSONFactory implements AutonomousState {
	
	/**
	 * This function takes a string and creates a new file from that given string
	 * @param AutoModesArrayList (Gets created in the autonomousController, and is sent here from the robot)
	 * @param AutoModeNames (Gets created in the autonomousController, and is sent here from the robot)
	 */
	
	public void save(ArrayList<AutonomousState>[] autonomousModeList, ArrayList<String> autoModeNames){

		String newAmode = "";
		//Creates the new json string with params given
		newAmode = BuildNewJson(autonomousModeList,autoModeNames);
		
		try {

			String fileName = "amode238";
			//Specify a directory for this new file
			FileWriter file = new FileWriter("/home/lvuser/"+fileName+".txt");
			//Write out a the new file
			file.write(newAmode);
			//Push it out
			file.flush();
			//Close it
			file.close();
			
			//System.out.println("    SAVED    ");
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FileWriter Failed!");
		} 
	}
	
	/**
	 * Builds a new JSON file using the AutoModeArrayList and the AutoModeNames and returns it as a string
	 * @param AutoModesArrayList (Gets created in the autonomousController, and is sent here from the robot)
	 * @param AutoModeNames (Gets created in the autonomousController, and is sent here from the robot)
	 * @returns A new JSON String Object
	 */
	@SuppressWarnings("unchecked")
	public String BuildNewJson (ArrayList<AutonomousState>[] AutoModesArrayList, ArrayList<String> AutoModeNames) {	
		
		//Create a new string for the JSON
		String modeString = "";
		
		try{
			
			//Used to increment through each set of commands in the AutoModeArrayList
			ArrayList<AutonomousState> CommandsArrayList;
			//Incrementor in order to get each set of autonomousMode States
			int modeNameInc = 0;
			//Starting with a JsonObject
			JSONObject newAmodeJSONObject = new JSONObject();
			//Array for all autoModes
			JSONArray jsonAutoModeArray = new JSONArray(); 
			//Put a name and the new JSONArray into the starting JsonObject,
			newAmodeJSONObject.put("AutonomousModes",jsonAutoModeArray);
			//Create an iterator for the list of modes
			Iterator<String> ModeIterator = AutoModeNames.iterator();
			
			while(ModeIterator.hasNext()){
				
				//Create a new string for the name of this new automode
				String XmodeName = new String();
				//Could this be used instead?
				XmodeName = ModeIterator.next().toString();
				//Make the CommandArrayList equal to a certain mode in an autoModeArrayList
				CommandsArrayList = AutoModesArrayList[modeNameInc];
				Iterator<AutonomousState> CommandIterator = CommandsArrayList.iterator();
				//Create a new object for this specific AutoMode
				JSONObject jsonAutoModeArrayObject = new JSONObject();
				//Hold the Array of Commands for this specific AutoMode
				JSONArray jsonCommands = new JSONArray(); 
				//Adds this new AutoMode to the JSONAutoModeArray
				jsonAutoModeArray.add(jsonAutoModeArrayObject);//Add an AutoMode
				//System.out.println("ModeIterator = "+ModeIterator.next());
			
				while(CommandIterator.hasNext()){
					
					//Creates a new string for the CommandName
					String commandName = new String();
					//Current Command being incremented
					AutonomousState CurrentCommandState = CommandIterator.next();
					//Create a new JSONArray for the params of this command
					JSONArray jsonParams = new JSONArray();
					//A JSONObject that holds the name and params of the Command
					JSONObject jsonCommandObject = new JSONObject(); //Object that holds the name and params
					//Create a new string for this specific CommandName
					commandName = CurrentCommandState.toString();
					//Make a substring for this CommandName so that it has just the name, with no extra characters
					commandName = commandName.substring(46, commandName.indexOf("@"));
					//System.out.println("CommandIterator = "+commandName);
					//Put the new command into this specific AutoMode
					jsonAutoModeArrayObject.put("Commands", jsonCommands); //Add a Command Array to the mode
					//Put the name of the command in the Command Object
					jsonCommandObject.put("Name", commandName); 
					
					//Increments through all the parameters in this command (If there's any)
					for(int i=0; i<4; i++){
						
							//Grab the parameter of this specific Command
							String paramString = CurrentCommandState.getParam(i);
							//System.out.println("PARAMSTRING="+paramString);
							
							//If this paramString has nothing in it
							if(paramString==""){
								//Get out of this param loop
								break;
							}
							
							//Add this paramString into this Specific Command's params
							jsonParams.add(paramString);
					}
					
					//Put the parameter array into the Command Object
					jsonCommandObject.put("Parameters", jsonParams);
					//Add the command Object into the Command Array
					jsonCommands.add(jsonCommandObject); 
					
				}

				//System.out.println(XmodeName);
				//Put the name of this autoMode in
				jsonAutoModeArrayObject.put("Name", XmodeName); 
				//Increment through to the next mode
				modeNameInc++;
				
			}
			
			//Output the new JSON
			modeString = newAmodeJSONObject.toJSONString();

		}catch(Exception e){
			
			e.printStackTrace();
			System.out.println("BuildNewJSON has failed!");
			
		}
		
		return modeString;
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
