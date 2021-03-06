package main;

import robot.Robot;
import lejos.nxt.Button;

/**
 * Main runnable class for the final project 
 * @author Matthew Vertescher
 */
public class Main {

	/**
	 * Main runnable method
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create our robot
		Robot mattbot = new Robot(); 
		
		// Start the odometer and display
		mattbot.startOdometer();
		//mattbot.startDisplay();
		 
		// Wait for start8
		Button.waitForAnyPress();
		
		// Start tasks
		mattbot.begin(); 
		
		// Wait for exit
		Button.waitForAnyPress();
		
	}

}
