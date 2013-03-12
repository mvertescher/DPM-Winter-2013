package navigation;


/**
 * Navigation.java
 * 
 * Main navigation, in control of movement. 
 * 
 * @author Matthew Vertescher
 */
public class Trigonometry {

	/**
	 * Robot constants
	 */
	private static double LEFT_RADIUS, RIGHT_RADIUS, WIDTH;

	/**
	 * Constructor for Navigation.java 
	 * @param leftRad
	 * @param rightRad
	 * @param width
	 */
	public Trigonometry(double leftRad, double rightRad, double width) {
		LEFT_RADIUS = leftRad;
		RIGHT_RADIUS = rightRad;
		WIDTH = width;
	}
	
		
	/**
	 * Get the heading to the point
	 * @param x
	 * @param y
	 * @return heading angle in degrees
	 */
	public double getPointToPointHeading(double xInit, double yInit, double xFinal, double yFinal) {
		// Calculate relative x,y distance
		double relativeX = xFinal - xInit, relativeY = yFinal - yInit;
		
		// Get heading
		double heading = Math.toDegrees(Math.atan2(relativeX,relativeY));
		
		// Ensure heading is between 0 and 360
		if (heading < 0)
			heading += 360; 
		
		return heading; 
	}
	
	/**
	 * Turn to angle
	 * @param initTheta in degrees
	 * @param finalTheta in degrees
	 */
	public double turnTo(double initTheta, double finalTheta) {			
		// Return value
		double deltaTheta = 0;

		// Ensure theta is between 0 and 360
		if (finalTheta < 0)
			finalTheta += 360; 
		
		// Get the raw difference
		double rawDif = (finalTheta - initTheta); 
		
		// Calculation from tutorial slides
		if (Math.abs(rawDif) <= 180) {
			deltaTheta = rawDif; 
		}
		else if (rawDif < -180) {
			deltaTheta = rawDif + 360;
		}
		else if (rawDif > 180) {
			deltaTheta = rawDif - 360;
		}
		
		return -deltaTheta; // was +
	}
	
	/**
	 * Finds the distance between two points
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return
	 */
	public double getPointToPointDistance(double xInit, double yInit, double xFinal, double yFinal) {
		double deltaX = xFinal - xInit;
		double deltaY = yFinal - yInit;
		return Math.sqrt((Math.pow(deltaX,2)+(Math.pow(deltaY,2)))); 
	}
	
	/**
	 * Converts angle
	 * @param radius
	 * @param width
	 * @param angle
	 * @return
	 */
	public int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}
	
	/**
	 * Converts distance
	 * @param radius
	 * @param distance
	 * @return
	 */
	public int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}


}
