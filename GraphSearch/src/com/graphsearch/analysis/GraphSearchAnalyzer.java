package com.graphsearch.analysis;

import com.graphsearch.agents.DepthLimitedA;
import com.graphsearch.agents.IGraphSearcher;
import com.graphsearch.core.Edge;
import com.graphsearch.core.Path;
import com.graphsearch.generation.GraphGenerator;

public class GraphSearchAnalyzer {

	public static void main(String[] args) {
		Edge[] graph = GraphGenerator.GenerateTestGraph();
		IGraphSearcher searcher = new DepthLimitedA();
		
		long beforeTime = System.nanoTime();
		Path resultPath = searcher.SearchGraph(graph, "S", "G", 6);
		long afterTime = System.nanoTime();
		
		System.out.println("Time in nanoseconds: " + (afterTime - beforeTime));
		System.out.println("Shortest Path: " + resultPath.getPath());
		System.out.println("Weight: " + resultPath.getTotalLength());
	}

}
