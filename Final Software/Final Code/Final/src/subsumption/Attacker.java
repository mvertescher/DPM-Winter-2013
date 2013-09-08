package subsumption;

import robot.Robot;
import subsumption.behaviors.Avoid;
import subsumption.behaviors.GetBall;
import subsumption.behaviors.Localize;
import subsumption.behaviors.Shoot;
import subsumption.behaviors.Shutdown;
import subsumption.behaviors.TravelPoints;
import lejos.geom.Point;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Attacker.java
 * 
 * Attacker protocol 
 * 
 * @author Matthew Vertescher
 */
public class Attacker extends AbstractBot {
		
	/**
	 * Creates a new Attacker
	 * @param r
	 */
	public Attacker(Robot r) {
		super(r); 
		
		
		Point[] pts = new Point[3];
		pts[0] = r.ballDispenserTile();
		pts[1] = r.shootingLocationForFinal();
		pts[2] = r.firstPoint();
		
		Behavior[] b = new Behavior[6];  
		
		b[5] = new Localize(this, r.firstPoint());
		
		b[4] = new GetBall(this);
		b[3] = new Shoot(this);
		
		b[2] = new Avoid(this); 
		
		b[1] = new TravelPoints(this,pts);
		
		b[0] = new Shutdown(this);
		
		this.setArbitrator(new Arbitrator(b,false));
		
	}

	
	// Highest priority
	// 1. Localize
	// 2. Move to Ball Launcher -- once done localize
	// 3. Move to specific point to get ball? 
	// --Assume the robot now has a ball-- 
	// 4. Move to shooting position  -- once done localize
	// 5. Fire 
	// 6. Avoid to target
	// 7. Shutdown
	// Lowest Priority
	
}
