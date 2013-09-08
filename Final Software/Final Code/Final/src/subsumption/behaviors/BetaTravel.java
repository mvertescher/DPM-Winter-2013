package subsumption.behaviors;

import subsumption.AbstractBot;
import subsumption.Attacker;
import lejos.geom.Point;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

/**
 * Travel.java
 * 
 * Behavior to begin traveling to a point.
 * If the robot sees an obstacle it will start avoiding.
 * 
 * @author Matthew Vertescher
 *
 */
public class BetaTravel implements Behavior {

	private AbstractBot bot;
	private Point goal;
	boolean lock = false; // Ensures that stopMoving is not continuously called
	
	/**
	 * Constructor
	 * @param b, abstract bot
	 * @param p, goal point
	 */
	public BetaTravel(AbstractBot b, Point p) {
		this.bot = b;
		this.goal = p;
	}
	
	@Override
	public boolean takeControl() {
		// This will lockout any further movement to the shooting position if at goal
		//TODO: Figure out a better way to do this.
		if (this.bot.doneShooting && this.goal.equals(this.bot.shootingPosition()))
			return false;
		else if (!this.bot.doneShooting && this.goal.equals(this.bot.endpoint()))
			return false;
		else if (this.bot.atEndpoint)
			return false;
		
		return (!this.bot.atDestination(goal,false) && !this.bot.obstacleInPath);
	}

	@Override
	public void action() {
		
		//Sound.beepSequenceUp();
		this.lock = false;
		//this.bot.moveToPoint(goal);
		this.bot.travelOptimalPathToPoint(goal);
		
		this.bot.moveToPointOnGrid(goal);
		
		//TEMP
		//if (this.bot.isAtShootingPosition() && this.goal.equals(this.bot.shootingPosition())) 
		if (this.bot.isInShootingTile() && this.goal.equals(this.bot.shootingPosition())) 
			this.bot.inShootingPosition = true;
		else if (this.bot.isAtEndpoint() && this.goal.equals(this.bot.endpoint()))
			this.bot.atEndpoint = true;
	}

	@Override
	public void suppress() {
		if (!lock) { 
			this.bot.stopMoving();
			this.lock = true;
			
			//Sound.buzz();
		}	
		//snooze(2000);
	}
	
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	
}
