package robot;

import odometry.Odometer;
import lejos.nxt.LCD;
import lejos.util.Timer;
import lejos.util.TimerListener;


public class LCDUpdater implements TimerListener {
	
	private Timer lcdUpdater;
	private Odometer odometer;
	private double[] position;
	
	// constructor
	public LCDUpdater(int period, Odometer odo) {
		lcdUpdater = new Timer(period, this);
		odometer = odo;
		position = new double[3];
	}

	@Override
	public void timedOut() {
		// clear the lines for displaying odometry information
		LCD.drawString("X:              ", 0, 0);
		LCD.drawString("Y:              ", 0, 1);
		LCD.drawString("T:              ", 0, 2);

		// get the odometry information
		odometer.getPosition(position, new boolean[] { true, true, true });

		// display odometry information
		for (int i = 0; i < 3; i++) {
			LCD.drawString(formattedDoubleToString(position[i], 2), 3, i);
		}

	}
	
	public void startTimer() {
		// clear the display once
		LCD.clearDisplay();
		lcdUpdater.start();
	}
	
	public void stopTimer() {
		lcdUpdater.stop();
	}
	
	private static String formattedDoubleToString(double x, int places) {
		String result = "";
		String stack = "";
		long t;
		
		// put in a minus sign as needed
		if (x < 0.0)
			result += "-";
		
		// put in a leading 0
		if (-1.0 < x && x < 1.0)
			result += "0";
		else {
			t = (long)x;
			if (t < 0)
				t = -t;
			
			while (t > 0) {
				stack = Long.toString(t % 10) + stack;
				t /= 10;
			}
			
			result += stack;
		}
		
		// put the decimal, if needed
		if (places > 0) {
			result += ".";
		
			// put the appropriate number of decimals
			for (int i = 0; i < places; i++) {
				x = Math.abs(x);
				x = x - Math.floor(x);
				x *= 10.0;
				result += Long.toString((long)x);
			}
		}
		
		return result;
	}

	
}
