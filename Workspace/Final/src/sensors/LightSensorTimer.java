package sensors;

import robot.Constants;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.util.Timer;
import lejos.util.TimerListener;

/**
 * LightSensorTimer.java
 * 
 * Implements a timer listener implementation of the light sensor. 
 * 
 * @author Matthew Vertescher
 */
public class LightSensorTimer implements TimerListener, SensorTimer {
	
	private SensorID id;
	private LightSensor ls; 
	private Timer lsTimer; 
	private int currentValue, previousValue;
	private double X,Y;
	private final int THRESHOLD = Constants.NORMALIZED_LIGHT_VALUE_THRESHOLD;
	private boolean hitLock;
	
	/**
	 * Constructor
	 * @param sid
	 * @param s
	 * @param x
	 * @param y
	 */
	public LightSensorTimer(SensorID sid, SensorPort s, double x, double y) {
		this.id = sid;
		this.ls = new LightSensor(s,true);
		this.lsTimer = new Timer(20, this);
		//this.localizedValue = ls.readValue();
		this.currentValue = ls.getNormalizedLightValue();
		this.previousValue = 0;
		this.X = x; 
		this.Y = y; 
		this.hitLock = false;
	}

	/**
	 * Timer listener call
	 */
	@Override
	public void timedOut() {
		//this.currentValue = ls.readValue();
		this.previousValue = this.currentValue;
		this.currentValue = ls.getNormalizedLightValue();
		
		
		// Print for debugging 
		if (this.id.equals(SensorID.LEFT_LIGHT)) {
			LCD.drawString("L-L:          ", 0, 6);
			LCD.drawString("L-L: " + this.currentValue, 0, 6);	
		}
		else if (this.id.equals(SensorID.RIGHT_LIGHT)) {
			LCD.drawString("R-L:          ", 0, 7);
			LCD.drawString("R-L: " + this.currentValue, 0, 7);	
		}
		
	}
	
	/**
	 * Start the sensor timer
	 */
	public void startTimer() {
		lsTimer.start();
	}
	
	/**
	 * Stop the sensor timer
	 */
	public void stopTimer() {
		lsTimer.stop();
	}
	
	/**
	 * Returns the current light value
	 * @return
	 */
	public int getValue() {
		return this.currentValue;
	}
	

	@Override
	public SensorID getSensorID() {
		return id;
	}

	@Override
	public boolean flag() {
		//return (this.currentValue < THRESHOLD);
		//if(this.hitLock)  
		//	snooze(1000);
		
		this.hitLock = ((this.currentValue - this.previousValue) > 35); //20
		return this.hitLock;
	}
	
	/**
	 * Sleep the thread for some milliseconds
	 * 
	 * @param milliseconds
	 */
	private static void snooze(int milliseconds) {
		try {Thread.sleep(milliseconds);} catch (InterruptedException e) {e.printStackTrace();}
	}
}
