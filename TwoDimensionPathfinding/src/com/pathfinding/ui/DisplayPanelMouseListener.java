package com.pathfinding.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.pathfinding.models.Tile;


public class DisplayPanelMouseListener implements MouseListener {

	private PathfindingDemo frame;
	private Tile displayPanel;
	
	public DisplayPanelMouseListener(PathfindingDemo frame, Tile displayPanel) {
		this.frame = frame;
		this.displayPanel = displayPanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (frame.isMouseDown()) {
			switch (frame.selectorSelectorType()) {
				case Clear:
					displayPanel.isTarget = false;
					displayPanel.isOccupied = false;
					displayPanel.isStart = false;
					break;
				case Wall:
					displayPanel.isTarget = false;
					displayPanel.isOccupied = true;
					displayPanel.isStart = false;
					break;
				case Target:
					displayPanel.isTarget = true;
					displayPanel.isOccupied = false;
					displayPanel.isStart = false;
					break;
				case Start:
					displayPanel.isTarget = false;
					displayPanel.isOccupied = false;
					displayPanel.isStart = true;
					break;
				default:
					break;
			}
		} else {
			displayPanel.isHighlighted = true;
		}
		
		frame.repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		displayPanel.isHighlighted = false;
		
		if (frame.isMouseDown()) { 
			if(frame.selectorSelectorType() == SelectorType.Target) {
				displayPanel.isTarget = false;
			} else if (frame.selectorSelectorType() == SelectorType.Start) {
				displayPanel.isStart = false;
			}
		}
		
		frame.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		frame.setMouseDown(true);
		
		switch (frame.selectorSelectorType()) {
			case Clear:
				displayPanel.isTarget = false;
				displayPanel.isOccupied = false;
				displayPanel.isStart = false;
				break;
			case Wall:
				displayPanel.isTarget = false;
				displayPanel.isOccupied = true;
				displayPanel.isStart = false;
				break;
			case Target:
				frame.clearTargetTile();
				displayPanel.isTarget = true;
				displayPanel.isOccupied = false;
				displayPanel.isStart = false;
				break;
			case Start:
				frame.clearStartTile();
				displayPanel.isTarget = false;
				displayPanel.isOccupied = false;
				displayPanel.isStart = true;
				break;
			default:
				break;
		}
		
		frame.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		frame.setMouseDown(false);
	}

}
