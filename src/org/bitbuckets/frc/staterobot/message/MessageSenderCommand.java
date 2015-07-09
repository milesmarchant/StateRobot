package org.bitbuckets.frc.staterobot.message;

import edu.wpi.first.wpilibj.command.Command;

class MessageSenderCommand extends Command{
	
	MessageSource messageSource;

	/**
	 * @param sendableMessageSource
	 */
	MessageSenderCommand(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	protected void initialize() {
		messageSource.target.writeMessage(messageSource.getMessage());
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
	
}