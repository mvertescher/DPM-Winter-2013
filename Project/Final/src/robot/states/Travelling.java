package robot.states;

import robot.Robot;
import lejos.nxt.Sound;

public class Travelling implements State {

	private StateID id;
	private Robot robot;
	//private Avoiding avoiding;
	
	public Travelling(Robot r) {
		this.robot = r;
		this.id = StateID.TRAVELLING;
		//this.avoiding = new Avoiding(r);
		
		// Pre-loaded obstacles
		//avoiding.mapObstacleAt(-15,45);
		//avoiding.mapObstacleAt(15,105);
		
	}
	
	public void travelTo(int x, int y) {
		int usDistance = 0;
		boolean done = false;
		
		robot.turnToPoint(x,y);

		robot.startUltrasonicTimers();
		
		robot.moveForwardUntilStop();
		
		while (!done) {
			
			usDistance = robot.getLeftUltrasonicDistance();
			
			if (usDistance < 20) { 
				robot.stopMotors();
				snooze(500);
				
				// Enter avoid state
				
				robot.moveImmediatelyBy(-10);
				
				usDistance = robot.getLeftUltrasonicDistance();
				
				snooze(500);
				double theta = robot.getTheta();
				
				robot.avoidingState().mapObstacleAtPoint(robot.getX() + (usDistance*Math.sin(Math.toRadians(theta))), robot.getY() + (usDistance*Math.cos(Math.toRadians(theta))));
				robot.avoidingState().travelOptimalPath(x,y);
				
				
				// The robot will return to a navigating state here
				snooze(1000);
				//Sound.beep();
				robot.turnToPoint(x,y);
				Sound.buzz();
				snooze(1000);
				robot.moveForwardUntilStop();
			}
			
			//TODO: Check if the robot is at its position 
			else if (robot.getY() > y) {
				robot.stopMotors();
				done = true;
			}
			
			// Throttle 
			snooze(30);
		}
		
		Sound.beep();
	}
	
	@Override
	public StateID getStateID() {
		return id;
	}
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
}