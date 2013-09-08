package sensors;

/**
 * Interface that outlines the basic methods to be implemented for a sensor thread.
 * 
 * @author Matthew Vertescher
 *
 */
public interface DuelSensorTimer {
	
	/**
	 * Returns the sensor identification for a timer 
	 * @return the SensorID
	 */
	public SensorID getSensorID();
	
	/**
	 * Start the timer listener
	 */
	public void startTimer();
	
	/** 
	 * Stop the timer listener
	 */
	public void stopTimer();
	
	/**
	 * Returns the left value of the sensor.
	 * @return Integer value
	 */
	public int getLeftValue();
	
	/**
	 * Returns the right value of the sensor.
	 * @return Integer value
	 */
	public int getRightValue();
	
	/**
	 * A boolean flag for the sensor. 
	 * A filter can be implemented here.
	 * @return True if the sensor hits 
	 */
	public boolean flag();
	
}
