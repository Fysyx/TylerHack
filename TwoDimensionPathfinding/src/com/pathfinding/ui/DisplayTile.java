package com.pathfinding.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.pathfinding.models.Tile;

public class DisplayTile extends JPanel {
	public static Color Fringe_Color = Color.cyan;
	public static Color Selected_Color = Color.red;
	public static Color Base_Color = Color.white;
	public static Color Occupied_Color = Color.black;
	public static Color Target_Color = Color.blue;
	public static Color Start_Color = Color.green;
	public static Color Path_Color = Color.yellow;
	
	public static Color Highlight_Border_Color = Color.orange;
	public static Color Not_Highlight_Border_Color = Color.black;
	
	private Tile tile;
	
	public DisplayTile(Tile tile) {
		this.tile = tile;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (tile.isOccupied) {
			this.setBackground(Occupied_Color);
		} else if (tile.inPath) {
			this.setBackground(Path_Color);
		} else if (tile.isSelected) {
			this.setBackground(Selected_Color);
		} else if (tile.inFringe) {
			this.setBackground(Fringe_Color);
		} else {
			this.setBackground(Base_Color);
		}
		
		if (tile.isTarget) {
			this.setBorder(BorderFactory.createLineBorder(Target_Color, 5));
		} else if(tile.isStart) {
			this.setBorder(BorderFactory.createLineBorder(Start_Color, 5));
		} else if (tile.isHighlighted) { 
			this.setBorder(BorderFactory.createLineBorder(Highlight_Border_Color));
		} else {
			this.setBorder(BorderFactory.createLineBorder(Not_Highlight_Border_Color));
		}
		
		super.paintComponent(g);
	}
}
