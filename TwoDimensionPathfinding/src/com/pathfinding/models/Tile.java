package com.pathfinding.models;

/**
 * 'Struct' representing a tile on the board
 * @author Tyler Hackbarth
 */
public class Tile {
	public int x;
	public int y;
	public boolean inFringe;
	public boolean isSelected;
	public boolean isOccupied;
	public boolean isStart;
	public boolean isTarget;
	public boolean isHighlighted;
	public boolean inPath;
}
