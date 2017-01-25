package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.commands.CommandResetPcm;
import org.usfirst.frc.team238.robot.Drivetrain;

public class OperatorCmdFactory {

  CommandResetPcm commandResetPcm;

  HashMap<Integer, Command> operatorCommands;

  public void init() {

    operatorCommands = new HashMap<Integer, Command>(16);

  }

  public HashMap<Integer, Command> createOperatorCommands(Drivetrain theDrivetrain) {

    commandResetPcm = new CommandResetPcm(theDrivetrain);
    operatorCommands.put(4, commandResetPcm);

    return operatorCommands;

  }

}
