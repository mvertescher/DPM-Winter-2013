package sensors;

public interface SensorTimer {
	
	public SensorID getSensorID();
	
	public void startTimer();
	public void stopTimer();
	public int getValue();
	public boolean flag();
	
}