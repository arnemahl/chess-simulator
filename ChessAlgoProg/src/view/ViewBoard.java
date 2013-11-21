package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import algorithm.Code;
import board.Board;

public class ViewBoard extends JPanel implements MouseListener {
	private static final long serialVersionUID = -1848722005310418932L;

	private ViewSquare squares[][];
	private Board board;
	private boolean hasclicked1 = false;
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
					squares[j][i].setBackground(new Color(71, 32, 15));
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
		squares[coordinates[2]][coordinates[3]]
				.SetContent(squares[coordinates[0]][coordinates[1]].Contains());
		squares[coordinates[0]][coordinates[1]].SetContent(null);
		this.invalidate();
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent me) {
		System.out.println("Clicked mouse");
		if (!hasclicked1) { // clicked first pic
			hasclicked1 = true;
			clickedPanel = (ViewSquare) me.getSource();
			System.out.println("hasclicked1 = " + hasclicked1);
		} else { // clicked second pic
			hasclicked1 = false;
			int coordinates[] = new int[4];
			int from[] = clickedPanel.GetCoordinates();
			ViewSquare tmp = (ViewSquare) me.getSource();
			int to[] = tmp.GetCoordinates();
			String s = from[0] + "," + from[1] + "x" + to[0] + "," + to[1];
			Code c = new Code(s);
			coordinates[0] = from[0];
			coordinates[1] = from[1];
			coordinates[2] = to[0];
			coordinates[3] = to[1];
			if (board.Move(c)) {
				System.out.println("Lets make a move");
				System.out.println(coordinates[0] + " " + coordinates[1] + " "
						+ coordinates[2] + " " + coordinates[3]);
				this.Move(coordinates);
				board.PrintBoard();
			}
		}
	}

}
