package subsumption.behaviors;

import subsumption.AbstractBot;
import lejos.geom.Point;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

/**
 * Travel.java
 * 
 * Behavior to travel directly to a point
 * 
 * @author Matthew Vertescher
 *
 */
public class Travel implements Behavior {

	private AbstractBot bot;
	private Point goal;
	boolean lock = false; // Ensures that stopMoving is not continuously called
	
	public Travel(AbstractBot b, Point p) {
		this.bot = b;
		this.goal = p;
	}
	
	@Override
	public boolean takeControl() {
		return (!this.bot.atDestination(goal) && !this.bot.obstacleDetected());
	}

	@Override
	public void action() {
		this.lock = false;
		this.bot.moveToPoint(goal);
		
	}

	@Override
	public void suppress() {
		if (!lock) { 
			this.bot.stopMoving();
			this.lock = true;
			Sound.twoBeeps();
		}	
	
	}
	
	
	
}
