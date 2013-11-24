package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import algorithm.Code;
import board.Board;

public class ViewBoard extends JPanel {
	private static final long serialVersionUID = -1848722005310418932L;

	private ViewSquare squares[][];
	private Board board;
	private boolean hasClicked = false;
	private ViewSquare clickedPanel = null;

	public ViewBoard(Board board) {
		super(new GridLayout(8, 8));
		this.board = board;
	}

	public void InitBoard() {
		this.removeAll();
		squares = new ViewSquare[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[j][i] = new ViewSquare(this, j, i);

				if ((i + j) % 2 == 0) {
					squares[j][i].setBackground(new Color(246, 210, 170));
				} else {
					squares[j][i].setBackground(new Color(55, 45, 28));
				}
				this.add(squares[j][i]);
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (j == 1)
					squares[i][j].SetContent(new ViewPiece(
							"bin/Resources/bp.png"));
				if (j == 6)
					squares[i][j].SetContent(new ViewPiece(
							"bin/Resources/wp.png"));
				if (i == 0 || i == 7) {
					if (j == 0)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/bR.png"));
					if (j == 7)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/wR.png"));
				} else if (i == 1 || i == 6) {
					if (j == 0)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/bH.png"));
					if (j == 7)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/wH.png"));
				} else if (i == 2 || i == 5) {
					if (j == 0)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/bB.png"));
					if (j == 7)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/wB.png"));
				} else if (i == 3) {
					if (j == 0)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/bQ.png"));
					if (j == 7)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/wQ.png"));
				} else if (i == 4) {
					if (j == 0)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/bK.png"));
					if (j == 7)
						squares[i][j].SetContent(new ViewPiece(
								"bin/Resources/wK.png"));
				}
			}
		}
	}

	public void Move(int coordinates[]) {
		if (clickedPanel != null)
			clickedPanel.SetClicked(false);
		squares[coordinates[2]][coordinates[3]]
				.SetContent(squares[coordinates[0]][coordinates[1]].Contains());
		squares[coordinates[0]][coordinates[1]].SetContent(null);
		this.repaint();
		board.CheckBoard();
	}

	public void SetClicked(boolean click) {
		hasClicked = click;
		if (!click) {
			if (clickedPanel != null) {
				clickedPanel.SetClicked(false);
			}
		}
	}

	public boolean IsClicked() {
		return hasClicked;
	}

	public void SetClickedSquare(ViewSquare vs) {
		clickedPanel = vs;
	}

	public Board GetBoard() {
		return board;
	}

	public void HighLight(Code now, Code prev) {
		if (prev != null) {
			int prevCoor[] = prev.GetCoordinates();
			squares[prevCoor[0]][prevCoor[1]].HighLight(false);
			squares[prevCoor[2]][prevCoor[3]].HighLight(false);
		}
		if (now != null) {
			int nowCoor[] = now.GetCoordinates();
			squares[nowCoor[0]][nowCoor[1]].HighLight(true);
			squares[nowCoor[2]][nowCoor[3]].HighLight(true);
		}
	}

	public void TryToMove(ViewSquare vs) {
		int coordinates[] = new int[4];
		if (clickedPanel.Contains() == null)
			return;
		int from[] = clickedPanel.GetCoordinates();
		int to[] = vs.GetCoordinates();
		String s = from[0] + "," + from[1] + "x" + to[0] + "," + to[1];
		Code c = new Code(s);
		coordinates[0] = from[0];
		coordinates[1] = from[1];
		coordinates[2] = to[0];
		coordinates[3] = to[1];
		if (board.Move(c)) {
			this.Move(coordinates);
			board.PrintBoard();
		}
	}

	public void MakeQueen(int x, int y) {
		if (squares[x][y].Contains() == null) {
			return;
		}
		System.out.println("Will make queen!");
		if (y == 0)
			squares[x][y].SetContent(new ViewPiece("bin/Resources/wQ.png"));
		if (y == 7)
			squares[x][y].SetContent(new ViewPiece("bin/Resources/bQ.png"));
		squares[x][y].Contains().repaint();
	}
}
