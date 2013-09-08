package subsumption.behaviors;

import subsumption.AbstractBot;
import lejos.robotics.subsumption.Behavior;

/**
 * GetBall.java
 * 
 * Behavior to have the robot retrieve the ball.
 * 
 * @author Matthew Vertescher
 *
 */
public class GetBall implements Behavior {

	private AbstractBot bot;
	
	public GetBall(AbstractBot b) {
		this.bot = b;
	}
	
	@Override
	public boolean takeControl() {	
		return this.bot.atBallDispenser && !this.bot.hasBall;
	}

	@Override
	public void action() {
		this.bot.retrieveBalls();
	}

	@Override
	public void suppress() {
		// This behavior should never be suppressed 
	}

}
