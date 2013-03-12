package subsumption;

import lejos.geom.Point;
import lejos.robotics.subsumption.Arbitrator;
import robot.Robot;

/**
 * AbstractBot.java
 * 
 * Define a set of methods used in Robot 
 * 
 * @author Matthew Vertescher
 *
 */
public abstract class AbstractBot {
	
	private Robot robot; 
	private Arbitrator arbitrator;
	private boolean lost = true;
	
	public AbstractBot(Robot r) {
		this.robot = r;
	}
	
	// Note that the highest priority is the highest index
	public void start() {
		arbitrator.start();
	}

	public Arbitrator getArbitrator() {
		return arbitrator;
	}

	public void setArbitrator(Arbitrator a) {
		this.arbitrator = a;
	}

	public boolean isLost() {
		return this.lost;
	}
	
	public void setLost(boolean l) {
		this.lost = l;
	}
	
	public void localize() {
		this.robot.localize();
	}

	
	public void turnToPoint(double x, double y) {
		this.robot.turnToPoint(x,y);
	}

	public void stopMoving() {
		this.robot.stopMotors();
	}
	
	public boolean obstacleDetected() {
		return this.robot.obstacleDetected();
	}

	public void moveToPoint(Point p) {
		robot.startUltrasonicTimers(); // STARTS TIMERS HERE CAN BE MOVED
		
		this.robot.turnToPoint(p.getX(),p.getY());
		this.robot.moveForwardUntilStop();
		
		// Check if at destination or the robot detects and obstacle 
		while (!atDestination(p) && !obstacleDetected())  
			snooze(30);
		this.robot.stopMotors();
	}

	public boolean atDestination(Point p) {
		return this.robot.isAtDestination(p);
	}
	
	
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
}