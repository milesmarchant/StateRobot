
package org.bitbuckets.frc.staterobot;

import org.bitbuckets.frc.staterobot.message.Message;
import org.bitbuckets.frc.staterobot.message.RobotModeChange;
import org.bitbuckets.frc.staterobot.message.StateChange;
import org.bitbuckets.frc.staterobot.message.TargetedStateChange;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the StateRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends StateRobot {

	public static OI oi;

    Command autonomousCommand;

    @Override
    public void robotInit() {
		oi = new OI();
    }

    @Override
    public void autonomousInit() {
        if (autonomousCommand != null) autonomousCommand.start();
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
    }
    
    @Override
    public void disabledInit(){
    }
    
    @Override
    public void testInit() {
    }

    @Override
    protected void processMessage(Message m){
    	if(m instanceof TargetedStateChange){
    		//pass message to the targeted state machine. You may want to convert it to a StateChange message
    	}
    	if(m instanceof StateChange){
    		//error - something should have passed a TargetedStateChange message
    	}
    	if(m instanceof RobotModeChange){
    		super.currentMode = ((RobotModeChange)m).mode;
    	}
    }
    
}
