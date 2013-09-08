package tests;

import lejos.geom.Point;
import lejos.nxt.Button;
import robot.Robot;

public class DiagCorrectTest {

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
		bot.startGridCorrection();
		snooze(1000);
		
		bot.turnToHeading(45);
		bot.moveImmediatelyBy(30);
		
		//bot.moveForwardUntilStop();
		//bot.moveImmediatelyBy(15);
		

		bot.stopGridCorrection();
		
		snooze(1000);
		bot.moveForwardToPoint(new Point(0,0));
		snooze(1000);
		bot.turnToHeading(0);
		 

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
