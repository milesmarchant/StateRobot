package org.bitbuckets.frc.staterobot.message;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A MessageSource which allows the user to select from a radio-button list during runtime and send that particular message
 * by pressing a button on SmartDashboard.
 * 
 * @author Miles Marchant
 *
 */
public class SendableMessageChooser extends MessageSource {
	
	SendableChooser messages = new SendableChooser();
	String key;

	public SendableMessageChooser(Messageable target) {
		this(target, "");
	}
	
	public SendableMessageChooser(Messageable target, String key){
		super(target);
		this.key = key;
	}
	
	public void addMessage(String name, Message m){
		messages.addObject(name, m);
	}
	
	public Message getMessage(){
		Message m = (Message) messages.getSelected();
		if(m != null){
			return m;
		}
		return new NoMessageSelected();
	}
	
	public void sendToDashboard(){
		SmartDashboard.putData("Message:", messages);
		SmartDashboard.putData("Send message ("+key+"):", new MessageSenderCommand(this));
	}

}
