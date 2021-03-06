package tests;
import lejos.geom.Point;
import lejos.nxt.Button;
import robot.Robot;


// Localize the robot using odometry correction
/**
 * 
 * 1. Place robot skew in the center of square 1
 * 2. Robot will drive in skew square where it will correct itself twice
 * 
 * @author Matthew Vertescher
 *
 */
public class CorrectionTest {

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
		
		
		//bot.moveForwardUntilStop();
		//bot.moveImmediatelyBy(15);
		
		//squareCorrect(bot);
		
		//xCorrect(bot); 
		
		negXCorrect(bot);
		
		//squareCorrect(bot);
		
		//snooze(1000);
		//bot.turnToPoint(0,90);
		
		
		snooze(1000);
		bot.moveForwardToPoint(new Point(0,0));
		snooze(1000);
		bot.turnToPoint(0,90);
		 

		// Wait for exit
		Button.waitForAnyPress();

	}

	
	private static void posXCorrect(Robot b) {
		
		b.turnToPoint(90,0);
		snooze(1000);
		b.moveImmediatelyBy(15);
		
		snooze(30000);
	}

	private static void posYCorrect(Robot b) {
		
		snooze(1000);
		b.moveImmediatelyBy(15);
		
		snooze(30000);
	}

	private static void negXCorrect(Robot b) {

		b.turnToPoint(-20,0);
		snooze(1000);
		b.moveImmediatelyBy(15);

		snooze(30000);
	}

	private static void negYCorrect(Robot b) {

		b.turnToPoint(0,-20);
		snooze(1000);
		b.moveImmediatelyBy(15);

		snooze(30000);
	}
	
	private static void squareCorrect(Robot b) {
		b.moveImmediatelyBy(30);
		snooze(1000);
		b.turnImmediatelyBy(-90);
		snooze(1000);
		b.moveImmediatelyBy(30);
		snooze(1000);
		b.stopGridCorrection();
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
