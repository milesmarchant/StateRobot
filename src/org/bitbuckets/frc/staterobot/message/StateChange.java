package org.bitbuckets.frc.staterobot.message;

public class StateChange implements Message {
	
	public String targetState;
	
	public StateChange(String targetState){
		this.targetState = targetState;
	}

}
