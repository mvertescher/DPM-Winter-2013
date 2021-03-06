package odometry;

import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Timer;
import lejos.util.TimerListener;

/**
 * Odometer.java
 * 
 * Timer listener implementation of the odometer
 * Same as laboratory 2
 * 
 * @author Matthew Vertescher
 */
public class Odometer implements TimerListener {
	private Timer odometerTimer; 
	private NXTRegulatedMotor leftMotor, rightMotor;
	
	// Robot position with angle
	private double x, y, theta;

	// lock object for mutual exclusion
	private Object lock;

	// Final double for the Radius between the wheels and the width
	private final double RADIUS, WIDTH; 
	
	// Distance from the center of the arc, and the angle of the arc
	private double deltaCenterArcLength = 0, deltaCenterAngle = 0;
	// The current tachometer values in degrees 
	private int currentRightTachoDegree = 0, currentLeftTachoDegree = 0; 
	// The change in the tachometer values in degrees
	private int  deltaLeftTachoDegree = 0, deltaRightTachoDegree = 0;
	// The change in the tachometer values in radians 
	private double deltaLeftTachoRadian = 0, deltaRightTachoRadian = 0; 
			
	// Initialization of the tachometer values
	private int previousRightTachoDegree = 0; 
	private int previousLeftTachoDegree = 0; 
	
	
	/**
	 * Constructor 
	 * @param period
	 * @param leftmtr
	 * @param rightmtr
	 * @param wheelRadius
	 * @param width
	 */
	public Odometer(int period, NXTRegulatedMotor leftmtr, NXTRegulatedMotor rightmtr, double wheelRadius, double width) {
		odometerTimer = new Timer(period, this);
		x = 0.0;
		y = 0.0;
		theta = 0;
		RADIUS = wheelRadius;
		WIDTH = width;	
		lock = new Object();
		
		leftMotor = leftmtr;
		rightMotor = rightmtr; 
		
	}
	
	/**
	 * Called continuously by the listener
	 */
//	@Override
	public void timedOut() {
		
		// Re-measure tachometers values
		currentRightTachoDegree = leftMotor.getTachoCount(); 
		currentLeftTachoDegree = rightMotor.getTachoCount();  
					
		// Calculate the difference between the current tachometer value and the previous value
		deltaRightTachoDegree = currentRightTachoDegree - previousRightTachoDegree; 
		deltaLeftTachoDegree = currentLeftTachoDegree - previousLeftTachoDegree; 	 
					
		// Convert the tachometer values from radians to degrees 
		deltaRightTachoRadian = Math.toRadians(deltaRightTachoDegree); 
		deltaLeftTachoRadian = Math.toRadians(deltaLeftTachoDegree);  
					
		// Equations for deltaCenterArcLength and deltaCenterAngle given in tutorial slides
		// Calculation of the center arc and angle
		deltaCenterArcLength = ((deltaRightTachoRadian * RADIUS) + (deltaLeftTachoRadian * RADIUS)) / 2;   
		deltaCenterAngle = ((deltaRightTachoRadian * RADIUS) - (deltaLeftTachoRadian * RADIUS)) / WIDTH;
					
		// Store the new values previous tachometer values  
		previousRightTachoDegree = currentRightTachoDegree; 
		previousLeftTachoDegree = currentLeftTachoDegree;  
					
		synchronized (lock) {
			// don't use the variables x, y, or theta anywhere but here!
			//theta = -0.7376; 
						
			// Equations for x and y from the tutorial slides
			x += (deltaCenterArcLength * (Math.sin(Math.toRadians(theta) + deltaCenterAngle/2))); //cos -=
			y += (deltaCenterArcLength * (Math.cos(Math.toRadians(theta) + deltaCenterAngle/2))); //sin -=
			theta -= Math.toDegrees(deltaCenterAngle); // was +=, backwards
						
			// Ensure that theta is positive
			if (theta < 0){ 
				theta += 360;
			}
			theta = theta % 360; 				
		}
	
	}
	
	/**
	 * Starts the odometer
	 */
	public void startTimer() {
		// Initialization of the tachometer values
		previousRightTachoDegree = leftMotor.getTachoCount(); 
		previousLeftTachoDegree = rightMotor.getTachoCount(); 
		
		odometerTimer.start();
	}
	
	/**
	 * Stops the odometer 
	 */
	public void stopTimer() {
		odometerTimer.stop();
	}
	
	// Getters and Setters
		public void getPosition(double[] position, boolean[] update) {
			// ensure that the values don't change while the odometer is running
			synchronized (lock) {
				if (update[0])
					position[0] = x;
				if (update[1])
					position[1] = y;
				if (update[2])
					position[2] = theta;
			}
		}

		public double getX() {
			double result;

			synchronized (lock) {
				result = x;
			}

			return result;
		}

		public double getY() {
			double result;

			synchronized (lock) {
				result = y;
			}

			return result;
		}

		public double getTheta() {
			double result;

			synchronized (lock) {
				result = theta;
			}

			return result;
		}

		// mutators
		public void setPosition(double[] position, boolean[] update) {
			// ensure that the values don't change while the odometer is running
			synchronized (lock) {
				if (update[0])
					x = position[0];
				if (update[1])
					y = position[1];
				if (update[2])
					theta = position[2];
			}
		}

		public void setX(double x) {
			synchronized (lock) {
				this.x = x;
			}
		}

		public void setY(double y) {
			synchronized (lock) {
				this.y = y;
			}
		}

		public void setTheta(double theta) {
			synchronized (lock) {
				this.theta = theta;
			}
		}
	
}
