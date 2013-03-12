package robot;

import navigation.MotorController;
import navigation.Navigator;
import odometry.Odometer;
import odometry.OdometerCorrection;
import pathfinding.Pathfinder;
import robot.states.Avoiding;
import robot.states.StateController;
import robot.states.Travelling;
import sensors.SensorController;
import subsumption.Attacker;
import lejos.geom.Point;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import localization.LocalizationController;

/**
 * Robot.java
 * 
 * Models an autonomous robot 
 * 
 * @author Matthew Vertescher
 *
 */
public class Robot {

	public NXTRegulatedMotor leftMotor, rightMotor;
	private NXTRegulatedMotor leftFlywheelMotor, rightFlywheelMotor;
	
	/**
	 * Mechanical Robot Constants
	 */
	public final double LEFT_RADIUS = 2.80, RIGHT_RADIUS = 2.80, WIDTH = 16.3;
	
	/**
	 * The Robot's Odometer 
	 */
	public Odometer odometer;
	
	/**
	 * A simple grid correction
	 */
	private OdometerCorrection gridCorrection;
	
	/**
	 * LCD Display Timer Listener
	 */
	public LCDUpdater display; 
	
	/**
	 * The robot's navigation system
	 */
	public Navigator navigator;
	
	/**
	 * The robot's sensor system
	 */
	private SensorController sc;
	
	/**
	 * The robot's pathfinder
	 */
	private Pathfinder pathfinder;
	
	/**
	 * The robot's state controller
	 */
	private StateController states;
	
	/**
	 * The robot's localization controller
	 */
	private LocalizationController lc;
	
	/**
	 * Basic Constructor
	 */
	public Robot() {
		leftMotor = Constants.LEFT_MOTOR;
		rightMotor = Constants.RIGHT_MOTOR;
		
		odometer = new Odometer(25, leftMotor, rightMotor, LEFT_RADIUS, WIDTH);
		gridCorrection = new OdometerCorrection(50,this,odometer);
		display = new LCDUpdater(25, odometer);
		navigator = new Navigator(this, leftMotor, rightMotor, LEFT_RADIUS, RIGHT_RADIUS, WIDTH);
		sc = new SensorController();
		pathfinder = new Pathfinder(this);
		states = new StateController(this);
		lc = new LocalizationController(this,null); // NASTY NULL POINTER.
	}

	/**
	 * Start odometer
	 */
	public void startOdometer() {
		odometer.startTimer();
	}
	

	
	/**
	 * Start display
	 */
	public void startDisplay() {
		display.startTimer();
	}
	
	/**
	 * Main method for robot's execution thread
	 */
	public void begin() {
		
		//mc.setCombinedSpeed(100);
		//mc.setAcceleration(1000);
		
		Attacker bot = new Attacker(this);
	
		this.startLightTimers();
		this.startUltrasonicTimers();
		
		this.startGridCorrection();
		
		snooze(1000);
		
		//lc.localize();
		
		//navigator.moveForwardUntilStop();
		this.moveToPoint(new Point(0,120));
		
		
		snooze(1000);
		
		navigator.setSpeed(100);
		navigator.travelForwardsToPoint(new Point(0,0));
		navigator.turnToPoint(new Point(0,0));
		
		//pathfinder.getOptimalPathToPoint(new Point(15,75));
		//navigator.travelPath(pathfinder.getOptimalPathToPoint(new Point(15,75)));
		
		//this.travellingState().travelTo(15,75);
		

		
		Sound.beepSequenceUp();
		navigator.stopMotors();
		
		//navigator.moveForwardUntilStop();
		//while (this.getLeftUltrasonicDistance() > 20 && this.getRightUltrasonicDistance() > 20) 
			//snooze(30);
		//navigator.stopMotors();
		//this.moveToPoint(new Point(60,0));
		//watchForDetection();
		
		
		
		
		snooze(3000);
		
		navigator.stopMotors();
		
		System.exit(0);
		
	}
	
	/**
	 * Start odometry 
	 */
	public void startGridCorrection() {
		gridCorrection.startTimer();
	}
	
	
	// MOVED TO ABSTRACT BOT
	private void moveToPoint(Point p) {
		navigator.turnToPoint(p);
		navigator.moveForwardUntilStop();
		while (!navigator.atDestination(p) && !this.obstacleDetected()) 
			snooze(30);
		navigator.stopMotors();
		
	}
	
	
	/*
	public Localizing localizingState() {
		return states.localizingState();
	}
	*/
	public Travelling travellingState() {
		return states.travellingState();
	}
	
	public Avoiding avoidingState() {
		return states.avoidingState();
	}

	/**
	 * Starts the ultrasonic timers
	 */
	public void startUltrasonicTimers() {
		sc.startLeftUltrasonicTimer();
		sc.startRightUltrasonicTimer();
	}
	
	/**
	 * Stops the ultrasonic timers
	 */
	public void stopUltrasonicTimers() {
		sc.stopLeftUltrasonicTimer();
		sc.stopRightUltrasonicTimer();
	}
	
