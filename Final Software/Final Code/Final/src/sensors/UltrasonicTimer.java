package sensors;

import robot.Constants;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Timer;
import lejos.util.TimerListener;

/**
 * UltrasonicTimer.java
 * 
 * Timer listener driven ultrasonic sensor thread
 * 
 * @author Matthew Vertescher
 */
public class UltrasonicTimer implements TimerListener, DuelSensorTimer {
	
	private SensorID id;
	private Timer usTimer; 
	
	private UltrasonicSensor leftUS;
	private UltrasonicSensor rightUS;
	private int leftDistance = 255;
	private int rightDistance = 255;
	
	private boolean active;
	private boolean lock;

	/**
	 * Constructor with a reference to Navigation 
	 * @param sid
	 * @param s
	 */
	public UltrasonicTimer(SensorID sid, SensorPort sl, SensorPort sr) {
		this.id = sid;
		this.leftUS = new UltrasonicSensor(sl);
		this.rightUS = new UltrasonicSensor(sr);
		this.usTimer = new Timer(Constants.ULTRASONIC_SENSOR_PERIOD, this);
		this.leftDistance = leftUS.getDistance();
		this.rightDistance = rightUS.getDistance();
		
		this.active = false;
		this.lock = false;
	}
	
	/**
	 * Start the timer
	 */
	public void startTimer() {
		if (!active)
			usTimer.start();
		active = true;
	}
	
	/**
	 * Stop the timer
	 */
	public void stopTimer() {
		if (active)
			usTimer.stop();
		active = false;
	}
	
	/**
	 * Timer method continuously called
	 */
	@Override
	public void timedOut() {
		// Get the current distance
		if (lock == false) {
			this.leftDistance = leftUS.getDistance();
			lock = true;
		}	
		//this.snooze(Constants.ULTRASONIC_SENSOR_PERIOD / 2);
		else if (lock == true) {
			this.rightDistance = rightUS.getDistance();
			lock = false;
		}
		
			
		// Print for debugging 
		LCD.drawString("L-US:          ", 0, 4);
		LCD.drawString("L-US: " + this.leftDistance, 0, 4);	
		LCD.drawString("R-US:          ", 0, 5);
		LCD.drawString("R-US: " + this.rightDistance, 0, 5);	
		
		
	}
	
	/**
	 * Return the left ultrasonic distance
	 */
	public int getLeftValue() { //synchronized
		return this.leftDistance; 
	}
	
	/**
	 * Return the right ultrasonic distance
	 */
	public int getRightValue() { //synchronized
		return this.rightDistance; 
	}
	
	@Override
	public SensorID getSensorID() {
		return id;
	}

	@Override
	public boolean flag() {
		return (this.rightDistance < Constants.OBSTACLE_DETECTION_RANGE);
	}
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}
