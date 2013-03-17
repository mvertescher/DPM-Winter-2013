package tests;
import lejos.geom.Point;
import lejos.nxt.Button;
import robot.Robot;


/**
 * 
 * TwoLineLocTest.java
 * 
 * 1. Place robot in square 1
 * 2. Watch robot localized to the pose (0,0,0)
 * 
 * @author Matthew Vertescher
 *
 */
public class TwoLineLocTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Create our robot
		Robot bot = new Robot();

		// Start the odometer and display
		bot.startOdometer();
		bot.startDisplay();

		// Wait for start
		Button.waitForAnyPress();

		bot.startLightTimers();
		snooze(1000);
		
		bot.localizeIn1();
		
		snooze(1000);
		bot.setSpeed(100);
		bot.moveForwardToPoint(new Point(0,0));
		bot.turnToPoint(0,90);
		

		// Wait for exit
		Button.waitForAnyPress();

	}

	/**
	 * Sleep the thread for some milliseconds
	 * 
	 * @param milliseconds
	 */
	private static void snooze(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	
	
}
