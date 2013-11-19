package board;

import java.util.ArrayList;

import pieces.Bishop;
import pieces.BoardPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import algorithm.Code;

public class Board {
	// The squares are stored in a matrix where 0,0 is the UPPER LEFT square of
	// the board. This is the a8 position in chess
	private Square[][] squares;
	// The board will know what pieces have been taken
	private ArrayList<BoardPiece> takenWhite;
	private ArrayList<BoardPiece> takenBlack;

	public Board() {
		takenWhite = new ArrayList<BoardPiece>();
		takenBlack = new ArrayList<BoardPiece>();

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
		takenWhite = new ArrayList<BoardPiece>();
		takenBlack = new ArrayList<BoardPiece>();
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

	}

	// Used to check the program. This will print out the board
	public void PrintBoard() {
		String letter = null;
		for (int j = 0; j < 8; j++) {
			switch (j) {
			case 0:
				letter = "h";
				break;
			case 1:
				letter = "g";
				break;
			case 2:
				letter = "f";
				break;
			case 3:
				letter = "e";
				break;
			case 4:
				letter = "d";
				break;
			case 5:
				letter = "c";
				break;
			case 6:
				letter = "b";
				break;
			case 7:
				letter = "a";
				break;
			}
			System.out.print(letter + " ");
			for (int i = 0; i < 8; i++) {
				System.out.print(squares[i][j].toString() + " ");
			}
			System.out.println("");
		}
		System.out.println("   1  2  3  4  5  6  7  8");
	}

	// Used to move the pieces. Takes a Code object as input. Returns true if
	// the move is good
	public boolean Move(Code code) {
		if (code.ValidCode()) {
			return Move(code.GetCoordinates());
		}
		return false;
	}

	// Used to move the pieces. Takes an int array as input. Returns true if the
	// move is good
	private boolean Move(int[] move) {
		if(this.squares[move[0]][move[1]].Contains()==null)
			return false;
		return this.squares[move[0]][move[1]].Contains().MoveTo(
				this.squares[move[2]][move[3]]);
	}

	// This method is called by the pieces when they are taken so the board can
	// be updated
	public void Taken(BoardPiece bp) {
		if (bp.GetColor() == BoardPiece.Color.white) {
			takenWhite.add(bp);
		} else if (bp.GetColor() == BoardPiece.Color.black) {
			takenBlack.add(bp);
		}
	}

	// Checks if the gicen colors king is threathened
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

		// Then go through all squares and check if the piece there can move to
		// the kings square (but only if the piece is of different color
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (squares[i][j].Contains() != null) {
					if (squares[i][j].Contains().GetColor() != color) {
						if (squares[i][j].Contains().CanMoveTo(squares[ki][kj]))
							return true;
					}
				}
			}
		}
		return false;
	}
}