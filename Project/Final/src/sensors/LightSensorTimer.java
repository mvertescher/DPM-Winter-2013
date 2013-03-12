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
	private int localizedValue, currentValue, previousValue;
	private double X,Y;
	private final int THRESHOLD = Constants.NORMALIZED_LIGHT_VALUE_THRESHOLD;
	
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
		this.lsTimer = new Timer(30, this);
		//this.localizedValue = ls.readValue();
		this.currentValue = ls.getNormalizedLightValue();
		this.localizedValue = ls.getNormalizedLightValue();
		this.X = x; 
		this.Y = y; 
	}

	/**
	 * Timer listener call
	 */
	@Override
	public void timedOut() {
		//this.currentValue = ls.readValue();
		this.currentValue = ls.getNormalizedLightValue();
		
		// Print for debugging 
		if (this.id.equals(SensorID.LEFT_LIGHT)) {
			LCD.drawString("L-L:          ", 0, 4);
			LCD.drawString("L-L: " + this.currentValue, 0, 6);	
		}
		else if (this.id.equals(SensorID.RIGHT_LIGHT)) {
			LCD.drawString("R-L:          ", 0, 5);
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
	
	// TODO: Get rid of this
	public boolean lineHit() {
		if (this.localizedValue - this.currentValue > 5) {
			Sound.beep();
			return true;
		}	
		return false;
	}

	@Override
	public SensorID getSensorID() {
		return id;
	}

	@Override
	public boolean flag() {
		return (this.currentValue < THRESHOLD);
		//return ((this.currentValue - this.localizedValue) > 200);
	}
	
}