	/**
	 * Returns the distance from the front ultrasonic timer 
	 * @return
	 */
	public int getLeftUltrasonicDistance() {
		return sc.getLeftUltrasonicTimerDistance();
	}
	
	/**
	 * Returns the distance from the front ultrasonic timer 
	 * @return
	 */
	public int getRightUltrasonicDistance() {
		return sc.getRightUltrasonicTimerDistance();
	}

	/**
	 * Start light timers
	 */
	public void startLightTimers() {
		this.sc.startLeftLightTimer();
		this.sc.startRightLightTimer();
	}
	
	/**
	 * Stop light timers
	 */
	public void stopLightTimers() {
		this.sc.stopLeftLightTimer();
		this.sc.stopRightLightTimer();
	}
	
	
	public boolean lineHitLeft() {
		return this.sc.lineHitLeft();
	}
	
	public boolean lineHitRight() {
		return this.sc.lineHitRight();
	}
	
	
	/**
	 * Wrapper method to get the robots current x coordinate from the odometer
	 * @return double x
	 */
	public double getX() {
		return odometer.getX();
	}
	
	/**
	 * Sets the x coordinate of the robot
	 * @param x
	 */
	public void setX(double x) {
		this.odometer.setX(x);
	}
	
	/**
	 * Wrapper method to get the robots current y coordinate from the odometer
	 * @return double y
	 */
	public double getY() {
		return odometer.getY();
	}
	
	/**
	 * Sets the y coordinate of the robot
	 * @param y
	 */
	public void setY(double y) {
		this.odometer.setY(y);
	}
	
	/**
	 * Wrapper method to get the robots current theta coordinate from the odometer
	 * @return double theta
	 */
	public double getTheta() {
		return odometer.getTheta();
	}
	
	/**
	 * Sets the theta of the robot
	 * @param y
	 */
	public void setTheta(double t) {
		this.odometer.setTheta(t);
	}
	
	/**
	 * Have the robot localize 
	 */
	public void localize() {
		//TODO:LOCALIZE
		Sound.beepSequence();
		snooze(1000);
	}
	
	/**
	 * Wrapper method for the robot to turn to a point
	 * @param x
	 * @param y
	 */
	public void turnToPoint(double x, double y) {
		navigator.turnToPoint(new Point((float)x,(float)y));
	}
	
	/**
	 * Rotate Immediate, polar theta
	 * @param t
	 */
	public void rotateImmediate(double t) {
		navigator.rotateImmediate(t);
	}
	
	/**
	 * Wrapper method to have the robot move forward until a stopMotors() is called
	 */
	public void moveForwardUntilStop() {
		navigator.moveForwardUntilStop();
	}
	
	/**
	 * Pivot left
	 */
	public void pivotLeft() {
		navigator.pivotLeft();
	}
	
	/**
	 * Pivot right 
	 */
	public void pivotRight() {
		navigator.pivotRight();
	}
	
	/**
	 * Stops the robot immediately 
	 */
	public void stopMotors() {
		navigator.stopMotors();
	}
	
	/**
	 * Set the speed of the robot
	 * @param i
	 */
	public void setSpeed(int s) {
		navigator.setSpeed(s);	
	}
	
	/**
	 * Check if the robot is at its destination
	 * @param p
	 * @return true if at destination
	 */
	public boolean isAtDestination(Point p) {
		return navigator.atDestination(p);
	}
	
	
	/**
	 * Moves the robot forward by a distance
	 * @param d
	 */
	public void moveImmediatelyBy(double d) {
		navigator.moveImmediatelyBy(d);
	}
	
	/**
	 * Backs the robot to a point
	 * @param x
	 * @param y
	 */
	public void backupToPoint(double x, double y) {
		navigator.travelBackwardsToPoint(new Point((float) x,(float) y));
	}
	
	/**
	 * Check if the robot detects an obstacle 
	 * @return true if an obstacle is in range
	 */
	public boolean obstacleDetected() {
		return (this.getLeftUltrasonicDistance() < Constants.OBSTACLE_DETECTION_RANGE || this.getRightUltrasonicDistance() < Constants.OBSTACLE_DETECTION_RANGE);
	}
	
	/**
	 * Maps an adjacent obstacle  
	 */
	public void mapAdjecentObstacle(CompassDirection cd) {
		pathfinder.removeAdjacentNode(cd);
	}
	
	/**
	 * Returns a compass approximation of the robot's heading
	 * @return
	 */
	public CompassDirection getDirection() {
		if (Math.cos(Math.toRadians(this.getTheta())) > .707)
			return CompassDirection.NORTH;
		else if (Math.cos(Math.toRadians(this.getTheta())) < -.707)
			return CompassDirection.SOUTH;
		else if (Math.sin(Math.toRadians(this.getTheta())) > .707)
			return CompassDirection.EAST;
		else if (Math.sin(Math.toRadians(this.getTheta())) < -.707)
			return CompassDirection.WEST;
		
		Sound.buzz();
		return null;	
	}
	
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}


	
}
