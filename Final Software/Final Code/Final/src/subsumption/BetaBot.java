package subsumption;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import robot.Robot;
import subsumption.behaviors.Avoid;
import subsumption.behaviors.Localize;
import subsumption.behaviors.Shoot;
import subsumption.behaviors.BetaTravel;
import subsumption.behaviors.Shutdown;


/**
 * BetaTest.java
 * 
 * Beta test protocol 
 * 
 * @author Matthew Vertescher
 */
public class BetaBot extends AbstractBot  {
		
	/**
	 * Creates a new Beta
	 * @param r
	 */
	public BetaBot(Robot r) {
		super(r); 
		
		this.hasBall = true;
		
		Behavior[] b = new Behavior[5];  
			
		b[4] = new Localize(this, r.firstPoint());
		b[3] = new Shoot(this);
		b[2] = new Avoid(this); 
		//b[1] = new GridTravel(this, r.betaEndPoint());
		b[1] = new BetaTravel(this, r.shootingLocationForBeta());
		b[0] = new Shutdown(this);
		
		
		this.setArbitrator(new Arbitrator(b,true));
		
	}

		
	// Highest priority
	// 1. Localize
	// 2. Shoot 
	// 3. Avoid
	// 4. Travel to end location
	// 5. Travel to shooting location
	// 6. Shutdown
	// Lowest Priority

}
