package com.graphsearch.core;

import java.util.ArrayList;

public class Path implements Comparable{
	

	private String[] vertices;
	private int totalLength;
	private int currentVertIndex;
	
	public Path(int maxVertices, String initialVert) {
		vertices = new String[maxVertices];
		vertices[0] = initialVert;
		currentVertIndex = 0;
		totalLength = 0;
	}
	
	public Path(Path initialPath) {
		this.totalLength = initialPath.getTotalLength();
		this.currentVertIndex = initialPath.getCurrentVertIndex();
		
		String[] verts = initialPath.getVertices();
		vertices = new String[verts.length];
		
		for (int index = 0; index < verts.length; index++) {
			vertices[index] = verts[index];
		}
	}
	
	public void addVertice(String vert, int length) {
		vertices[currentVertIndex + 1] = vert;
		currentVertIndex++;
		totalLength += length;
	}
	
	public String getCurrentVertice() {
		return vertices[currentVertIndex];
	}
	
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
	public int compareTo(Object arg0) {
		Path otherPath = (Path)arg0;
		int result = totalLength - otherPath.getTotalLength();
		return result;
	}
}
