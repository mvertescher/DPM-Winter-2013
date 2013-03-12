package subsumption.behaviors;

import subsumption.Attacker;
import lejos.robotics.subsumption.Behavior;

public class Shoot implements Behavior {

	private Attacker bot;
	
	public Shoot(Attacker a) {
		this.bot = a; 
	}
	
	@Override
	public boolean takeControl() {
		return (this.bot.inShootingPosition && this.bot.hasBall);
	}

	@Override
	public void action() {
		// TODO: Send fire command
	}

	@Override
	public void suppress() {
		// This behavior should not be suppressed
	}
	
}
