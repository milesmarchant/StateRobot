package org.bitbuckets.frc.staterobot.message;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Implementation of MessageSource which is activated by pressing a button on SmartDashboard.
 * 
 * @author Miles Marchant
 *
 */
public class SendableMessageSource extends MessageSource{
	
	Message message;
	String key;
	
	public SendableMessageSource(Messageable target, Message message, String key) {
		super(target);
		this.message = message;
		this.key = key;
	}
	
	public Message getMessage(){
		return message;
	}
	
	public void sendToDashboard(){
		SmartDashboard.putData(key, new MessageSenderCommand(this));
	}

}
