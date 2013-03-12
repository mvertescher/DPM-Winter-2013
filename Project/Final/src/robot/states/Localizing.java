package robot.states;


/**
 * Localization.java
 * 
 * Initial localization state 
 * 
 * @author Matthew Vertescher
 *
 */
public class Localizing implements State {

	private StateID id; 
	//private Robot robot;
	private StateController sc;
	
	public Localizing(StateController c) {
		this.sc = c;
		this.id = StateID.LOCALIZING;
	}
	
	
	public void begin() {
		ultrasonicLocalization();
		lightLocalization();
	}
	
	private void ultrasonicLocalization() {
		
		
	}
	
	private void lightLocalization() {
		
	}


	@Override
	public StateID getStateID() {
		return id;
	}
	
	
}