package subsumption.behaviors;

import subsumption.AbstractBot;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

/**
 * Simple behavior that ensures the Arbitrator does not finish until the robot is at its endpoint.
 *  
 * @author Matthew Vertescher
 *
 */
public class Shutdown implements Behavior {

	/**
	 * Link to bot
	 */
	private AbstractBot bot;
	
	/**
	 * Trivial constructor
	 */
	public Shutdown(AbstractBot a) {
		this.bot = a;
	}
	
	@Override
	public boolean takeControl() {
		return !this.bot.atEndpoint;
		//return !this.bot.doneShooting;
	}

	@Override
	public void action() {
		Sound.beepSequenceUp();
		snooze(2000);
		this.bot.obstacleInPath = false; // If in shutdown, there is no obstacle
		this.bot.shutdown();
		//System.exit(0);
	}

	@Override
	public void suppress() {
		// Do nothing
	}

	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}
