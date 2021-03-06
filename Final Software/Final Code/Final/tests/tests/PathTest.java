package tests;
import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import robot.Robot;



/**
 * Test for path following
 * 
 * 1. Center the robot at zero zero
 * 2. Watch the robot follow the path
 * 
 * @author Matthew Vertescher
 *
 */
public class PathTest {

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
		
		bot.travelPath(path1());
		
		bot.stopGridCorrection();
		
		bot.moveForwardToPoint(new Point(0,0));
		
		bot.turnToPoint(0,90);
			

		// Wait for exit
		Button.waitForAnyPress();

	}
	
	/**
	 * Path 1: Simple path moving on the grid lines
	 * @return
	 */
	public static Path path1() {
		Path p = new Path();
		
		p.add(new Waypoint(15,15));
		p.add(new Waypoint(15,45));
		p.add(new Waypoint(45,45));
		p.add(new Waypoint(45,75));
		p.add(new Waypoint(15,75));
		p.add(new Waypoint(15,15));
		
		return p;
	}
	
	/**
	 * Path 2: Moving off the grid lines
	 * @return
	 */
	public static Path path2() {
		Path p = new Path();
		
		p.add(new Waypoint(15,15));
		p.add(new Waypoint(45,45));
		p.add(new Waypoint(30,30));
		p.add(new Waypoint(60,60));
		p.add(new Waypoint(15,15));
		
		return p;
	}
	
	/**
	 * Path 3: Drift test 
	 * @return
	 */
	public static Path path3() {
		Path p = new Path();
		
		p.add(new Waypoint(15,15));
		p.add(new Waypoint(15,45));
		p.add(new Waypoint(15,75));
		p.add(new Waypoint(15,105));
		p.add(new Waypoint(15,75));
		p.add(new Waypoint(15,45));
		p.add(new Waypoint(15,15));
		
		return p;
	}
	
	/**
	 * Path 4: Grid Square 
	 * @return
	 */
	public static Path path4() {
		Path p = new Path();
		
		p.add(new Waypoint(15,15));
		p.add(new Waypoint(15,45));
		p.add(new Waypoint(45,45));
		p.add(new Waypoint(45,15));
		p.add(new Waypoint(15,15));
		
		return p;
	}
	
	public static Path path5() {
		Path p = new Path();
		
		p.add(new Waypoint(15,15));
		p.add(new Waypoint(15,45));
		p.add(new Waypoint(15,75));
		p.add(new Waypoint(15,75));
		p.add(new Waypoint(45,75));
		p.add(new Waypoint(45,45));
		p.add(new Waypoint(45,15));
		p.add(new Waypoint(75,15));
		p.add(new Waypoint(75,45));
		p.add(new Waypoint(45,45));
		p.add(new Waypoint(45,15));
		p.add(new Waypoint(15,15));
		
		return p;
	}
	
	public static Path felizPath() {
		Path p = new Path();
		
		p.add(new Waypoint(15,15));		
		p.add(new Waypoint(15,45));		
		p.add(new Waypoint(15,75));		
		p.add(new Waypoint(15,105));		
		p.add(new Waypoint(45,105));		
		p.add(new Waypoint(45,135));		
		p.add(new Waypoint(45,165));		
		p.add(new Waypoint(15,165));		
		p.add(new Waypoint(15,195));		
		p.add(new Waypoint(15,225));		
		p.add(new Waypoint(45,225));		
		p.add(new Waypoint(45,195));		
		p.add(new Waypoint(45,165));		
		p.add(new Waypoint(15,165));		
		p.add(new Waypoint(15,135));		
		p.add(new Waypoint(15,105));		
		p.add(new Waypoint(45,105));		
		p.add(new Waypoint(45,75));		
		p.add(new Waypoint(45,45));		
		p.add(new Waypoint(15,45));		
		p.add(new Waypoint(15,15));
		
		return p;
	}
	
	/**
	 * Sleep the thread for some milliseconds
	 * 
	 * @param milliseconds
	 */
	private static void snooze(int milliseconds) {
		try {Thread.sleep(milliseconds);} catch (InterruptedException e) {e.printStackTrace();}
	}
	
}
