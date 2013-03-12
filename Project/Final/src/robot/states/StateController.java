package robot.states;

import robot.Robot;

/**
 * StateController.java
 * 
 * Organizes states, a wrapper class
 * 
 * @author Matthew Vertescher
 *
 */
public class StateController {
	
	private Robot robot;
	private State[] states;
	
	/**
	 * Constructor
	 * @param Robot r
	 */
	public StateController(Robot r) {
		this.robot = r;
		this.states = new State[3];
		this.states[StateID.LOCALIZING.ordinal()] = new Localizing(this); 
		this.states[StateID.TRAVELLING.ordinal()] = new Travelling(r); 
		this.states[StateID.AVOIDING.ordinal()] = new Avoiding(r); 
	}

	/**
	 * Returns the controllers robot
	 * @return robot
	 */
	public Robot getRobot() {
		return robot;
	}
	
	/**
	 * Returns the localization state 
	 * @return Localizing
	 */
	public Localizing localizingState() {
		return (Localizing) this.getState(StateID.LOCALIZING);
	}
	
	/**
	 * Returns the traveling state 
	 * @return Travelling
	 */
	public Travelling travellingState() {
		return (Travelling) this.getState(StateID.TRAVELLING);
	}
	
	/**
	 * Returns the avoiding state 
	 * @return Avoiding
	 */
	public Avoiding avoidingState() {
		return (Avoiding) this.getState(StateID.AVOIDING);
	}
	
	/**
	 * Private auxiliary method to retrieve a state based upon its id
	 * @param sid
	 * @return state
	 */
	private State getState(StateID sid) {
		return states[sid.ordinal()];
	}
	
}
