package com.graphsearch.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import com.graphsearch.core.Edge;
import com.graphsearch.core.Node;
import com.graphsearch.core.Path;

public class DepthLimitedA implements IGraphSearcher {

	@Override
	public Path SearchGraph(Edge[] graph, String start, String target, int k) {
		HashMap<String, Node> hashGraph = buildNodeHash(graph);
		
		PriorityQueue<Path> fringe = new PriorityQueue<Path>();
		fringe.add(new Path(k, start));
		
		while (fringe.size() > 0) {
			Path curPath = fringe.poll();
			
			if (curPath.getCurrentVertice().equalsIgnoreCase(target)) {
				return curPath;
			}
			
			if (curPath.getCurrentVertIndex() < k - 1) {
				Node curNode = hashGraph.get(curPath.getCurrentVertice());
				ArrayList<Path> paths = curPath.branch(curNode.edges);
				fringe.addAll(paths);
			}
		}
		
		return null;
	}
	
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
