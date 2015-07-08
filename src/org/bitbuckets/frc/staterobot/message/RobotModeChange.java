package org.bitbuckets.frc.staterobot.message;

import org.bitbuckets.frc.staterobot.StateRobot;

public class RobotModeChange implements Message {
	
	public StateRobot.Mode mode;
	
	public RobotModeChange(StateRobot.Mode mode){
		this.mode = mode;
	}

}
