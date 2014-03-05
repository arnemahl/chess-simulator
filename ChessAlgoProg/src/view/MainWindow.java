package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import menu.Menu;
import menu.TopPanel;
import algorithm.Code;
import board.Board;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 7738834825109023568L;
	private int width = 800;
	private int height = 600;
	private ViewBoard viewBoard;
	private Board board;
	private JPanel boardContain;
	private Menu menu;
	private TopPanel topPanel;

	public MainWindow() {
		super("Chess");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Makes sure it can be closed
		setLayout(new BorderLayout()); // Layout of the main window
		setMinimumSize(new Dimension(600, 500));
		setResizable(true);
		setSize(width, height);
		setLocationRelativeTo(getRootPane()); // Sets the location of the window
		
		board = new Board();
		topPanel = new TopPanel(this,board);
		menu = new Menu(this);
		viewBoard = new ViewBoard(board);
		board.SetViewBoard(viewBoard);
		board.SetTopPanel(topPanel);

		boardContain = new JPanel(new FlowLayout());
		int size = height;
		if (width < height)
			size = width;
		viewBoard.setPreferredSize(new Dimension(size * 6 / 10, size * 6 / 10));
		menu.setPreferredSize(new Dimension(size * 6 / 10, size * 6 / 10));
		boardContain.add(menu);

		this.add(topPanel, BorderLayout.NORTH);
		this.add(boardContain, BorderLayout.CENTER);

	}
	
	// Will be called when closing the window
	public void ClosingWindow(){
		topPanel.clearAlgorithms();
	}

	// This will be used by the algorithm to try to move
	public boolean TryToMoveAlgo(Code code){
		return viewBoard.TryToMoveAlgo(code);
	}
	// This method will be called when the game should be started
	public void StartGame() {
		board.InitBoard();
		boardContain.removeAll();
		boardContain.add(viewBoard);
		this.validate();
		topPanel.initGame();
		topPanel.startTimer();
	}
	
	public void setNames(String st1, String st2){
		topPanel.setNames(st1,st2);
	}
	public boolean createAlgorithmWhite(String path){
		return topPanel.createAlgorithmWhite(path);
	}
	public boolean createAlgorithmBlack(String path){
		return topPanel.createAlgorithmBlack(path);
	}
	public void clearAlgorithms(){
		topPanel.clearAlgorithms();
	}
	
	public void disableMove(){
		viewBoard.disableMove();
	}
	public void enableMove(){
		viewBoard.enableMove();
	}

	@SuppressWarnings("deprecation")
	public void gotoMainMenu() {
		boardContain.removeAll();
		boardContain.validate();
		boardContain.add(menu);
		menu.resetMenu();
		boardContain.hide();
		boardContain.show();
	}

	@Override
	public void validate() {
		super.validate();
		this.height = this.getHeight();
		this.width = this.getWidth();
		int size = height;
		if (width < height)
			size = width;
		if (viewBoard != null) {
			viewBoard.setPreferredSize(new Dimension(size * 6 / 10,
					size * 6 / 10));
		}
		if (menu != null) {
			menu.setPreferredSize(new Dimension(size * 6 / 10, size * 6 / 10));
		}
	}
}
