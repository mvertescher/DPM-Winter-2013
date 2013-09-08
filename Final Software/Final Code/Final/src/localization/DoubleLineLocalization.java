package localization;

import lejos.nxt.Sound;
import robot.Constants;
import robot.Robot;

/**
 * DoubleLineLocalization.java
 * 
 * Localizes the robot in a corner.
 * 
 * @author Matthew Vertescher
 * @author Felix Dube
 *
 */
public class DoubleLineLocalization implements Localization {

	private Robot robot;
	private Corner corner;
	private boolean rightHit;
	
	private double xLine, yLine;  
	
	/**
	 * The final value for theta
	 */
	private double theta1, theta2;

	public DoubleLineLocalization(Robot r, Corner c) {
		this.robot = r;
		this.corner = c;
		this.rightHit = false;
		
		this.xLine = 0;
		this.yLine = 0;
		this.theta1 = 0;
		this.theta2 = 0;
	}
	
	@Override
	public void localize() {
		
		// Determine the offset constants based upon the corner
		switch(corner) {
			case ONE:
				xLine = 0 - Constants.LIGHT_SENSOR_Y;
				yLine = 0 - Constants.LIGHT_SENSOR_Y;
				theta1 = 0;
				theta2 = 90;
				break;
			case TWO:
				xLine = 300 + Constants.LIGHT_SENSOR_Y;
				yLine = 0 - Constants.LIGHT_SENSOR_Y;
				theta1 = 270;
				theta2 = 0;
				break;
			case THREE:
				xLine = 300 + Constants.LIGHT_SENSOR_Y;
				yLine = 300 + Constants.LIGHT_SENSOR_Y;
				theta1 = 180;
				theta2 = 270;
				break;
			case FOUR:
				xLine = 0 - Constants.LIGHT_SENSOR_Y;
				yLine = 300 +  Constants.LIGHT_SENSOR_Y;
				theta1 = 90;
				theta2 = 180;
				break;
		}
		
	
		
		UltrasonicApproximation ua = new UltrasonicApproximation(robot);
		ua.approximateToAngle(this.corner, 0);
				
		robot.startLightTimers();
		snooze(500);
				
		this.lineLocalize();
		
		if (corner == Corner.ONE || corner == Corner.THREE)
			robot.setY(yLine);
		else if (corner == Corner.TWO || corner == Corner.FOUR)
			robot.setX(xLine);
		
		robot.setTheta(theta1);
		
		robot.setSpeed(Constants.SLOW_SPEED);
		robot.moveImmediatelyBy(-7);
		robot.rotateImmediate(-90);
		this.lineLocalize();
						
		if (corner == Corner.ONE || corner == Corner.THREE)
			robot.setX(xLine);
		else if (corner == Corner.TWO || corner == Corner.FOUR)
			robot.setY(yLine);
		
		robot.setTheta(theta2);
		
		robot.setSpeed(Constants.NORMAL_SPEED);
		robot.stopLightTimers();
	}
	
	/**
	 * Localization protocol for a line
	 */
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
