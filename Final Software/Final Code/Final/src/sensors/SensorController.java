package sensors;

import robot.Constants;
import lejos.nxt.SensorPort;

/**
 * Wrapper class to organize sensors.
 * This is the interface that the Robot class uses to access and control it's sensors.
 * 
 * @author Matthew Vertescher
 */
public class SensorController {

	private DuelSensorTimer[] sensors;
	
	/**
	 * Constructor 
	 */
	public SensorController() {
		this.sensors = new DuelSensorTimer[3];
		this.sensors[0] = new UltrasonicTimer(SensorID.ULTRASONIC,Constants.US_LEFT,Constants.US_RIGHT);
		this.sensors[1] = new LightSensorTimer(SensorID.LEFT_LIGHT,Constants.LIGHT_LEFT,-7,11);
		this.sensors[2] = new LightSensorTimer(SensorID.RIGHT_LIGHT,Constants.LIGHT_RIGHT,7,11);
	}
	
	/**
	 * Start the ultrasonic timer
	 */
	public void startUltrasonicTimer() {
		this.getSensor(SensorID.ULTRASONIC).startTimer();
	}
	
	/**
	 * Stop the ultrasonic timer
	 */
	public void stopUltrasonicTimer() {
		this.getSensor(SensorID.ULTRASONIC).stopTimer();
	}
	
	/**
	 * Return the distance of the left ultrasonic 
	 * @return int distance
	 */
	public int getLeftUltrasonicTimerDistance() {
		return this.getSensor(SensorID.ULTRASONIC).getLeftValue();
	}
	
	/**
	 * Return the distance of the right ultrasonic 
	 * @return int distance
	 */
	public int getRightUltrasonicTimerDistance() {
		return this.getSensor(SensorID.ULTRASONIC).getRightValue();
	}
	
	/**
	 * Start the left light sensor
	 */
	public void startLeftLightTimer() {
		this.getSensor(SensorID.LEFT_LIGHT).startTimer();
	}
	
	/**
	 * Stop the left light sensor
	 */
	public void stopLeftLightTimer() {
		this.getSensor(SensorID.LEFT_LIGHT).stopTimer();
	}
	
	/**
	 * Return the actual value of the left light sensor. 
	 * This is the normalized light value.
	 * Used if a filter is implemented outside of the sensor package.
	 * @return int distance 
	 */
	public int getLeftLightTimerValue() {
		return this.getSensor(SensorID.LEFT_LIGHT).getLeftValue();
	}
	
	/**
	 * Determines if the left light sensor sees a line
	 * @return true if the left sensor is hit, false otherwise
	 */
	public boolean lineHitLeft() {
		return this.getSensor(SensorID.LEFT_LIGHT).flag();
	}
	
	/**
	 * Start the right light sensor
	 */
	public void startRightLightTimer() {
		this.getSensor(SensorID.RIGHT_LIGHT).startTimer();
	}
	
	/**
	 * Stop the right light sensor
	 */
	public void stopRightLightTimer() {
		this.getSensor(SensorID.RIGHT_LIGHT).stopTimer();
	}
	
	/**
	 * Return the actual value of the right light sensor. 
	 * This is the normalized light value.
	 * Used if a filter is implemented outside of the sensor package.
	 * @return int distance 
	 */
	public int getRightLightTimerValue() {
		return this.getSensor(SensorID.RIGHT_LIGHT).getRightValue();
	}
	
	/**
	 * Determines if the right light sensor sees a line
	 * @return true if the right sensor is hit, false otherwise
	 */
	public boolean lineHitRight() {
		return this.getSensor(SensorID.RIGHT_LIGHT).flag();
	}
	
	/**
	 * Helper method that returns the timer object from a SensorID.
	 * @param SensorID sid
	 * @return the associated SensorTimer object
	 */
	private DuelSensorTimer getSensor(SensorID sid) {
		return this.sensors[sid.ordinal()];
	}
	
}
