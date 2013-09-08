package odometry.correction;

/**
 * Windrose.java
 * 
 * Give information based upon the robots position needed for correction. 
 * 
 * @author Matthew Vertescher
 *
 */
public enum Windrose {
	NORTH(0,true,false), NORTHEAST(45,false,true), EAST(90,true,false), SOUTHEAST(135,false,true), 
	SOUTH(180,true,false), SOUTHWEST(225,false,true), WEST(270,true,false), NORTHWEST(315,false,true), OTHER(0,false,false);
	
	private int theta;
	private boolean grid;
	private boolean diagonal;
	
	private Windrose(int t, boolean g, boolean d) {
		this.theta = t;
		this.grid = g;
		this.diagonal = d;
	}
	
	public int getTheta() {
		return this.theta;
	}
	
	public boolean isGrid() {
		return grid;
	}
	
	public boolean isDiagonal() {
		return diagonal;
	}
	
	
	
	
}
