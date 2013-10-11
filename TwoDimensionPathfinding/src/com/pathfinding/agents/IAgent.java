package com.pathfinding.agents;

import java.awt.Point;

import com.pathfinding.models.Gameboard;

public interface IAgent {
	public Gameboard Step();
	public void Run();
}
