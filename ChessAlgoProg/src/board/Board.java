package board;

import java.util.ArrayList;

import menu.TopPanel;
import pieces.Bishop;
import pieces.BoardPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import view.ViewBoard;
import algorithm.Code;

public class Board {
	// The squares are stored in a matrix where 0,0 is the UPPER LEFT square of
	// the board. This is the a8 position in chess
	private Square[][] squares;
	private ViewBoard viewBoard;
	private TopPanel topPanel;
	private ArrayList<Code> moves;

	private boolean inPlay;
	private BoardPiece.Color player;

	public Board() {
		moves = new ArrayList<Code>();
		player = BoardPiece.Color.white;
		inPlay = false;

		// Initialize all the squares 8*8
		squares = new Square[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				squares[i][j] = new Square(i, j);
		// Temporary list of squares to make it easier to set the neighbors
		Square[][] tmp = new Square[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (i == 0 || i == 9 || j == 0 || j == 9)
					tmp[i][j] = null;
				else
					tmp[i][j] = squares[i - 1][j - 1];

		// Set the neighbors of all the squares on the board
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				squares[i][j].UpdateNeighbors(tmp[i + 1 - 1][j + 1 - 1],
						tmp[i + 1][j + 1 - 1], tmp[i + 1 + 1][j + 1 - 1],
						tmp[i + 1 - 1][j + 1], tmp[i + 1 + 1][j + 1],
						tmp[i + 1 - 1][j + 1 + 1], tmp[i + 1][j + 1 + 1],
						tmp[i + 1 + 1][j + 1 + 1]);
	}

	// This will initialize a new board and place the pieces on the correct
	// location on the board
	public void InitBoard() {
		moves.clear();
		player = BoardPiece.Color.white;
		inPlay = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j].PlacePiece(null);
			}
		}
		squares[0][0].PlacePiece(new Rook(BoardPiece.Color.black, this,
				squares[0][0]));
		squares[1][0].PlacePiece(new Knight(BoardPiece.Color.black, this,
				squares[1][0]));
		squares[2][0].PlacePiece(new Bishop(BoardPiece.Color.black, this,
				squares[2][0]));
		squares[3][0].PlacePiece(new Queen(BoardPiece.Color.black, this,
				squares[3][0]));
		squares[4][0].PlacePiece(new King(BoardPiece.Color.black, this,
				squares[4][0]));
		squares[5][0].PlacePiece(new Bishop(BoardPiece.Color.black, this,
				squares[5][0]));
		squares[6][0].PlacePiece(new Knight(BoardPiece.Color.black, this,
				squares[6][0]));
		squares[7][0].PlacePiece(new Rook(BoardPiece.Color.black, this,
				squares[7][0]));
		for (int i = 0; i < 8; i++)
			squares[i][1].PlacePiece(new Pawn(BoardPiece.Color.black, this,
					squares[i][1]));

