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
public class UltrasonicTimer implements TimerListener, SensorTimer {
	
	private SensorID id;
	private Timer usTimer; 
	private UltrasonicSensor us;
	private int distance = 255;
	private boolean active = false;

	/**
	 * Constructor with a reference to Navigation 
	 * @param sid
	 * @param s
	 */
	public UltrasonicTimer(SensorID sid, SensorPort s) {
		this.id = sid;
		this.us = new UltrasonicSensor(s);
		this.usTimer = new Timer(30, this);
		this.distance = us.getDistance();
		this.active = false;
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
		this.distance = us.getDistance(); 
			
		// Print for debugging 
		if (this.id.equals(SensorID.LEFT_ULTRASONIC)) {
			LCD.drawString("L-US:          ", 0, 4);
			LCD.drawString("L-US: " + this.distance, 0, 4);	
		}
		else if (this.id.equals(SensorID.RIGHT_ULTRASONIC)) {
			LCD.drawString("R-US:          ", 0, 5);
			LCD.drawString("R-US: " + this.distance, 0, 5);	
		}
		
	}
	
	/**
	 * Return the ultrasonic distance
	 */
	public int getValue() { //synchronized
		return this.distance; 
	}
	
	@Override
	public SensorID getSensorID() {
		return id;
	}

	@Override
	public boolean flag() {
		return (this.distance < Constants.OBSTACLE_DETECTION_RANGE);
	}
	
	
}