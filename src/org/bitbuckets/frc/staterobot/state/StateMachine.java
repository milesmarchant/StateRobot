package org.bitbuckets.frc.staterobot.state;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.bitbuckets.frc.staterobot.Robot;
import org.bitbuckets.frc.staterobot.message.Message;
import org.bitbuckets.frc.staterobot.message.Messageable;
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
    					processMessage(nextMessage);
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
    	
		while(Thread.interrupted()){
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
	
	protected void processMessage(Message m){
		if(m instanceof StateChange){
			nextState = states.get(((StateChange)m).targetState);
		}
	}
	
}
