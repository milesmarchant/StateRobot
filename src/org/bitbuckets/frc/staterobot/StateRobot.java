package org.bitbuckets.frc.staterobot;

import java.util.concurrent.LinkedBlockingQueue;

import org.bitbuckets.frc.staterobot.message.Message;
import org.bitbuckets.frc.staterobot.message.Messageable;
import org.bitbuckets.frc.staterobot.message.RobotModeChange;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class StateRobot extends RobotBase implements Messageable{
	
	public enum Mode{
		DISABLED, TELEOP, AUTONOMOUS, TEST;
	}
	
	protected Mode currentMode;
	
	private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();
	
	public StateRobot(){
	}

    @Override
    protected void prestart() {
    	// Don't immediately say that the robot's ready to be enabled.
    	// Gets called in startCompetition after a function robotInit(); this allows the user to do initialization code first.
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
    }

    /**
     * Provide an alternate "main loop" via startCompetition().
     *
     */
    public void startCompetition() {
        UsageReporting.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);

        robotInit();
        
        // We call this now (not in prestart like default) so that the robot
        // won't enable until the initialization has finished. This is useful
        // because otherwise it's sometimes possible to enable the robot
        // before the code is ready. 
        FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramStarting();

        // loop forever, calling the appropriate mode-dependent function
        LiveWindow.setEnabled(false);
        while (true) {
            // Call the appropriate function depending upon the current robot mode
            if (isDisabled()) {
                // call DisabledInit() if we are now just entering disabled mode from
                // either a different mode or from power-on
                if (currentMode != Mode.DISABLED) {
                    LiveWindow.setEnabled(false);
                    disabledInit();
                    currentMode = Mode.DISABLED;
                    writeMessage(new RobotModeChange(currentMode));
                }
                if (nextPeriodReady()) {
                	FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramDisabled();
                }
            } else if (isTest()) {
                // call TestInit() if we are now just entering test mode from either
                // a different mode or from power-on
                if (currentMode != Mode.TEST) {
                    LiveWindow.setEnabled(true);
                    testInit();
                    currentMode = Mode.TEST;
                    writeMessage(new RobotModeChange(currentMode));
                }
                if (nextPeriodReady()) {
                	FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramTest();
                }
            } else if (isAutonomous()) {
                // call Autonomous_Init() if this is the first time
                // we've entered autonomous_mode
                if (currentMode != Mode.AUTONOMOUS) {
                    LiveWindow.setEnabled(false);
                    // KBS NOTE: old code reset all PWMs and relays to "safe values"
                    // whenever entering autonomous mode, before calling
                    // "Autonomous_Init()"
                    autonomousInit();
                    currentMode = Mode.AUTONOMOUS;
                    writeMessage(new RobotModeChange(currentMode));
                }
                if (nextPeriodReady()) {
                    FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramAutonomous();
                }
            } else {
                // call Teleop_Init() if this is the first time
                // we've entered teleop_mode
                if (currentMode != Mode.TELEOP) {
                    LiveWindow.setEnabled(false);
                    teleopInit();
                    currentMode = Mode.TELEOP;
                    writeMessage(new RobotModeChange(currentMode));
                }
                if (nextPeriodReady()) {
                    FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramTeleop();
                }
            }
            periodic(currentMode);
            m_ds.waitForData();
        }
    }
    
    /**
     * Determine if the appropriate next periodic function should be called.
     * Call the periodic functions whenever a packet is received from the Driver Station, or about every 20ms.
     */
    private boolean nextPeriodReady() {
        return m_ds.isNewControlData();
    }

    /**
     * Robot-wide initialization code should go here.
     *
     * Users should override this method for default Robot-wide initialization which will
     * be called when the robot is first powered on.  It will be called exactly 1 time.
     */
    public void robotInit() {
        System.out.println("Default IterativeRobot.robotInit() method... Overload me!");
    }
    
    /**
     * Initialization code for disabled mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters disabled mode.
     */
    public void disabledInit() {
        System.out.println("Default IterativeRobot.disabledInit() method... Overload me!");
    }

    /**
     * Initialization code for autonomous mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters autonomous mode.
     */
    public void autonomousInit() {
        System.out.println("Default IterativeRobot.autonomousInit() method... Overload me!");
    }

    /**
     * Initialization code for teleop mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters teleop mode.
     */
    public void teleopInit() {
        System.out.println("Default IterativeRobot.teleopInit() method... Overload me!");
    }

    /**
     * Initialization code for test mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters test mode.
     */
    public void testInit() {
        System.out.println("Default IterativeRobot.testInit() method... Overload me!");
    }
    
    /**
     * This method is called equivalently to IterativeRobot's teleopPeriodic, disabledPeriodic, etc.
     * 
     * This is not a preferable approach in a state and message driven system and as such should be used sparingly.
     * 
     * Example: if the user wished to count the number of packets sent through the dashboard, this method would be appropriate.
     */
    protected void periodic(Mode mode){
    }
    
    public final void writeMessage(Message m){
    	messages.offer(m);
    }
    
    /**
     * Overwrite this function to give your robot message-driven behavior.
     * 
     * @param m
     */
    protected void processMessage(Message m){
    }
    
}
