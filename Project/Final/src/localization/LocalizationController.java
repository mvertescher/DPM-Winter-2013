package localization;

import robot.Constants;
import robot.Robot;
import lejos.nxt.Sound;
import lejos.robotics.pathfinding.Node;


public class LocalizationController {
	
	public Node currentNode;
	private Robot robot;
	
	public LocalizationController(Robot r, Node n) {
		this.robot = r;
		this.currentNode = n;
	}
	
	public void localize() {
		//boolean leftFlag = false, rightFlag = false; 
		
		
		// Localization in square 1, correcting y first
		this.lineLocalize();
		
		robot.setY(-Constants.LIGHT_SENSOR_Y);
		robot.setTheta(0);
		
		robot.setSpeed(50);
		robot.moveImmediatelyBy(-10);
		robot.rotateImmediate(-90);
		this.lineLocalize();
		
		robot.setX(-Constants.LIGHT_SENSOR_X);
		robot.setTheta(90);
		
	}
	
	private void lineLocalize() {
		robot.setSpeed(50);
		robot.moveForwardUntilStop();
		
		this.waitForAnyLineHit();
		
		if(robot.lineHitRight()) {
			this.waitForLeftLineHit();
		}
			
		else if(robot.lineHitLeft())	{
			this.waitForRightLineHit();
		}
		
		Sound.beep();
	}
	
	private void waitForAnyLineHit() {
		while(!robot.lineHitLeft() && !robot.lineHitRight()) 
			snooze(20);
		Sound.beep();
	}
	
	private void waitForRightLineHit() {
		robot.pivotRight(); //Backwards
		while(!robot.lineHitRight()) {
			snooze(20);
		}
		robot.stopMotors();
	}
	
	public void waitForLeftLineHit() {
		robot.pivotLeft(); //Backwards
		while(!robot.lineHitLeft()) {
			snooze(20);
		}
		robot.stopMotors();
	}
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}