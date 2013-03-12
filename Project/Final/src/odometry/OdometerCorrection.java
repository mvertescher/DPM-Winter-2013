package odometry;

import lejos.nxt.Sound;
import lejos.util.Timer;
import lejos.util.TimerListener;
import robot.Constants;
import robot.Robot;

public class OdometerCorrection implements TimerListener {
	//TODO: There needs to be an ongoing correction for the robot to increase the pathfinding accuracy 
	
	private Timer correctionTimer;
	private Robot robot;
	private Odometer odometer;
	
	public OdometerCorrection(int period, Robot r, Odometer odo) {
		this.correctionTimer = new Timer(period, this);
		this.robot = r;
		this.odometer = odo;
	}
	
	@Override
	public void timedOut() {
		
		// Simple Grid Correction in positive y
		//double theta1, theta2, tick1, tick2; 
		
		
		
		if(Math.cos(Math.toRadians(robot.getTheta())) > .95 && robot.lineHitLeft() || robot.lineHitRight()) {
			double tick = robot.getY() - Constants.LIGHT_SENSOR_Y;
			if(tick > -5 && tick < 5)
				robot.setY(0);
			else if(tick > 25 && tick < 35)
				robot.setY(30 - Constants.LIGHT_SENSOR_Y);
			else if(tick > 55 && tick < 65)
				robot.setY(60 - Constants.LIGHT_SENSOR_Y);
			else if(tick > 115 && tick < 125)
				robot.setY(120 - Constants.LIGHT_SENSOR_Y);
			else if(tick > 145 && tick < 155)
				robot.setY(150 - Constants.LIGHT_SENSOR_Y);
			Sound.twoBeeps();
			snooze(1000);
		}
		
	}
	
	/**
	 * Starts the odometer correction
	 */
	public void startTimer() {
		correctionTimer.start();
	}
	
	/**
	 * Stops the odometer correction
	 */
	public void stopTimer() {
		correctionTimer.stop();
	}
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}
