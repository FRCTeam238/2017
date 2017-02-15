package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.SprocketDoor;

public class CommandOpenSprocket implements Command {

  SprocketDoor theSprocket;
  
  public CommandOpenSprocket(SprocketDoor theSprocketdoor) {
    // TODO Auto-generated constructor stub
    
    this.theSprocket = theSprocketdoor;
    
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    
    theSprocket.openDoor();
    
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

}
