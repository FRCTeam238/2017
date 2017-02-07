package org.usfirst.frc.team238.commands;

import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.robot.Serializer;

public class CommandStartSerializer implements Command {

  Serializer mySerializer;
  
  public CommandStartSerializer (Serializer theSerializer){
    this.mySerializer = theSerializer;
  }
  
  @Override
  public void execute() {
    // TODO Auto-generated method stub
    mySerializer.startSpinning();
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
    return mySerializer.complete();
  }

}