		squares[0][7].PlacePiece(new Rook(BoardPiece.Color.white, this,
				squares[0][7]));
		squares[1][7].PlacePiece(new Knight(BoardPiece.Color.white, this,
				squares[1][7]));
		squares[2][7].PlacePiece(new Bishop(BoardPiece.Color.white, this,
				squares[2][7]));
		squares[3][7].PlacePiece(new Queen(BoardPiece.Color.white, this,
				squares[3][7]));
		squares[4][7].PlacePiece(new King(BoardPiece.Color.white, this,
				squares[4][7]));
		squares[5][7].PlacePiece(new Bishop(BoardPiece.Color.white, this,
				squares[5][7]));
		squares[6][7].PlacePiece(new Knight(BoardPiece.Color.white, this,
				squares[6][7]));
		squares[7][7].PlacePiece(new Rook(BoardPiece.Color.white, this,
				squares[7][7]));
		for (int i = 0; i < 8; i++)
			squares[i][6].PlacePiece(new Pawn(BoardPiece.Color.white, this,
					squares[i][6]));
		player = BoardPiece.Color.white;
		viewBoard.InitBoard();
	}

	public boolean InPlay() {
		return inPlay;
	}
	
	// Used to print the board to the socket
	public String PrintBoardSocket(){
		String printing = "";
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				printing += (squares[i][j].toString());
			}
			printing += ",";
		}
		return printing;
	}

	// Used to check the program. This will print out the board
	public void PrintBoard() {
		String letter = null;
		for (int j = 0; j < 8; j++) {
			switch (j) {
			case 0:
				letter = "8";
				break;
			case 1:
				letter = "7";
				break;
			case 2:
				letter = "6";
				break;
			case 3:
				letter = "5";
				break;
			case 4:
				letter = "4";
				break;
			case 5:
				letter = "3";
				break;
			case 6:
				letter = "2";
				break;
			case 7:
				letter = "1";
				break;
			}
			System.out.print(letter + " ");
			for (int i = 0; i < 8; i++) {
				System.out.print(squares[i][j].toString() + " ");
			}
			System.out.println("");
		}
		System.out.println("   a  b  c  d  e  f  g  h");
	}

	// Used to move the pieces. Takes a Code object as input. Returns true if
	// the move is good
	public boolean Move(Code code) {
		if (code.ValidCode()) {
			if (Move(code.GetCoordinates())) {
				moves.add(code);
				Code prev = null;
				Code now = null;
				if (moves.size() > 1)
					prev = moves.get(moves.size() - 2);
				if (moves.size() > 0)
					now = moves.get(moves.size() - 1);
				viewBoard.HighLight(now, prev);
				return true;
			}
		}
		return false;
	}

	// Setting the viewBoard
	public void SetViewBoard(ViewBoard vb) {
		this.viewBoard = vb;
	}

	// Setting reference to topPanel
	public void SetTopPanel(TopPanel tp) {
		this.topPanel = tp;
		tp.noGame();
	}

	// Used to move the pieces. Takes an int array as input. Returns true if the
	// move is good
	private boolean Move(int[] move) {
		if (this.squares[move[0]][move[1]].Contains() == null)
			return false;
		if (this.player != squares[move[0]][move[1]].Contains().GetColor()) {
			return false;
		}
		return this.squares[move[0]][move[1]].Contains().MoveTo(
				this.squares[move[2]][move[3]]);
	}

	// Return the square at the location
	public Square GetSquare(int x, int y) {
		return squares[x][y];
	}

	/*
	 * This method will run after every move and it will do the following
	 * things; (1)Change all pawns that crossed the borders to Queens (2)Check
	 * if the kings are checked (3)Check if any of the kings are check mated
	 * thus ending the game (4)Check if the next person to make a move can
	 * perform a move or not
	 */
	public void CheckBoard() {
		// First check if a pawn is across the border
		// If yes, turn it into a Queen (no option will be given)
		for (int i = 0; i < 8; i++) {
			if (squares[i][0].Contains() != null) {
				if (squares[i][0].Contains().toString().equals("wp")) {
					squares[i][0].PlacePiece(new Queen(BoardPiece.Color.white,
							this, squares[i][0]));
					viewBoard.MakeQueen(i, 0);
				}
			}
			if (squares[i][7].Contains() != null) {
				if (squares[i][7].Contains().toString().equals("bp")) {
					squares[i][7].PlacePiece(new Queen(BoardPiece.Color.black,
							this, squares[i][7]));
					viewBoard.MakeQueen(i, 7);
				}
			}
		}

		int ki = -1, kj = -1;
		boolean winner = true;
		// Then check if it is check mate for the person that is about to move
		// First we locate the king
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (squares[i][j].Contains() != null) {
					if (player == BoardPiece.Color.white) {
						if (squares[i][j].Contains().toString().equals("wK")) {
							ki = i;
							kj = j;
						}
					}
					if (player == BoardPiece.Color.black) {
						if (squares[i][j].Contains().toString().equals("bK")) {
							ki = i;
							kj = j;
						}
					}
				}
			}
		}
		BoardPiece king = squares[ki][kj].Contains();
		// Then check if the player is checked
		if (this.IsCheck(player)) {
			// Then by performing all possible moves with the king and see
			// if it is still checked
			for (int i = 0; i < 8; i++) {
				if (king.GetLocation().GetNeighbor(i) != null) {
					if (king.CanMoveTo(king.GetLocation().GetNeighbor(i))) {
						winner = false;
					}
				}
			}
			// Then check if there is any valid move with any piece of this
			// color
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (i != ki || j != kj)
						if (squares[i][j].Contains() != null) {
							if (squares[i][j].Contains().GetColor() == player) {
								if (squares[i][j].Contains().CanMove())
									winner = false;
							}
						}
				}
			}
		} else
			winner = false;
		// If we could not find a valid move for player we have a winner
		if (winner) {
			String winnerst = "";
			if (player == BoardPiece.Color.white) {
				winnerst = "black";
			} else {
				winnerst = "white";
			}
			this.inPlay = false;
			topPanel.noGame();
			topPanel.winner(winnerst);
			return;
		}
		// If we did not find a winner, lets check if it is draw
		boolean draw = true;
		if (this.IsCheck(player))
			draw = false;
		if (!this.IsCheck(player)) {
			// We already have the location of the king, we now check the
			// exact same things, but this time our prior knowledge is that
			// the king is not checked
			for (int i = 0; i < 8; i++) {
				if (king.GetLocation().GetNeighbor(i) != null) {
					if (king.CanMoveTo(king.GetLocation().GetNeighbor(i))) {
						draw = false;
					}
				}
			}
			// Then check if there is any valid move with any piece of this
			// color
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (squares[i][j].Contains() != null) {
						if (squares[i][j].Contains().GetColor() == player) {
							if (squares[i][j].Contains().CanMove())
								draw = false;
						}
					}
				}
			}
		}
		if (draw) {
			this.inPlay = false;
			topPanel.noGame();
			topPanel.winner("draw");
			return;
		}
	}

	// Returns the last move. Pawn needs it for checking if en passant
	public Code GetLastMove() {
		if (!moves.isEmpty())
			return moves.get(moves.size() - 1);
		return null;
	}

	// Checks if the given colors king is threatened
	public boolean IsCheck(BoardPiece.Color color) {
		// first locate the king
		int ki = 0;
		int kj = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (color == BoardPiece.Color.white) {
					if (squares[i][j].Contains() != null)
						if (squares[i][j].Contains().toString().equals("wK")) {
							ki = i;
							kj = j;
						}
				} else if (color == BoardPiece.Color.black) {
					if (squares[i][j].Contains() != null)
						if (squares[i][j].Contains().toString().equals("bK")) {
							ki = i;
							kj = j;
						}
				}
			}
		}
		// Then check all the squares surrounding the king
		for (int i = -2; i < 3; i++)
			for (int j = -2; j < 3; j++) {
				if (!(i == 0 && j == 0)) {
					if (!(ki + i < 0 || ki + i > 7 || kj + j < 0 || kj + j > 7))
						if (squares[ki + i][kj + j].Contains() != null)
							if (squares[ki + i][kj + j].Contains().GetColor() != color)
								if (squares[ki + i][kj + j].Contains().CanGoTo(
										squares[ki][kj]))
									return true;
				}
			}
		// Check the diagonals and straight lanes
		Square tmp = squares[ki][kj];
		for (int dir = 0; dir < 8; dir++) {
			tmp = squares[ki][kj].GetNeighbor(dir);
			while (tmp != null) {
				if (tmp.Contains() != null) {
					if (tmp.Contains().GetColor() != color) {
						if (tmp.Contains().CanGoTo(squares[ki][kj]))
							return true;
					} else
						tmp = null;
				}
				if (tmp != null) {
					tmp = tmp.GetNeighbor(dir);
				}
			}
		}
		return false;
	}

	public ViewBoard GetViewBoard() {
		return viewBoard;
	}

	public BoardPiece.Color GetPlayer() {
		return player;
	}

	public void NextPlayer() {
		if (player == BoardPiece.Color.white)
			player = BoardPiece.Color.black;
		else
			player = BoardPiece.Color.white;
		topPanel.nextPlayer();
	}
}