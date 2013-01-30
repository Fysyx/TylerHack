package com.graphsearch.core;

import java.util.ArrayList;

/**
 * Object representing a vertice
 * @author Tyler Hackbarth
 */
public class Node {
	// The id of this vertice
	public String id;
	// The edges that link to this vertice
	public ArrayList<Edge> edges;
	
	public Node(String id) {
		this.id = id;
		edges = new ArrayList<Edge>();
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
}
