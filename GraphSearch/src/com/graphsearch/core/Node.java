package com.graphsearch.core;

import java.util.ArrayList;

public class Node {
	public String id;
	public ArrayList<Edge> edges;
	
	public Node(String id) {
		this.id = id;
		edges = new ArrayList<Edge>();
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
}
