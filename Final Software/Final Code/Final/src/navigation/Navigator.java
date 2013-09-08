package navigation;

import robot.Robot;
import lejos.geom.Point;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;


/** 
 * Navigator.java
 * 
 * Class dedicated to high level traveling
 * 
 * To interrupt any travel method, stop motors must be called
 * 
 * @author Matthew Vertescher
 *
 */
public class Navigator {

	private Robot robot; 
	private MotorController mc;
	//private boolean navigating = true;
	
	/**
	 * Constructor 
	 * @param r
	 * @param leftmtr
	 * @param rightmtr
	 * @param leftrad
	 * @param rightrad
	 * @param width
	 */
	public Navigator(Robot r, NXTRegulatedMotor leftmtr, NXTRegulatedMotor rightmtr, double leftrad, double rightrad, double width) {
		this.robot = r;
		this.mc = new MotorController(this, leftmtr, rightmtr, leftrad, rightrad, width);
	}
	
	/**
	 * Have the robot move to a point p.
	 * This can be stopped, but there is no avoidance. 
	 * @param p
	 */
	public void travelForwardsToPoint(Point p) {
		this.moveToPoint(p);
	}
	
	/**
	 * Have the robot travel the distance between its current point, and a point p, backwards. 
	 * @param p
	 */
	public void travelBackwardsToPoint(Point p) {
		mc.reverseImmediatelyBackwardsToPoint(p.x, p.y);
	}
	
	/**
	 * Have the robot immediately travel a path p
	 * @param p
	 */
	public void travelPathImmediately(Path p) {
		for (Waypoint w: p) 
			mc.travelImmediatelyTo(w.x,w.y);
	}
	
	/**
	 * Wrapper method for the robot to turn a point.
	 * @param p
	 */
	public void turnToPoint(Point p) {
		mc.turnToPoint(p.x,p.y);
	}
	
	/**
	 * Wrapper method to turn the robot immediately to a heading theta.
	 * @param theta
	 */
	public void turnToHeading(double theta){
		mc.turnToHeading(theta);
	}
	
	/**
	 * Moves the robot immediately by a fixed distance. 
	 * @param d
	 */
	public void moveImmediatelyBy(double d) {
		mc.moveImmediatelyBy(d);
	}
	
	/**
	 * Have the robot keep moving foward until stop motors is called.
	 */
	public void moveForwardUntilStop() {
		mc.moveForwardUntilStop();
	}
	
	/**
	 * Stops the left motor, causing the robot to pivot to the left.
	 */
	public void pivotLeft() {
		mc.setLeftMotorSpeed(0);
	}
	
	/**
	 * Stops the right motor causing the robot to pivot to the right
	 */
	public void pivotRight() {
		mc.setRightMotorSpeed(0);
	}
	
	/**
	 * Stops the motors
	 */
	public void stopMotors() {
		mc.stopMotors();
	}
	
	/**
	 * Determines if the robot is at its destination point p.
	 * @param p
	 * @return true if at destination, false otherwise
	 */
	public boolean atDestination(Point p) {
		return (Math.abs(p.getX() - this.getX()) < 2 && Math.abs(p.getY() - this.getY()) < 2);
	}
	
	/**
	 * The robots current x coordinate
	 * @return double X coordinate
	 */
	public double getX() {
		return this.robot.getX();
	}
	
	/**
	 * The robots current y coordinate
	 * @return double Y coordinate
	 */
	public double getY() {
		return this.robot.getY();
	}
	
	/**
	 * The robots current theta angle
	 * @return double theta angle
	 */
	public double getTheta() {
		return this.robot.getTheta();
	}

	/**
	 * Set the speed of both motors
	 * @param s
	 */
	public void setSpeed(int s) {
		mc.setCombinedSpeed(s);
	}
	
	/**
	 * Immediately rotates the robot by a fixed angle
	 * @param t, angle in degrees
	 */
	public void rotateImmediate(double t) {
		mc.turnImmediatelyBy(t);
	}
	
	/**
	 * Perform a calculated travel to a point.
	 * This is an immediate travel where there is no avoidance or stopping.
	 * @param p
	 */
	public void travelImmediate(Point p) {
		mc.travelImmediatelyTo(p.getX(),p.getY());
	}
	
	/**
	 * Rotate the robot left until stop
	 */
	public void rotateLeftTilStop() {
		mc.rotateUntilStop(PolarDirection.COUNTERCLOCKWISE);
	}
	
	/**
	 * Rotate the robot right until stop
	 */
	public void rotateRightTilStop() {
		mc.rotateUntilStop(PolarDirection.CLOCKWISE);
	}
	
	
	
	// Does not work, need another thread to monitor Robot
	private void moveToPoint(Point p) {
		this.turnToPoint(p);
		this.moveForwardUntilStop();
		while (!this.atDestination(p)) 
			snooze(30);
		this.stopMotors();
	}
	

	/*
	public boolean isNavigating() {
		return navigating;
	}
	

	public void stopNavigating() {
		navigating = false;
	}*/
	
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}


	
}
