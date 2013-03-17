package pathfinding;

import lejos.geom.Point;
import lejos.robotics.pathfinding.AstarSearchAlgorithm;
import lejos.robotics.pathfinding.Node;
import lejos.robotics.pathfinding.Path;
import robot.CompassDirection;
import robot.Robot;

/**
 * Pathfinder.java
 * 
 * Class dedicated to finding the optimal path to a point
 * 
 * @author Matthew Vertescher
 *
 */
public class Pathfinder {
	
	private Robot robot;
	private GridMap map;
	private AstarSearchAlgorithm aStar;
	
	public Pathfinder(Robot r) {
		this.robot = r;
		this.map = new GridMap();
		aStar = new AstarSearchAlgorithm();
	}

	/**
	 * Finds the optimal path to a point
	 * @param p
	 * @return Path 
	 */
	public Path getOptimalPathToPoint(Point p) {
		return this.getOptimalPath(this.getCurrentNode(),this.map.getClosestNode(p.x,p.y));
	}
	
	
	/**
	 * Returns the closest node to the robot
	 * @return current node 
	 */
	private Node getCurrentNode() {
		return this.map.getClosestNode(robot.getX(),robot.getY());
	}
	
	
	/**
	 * Returns the optimal path between two points
	 * @param from
	 * @param to
	 * @return Path
	 */
	private Path getOptimalPath(Node from, Node to) {
		return aStar.findPath(from, to);
	}
	
	/**
	 * Removes a node in an adjacent tile
	 * @param cmpdir
	 */
	public void removeAdjacentNode(CompassDirection cmpdir) {
		switch (cmpdir) {
			case NORTH:
				this.map.mapObstacleAtPoint(robot.getX(),robot.getY()+20);
				break;
			case SOUTH:
				this.map.mapObstacleAtPoint(robot.getX(),robot.getY()-20);
				break;
			case EAST:
				this.map.mapObstacleAtPoint(robot.getX()+20,robot.getY());
				break;
			case WEST:
				this.map.mapObstacleAtPoint(robot.getX()-20,robot.getY());
				break;
			default:
				break;
				
		}		
	}
	
}