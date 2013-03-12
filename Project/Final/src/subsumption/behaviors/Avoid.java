package subsumption.behaviors;

import subsumption.AbstractBot;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class Avoid implements Behavior {

	private AbstractBot bot;
	boolean suppressed = false; 
	
	public Avoid(AbstractBot b) {
		this.bot = b;
	}
	
	
	@Override
	public boolean takeControl() {
		//Sound.beep();
		return bot.obstacleDetected();
	}

	@Override
	public void action() {
		//snooze(1000);
		Sound.beep();
		//snooze(2000);
		this.suppressed = false;
		bot.turnToPoint(30,30);
		//Sound.buzz();
		// Log obstacle
		
		// Travel path
	}

	@Override
	public void suppress() {
		if (!suppressed) {
			//bot.stopMoving();
			this.suppressed = true;
		}
	}

	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}
