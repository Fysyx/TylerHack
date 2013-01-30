package com.graphsearch.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import com.graphsearch.core.Edge;
import com.graphsearch.core.Node;
import com.graphsearch.core.Path;

/**
 * Agent for performing a depth limited A* search.
 * 
 * @author Tyler Hackbarth
 */
public class DepthLimitedA implements IGraphSearcher {

	@Override
	/**
	 * Performs the depth limited A* search.
	 * graph - graph to search through
	 * start - starting node
	 * target - node to search for
	 * k - depth to limit search by
	 * return - A path object containing the optimal route from the 
	 * 			start to the target vertice. Returns null if no path 
	 * 			can be found.
	 */
	public Path SearchGraph(Edge[] graph, String start, String target, int k) {
		HashMap<String, Node> hashGraph = buildNodeHash(graph);

		// Sorted by the paths total weight (ascending)
		PriorityQueue<Path> fringe = new PriorityQueue<Path>();
		fringe.add(new Path(k, start));

		while (fringe.size() > 0) {
			// Grab the shortest path
			Path curPath = fringe.poll();

			// Check if target is in the shortest path
			if (curPath.getCurrentVertice().equalsIgnoreCase(target)) {
				return curPath;
			}

			// Expand current path along the edges connected to it
			if (curPath.getCurrentVertIndex() < k - 1) {
				Node curNode = hashGraph.get(curPath.getCurrentVertice());
				ArrayList<Path> paths = curPath.branch(curNode.edges);
				fringe.addAll(paths);
			}
		}

		return null;
	}

	/**
	 * Builds a hashmap containing Node objects. Each node represents a vertice
	 * and is labeled with a string.
	 * 
	 * @param graph
	 *            - graph to create a node hashmap for
	 * @return - hashmap with a node object for each vertice in the graph
	 */
	private HashMap<String, Node> buildNodeHash(Edge[] graph) {
		HashMap<String, Node> hashGraph = new HashMap<String, Node>();

		for (int edge = 0; edge < graph.length; edge++) {
			String startID = graph[edge].start;
			String endID = graph[edge].end;

			if (hashGraph.containsKey(startID)) {
				hashGraph.get(startID).addEdge(graph[edge]);
			} else {
				hashGraph.put(startID, new Node(startID));
			}

			if (hashGraph.containsKey(endID)) {
				hashGraph.get(endID).addEdge(graph[edge]);
			} else {
				hashGraph.put(endID, new Node(endID));
			}
		}

		return hashGraph;
	}

}
