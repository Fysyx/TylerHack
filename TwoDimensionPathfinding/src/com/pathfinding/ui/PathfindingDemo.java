package com.pathfinding.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import com.pathfinding.agents.AStarAgent;
import com.pathfinding.agents.DijkstraAgent;
import com.pathfinding.agents.IAgent;
import com.pathfinding.models.Gameboard;

public class PathfindingDemo extends JFrame{

	private DisplayTile[][] displayBoard;
	private JPanel displayBoardContainer;
	private Point boardSize;
	private SelectorType selectorType;
	private boolean isMouseDown;
	private Gameboard board;
	private IAgent agent;
	private Timer tmr;
	
	public static void main(String[] args) {
		new PathfindingDemo();
	}
	
	public PathfindingDemo() {
		this.setSize(1000, 755);
		this.setResizable(false);
		this.setTitle("Pathfinding Demo - Tyler Hackbarth");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		tmr = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				agent.Step();
				
				if (checkGameOver()) { 
					tmr.stop();
					agent = null;
				}
			}
			
		});
		
		JButton aStarBtn = new JButton("A* Search");
		aStarBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (agent == null) {
					clearSearchFromBoard();
					agent = new AStarAgent(board);
					tmr.start();
				}
			}
			
		});
		aStarBtn.setLocation(775, 500);
		aStarBtn.setSize(100, 25);
		this.add(aStarBtn);
		
		JButton dijBtn = new JButton("Dijkstra's");
		dijBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (agent == null) {
					clearSearchFromBoard();
					agent = new DijkstraAgent(board);
					tmr.start();
				}
			}
			
		});
		dijBtn.setLocation(775, 465);
		dijBtn.setSize(100, 25);
		this.add(dijBtn);
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (agent == null) {
					clearSearchFromBoard();
				}
			}
			
		});
		clearBtn.setLocation(775, 550);
		clearBtn.setSize(100, 25);
		this.add(clearBtn);
		
		JButton clearAllBtn = new JButton("Clear All");
		clearAllBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (agent == null) {
					clearBoard();
				}
			}
			
		});
		clearAllBtn.setLocation(775, 585);
		clearAllBtn.setSize(100, 25);
		this.add(clearAllBtn);
		
		JButton randBtn = new JButton("Randomize");
		randBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Random r = new Random();
				
				if (agent == null) {
					int width = boardSize.x;
					int height = boardSize.y;
					clearSearchFromBoard();
					
					for (int x = 0; x < width; x++) {
						for (int y = 0; y < height; y++) {
							board.board[x][y].isOccupied = false;
							if (r.nextDouble() < 0.35 && !board.board[x][y].isStart && !board.board[x][y].isTarget) {
								board.board[x][y].isOccupied = true;
							}
						}
					}
				}
			}
			
		});
		randBtn.setLocation(775, 630);
		randBtn.setSize(100, 25);
		this.add(randBtn);
		
		selectorType = SelectorType.Start;
		isMouseDown = false;
		
		boardSize = new Point(15, 15);
		board = new Gameboard(boardSize.x, boardSize.y);
		initDisplayBoard();
		initCursorSelectorMenu();
		
		this.add(displayBoardContainer);
		
		this.setLayout(null);
		this.setVisible(true);
	}
	
	private void initDisplayBoard() {
		int width = boardSize.x;
		int height = boardSize.y;
		
		displayBoardContainer = new JPanel();
		displayBoardContainer.setSize(700, 700);
		displayBoardContainer.setLocation(10, 10);
		GridLayout boardLayout = new GridLayout(height, width);
		displayBoardContainer.setLayout(boardLayout);
		
		displayBoard = new DisplayTile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				displayBoard[x][y] = createDisplayTile(x, y);
				displayBoardContainer.add(displayBoard[x][y]);
			}
		}
	}
	
	private void initCursorSelectorMenu() {
		JRadioButton startRadBtn = new JRadioButton("Start");
		startRadBtn.setMnemonic(KeyEvent.VK_1);
		startRadBtn.setActionCommand("start");
		startRadBtn.setSelected(true);
		startRadBtn.setFont(new Font("Helvetica", Font.PLAIN, 26));
		startRadBtn.addActionListener(new CursorSelectorListener(this));
		
		JRadioButton targetRadBtn = new JRadioButton("Target");
		targetRadBtn.setMnemonic(KeyEvent.VK_2);
		targetRadBtn.setActionCommand("target");
		targetRadBtn.setSelected(true);
		targetRadBtn.setFont(new Font("Helvetica", Font.PLAIN, 26));
		targetRadBtn.addActionListener(new CursorSelectorListener(this));
		
		JRadioButton wallRadBtn = new JRadioButton("Wall");
		wallRadBtn.setMnemonic(KeyEvent.VK_3);
		wallRadBtn.setActionCommand("wall");
		wallRadBtn.setFont(new Font("Helvetica", Font.PLAIN, 26));
		wallRadBtn.addActionListener(new CursorSelectorListener(this));
		
		JRadioButton clearRadBtn = new JRadioButton("Clear");
		clearRadBtn.setMnemonic(KeyEvent.VK_4);
		clearRadBtn.setActionCommand("clear");
		clearRadBtn.setFont(new Font("Helvetica", Font.PLAIN, 26));
		clearRadBtn.addActionListener(new CursorSelectorListener(this));
		
		ButtonGroup group = new ButtonGroup();
		group.add(startRadBtn);
		group.add(targetRadBtn);
		group.add(wallRadBtn);
		group.add(clearRadBtn);
		
		JPanel cursorPanel = new JPanel();
		cursorPanel.setSize(100, 300);
		cursorPanel.setLocation(775, 10);
		cursorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		cursorPanel.add(startRadBtn);
		cursorPanel.add(targetRadBtn);
		cursorPanel.add(wallRadBtn);
		cursorPanel.add(clearRadBtn);
		
		this.add(cursorPanel);
	}
	
	private DisplayTile createDisplayTile(int x, int y) {
		DisplayTile curTile = new DisplayTile(board.board[x][y]);
		curTile.setBorder(BorderFactory.createLineBorder(Color.black));
		curTile.setBackground(Color.white);
		curTile.addMouseListener(new DisplayPanelMouseListener(this, board.board[x][y]));
		return curTile;
	}
	
	public void clearTargetTile() {
		int width = boardSize.x;
		int height = boardSize.y;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board.board[x][y].isTarget = false;
			}
		}
	}
	
	public void clearStartTile() {
		int width = boardSize.x;
		int height = boardSize.y;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board.board[x][y].isStart = false;
			}
		}
	}
	
	public boolean checkGameOver() {
		int width = boardSize.x;
		int height = boardSize.y;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (board.board[x][y].isTarget && board.board[x][y].isSelected) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void clearSearchFromBoard() {
		int width = boardSize.x;
		int height = boardSize.y;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board.board[x][y].isSelected = false;
				board.board[x][y].inFringe = false;
				board.board[x][y].inPath = false;
			}
		}
	}
	
	public void clearBoard() {
		int width = boardSize.x;
		int height = boardSize.y;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board.board[x][y].isSelected = false;
				board.board[x][y].inFringe = false;
				board.board[x][y].inPath = false;
				board.board[x][y].isStart = false;
				board.board[x][y].isTarget = false;
				board.board[x][y].isOccupied = false;
			}
		}
	}
	
	public SelectorType selectorSelectorType() {
		return selectorType;
	}
	
	public void setSelectorType(SelectorType selectorType) {
		this.selectorType = selectorType;
	}
	
	public boolean isMouseDown() {
		return isMouseDown;
	}
	
	public void setMouseDown(boolean isMouseDown) {
		this.isMouseDown = isMouseDown;
	}
	
	private class CursorSelectorListener implements ActionListener {

		PathfindingDemo frame;
		
		public CursorSelectorListener(PathfindingDemo frame) {
			this.frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (actionCommand.equalsIgnoreCase("start")) {
				frame.setSelectorType(SelectorType.Start);
			} else if (actionCommand.equalsIgnoreCase("target")) {
				frame.setSelectorType(SelectorType.Target);
			} else if (actionCommand.equalsIgnoreCase("wall")) {
				frame.setSelectorType(SelectorType.Wall);
			} else if (actionCommand.equals("clear")) {
				frame.setSelectorType(SelectorType.Clear);
			}
		}
		
	}
}
