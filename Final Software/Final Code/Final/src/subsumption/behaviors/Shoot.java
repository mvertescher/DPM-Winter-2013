package subsumption.behaviors;

import subsumption.AbstractBot;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

/**
 * Shooting behavior 
 *  
 * @author Matthew Vertescher
 *
 */
public class Shoot implements Behavior {

	private AbstractBot bot;
	
	/**
	 * Constructor
	 * @param a, abstract bot
	 */
	public Shoot(AbstractBot a) {
		this.bot = a; 
	}
	
	
	@Override
	public boolean takeControl() {
		return (this.bot.inShootingPosition && this.bot.hasBall && !this.bot.doneShooting);
	}

	@Override
	public void action() {
		Sound.beepSequenceUp();
		//bot.faceTarget();
		bot.shoot();
		
	}

	@Override
	public void suppress() {
		// This behavior should not be suppressed
		
	}
	
	
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
}
