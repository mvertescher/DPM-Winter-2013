package tests;

import lejos.geom.Point;
import lejos.nxt.Button;
import robot.Robot;
import subsumption.BetaBot;

/**
 * Test navigation and obstacle avoidance at the same time. 
 * 
 * 1. The robot localizes first
 * 2. Begins to move to a the predetermined shooting location, with avoidance 
 * 
 * 
 * 
 * @author Matthew Vertescher
 *
 */
public class AvoidanceTest {

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

		//bot.startLightTimers();
		//bot.startUltrasonicTimers();
		//snooze(1000);
		//bot.startGridCorrection();
		//snooze(1000);
		
		
		BetaBot betabot = new BetaBot(bot);
	
		betabot.start();
		
		
		
		
		snooze(1000);
		bot.moveForwardToPoint(new Point(0,0));
		snooze(1000);
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
