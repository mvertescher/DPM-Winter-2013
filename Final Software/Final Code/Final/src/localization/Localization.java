package localization;

/**
 * Trivial interface for a localization routine.
 * 
 * @author Matthew Vertescher
 *
 */
public interface Localization {

	/**
	 * The robot is assumed to be in a safe position to localize. 
	 * This method will return once the robot is localized.
	 */
	public void localize();
	
}
