package com.pathfinding.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.pathfinding.agents.AStarAgent;
import com.pathfinding.agents.IAgent;
import com.pathfinding.models.Gameboard;

public class SearchAnalyzer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SearchAnalyzer();
	}
	
	public SearchAnalyzer() {
		IAgent agent = null;
		Gameboard board = null;
		List<ExpVal> testValues = new ArrayList<ExpVal>();
		
		int[] boardSizes = { 20, 40, 60, 80, 100,
							 200, 400, 600, 800, 1000,
							 1200, 1400, 1600, 1800, 2000 };
		
		for (int index = 0; index < boardSizes.length; index++) {
			int curVal = boardSizes[index];
			System.out.println("Testing: " + curVal);
			
			board = new Gameboard(curVal, curVal);
			board.board[0][0].isStart = true;
			board.board[0][curVal-1].isTarget = true;
			
			agent = new AStarAgent(board);
			
			long startTime = System.nanoTime();
			agent.Run();
			long endTime = System.nanoTime();
			
			testValues.add(new ExpVal(curVal, endTime - startTime));
		}
		
		PrintWriter outFile = null;
		
		try {
			outFile = new PrintWriter(new File("data.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (ExpVal val : testValues) {
			outFile.write("" + val.size + "," + val.time + "\n");
		}
		
		outFile.close();
	}
	
	private class ExpVal {
		public int size;
		public long time;
		
		public ExpVal(int size, long time) {
			this.size = size;
			this.time = time;
		}
	}

}
