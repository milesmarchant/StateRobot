package org.bitbuckets.frc.staterobot.state;


public abstract class State {
	
	StateMachine context;
	
	public State(StateMachine context){
		this.context = context;
	}
	
	public abstract void start();
	
	public abstract void run();
	
	public abstract void end();

}
