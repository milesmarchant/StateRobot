package org.bitbuckets.frc.staterobot.message;

public abstract class TargetedMessage implements Message {
	
	public String targetID;
	
	/**
	 * A message that can be targeted.
	 * 
	 * @param targetID is the name of the target.
	 */
	public TargetedMessage(String targetID){
		this.targetID = targetID;
	}

}
