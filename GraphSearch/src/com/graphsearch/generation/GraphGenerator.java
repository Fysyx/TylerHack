package com.graphsearch.generation;

import com.graphsearch.core.Edge;

public class GraphGenerator {
	
	public static Edge[] GenerateTestGraph() {
		Edge[] graph = new Edge[10];
		
		graph[0] = new Edge();
		graph[0].length = 2;
		graph[0].start = "S";
		graph[0].end = "A";
		
		graph[1] = new Edge();
		graph[1].length = 3;
		graph[1].start = "S";
		graph[1].end = "B";
		
		graph[2] = new Edge();
		graph[2].length = 3;
		graph[2].start = "A";
		graph[2].end = "C";
		
		graph[3] = new Edge();
		graph[3].length = 1;
		graph[3].start = "B";
		graph[3].end = "C";
		
		graph[4] = new Edge();
		graph[4].length = 3;
		graph[4].start = "B";
		graph[4].end = "D";
		
		graph[5] = new Edge();
		graph[5].length = 1;
		graph[5].start = "C";
		graph[5].end = "D";
		
		graph[6] = new Edge();
		graph[6].length = 3;
		graph[6].start = "C";
		graph[6].end = "E";
		
		graph[7] = new Edge();
		graph[7].length = 2;
		graph[7].start = "D";
		graph[7].end = "F";
		
		graph[8] = new Edge();
		graph[8].length = 2;
		graph[8].start = "E";
		graph[8].end = "G";
		
		graph[9] = new Edge();
		graph[9].length = 1;
		graph[9].start = "F";
		graph[9].end = "G";
		
		return graph;
	}
}
