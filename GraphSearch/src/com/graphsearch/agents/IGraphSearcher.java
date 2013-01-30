package com.graphsearch.agents;

import com.graphsearch.core.Edge;
import com.graphsearch.core.Path;

public interface IGraphSearcher {
	public Path SearchGraph(Edge[] graph, String start, String target, int k);
}
