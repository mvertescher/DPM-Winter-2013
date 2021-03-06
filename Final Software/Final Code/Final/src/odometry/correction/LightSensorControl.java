package odometry.correction;

import robot.Constants;
import lejos.nxt.LightSensor;

public class LightSensorControl {
	
	private final int sampleSize = 3; 
	private final int CUTOFF = 24;
	
	private LightSensor lsl, lsr;
	private int[] leftSample, rightSample;
	private int leftIndex, rightIndex; 	
	private int leftCur, leftPre;
	private int rightCur, rightPre;
	
	
	public LightSensorControl() {
		this.lsl = new LightSensor(Constants.LIGHT_LEFT,true);
		this.lsr = new LightSensor(Constants.LIGHT_RIGHT,true);
		
		this.leftCur =  lsl.getNormalizedLightValue();
		this.leftPre = this.leftCur;
		this.rightCur = lsr.getNormalizedLightValue();
		this.rightPre = this.rightCur;
		
		
		this.leftSample = new int[sampleSize];
		this.rightSample = new int[sampleSize];
		
		this.initSample(lsl, leftSample);
		this.initSample(lsr, rightSample);
		
		this.leftIndex = 0;
		this.rightIndex = 0;
	}
	
	public boolean leftLineHit() { 
		this.leftPre = this.leftCur;
		int newVal = lsl.getNormalizedLightValue();
		this.leftCur = this.leftCur + (newVal / this.sampleSize) - (this.leftSample[leftIndex] / this.sampleSize);
		this.leftSample[leftIndex] = newVal; 
		
		leftIndex++;
		if (leftIndex >= this.sampleSize)
			leftIndex = 0;
		
		
		//this.leftCur = lsl.getNormalizedLightValue();
		
		//return (((this.leftCur - this.leftPre) / this.leftPre) >  Constants.LIGHT_SENSOR_ERROR);
		
		return (this.leftPre - this.leftCur) > CUTOFF; 
		
		
	}
	


	public boolean rightLineHit() {
		this.rightPre = this.rightCur;
		int newVal = lsr.getNormalizedLightValue();
		this.rightCur = this.rightCur + (newVal / this.sampleSize) - (this.rightSample[rightIndex] / this.sampleSize);
		this.rightSample[rightIndex] = newVal; 
		
		rightIndex++;
		if (rightIndex >= this.sampleSize)
			rightIndex = 0;
		
		
		
		
		//this.rightCur = lsr.getNormalizedLightValue();
		
		//return (((this.rightCur - this.rightPre) /  this.rightPre) >  Constants.LIGHT_SENSOR_ERROR);
		
		return (this.rightPre - this.rightCur) > CUTOFF;
		
		
	}
	
	
	//FELIZ
	private void initSample(LightSensor ls, int[] sample) {
		for (int i = 0; i < sampleSize; i++) {
			sample[i] = ls.getNormalizedLightValue();
	}
	
	}
}