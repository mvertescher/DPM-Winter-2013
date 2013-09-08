package tests;
import lejos.nxt.Button;
import robot.Robot;

/**
 * Test used to calibrate the robot
 * 
 * 1. Center the robot at pose (0,0,0)
 * 2. Watch the robot move forward 60cm then the turn heading 90deg
 * 
 * @author Matthew Vertescher
 * 
 */
public class CalibrationTest {
	
	public static void main(String[] args) {

		// Create our robot
		Robot bot = new Robot();

		// Start the odometer and display
		bot.startOdometer();
		bot.startDisplay();

		// Wait for start
		Button.waitForAnyPress();

		snooze(1000);
		bot.moveImmediatelyBy(60);
		snooze(1000);
		bot.turnImmediatelyBy(-90);

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
