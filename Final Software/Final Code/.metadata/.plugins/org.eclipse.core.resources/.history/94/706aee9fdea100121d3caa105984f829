package subsumption.behaviors;

import subsumption.AbstractBot;
import subsumption.Attacker;
import lejos.geom.Point;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

/**
 * Avoid behavior. 
 * 
 * If an obstacle is detected an bot is not at its required destination, 
 * then log the obstacle and recalculate the path. Travel the path?
 * 
 * @author Matthew Vertescher
 *
 */
public class Avoid implements Behavior {

	private AbstractBot bot;
	//private Point destination; 
	boolean suppressed = false; 
	
	/**
	 * Constructor
	 * @param AbstractBot b, bot 
	 * @param Point p, destination 
	 */
	public Avoid(AbstractBot b) {
		this.bot = b;
		//this.destination = p;
	}
	
	
	@Override
	public boolean takeControl() {
		// This will lockout any further movement to the shooting position if at goal
		//TODO: Figure out a better way to do this.
		//if (this.bot.doneShooting && this.destination.equals(this.bot.shootingPosition()))
		//	return false;
		//else if (this.bot.atEndpoint)
		//	return false;
		if (this.bot.isFacingWall()) {
			this.bot.obstacleInPath = false;
			return false;
		}
		//Sound.beep();
		return (this.bot.obstacleInPath && !this.bot.avoidLock && (!this.bot.isAtShootingPosition() || !this.bot.isAtEndpoint()));
	}

	@Override
	public void action() {
		
		Sound.beep();
		this.suppressed = false;
		// Stop
		bot.stopMoving();
		
		if (this.bot.inCenterOfTile()) {
			// Do something special 
			bot.centerAvoid();
			
		}
		
		else {
			// Log obstacle
			bot.markObstacleAndReverse();
		}
		
		
		
		// Tell the bot that avoiding is over
		//bot.obstacleInPath = false;
	
		
	}

	@Override
	public void suppress() {
		
		// Should never be suppressed?
		
		if (!suppressed && !this.bot.isFacingWall()) {
			bot.stopMoving();
			
			//this.action(); //???
			
			this.suppressed = true;
			Sound.twoBeeps();
		}
	}
	
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}
