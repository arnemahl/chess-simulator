package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import board.Board;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 7738834825109023568L;
	private int width = 800;
	private int height = 600;
	private ViewBoard viewBoard;
	private Board board;

	public MainWindow() {
		super("Chess");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Makes sure it can be closed
		setLayout(new BorderLayout()); // Layout of the main window
		setMinimumSize(new Dimension(600, 450));
		setResizable(true);
		setSize(width, height);
		setLocationRelativeTo(getRootPane()); // Sets the location of the window

		board = new Board();
		viewBoard = new ViewBoard(board);
		board.SetViewBoard(viewBoard);
		board.InitBoard();
		
		JPanel dummy = new JPanel(new FlowLayout());
		viewBoard
				.setPreferredSize(new Dimension(height * 2 / 3, height * 2 / 3));
		dummy.add(viewBoard);

		this.add(dummy, BorderLayout.CENTER);

	}

	@Override
	public void validate() {
		super.validate();
		this.height = this.getHeight();
		this.width = this.getWidth();
		if (viewBoard != null) {
			viewBoard.setPreferredSize(new Dimension(this.height * 2 / 3,
					this.height * 2 / 3));
		}
	}
}
