package pieces;

import board.Board;
import board.Square;

public abstract class BoardPiece {
	public enum Color {
		black, white
	};

	protected Board board;
	protected Color color;
	protected Square location;

	public BoardPiece(Color color, Board board) {
		this.color = color;
		this.board = board;
	}

	public BoardPiece(Color color, Board board, Square square) {
		this.color = color;
		this.board = board;
		this.location = square;
	}

	// Will place the piece to this square if it is unoccupied or have a
	// different color
	protected boolean PlaceTo(Square square) {
		BoardPiece tmpPiece = square.Contains();
		Square tmpSquare = this.location;
		// If the piece has the same color, this move is invalid
		if (tmpPiece != null)
			if (tmpPiece.GetColor() == this.GetColor())
				return false;

		location.PlacePiece(null);
		square.PlacePiece(this);
		this.location = square;

		if (board.IsCheck(this.color)) {
			square.PlacePiece(tmpPiece);
			tmpSquare.PlacePiece(this);
			this.location = tmpSquare;
			return false;
		}
		return true;
	}

	// Piece will be responsible to check if it can move to this location, and
	// move if it can. Otherwise return false
	// This method must be overwritten by all the different piece classes as
	// they move differently
	public boolean MoveTo(Square square) {
		if (this.CanMoveTo(square))
			return this.PlaceTo(square);
		return false;
	}

	// This will check without performing the move
	public abstract boolean CanMoveTo(Square square);

	/* Some getters */
	public Color GetColor() {
		return color;
	}

	public Square GetLocation() {
		return location;
	}

	// Will notify the piece that it has been taken
	public void IsTaken() {
		location = null;
		board.Taken(this);
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
