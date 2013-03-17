package subsumption.behaviors;

import subsumption.AbstractBot;
import lejos.robotics.subsumption.Behavior;

public class Localize implements Behavior {

	private AbstractBot bot;
	
	public Localize(AbstractBot b) {
		this.bot = b;
	}
	
	@Override
	public boolean takeControl() {
		return this.bot.isLost();
	}

	@Override
	public void action() {
		this.bot.localize();
		this.bot.setLost(false);
	}

	@Override
	public void suppress() {
		// Do nothing if suppressed,
	}

}
