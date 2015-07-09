package org.bitbuckets.frc.staterobot.message;

/**
 * Message indicating that no message was selected. It should be used to avoid null pointers, while retaining the information that a null message was
 * used somewhere.
 * 
 * @author Miles Marchant
 *
 */
public class NoMessageSelected implements Message {
	
	public NoMessageSelected(){
	}

}
