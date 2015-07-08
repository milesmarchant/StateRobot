package org.bitbuckets.frc.staterobot.state;

public abstract class BlockingState extends State {
	
	String[] blockedStates;

	public BlockingState(StateMachine context) {
		super(context);
		setBlockedStates();
	}
	
	protected abstract void setBlockedStates();

}
