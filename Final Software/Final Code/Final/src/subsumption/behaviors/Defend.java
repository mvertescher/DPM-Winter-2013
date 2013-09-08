package subsumption.behaviors;

import subsumption.AbstractBot;
import lejos.robotics.subsumption.Behavior;

public class Defend implements Behavior {

	private AbstractBot bot;
	
	public Defend(AbstractBot b) {
		this.bot = b;
	}
	
	@Override
	public boolean takeControl() {
		return this.bot.inDefenseTile;
	}

	@Override
	public void action() {
		this.bot.defend();
	}

	@Override
	public void suppress() {
		// This should not be suppressed 
	}

}
