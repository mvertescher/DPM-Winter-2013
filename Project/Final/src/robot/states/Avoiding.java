package robot.states;

import java.util.ArrayList;
import java.util.Collection;

import lejos.geom.Line;
import lejos.geom.Rectangle;
import lejos.nxt.Sound;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.AstarSearchAlgorithm;
import lejos.robotics.pathfinding.FourWayGridMesh;
import lejos.robotics.pathfinding.Node;
import lejos.robotics.pathfinding.Path;
import robot.CompassDirection;
import robot.Robot;

/**
 * Avoiding.java
 * 
 * Represents the avoiding state.
 * 
 * @author Matthew Vertescher
 * 
 */
public class Avoiding implements State {

	private StateID id;
	private Robot robot;
	private Node[][] nodes;
	private AstarSearchAlgorithm aStar;
	private double goalX = 0, goalY = 0;
	
	/**
	 * Constructor
	 * @param r
	 */
	public Avoiding(Robot r) {
		this.robot = r;
		this.id = StateID.AVOIDING;
		this.aStar = new AstarSearchAlgorithm();
		this.createNodes();
	}
	
	/**
	 * Simple avoid for testing
	 */
	public void simpleAvoid() {
		double initX = robot.getX();
		double initY = robot.getY();
		
		robot.turnToPoint(initX+10,initY);
		robot.moveImmediatelyBy(10);
		robot.turnToPoint(initX+10,initY+10);		
	}
	
	
	/**
	 * Creates the map of nodes
	 */
	private void createNodes() {	
		nodes = new Node[12][12]; 

		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				nodes[i][j] = new Node((30*i-15), (30*j-15));
			}
		}
		
		Node n = null;
		for (int i = 0; i < 12; i++) { 
			for (int j = 0; j < 12; j++) { 
				n = nodes[i][j];
				
				if (i != 0)
					n.addNeighbor(nodes[i-1][j]);
				if (j != 0)
					n.addNeighbor(nodes[i][j-1]);
				if (i != 11)
					n.addNeighbor(nodes[i+1][j]);
				if (j != 11)
					n.addNeighbor(nodes[i][j+1]);			
			}
		}
	}
	
	
	/**
	 * Travel to a node, the zero-zero node is in the bottom left of the map
	 * @param i, the x coordinate node
	 * @param j, the y coordinate node
	 */
	public void goToNode(int i, int j) {
		float finalX = nodes[i][j].x, finalY = nodes[i][j].y;
		
		robot.turnToPoint(finalX, finalY);
		
		robot.moveForwardUntilStop();
		
		while (Math.abs(finalX - robot.getX()) > 1 || Math.abs(finalY - robot.getY()) > 1) { 
			snooze(30);
		}
		robot.stopMotors();
		Sound.beep();
		snooze(5000);
	}
	
	/*
	public void travelPathTest() {
		Path p = new Path();
		p.add(new Waypoint(45,45));
		p.add(new Waypoint(75,75));
		this.travelPath(p);
	}*/
	
	/**
	 * Travel a node to node path
	 * @param p
	 */
	public void travelPath(Path p) {	
		for (Waypoint w: p) 
			this.travel(w.x,w.y);
			//travelling.travelTo((int)w.x,(int)w.y);
	}
	
	
	/**
	 * Travel the optimal path between points
	 * @param finalX
	 * @param finalY
	 */
	public void travelOptimalPath(double finalX, double finalY) {
		goalX = finalX;
		goalY = finalY;
		
		double initX = robot.getX(), initY = robot.getY();
		
		Node start = getClosestNode(initX,initY);
		Node end = getClosestNode(finalX,finalY);
		
		Path optPath = getOptimalPath(start,end);
		
		this.travelPath(optPath);
		
		Sound.beepSequenceUp();
		snooze(1000);
	}
	
	/**
	 * Travel the optimal path, but with the ability to skip the first node.
	 * Used when the robot is assumed to be located at the first node.
	 * @param finalX
	 * @param finalY
	 * @param atFirstNode
	 */
	public void travelOptimalPath(double finalX, double finalY, boolean atFirstNode) {
		if (atFirstNode) {
			Path optPath = this.getOptimalPath(this.getCurrentNode(),getClosestNode(finalX,finalY));
			
			// If no path is found move backwards and try again
			if (optPath == null) {
				Sound.buzz();
				robot.moveImmediatelyBy(-30);
				optPath = this.getOptimalPath(this.getCurrentNode(),getClosestNode(finalX,finalY));
			}
			
			// Remove the current node from the path
			//TODO: Not Working, try to remove optPath(0);
			if (optPath.contains(this.getCurrentNode()))
				optPath.remove(this.getCurrentNode());
			
			this.travelPath(optPath);
		}
		else if (!atFirstNode)
			this.travelOptimalPath(finalX,finalY);
	}
	
	
	/**
	 * Returns the closest node to a point
	 * @param x
	 * @param y
	 * @return Node
	 */
	private Node getClosestNode(double x, double y) {
		int xInt = (int) x;
		int yInt = (int) y;
		int xDiv = ((xInt + 30) / 30);
		int yDiv = ((yInt + 30) / 30);
		
		return nodes[xDiv][yDiv];
	}
	
	/**
	 * Returns the closest node to the robot
	 * @return current node 
	 */
	private Node getCurrentNode() {
		return this.getClosestNode(robot.getX(),robot.getY());
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
	 * Maps an obstacle by removing a node
	 * @param x
	 * @param y
	 */
	public void mapObstacleAtPoint(double x, double y) {
		if (x > -25 && x < 325 && y > -25 && y < 325) {
			this.removeNode(this.getClosestNode(x,y));
			//Sound.playTone(2600, 500);
			Sound.twoBeeps();
			snooze(2000);
			Sound.twoBeeps();                                           
		}	
	}                                         
	
	/**
	 * Maps an obstacle at a particular node
	 * @param i
	 * @param j
	 */
	public void mapObstacleAtNode(int i, int j) {
		this.removeNode(i,j);
	}
	
	/**
	 * Removes a node in an adjacent tile
	 * @param cmpdir
	 */
	private void removeAdjacentNode(CompassDirection cmpdir) {
		//Node cur = this.getClosestNode(robot.getX(), robot.getY());
		switch (cmpdir) {
			case NORTH:
				this.mapObstacleAtPoint(robot.getX(),robot.getY()+20);
				break;
			case SOUTH:
				this.mapObstacleAtPoint(robot.getX(),robot.getY()-20);
				break;
			case EAST:
				this.mapObstacleAtPoint(robot.getX()+20,robot.getY());
				break;
			case WEST:
				this.mapObstacleAtPoint(robot.getX()-20,robot.getY());
				break;
			default:
				break;
				
		}
		//Sound.twoBeeps();
		
	}

	/**
	 * Removes a node from the grid by emptying its neighbors 
	 * @param i
	 * @param j
	 */
	private void removeNode(int i, int j) {
		this.removeNode(nodes[i][j]);

	}
	

	/**
	 * Removes a node from the grid by emptying its neighbors
	 * @param n
	 */
	private void removeNode(Node n) {
		ArrayList<Node> c = (ArrayList<Node>) n.getNeighbors();
		//for (Node a : c)
			//n.removeNeighbor(a);
		c.clear();
		
	}
	
	
	/** 
	 * Watchful travel method
	 * @param x
	 * @param y
	 */
	private void travel(double x, double y) {

		robot.turnToPoint(x, y);
		
		robot.moveForwardUntilStop();
		
		int usDistance = robot.getLeftUltrasonicDistance();
		while (Math.abs(x - robot.getX()) > 1 || Math.abs(y - robot.getY()) > 1) { 
			usDistance = robot.getLeftUltrasonicDistance();
			if (robot.getLeftUltrasonicDistance() < 15) {
				robot.stopMotors();
				snooze(1000);
				
				//usDistance = robot.getLeftUltrasonicDistance();
				//double theta = robot.getTheta();
				//this.mapObstacleAtPoint(robot.getX() + (usDistance*Math.sin(Math.toRadians(theta))), robot.getY() + (usDistance*Math.cos(Math.toRadians(theta))));
				
				this.removeAdjacentNode(robot.getDirection());
				snooze(500);
				
				Node curnode = this.getCurrentNode();
				robot.backupToPoint(curnode.x,curnode.y);
				//Sound.beep();
				snooze(1000);
				
				
				// Works
				//this.removeAdjacentNode(robot.getDirection());
				

				//this.travelOptimalPath(goalX,goalY);
				//if (curnode.getNeighbors() != null)
					this.travelOptimalPath(goalX,goalY,true); //true
				//else 
					//Sound.buzz();
				
				
				continue;
			}
			snooze(30);
		}
		robot.stopMotors();
	}
	
	
	
	
	
	
	/**
	 * Sleep the thread for some milliseconds
	 * @param milliseconds
	 */
	private void snooze(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	@Override
	public StateID getStateID() {
		return id;
	}
	
	

	
	/*
	public void createMap() {
		
		Line[] lines = new Line[22];
		
		int index = 0;
		for (int i = -30; i < 330; i = i + 30) {
			lines[index] = new Line(i,-30,i,330);
			index++;
			lines[index] = new Line(-30,i,330,i);
			index++;
		}
		
		Rectangle border = new Rectangle(-30,-30,360,360);
		
		
		LineMap lineMap = new LineMap(lines,border);
		
		FourWayGridMesh mesh = new FourWayGridMesh(lineMap,30,0);
	
	}*/
	
	
}