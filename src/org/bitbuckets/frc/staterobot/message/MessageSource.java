package org.bitbuckets.frc.staterobot.message;

/**
 * Abstract class describing objects which generate messages and send them to be read by a target.
 * The implementation of this may vary; some may use a threaded, timed MessageSource implementation, while
 * for other applications, activating the MessageSource via a method call may be more appropriate.
 * 
 * @author Miles Marchant
 *
 */
public abstract class MessageSource {
	
	Messageable target;
	
	public MessageSource(Messageable target){
		this.target = target;
	}
	
	public abstract Message getMessage();

}
