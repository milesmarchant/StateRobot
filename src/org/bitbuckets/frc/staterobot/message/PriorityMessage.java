package org.bitbuckets.frc.staterobot.message;

/**
 * This message wraps another message, and gives it priority.
 * <p>
 * When using this message, make sure that any client classes will properly unwrap priority messages and note their status, unless
 * priority is never needed for that client.
 * 
 * @author Miles Marchant
 *
 */
public class PriorityMessage implements Message{
	
	public Message message;

	public PriorityMessage(Message m) {
		this.message = m;
	}

}
