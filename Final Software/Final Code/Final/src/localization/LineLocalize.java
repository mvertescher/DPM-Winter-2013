package localization;

import robot.Constants;
import robot.Robot;
import lejos.nxt.Sound;

public class LineLocalize implements Localization {

	private Robot robot;
	private boolean rightHit;
	
	public LineLocalize(Robot r) {
		this.robot = r;
	}
	
	@Override
	public void localize() {
		this.lineLocalize();
		robot.setSpeed(Constants.SLOW_SPEED);
	}
	
	/**
	 * Localization protocol for a line
	 */
	private void lineLocalize() {
		robot.setSpeed(Constants.SLOW_SPEED);
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
	
	/**
	 * Wait for a line hit from a sensor
	 */
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
	
	/**
	 * Wait for a line hit from the right light sensor
	 */
	private void waitForRightLineHit() {
		robot.pivotRight(); //Backwards
		while(!robot.lineHitRight()) {
			snooze(20);
		}
		robot.stopMotors();
	}
	
	/**
	 * Wait for a line hit from the left light sensor
	 */
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
