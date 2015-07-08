package org.bitbuckets.frc.staterobot.message;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * Implementation of MessageSource which is activated by pressing a button on SmartDashboard.
 * 
 * @author Miles Marchant
 *
 */
public class SendableMessageSource extends MessageSource implements Sendable{
	
	Message message;
	
	public SendableMessageSource(Messageable target, Message message, String key) {
		super(target);
		this.message = message;
		SmartDashboard.putData(key, new MessageSenderCommand());
	}

	@Override
	public void initTable(ITable subtable) {
		
	}

	@Override
	public ITable getTable() {
		return null;
	}

	@Override
	public String getSmartDashboardType() {
		return null;
	}
	
	class MessageSenderCommand extends Command{

		@Override
		protected void initialize() {
			target.writeMessage(message);
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

}
