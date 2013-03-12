package robot;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

/**
 * Constants.java
 * 
 * Organizes the robotic constants 
 * 
 * @author Matthew Vertescher
 *
 */
public class Constants {
	
	public final static double LEFT_RADIUS = 2.80, RIGHT_RADIUS = 2.80, WIDTH = 16.3;
	public final static NXTRegulatedMotor LEFT_MOTOR = Motor.B;
	public final static NXTRegulatedMotor RIGHT_MOTOR = Motor.A;
	public final static SensorPort US_LEFT = SensorPort.S1, US_RIGHT = SensorPort.S2, LIGHT_LEFT = SensorPort.S3, LIGHT_RIGHT = SensorPort.S4; 
	
	public NXTRegulatedMotor leftFlywheelMotor, rightFlywheelMotor;
	
	/**
	 * Range in centimeters that will signal that an object is directly in front of the robot
	 */
	public final static int OBSTACLE_DETECTION_RANGE = 20;
	
	/**
	 * A simple threshold for the light sensor value
	 */
	public final static int NORMALIZED_LIGHT_VALUE_THRESHOLD = 480; //500
	
	
	/**
	 * The x coordinate of the light sensors with respect to the robot's center
	 */
	public final static double LIGHT_SENSOR_X = 7;
	
	/**
	 * The y coordinate of the light sensors with respect to the robot's center
	 */
	public final static double LIGHT_SENSOR_Y = 7;
}
