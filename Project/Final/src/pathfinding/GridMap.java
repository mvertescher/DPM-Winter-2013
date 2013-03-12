package pathfinding;

import java.util.ArrayList;

import lejos.nxt.Sound;
import lejos.robotics.pathfinding.Node;

/**
 * GridMap.java 
 * 
 * Hardcoded map of nodes to represent the DPM 9x9 tile square grid
 * 
 * @author Matthew Vertescher
 *
 */
public class GridMap {
	
	private Node nodes[][];
	
	/**
	 * Basic constructor
	 */
	public GridMap() {
		this.createGridNodesMap();
	}
	
	/**
	 * Creates the map of nodes
	 */
	private void createGridNodesMap() {	
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
	 * Returns the closest node to a point
	 * @param x
	 * @param y
	 * @return Node
	 */
	public Node getClosestNode(double x, double y) {
		int xInt = (int) x;
		int yInt = (int) y;
		int xDiv = ((xInt + 30) / 30);
		int yDiv = ((yInt + 30) / 30);
		
		return nodes[xDiv][yDiv];
	}
	
	/**
	 * Maps an obstacle by removing a node
	 * @param x
	 * @param y
	 */
	public void mapObstacleAtPoint(double x, double y) {
		if (x > -25 && x < 325 && y > -25 && y < 325) {
			this.removeNode(this.getClosestNode(x,y));
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
		c.clear();
	}
	
}