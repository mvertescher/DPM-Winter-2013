package subsumption;

import robot.Robot;
import subsumption.behaviors.Avoid;
import subsumption.behaviors.Localize;
import subsumption.behaviors.Travel;
import subsumption.behaviors.Shutdown;
import lejos.geom.Point;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Attacker extends AbstractBot {
	
	public boolean hasBall = false; 
	public boolean inShootingPosition = false; 
	
	public Attacker(Robot r) {
		super(r); 
		Behavior[] b = new Behavior[4];
		
		b[3] = new Localize(this);
		b[2] = new Travel(this, new Point(40,0));
		b[1] = new Avoid(this); 
		b[0] = new Shutdown();
		
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