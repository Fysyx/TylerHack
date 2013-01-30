package com.graphsearch.core;

import java.util.ArrayList;

/**
 * Represents a path through the graph. Contains vertices and the total length
 * of the path.
 * 
 * @author Tyler Hackbarth
 */
public class Path implements Comparable {
	// The vertices that this path travels through
	private String[] vertices;
	// The total length or weight of this vertice
	private int totalLength;
	// The current vertice in vertices
	private int currentVertIndex;

	/**
	 * Constructs a Path object
	 * 
	 * @param maxVertices
	 *            - the max number of vertices in this path
	 * @param initialVert
	 *            - the initial vertice that this path starts from
	 */
	public Path(int maxVertices, String initialVert) {
		vertices = new String[maxVertices];
		vertices[0] = initialVert;
		currentVertIndex = 0;
		totalLength = 0;
	}

	/**
	 * Constructs a new path to resemble the given path
	 * 
	 * @param initialPath
	 *            - path to clone
	 */
	public Path(Path initialPath) {
		this.totalLength = initialPath.getTotalLength();
		this.currentVertIndex = initialPath.getCurrentVertIndex();

		String[] verts = initialPath.getVertices();
		vertices = new String[verts.length];

		for (int index = 0; index < verts.length; index++) {
			vertices[index] = verts[index];
		}
	}

	/**
	 * Adds the given vertice to this path
	 * 
	 * @param vert
	 *            - vertice to add
	 * @param length
	 *            - length of the edge traveled to the new vertice
	 */
	public void addVertice(String vert, int length) {
		vertices[currentVertIndex + 1] = vert;
		currentVertIndex++;
		totalLength += length;
	}

	/**
	 * Branches the current path by creating a new path for each of the given
	 * edges and then adding the edge to its respective path.
	 * 
	 * @param edges
	 *            - edges to expand for
	 * @return - A list of paths expanded for their individual edges
	 */
	public ArrayList<Path> branch(ArrayList<Edge> edges) {
		ArrayList<Path> clones = new ArrayList<Path>(edges.size());

		for (int curClone = 0; curClone < edges.size(); curClone++) {
			clones.add(new Path(this));
			Edge curEdge = edges.get(curClone);

			if (vertices[currentVertIndex].equalsIgnoreCase(curEdge.start)) {
				clones.get(curClone).addVertice(curEdge.end, curEdge.length);
			} else {
				clones.get(curClone).addVertice(curEdge.start, curEdge.length);
			}
		}

		return clones;
	}

	public String getCurrentVertice() {
		return vertices[currentVertIndex];
	}

	public String[] getVertices() {
		return vertices;
	}

	public int getTotalLength() {
		return totalLength;
	}

	public int getCurrentVertIndex() {
		return currentVertIndex;
	}

	public String getPath() {
		String path = "";

		for (int index = 0; index < vertices.length; index++) {
			path += vertices[index] + " ";
		}

		return path;
	}

	@Override
	/**
	 * Compares two paths based on total length or weight.
	 * @param oPath 
	 * 			- the other path to compare to this path
	 * @return - A negative value if this path is shorter than the given path, 
	 * 			 a positive value if this path is larger, and 0 if the paths are 
	 * 			 the same length or weight.
	 */
	public int compareTo(Object oPath) {
		Path otherPath = (Path) oPath;
		int result = totalLength - otherPath.getTotalLength();
		return result;
	}
}
