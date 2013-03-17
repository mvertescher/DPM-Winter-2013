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
	private boolean navigating = true;
	
	public Navigator(Robot r, NXTRegulatedMotor leftmtr, NXTRegulatedMotor rightmtr, double leftrad, double rightrad, double width) {
		this.robot = r;
		this.mc = new MotorController(this, leftmtr, rightmtr, leftrad, rightrad, width);
	}
	
	public void travelForwardsToPoint(Point p) {
		this.moveToPoint(p);
	}
	
	public void travelBackwardsToPoint(Point p) {
		
	}
	
	
	public void travelPath(Path p) {
		for (Waypoint w: p) 
			mc.travelImmediatelyTo(w.x,w.y);
	}
	
	public void turnToPoint(Point p) {
		mc.turnToPoint(p.x,p.y);
	}
	
	public void turnToHeading(double theta){
		mc.turnToHeading(theta);
	}
	
	public void moveImmediatelyBy(double d) {
		mc.moveImmediatelyBy(d);
	}
	
	public void moveForwardUntilStop() {
		mc.moveForwardUntilStop();
	}
	
	public void pivotLeft() {
		mc.setLeftMotorSpeed(0);
	}
	
	public void pivotRight() {
		mc.setRightMotorSpeed(0);
	}
	
	public void stopMotors() {
		mc.stopMotors();
	}
	
	public boolean atDestination(Point p) {
		return (Math.abs(p.getX() - this.getX()) < 2 && Math.abs(p.getY() - this.getY()) < 2);
	}
	
	public double getX() {
		return this.robot.getX();
	}
	
	public double getY() {
		return this.robot.getY();
	}
	
	public double getTheta() {
		return this.robot.getTheta();
	}

	
	public void setSpeed(int s) {
		mc.setCombinedSpeed(s);
	}
	
	public void rotateImmediate(double t) {
		mc.turnImmediatelyBy(t);
	}
	
	// Does not work, need another thread to monitor Robot
	private void moveToPoint(Point p) {
		this.turnToPoint(p);
		this.moveForwardUntilStop();
		while (!this.atDestination(p)) 
			snooze(30);
		this.stopMotors();
	}
	

	
	public boolean isNavigating() {
		return navigating;
	}
	

	public void stopNavigating() {
		navigating = false;
	}
	
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}


	
}