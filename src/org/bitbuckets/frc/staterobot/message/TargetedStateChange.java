package org.bitbuckets.frc.staterobot.message;

public class TargetedStateChange extends TargetedMessage {
	
	public String targetState;

	/**
	 * A message indicating that a particular state machine should change to a particular state. It has no inherent validation for the
	 * names of its targets.
	 * 
	 * @param targetID is the name of the targeted state machine
	 * @param targetState is the name of the desired state
	 */
	public TargetedStateChange(String targetID, String targetState) {
		super(targetID);
		this.targetState = targetState;
	}

}
