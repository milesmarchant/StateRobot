package org.bitbuckets.frc.staterobot.state;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.bitbuckets.frc.staterobot.Robot;
import org.bitbuckets.frc.staterobot.message.Message;
import org.bitbuckets.frc.staterobot.message.Messageable;
import org.bitbuckets.frc.staterobot.message.PriorityMessage;
import org.bitbuckets.frc.staterobot.message.StateChange;

public abstract class StateMachine implements Runnable, Messageable{
	
	private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();
	
	private Robot context;
	
	String defaultState;
	
	private HashMap<String, State> states = new HashMap<String, State>();
	
	private State currState;
	private State nextState;
	
	public StateMachine(Robot context, String defaultState){
		this.context = context;
		this.defaultState = defaultState;
	}

	@Override
	public final void run() {
		populateStates();
		
		Thread messageReader = new Thread(){
    		@Override
    		public void run(){
    			//this will run until the program is terminated
    			Message nextMessage;
    			while(Thread.interrupted()){
    				nextMessage = messages.poll();
    				if(nextMessage != null){
    					processMessageDefault(nextMessage);
    				} else{
    					try {
							this.wait(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    				}
    			}
    			//log error
    		}
    	};
    	messageReader.start();
    	
    	currState = states.get(defaultState);
    	
		while(!Thread.interrupted()){
			if(nextState != null && nextState != currState){
				currState.end();
				currState = nextState;
				currState.start();
			}
			currState.run();
		}
		//log error
	}
	
	protected abstract void populateStates();
	
	@Override
	public final void writeMessage(Message m){
		if(m != null){
			messages.offer(m);
		}
	}
	
	protected final void processMessageDefault(Message m){
		boolean priority = false;
		if(m instanceof PriorityMessage){
			priority = true;
			m = ((PriorityMessage) m).message;
		}
		if(m instanceof StateChange){
			String targetStateName = ((StateChange)m).targetState;
			State targetState = states.get(targetStateName);
			//if the current state disallows transferring to certain other states, check for this.
			if(currState instanceof BlockingState && priority == false){
				for(String s: ((BlockingState) currState).blockedStates){
					if(s.equals(targetStateName)){
						return;
					}
				}
			}
			//if the current state is not a blocking state, or the target state is valid, then continue
			nextState = targetState;
			return;
		}
		processMessage(m);
	}
	
	protected abstract void processMessage(Message m);
	
}
