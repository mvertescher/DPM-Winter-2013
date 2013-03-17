package tests;
import lejos.nxt.Button;
import robot.Robot;


public class LightSensorGrapher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create our robot
		Robot mattbot = new Robot(); 
		
		// Start the odometer and display
		mattbot.startOdometer();
		mattbot.startDisplay();
		
		// Wait for start
		Button.waitForAnyPress();
		
		mattbot.startLightTimers();
		
		
		// Wait for exit
		Button.waitForAnyPress();

	}

	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	
}
