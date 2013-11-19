package pieces;

import board.Board;
import board.Square;

public class Rook extends BoardPiece {

	public Rook(Color color, Board board) {
		super(color, board);
	}

	public Rook(Color color, Board board, Square square) {
		super(color, board, square);
	}

	public String toString() {
		return super.toString() + "R";
	}

	@Override
	public boolean CanMoveTo(Square square) {
		return false;
	}
}
