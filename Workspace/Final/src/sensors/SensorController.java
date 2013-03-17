package sensors;

import lejos.nxt.SensorPort;

/**
 * SensorController.java
 * 
 * Wrapper class to organize sensors
 * 
 * @author Matthew Vertescher
 */
public class SensorController {

	private SensorTimer[] sensors;
	
	/**
	 * Constructor 
	 */
	public SensorController() {
		this.sensors = new SensorTimer[4];
		this.sensors[0] = new UltrasonicTimer(SensorID.LEFT_ULTRASONIC,SensorPort.S1);
		this.sensors[1] = new UltrasonicTimer(SensorID.RIGHT_ULTRASONIC,SensorPort.S2);
		this.sensors[2] = new LightSensorTimer(SensorID.LEFT_LIGHT,SensorPort.S3,-7,11);
		this.sensors[3] = new LightSensorTimer(SensorID.RIGHT_LIGHT,SensorPort.S4,7,11);
	}
	
	//LEFT US 
	public void startLeftUltrasonicTimer() {
		this.getSensor(SensorID.LEFT_ULTRASONIC).startTimer();
	}
	
	public void stopLeftUltrasonicTimer() {
		this.getSensor(SensorID.LEFT_ULTRASONIC).stopTimer();
	}
	
	public int getLeftUltrasonicTimerDistance() {
		return this.getSensor(SensorID.LEFT_ULTRASONIC).getValue();
	}
	
	//RIGHT US
	public void startRightUltrasonicTimer() {
		this.getSensor(SensorID.RIGHT_ULTRASONIC).startTimer();
	}
	
	public void stopRightUltrasonicTimer() {
		this.getSensor(SensorID.RIGHT_ULTRASONIC).stopTimer();
	}
	
	public int getRightUltrasonicTimerDistance() {
		return this.getSensor(SensorID.RIGHT_ULTRASONIC).getValue();
	}
	
	//LEFT LIGHT
	public void startLeftLightTimer() {
		this.getSensor(SensorID.LEFT_LIGHT).startTimer();
	}
	
	public void stopLeftLightTimer() {
		this.getSensor(SensorID.LEFT_LIGHT).stopTimer();
	}
	
	public int getLeftLightTimerValue() {
		return this.getSensor(SensorID.LEFT_LIGHT).getValue();
	}
	
	public boolean lineHitLeft() {
		return this.getSensor(SensorID.LEFT_LIGHT).flag();
	}
	
	// RIGHT LIGHT
	public void startRightLightTimer() {
		this.getSensor(SensorID.RIGHT_LIGHT).startTimer();
	}
	
	public void stopRightLightTimer() {
		this.getSensor(SensorID.RIGHT_LIGHT).stopTimer();
	}
	
	public int getRightLightTimerValue() {
		return this.getSensor(SensorID.RIGHT_LIGHT).getValue();
	}
	
	public boolean lineHitRight() {
		return this.getSensor(SensorID.RIGHT_LIGHT).flag();
	}
	

	private SensorTimer getSensor(SensorID sid) {
		return this.sensors[sid.ordinal()];
	}
	
}