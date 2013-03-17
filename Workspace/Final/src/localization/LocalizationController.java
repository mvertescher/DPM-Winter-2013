package localization;

import robot.Constants;
import robot.Robot;
import lejos.nxt.Sound;
import lejos.robotics.pathfinding.Node;


public class LocalizationController {
	
	public Node currentNode;
	private Robot robot;
	private boolean rightHit;
	
	public LocalizationController(Robot r, Node n) {
		this.robot = r;
		this.currentNode = n;
		this.rightHit = false;
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
		
		robot.setX(-Constants.LIGHT_SENSOR_Y);
		robot.setTheta(90);
		
	}
	
	private void lineLocalize() {
		robot.setSpeed(50);
		robot.moveForwardUntilStop();
		
		this.waitForAnyLineHit();
		//this.waitForAnyLineHit();
		
		if(this.rightHit == true) {
			this.waitForLeftLineHit();
		}
			
		else if (this.rightHit == false) {
			this.waitForRightLineHit();
		}
		
		Sound.beep();
	}
	
	private void waitForAnyLineHit() {
		this.rightHit = false;
		while(!robot.lineHitLeft() && !this.rightHit) {
			if(robot.lineHitRight())
				this.rightHit = true;
			else 
				this.rightHit = false;
			snooze(20);
		}

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
