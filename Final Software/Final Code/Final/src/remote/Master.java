package remote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.*;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Master {
	public static void main(String[] args) throws Exception {
		
		String name = "The Crusher";
		
		
			
		RemoteDevice btrd = Bluetooth.getKnownDevice(name);
			
		if (btrd == null) {
			LCD.clear();
			LCD.drawString("No such device", 0, 0);
			LCD.refresh();
			Thread.sleep(2000);
			System.exit(1);
		}
		
		BTConnection btc = Bluetooth.connect(btrd);
			
		if (btc == null) {
			LCD.clear();
			LCD.drawString("Connect fail", 0, 0);
			LCD.refresh();
			Thread.sleep(2000);
			System.exit(1);
		}	
		
		/*
		LCD.clear();
		LCD.drawString("Connected", 0, 0);
		LCD.refresh();
		*/
		
		
		//DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		while(true){
			
	
			int buttonChoice = 0; 
			buttonChoice = Button.waitForAnyPress();
		
			if (buttonChoice == Button.ID_LEFT) {
				try {
					LCD.clear();
					LCD.drawString("Shoot",0,0);
					LCD.refresh();
					dos.writeInt(1);
					dos.flush();
				} catch (IOException ioe) {
					LCD.drawString("Write Exception", 0, 0);
					LCD.refresh();
			
				}
				LCD.clear();
				LCD.drawString("Again?",0,0);
				LCD.refresh();
				
			}
		}
		
		
		
	}
}
