package org.bitbuckets.frc.staterobot.message;

public abstract class MessageSource {
	
	Messageable target;
	
	public MessageSource(Messageable target){
		this.target = target;
	}

}
