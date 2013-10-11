package com.pathfinding.agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

import com.pathfinding.models.Gameboard;

public class AStarAgent implements IAgent {
	
	private Point currentLocation;
	private PriorityQueue<Node> fringe;
	private Gameboard board;
	private Point target;

	public AStarAgent(Gameboard board) {
		this.board = board;
		fringe = new PriorityQueue<Node>();
		
		for (int x = 0; x < board.board.length; x++) {
			for (int y = 0; y < board.board[0].length; y++) {
				if (board.board[x][y].isStart) {
					currentLocation = new Point(x,y);
				} else if (board.board[x][y].isTarget) {
					target = new Point(x,y);
				}
			}
		}
		
		Node start = new Node();
		start.path.add(currentLocation);
		start.distanceCovered = 0;
		start.distanceLeft = Point.distance(currentLocation.x, currentLocation.y, target.x, target.y);
		board.board[currentLocation.x][currentLocation.y].isSelected = true;
		
		addNeighborsToFringe(start);
	}
	
	@Override
	public Gameboard Step() {
		Node minNode = fringe.poll();
		double minWeight = minNode.distanceCovered + minNode.distanceLeft;
		for (Node n : fringe) {
			double curWeight = n.distanceCovered + n.distanceLeft;
			
			if (curWeight < minWeight) {
				minNode = n;
				minWeight = curWeight;
			}
		}
		
		if (board.board[minNode.path.get(minNode.path.size()-1).x][minNode.path.get(minNode.path.size()-1).y].isSelected) {
			Step();
		} else {
			currentLocation = minNode.path.get(minNode.path.size()-1);
			board.board[currentLocation.x][currentLocation.y].isSelected = true;
			
			highlightPath(minNode);
			addNeighborsToFringe(minNode);
		}
		
		return board;
	}
	
	private void addNeighborsToFringe(Node n) {
		Point location = n.path.get(n.path.size()-1);
		ArrayList<Point> newFringe = new ArrayList<Point>();
		if (location.x > 0 && 
				!board.board[location.x-1][location.y].isOccupied &&
				!board.board[location.x-1][location.y].isSelected) {
			newFringe.add(new Point(location.x-1, location.y));
			board.board[location.x-1][location.y].inFringe = true;
		}
		
		if (location.x < board.board.length - 1 &&
				 !board.board[location.x+1][location.y].isOccupied &&
				 !board.board[location.x+1][location.y].isSelected) {
			newFringe.add(new Point(location.x+1, location.y));
			board.board[location.x+1][location.y].inFringe = true;
		}
		
		if (location.y > 0 &&
				 !board.board[location.x][location.y-1].isOccupied &&
				 !board.board[location.x][location.y-1].isSelected) {
			newFringe.add(new Point(location.x, location.y-1));
			board.board[location.x][location.y-1].inFringe = true;
		}
		
		if (location.y < board.board[0].length - 1 &&
				 !board.board[location.x][location.y+1].isOccupied &&
				 !board.board[location.x][location.y+1].isSelected) {
			newFringe.add(new Point(location.x, location.y+1));
			board.board[location.x][location.y+1].inFringe = true;
		}
		
		fringe.addAll(n.forkNode(newFringe));
	}
	
	private void highlightPath(Node n) {
		int width = board.board.length;
		int height = board.board[0].length;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board.board[x][y].inPath = false;
			}
		}
		
		for (Point p : n.path) {
			board.board[p.x][p.y].inPath = true;
		}
	}

	private class Node implements Comparable{
		double distanceLeft;
		double distanceCovered;
		ArrayList<Point> path;
		
		public Node() {
			path = new ArrayList<Point>();
		}
		
		public Node(Node n) {
			this.distanceLeft = n.distanceLeft;
			this.distanceCovered = n.distanceCovered;
			path = new ArrayList<Point>();
			
			for (Point p : n.path) {
				this.path.add(p);
			}
		}
		
		public ArrayList<Node> forkNode(ArrayList<Point> inNodes) {
			ArrayList<Node> outNodes = new ArrayList<Node>();
			
			for (Point p : inNodes) {
				Node newNode = new Node(this);
				newNode.path.add(p);
				newNode.distanceCovered ++;
				newNode.distanceLeft = Point.distance(p.x, p.y, target.x, target.y);
				outNodes.add(newNode);
			}
			
			return outNodes;
		}
		
		@Override
		public int compareTo(Object oNode) {
			Node otherNode = (Node)oNode;
			double thisAStarValue = distanceCovered + distanceLeft;
			double otherAStarValue = otherNode.distanceCovered + otherNode.distanceLeft;
			
			if (thisAStarValue < otherAStarValue) {
				return -1;
			} else if (thisAStarValue > otherAStarValue) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	@Override
	public void Run() {
		while (!board.board[target.x][target.y].isSelected && fringe.size() > 0) {
			Step();
		}
	}
}
