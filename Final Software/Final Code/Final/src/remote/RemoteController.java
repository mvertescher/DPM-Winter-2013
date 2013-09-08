package remote;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.bluetooth.RemoteDevice;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * RemoteController.java
 * 
 * The remote interface for the robot
 * 
 * @author Matthew Vertescher 
 *
 */
public class RemoteController {

	private String remoteName = "ANTBOT";
	private DataOutputStream dos;
	private DataInputStream dis;		//FELIZ
	
	/**
	 * Creates a new remote controller 
	 */
	public RemoteController() {
		this.connectToRemote(remoteName);
	}
	
	/**
	 * Connect to a remote device
	 * @param name
	 */
	private void connectToRemote(String name) {
		
		RemoteDevice btrd = Bluetooth.getKnownDevice(name);
			
		if (btrd == null) {
			LCD.clear();
			LCD.drawString("No such device", 0, 0);
			LCD.refresh();
			snooze(2000);
			System.exit(1);
		}
		
		BTConnection btc = Bluetooth.connect(btrd);
		
		if (btc == null) {
			LCD.clear();
			LCD.drawString("Connect fail", 0, 0);
			LCD.refresh();
			snooze(2000);
			System.exit(1);
		}	
		
		dos = btc.openDataOutputStream();
		dis = btc.openDataInputStream();		//FELIZ
	}
	
	
	public double[] waitForConstants() {
		return null;
	}
	
	/**
	 * Send a shoot command
	 * position from the goal, when facing goal right is positive and left negative
	 */
	public void shoot(int numberOfBalls, int tilesToShoot) {		//Modified FELIZ
		try {
			
			dos.writeInt(numberOfBalls);
			dos.flush();
		} catch (IOException ioe) {
			
			Sound.buzz();
			Sound.buzz();
			Sound.buzz();
		}
		
		try {
			
			dos.writeInt(tilesToShoot);
			dos.flush();
		} catch (IOException ioe) {
				
			Sound.buzz();
			Sound.buzz();
			Sound.buzz();
		}
		
		
		}
	
	/**
	 * FELIZ
	 */
	public void waitShootsDone(){
		boolean done = false;
		int shootsDone = 1;
		while(!done){
			Sound.beep();
			try{shootsDone = dis.readInt();} catch (IOException ieo){}
			Sound.buzz();
				
				
			if(shootsDone == 0 ){
					
				done = true;
			}
			
		}
	}
	
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
}
