package pieces;

import board.Board;
import board.Square;

public abstract class BoardPiece {
	// A BoardPiece can have two colors
	public enum Color {
		black, white
	};

	protected Board board;
	protected Square location;
	protected Color color;
	protected boolean hasMoved;

	public BoardPiece(Color color, Board board) {
		this.color = color;
		this.board = board;
		hasMoved = false;
	}

	public BoardPiece(Color color, Board board, Square square) {
		this.color = color;
		this.board = board;
		this.location = square;
		hasMoved = false;
	}

	// This method will strictly place the piece to the square without
	// performing any checking.
	protected void PlaceTo(Square square) {
		this.location.PlacePiece(null);
		square.PlacePiece(this);
		this.location = square;
	}

	// This method will check if the move causes check, then reverse the move
	protected boolean CheckMoveForCheck(Square square) {
		boolean retVal = false;
		BoardPiece tmpPiece = square.Contains();
		Square tmpSquare = this.location;
		this.PlaceTo(square);
		retVal = !board.IsCheck(this.color);

		this.PlaceTo(tmpSquare);
		if (tmpPiece != null)
			tmpPiece.PlaceTo(square);

		return retVal;
	}

	// If piece is asked to move to a square, it will first check if it can
	// move, and place itself in the square if it can
	public boolean MoveTo(Square square) {
		if (this.CanMoveTo(square)) {
			this.PlaceTo(square);
			this.hasMoved = true;
			return true;
		}
		return false;
	}

	// This will check if this piece can move to the given square
	public abstract boolean CanMoveTo(Square square);

	// This will check if the piece can move at all by performing a series of
	// CanMoveTo operations
	public boolean CanMove() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (i != this.location.GetX() || j != this.location.GetY())
					if (this.CanMoveTo(board.GetSquare(i, j)))
						return true;

		return false;
	}

	/* Some getters and setters */
	public Color GetColor() {
		return color;
	}

	public Square GetLocation() {
		return location;
	}

	public void HasMoved(boolean bool) {
		hasMoved = bool;
	}

	public boolean HasMoved() {
		return hasMoved;
	}

	public String toString() {
		String col = null;
		switch (this.color) {
		case white:
			col = "w";
			break;
		case black:
			col = "b";
			break;
		}
		return col;
	}
}
