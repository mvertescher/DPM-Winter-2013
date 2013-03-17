package subsumption.behaviors;

import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class Shutdown implements Behavior {

	public Shutdown() {
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		Sound.beepSequenceUp();
		snooze(2000);
		System.exit(0);
	}

	@Override
	public void suppress() {
		// Dead code
	}

	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}