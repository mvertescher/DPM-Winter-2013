package localization;

import navigation.Navigator;
import odometry.Odometer;
import lejos.nxt.*;

import lejos.nxt.NXTRegulatedMotor;

/**
 * LightSensorLocalization.java
 * 
 * This class will localize the x and y position of the robot
 * It is used ONLY after ultrasonic sensor localization have been done
 * The angle theta will also be corrected
 * But an rough estimate of the theta must be know prior using this class
 * 
 */
public class LightSensorLocalization{
	
	private Odometer odometer;
	private Navigator navigator;
	private double LEFT_RADIUS, RIGHT_RADIUS, WIDTH;
	private int FORWARD_SPEED = 75; 
	private NXTRegulatedMotor leftMotor, rightMotor;
	private LightSensor LightSensorLeft, LightSensorRight;
	
	private static final int DistanceLightSensorToCenter = 3;
	private static final int BackUpDistance = -10;
	private static final int TRESHOLD = 340;
	
	
	/**
	 * Constructor for LightSensorLocalization.java
	 * 
	 */
	public LightSensorLocalization(Odometer odo, Navigator nav, double leftRad, double rightRad, NXTRegulatedMotor leftmtr, NXTRegulatedMotor rightmtr, LightSensor LeftLS, LightSensor RightLS, double w){
		odometer = odo;
		navigator = nav;
		LEFT_RADIUS = leftRad;
		RIGHT_RADIUS = rightRad;
		leftMotor = leftmtr;
		rightMotor = rightmtr;
		LightSensorLeft = LeftLS;
		LightSensorRight = RightLS;
		WIDTH = w;
	}

	/**
	 * Perform the Light Sensor Localisation using two light sensors
	 * Find y zero line and correct the theta of the odometer
	 * Find x zero line
	 */
	public void run(){
		
		for (NXTRegulatedMotor motor : new NXTRegulatedMotor[] { leftMotor, rightMotor }) {
			motor.stop();
			motor.setAcceleration(5000);
		}
		
		/*FIND Y ZERO COORDINATE AND CORRECT THETA*/
		
		//Turn the robot so that it faces roughly the y zero coordinate
		//navigation.turnTo(0.0);
		
		
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		
		leftMotor.forward();
		rightMotor.forward();
		
		//Go to the line until one of the light sensor sees the line
		while(LightSensorLeft.getNormalizedLightValue() > TRESHOLD && LightSensorRight.getNormalizedLightValue() > TRESHOLD){
			
		}
		
		leftMotor.stop();
		rightMotor.stop();
		
		//If the robot is parallel ton the line
		if (LightSensorLeft.getNormalizedLightValue() < TRESHOLD && LightSensorRight.getNormalizedLightValue() < TRESHOLD){
			
			Sound.beep();
		}
			
		//If the first light sensor is the left, then stop left motor
		else if (LightSensorLeft.getNormalizedLightValue() < TRESHOLD){
			rightMotor.forward();
			Sound.beep();
			
			//But keep the right wheel turning until the right light sensor sees the line
			while(LightSensorRight.getNormalizedLightValue() > TRESHOLD){
					
			}
			
			//when the right light sensor sees the line the motor is stopped
			rightMotor.stop();
			Sound.beep();
			
			//now we know the the robot is accurately at an angle of 90
			odometer.setTheta(0);
			
			//And we know the the robot is touching the y zero coordinate
			//e.i. the robot y position is zero minus the distance between the light sensor and the center of the robot
			odometer.setY(-DistanceLightSensorToCenter);
		}
		
		//The first light sensor to see the light sensor the the right sensor, then the right wheel is stopped
		else{
			leftMotor.forward();
			Sound.beep();
			
			//Keep the left wheel turning until the left light sensor sees the line
			while (LightSensorLeft.getNormalizedLightValue() > TRESHOLD){
				
			}
			
			//and stop the left motor after the left sensor has seen the line
			leftMotor.stop();
			Sound.beep();
			
			//now we know the the robot is accurately at an angle of 90
			odometer.setTheta(0);
			
			//And we know the the robot is touching the y zero coordinate
			//e.i. the robot y position is zero minus the distance between the light sensor and the center of the robot
			odometer.setY(-DistanceLightSensorToCenter);
			
		}
		
		/*FIND X ZERO COORDINATE*/
		
		//the robot back up in the center of the tile and faces the zero x coordinate
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		
		leftMotor.rotate(convertDistance(LEFT_RADIUS, BackUpDistance), true);
		rightMotor.rotate(convertDistance(RIGHT_RADIUS, BackUpDistance), false);
		
		//navigation.turnTo(90);
		leftMotor.rotate(convertAngle(LEFT_RADIUS, WIDTH, 90.0), true);
		rightMotor.rotate(-convertAngle(RIGHT_RADIUS, WIDTH, 90.0), false);
		
		
		//Go to the line until one of the light sensor sees the line
		leftMotor.forward();
		rightMotor.forward();
			while(LightSensorLeft.getNormalizedLightValue() > TRESHOLD && LightSensorRight.getNormalizedLightValue() > TRESHOLD){
				
			}
			
			
			leftMotor.stop();
			rightMotor.stop();
			
			//If the robot is parallel ton the line
			if (LightSensorLeft.getNormalizedLightValue() < TRESHOLD && LightSensorRight.getNormalizedLightValue() < TRESHOLD){
				Sound.beep();
				
			}
			//If the first light sensor is the left, then stop left motor
			else if (LightSensorLeft.getNormalizedLightValue() < TRESHOLD){
				rightMotor.forward();
				Sound.beep();
				
				//But keep the right wheel turning until the right light sensor sees the line
				while(LightSensorRight.getNormalizedLightValue() > TRESHOLD){
						
				}
				
				//when the right light sensor sees the line the motor is stopped
				rightMotor.stop();
				Sound.beep();
				
				//And we know the the robot is touching the z zero coordinate
				//e.i. the robot x position is zero minus the distance between the light sensor and the center of the robot
				odometer.setX(-DistanceLightSensorToCenter);
				}
			
			//The first light sensor to see the light sensor the the right sensor, then the right wheel is stopped
			else{
				leftMotor.forward();
				Sound.beep();
				
				//Keep the left wheel turning until the left light sensor sees the line
				while (LightSensorLeft.getNormalizedLightValue() > TRESHOLD){
						
				}
					
				//and stop the left motor after the left sensor has seen the line
				leftMotor.stop();
				Sound.beep();
				
				//And we know the the robot is touching the X zero coordinate
				//e.i. the robot X position is zero minus the distance between the light sensor and the center of the robot
				odometer.setX(-DistanceLightSensorToCenter);
					
				}
		
		
		
		
		
	}
	
	/**
	 * Converts distance
	 * @param radius
	 * @param distance
	 * @return
	 */
	private static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}
	
	/**
	 * 
	 * @param radius
	 * @param width
	 * @param angle
	 * @return
	 */
	private static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}
}