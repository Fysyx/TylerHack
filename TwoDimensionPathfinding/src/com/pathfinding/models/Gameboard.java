package com.pathfinding.models;

public class Gameboard {
	public Tile[][] board;
	
	public Gameboard(int width, int height) {
		board = new Tile[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board[x][y] = new Tile();
			}
		}
	}
}
