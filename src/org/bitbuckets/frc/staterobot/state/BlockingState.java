package org.bitbuckets.frc.staterobot.state;

/**
 * BlockingState is an abstract class denoting that a class can block a state machine from going to certain other states.
 * An example usage might be to prevent a component on the robot from activating while moving. This blocking behavior
 * is implemented in StateMachine, not here. By convetion, a PriortyMessage will override a BlockingState.
 * 
 * @author Miles Marchant
 *
 */
public abstract class BlockingState extends State {
	
	String[] blockedStates;

	public BlockingState(StateMachine context) {
		super(context);
		setBlockedStates();
	}
	
	protected abstract void setBlockedStates();

}
