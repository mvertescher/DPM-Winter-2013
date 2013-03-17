package odometry.correction;

import odometry.Odometer;
import lejos.geom.Point;
import lejos.nxt.LCD;
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
	
	//private Point[] points;
	private double[] xCord; 
	private double[] yCord; 
	
	private int index = 0;
	private boolean leftHit, rightHit, lastLeft, lastRight;
	private double leftTime, rightTime;
	private double currentX, currentY, currentTheta;
	
	private Windrose previousWindrose, currentWindrose;
	
	public OdometerCorrection(int period, Robot r, Odometer odo) {
		this.correctionTimer = new Timer(period, this);
		this.robot = r;
		this.odometer = odo;
		
		//this.points = new Point[4];
		this.xCord = new double[4];
		this.yCord = new double[4];
		
		//this.rightPoints = new Point[4];	
		this.lastLeft = false;
		this.lastRight = false;
		
		this.leftTime = 0;
		this.rightTime = 0;
	}
	
	@Override
	public void timedOut() {
		
		// Simple Grid Correction in positive y
		//double theta1, theta2, tick1, tick2; 
		
		// Get inputs
		currentX = odometer.getX();
		currentY = odometer.getY();
		currentTheta = odometer.getTheta();
		
		currentWindrose = this.getWindrose();
		leftHit = robot.lineHitLeft();
		rightHit = robot.lineHitRight();
		
		
		if (currentWindrose == Windrose.NORTH) {
			if (leftHit && rightHit) {
				odometer.setY(this.closestLine(currentY) - Constants.LIGHT_SENSOR_Y);
				odometer.setTheta(0);
				Sound.buzz();
			}
			
			else if (leftHit && !this.lastLeft) {
				//odometer.setY(this.closestLine(currentY) - Constants.LIGHT_SENSOR_Y*Math.cos(Math.toRadians(currentTheta)));
				
				xCord[index] = currentX;
				yCord[index] = currentY;	 
					
				index++;
				
				Sound.beep();
				//leftTime = System.currentTimeMillis();
			}
			
			else if (rightHit && !this.lastRight) {
				//odometer.setY(this.closestLine(currentY) - Constants.LIGHT_SENSOR_Y*Math.cos(Math.toRadians(currentTheta)));
				
				xCord[index] = currentX;
				yCord[index] = currentY;
				
				index++;
				
				Sound.beep();
				//rightTime = System.currentTimeMillis();
			}
			
			if (index == 2) {
				//double deltaTime = rightTime - leftTime; // In milliseconds
				
				//if (Math.abs(deltaTime) < 2000) {
					double ptpd = this.pointToPointDistance(xCord[0],yCord[0],xCord[1],yCord[1]);
					
					double thetaCorrection = Math.toDegrees(Math.asin((ptpd/2)/(Constants.LIGHT_SENSOR_X)));
					
					if (leftHit) 
						thetaCorrection = -thetaCorrection;
						
					odometer.setTheta(thetaCorrection);
					
					double cos = Math.cos(Math.toRadians(thetaCorrection));
  					double deltaLight = (ptpd/2)*cos;	//COS //(ptpd/2)
  					double deltaBot = (Constants.LIGHT_SENSOR_Y)*cos;
  					
  					
  					//LCD.drawString("tht:              ", 0, 4);
					//LCD.drawString("tht: "+thetaCorrection, 0, 4);
					LCD.drawString("dL:              ", 0, 5);
					LCD.drawString("dL: "+deltaLight, 0, 4);
					LCD.drawString("dB:              ", 0, 4);
					LCD.drawString("dB: "+deltaBot, 0, 5);
					
					odometer.setY(this.closestLine(currentY) + deltaLight - deltaBot);
					
					Sound.buzz();
			
				//}
				
				index = 0;	
			}
				
		}
		
		else if (currentWindrose == Windrose.EAST) {
			if (leftHit && rightHit) {
				odometer.setX(this.closestLine(currentX) - Constants.LIGHT_SENSOR_Y);
				odometer.setTheta(90);
				Sound.buzz();
			}
			
			else if (leftHit && !this.lastLeft) {
				//odometer.setY(this.closestLine(currentY) - Constants.LIGHT_SENSOR_Y*Math.cos(Math.toRadians(currentTheta)));
				
				xCord[index] = currentX;
				yCord[index] = currentY;	 
					
				index++;
				
				Sound.beep();
				//leftTime = System.currentTimeMillis();
			}
			
			else if (rightHit && !this.lastRight) {
				//odometer.setY(this.closestLine(currentY) - Constants.LIGHT_SENSOR_Y*Math.cos(Math.toRadians(currentTheta)));
				
				xCord[index] = currentX;
				yCord[index] = currentY;
				
				index++;
				
				Sound.beep();
				//rightTime = System.currentTimeMillis();
			}
			
			if (index == 2) {
				//double deltaTime = rightTime - leftTime; // In milliseconds
				
				//if (Math.abs(deltaTime) < 2000) {
					double ptpd = this.pointToPointDistance(xCord[0],yCord[0],xCord[1],yCord[1]);
					
					double thetaCorrection = Math.toDegrees(Math.asin((ptpd/2)/(Constants.LIGHT_SENSOR_X)));
					
					if (leftHit) 
						thetaCorrection = -thetaCorrection;
					
					thetaCorrection = thetaCorrection + 90;
					
					odometer.setTheta(thetaCorrection);
					
					double sin = Math.sin(Math.toRadians(thetaCorrection));
  					double deltaLight = (ptpd/2)*sin;	//COS //(ptpd/2)
  					double deltaBot = (Constants.LIGHT_SENSOR_Y)*sin;
  					
  					
  					//LCD.drawString("tht:              ", 0, 4);
					//LCD.drawString("tht: "+thetaCorrection, 0, 4);
					LCD.drawString("dL:              ", 0, 5);
					LCD.drawString("dL: "+deltaLight, 0, 4);
					LCD.drawString("dB:              ", 0, 4);
					LCD.drawString("dB: "+deltaBot, 0, 5);
					
					odometer.setX(this.closestLine(currentX) + deltaLight - deltaBot);
					
					Sound.buzz();
			
				//}
				
				index = 0;	
			}
		}
		
		/*
		 * SOUTH
		 */
		else if (currentWindrose == Windrose.SOUTH) {
			if (leftHit && rightHit) {
				odometer.setY(this.closestLine(currentY) + Constants.LIGHT_SENSOR_Y);
				odometer.setTheta(180);
				Sound.buzz();
			}
			
			else if (leftHit && !this.lastLeft) {	
				xCord[index] = currentX;
				yCord[index] = currentY;	 			
				index++;				
				Sound.beep();
				//leftTime = System.currentTimeMillis();
			}
			
			else if (rightHit && !this.lastRight) {
				xCord[index] = currentX;
				yCord[index] = currentY;
				index++;
				Sound.beep();
				//rightTime = System.currentTimeMillis();
			}
			
			if (index == 2) {
				double ptpd = this.pointToPointDistance(xCord[0],yCord[0],xCord[1],yCord[1]);
					
				double thetaCorrection = Math.toDegrees(Math.asin((ptpd/2)/(Constants.LIGHT_SENSOR_X)));
					
				if (leftHit) 
					thetaCorrection = -thetaCorrection;
				
				thetaCorrection = thetaCorrection + 180;
				
				odometer.setTheta(thetaCorrection);
					
				double cos = Math.cos(Math.toRadians(thetaCorrection));
  				double deltaLight = (ptpd/2)*cos;	//COS //(ptpd/2)
  				double deltaBot = (Constants.LIGHT_SENSOR_Y)*cos;

				LCD.drawString("dL:              ", 0, 5);
				LCD.drawString("dL: "+deltaLight, 0, 4);
				LCD.drawString("dB:              ", 0, 4);
				LCD.drawString("dB: "+deltaBot, 0, 5);
					
				odometer.setY(this.closestLine(currentY) + (deltaLight - deltaBot));
					
				Sound.buzz();
				index = 0;	
			}
		}
		
		else if (currentWindrose == Windrose.WEST) {
			if (leftHit && rightHit) {
				odometer.setX(this.closestLine(currentX) + Constants.LIGHT_SENSOR_Y);
				odometer.setTheta(270);
				Sound.buzz();
			}
			
			else if (leftHit && !this.lastLeft) {
				xCord[index] = currentX;
				yCord[index] = currentY;	 	
				index++;
				Sound.beep();
				//leftTime = System.currentTimeMillis();
			}
			
			else if (rightHit && !this.lastRight) {
				xCord[index] = currentX;
				yCord[index] = currentY;
				index++;
				Sound.beep();
				//rightTime = System.currentTimeMillis();
			}
			
			if (index == 2) {
				double ptpd = this.pointToPointDistance(xCord[0],yCord[0],xCord[1],yCord[1]);
					
				double thetaCorrection = Math.toDegrees(Math.asin((ptpd/2)/(Constants.LIGHT_SENSOR_X)));
					
				if (leftHit) 
					thetaCorrection = -thetaCorrection;
					
				thetaCorrection = thetaCorrection + 270;
					
				odometer.setTheta(thetaCorrection);
					
				double sin = Math.sin(Math.toRadians(thetaCorrection));
  				double deltaLight = (ptpd/2)*sin;	//COS //(ptpd/2)
  				double deltaBot = (Constants.LIGHT_SENSOR_Y)*sin;
  					
				LCD.drawString("dL:              ", 0, 5);
				LCD.drawString("dL: "+deltaLight, 0, 4);
				LCD.drawString("dB:              ", 0, 4);
				LCD.drawString("dB: "+deltaBot, 0, 5);
					
				odometer.setX(this.closestLine(currentX) + (deltaLight - deltaBot));
					
				Sound.buzz();

				
				index = 0;	
			}
		}
		
		else if (currentWindrose == Windrose.OTHER) {
			index = 0;
		}
		
		this.lastLeft = leftHit;
		this.lastRight = rightHit;
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
	
	
	private int closestLine(double xOrY) {
		int div = (int) xOrY / 30;
		if ((xOrY % 30)  > 15)
			div++;
		return (div * 30); 
	}
	
	
	private void fullCorrection() {
		// Check if facing grid
		currentWindrose = this.getWindrose();
		leftHit = robot.lineHitLeft();
		rightHit = robot.lineHitRight();
				if(currentWindrose == Windrose.NORTH || currentWindrose == Windrose.EAST || currentWindrose == Windrose.SOUTH || currentWindrose == Windrose.WEST) {
					if (leftHit && rightHit) {
						// Both hit at same time
						
						if (currentWindrose == Windrose.NORTH) {
							odometer.setY(this.closestLine(odometer.getY()) - Constants.LIGHT_SENSOR_Y);
							odometer.setTheta(0);
						}
						
						else if (currentWindrose == Windrose.EAST) {
							odometer.setX(this.closestLine(odometer.getX()) - Constants.LIGHT_SENSOR_X);
							odometer.setTheta(90);
						}
						
						else if (currentWindrose == Windrose.SOUTH) {
							odometer.setY(this.closestLine(odometer.getY()) + Constants.LIGHT_SENSOR_Y);
							odometer.setTheta(180);
						}
						
						else if (currentWindrose == Windrose.WEST) {
							odometer.setX(this.closestLine(odometer.getX()) + Constants.LIGHT_SENSOR_X);
							odometer.setTheta(270);
						}
						index = 0;
						Sound.buzz();
						snooze(1000);
					}
					
					
					else if (leftHit) {
						//points[index] = new Point((float)odometer.getX(),(float)odometer.getY());
						xCord[index] = odometer.getX();
						yCord[index] = odometer.getY();
						
						index++;		 
						Sound.beep();
						
						// Reset the index if the same line hits twice
						//if (index == 2 && !this.lastRight) 
							//index = 0;
						
						this.lastRight = false; 
					}
					
					else if(rightHit) {
						//points[index] = new Point((float)odometer.getX(),(float)odometer.getY());
						xCord[index] = odometer.getX();
						yCord[index] = odometer.getY();
						index++;		 
						Sound.beep();
						 
						// Reset the index if the same line hits twice
						//if (index == 2 && this.lastRight) 
							//index = 0;
						
						this.lastRight = true;
					}

					// Correct theta
					if (index == 2) {
						
						double ptpd = this.pointToPointDistance(xCord[0],yCord[0],xCord[1],yCord[1]);
						
						// Check if the correction makes sense 
						if (ptpd < 10) {
							
							// Trig calculation 
							double thetaCorrection = Math.toDegrees(Math.asin((ptpd/2)/(7)));
						
							if (!this.lastRight)
								thetaCorrection = -thetaCorrection;
						
							if (currentWindrose == Windrose.EAST) 
								thetaCorrection = thetaCorrection + 90;
							else if (currentWindrose == Windrose.SOUTH) 
								thetaCorrection = thetaCorrection + 180;
							else if (currentWindrose == Windrose.WEST) 
								thetaCorrection = thetaCorrection + 270;
						
							odometer.setTheta(thetaCorrection);
		  					double delta = 0;
		  					// Correct y with theta
		  					if(currentWindrose == Windrose.NORTH) {
								delta = (ptpd/2)*Math.cos(Math.toRadians(thetaCorrection));
								LCD.drawString("cY:              ", 0, 4);
								LCD.drawString("cY: "+delta, 0, 4);
								odometer.setY(this.closestLine(odometer.getY()) - delta);
							}
						
							// Correct x with theta
							else if(currentWindrose == Windrose.EAST) {
								delta = (ptpd/2)*Math.cos(Math.toRadians(-thetaCorrection));
								odometer.setX(this.closestLine(odometer.getX()) - delta);
							}
						
							else if(currentWindrose == Windrose.SOUTH) {
								delta = (ptpd/2)*Math.cos(Math.toRadians(thetaCorrection));
								odometer.setY(this.closestLine(odometer.getY()) + delta);
							}
						
							else if(currentWindrose == Windrose.WEST) {
								delta = (ptpd/2)*Math.cos(Math.toRadians(-thetaCorrection));
								odometer.setX(this.closestLine(odometer.getX()) + delta);
							}
						}
						index = 0;
					}
					
					
					
				}
				
				else if (currentWindrose == Windrose.OTHER) {
					index = 0;
				}
				/*
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
				}*/
	}
	
	
	
	/**
	 * Returns a windrose approximation of the current direction 
	 * @return windrose direction
	 */
	private Windrose getWindrose() {
		
		double rad = Math.toRadians(odometer.getTheta());
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		if (cos > .88) 
			return Windrose.NORTH;
		else if (sin > .88)
			return Windrose.EAST;
		else if (cos < -.88) 
			return Windrose.SOUTH;
		else if (sin < -.88)
			return Windrose.WEST;
		
		
		return Windrose.OTHER;
	}
	
	private double pointToPointDistance(double aX, double aY, double bX, double bY) {
		double dX = bX - aX;
		double dY = bY - aY;
		return Math.sqrt(Math.pow(dX,2)+Math.pow(dY,2));
	}
	
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}