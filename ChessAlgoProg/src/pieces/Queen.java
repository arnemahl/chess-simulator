package pieces;

import board.Board;
import board.Square;

public class Queen extends BoardPiece {

	public Queen(Color color, Board board) {
		super(color, board);
	}

	public Queen(Color color, Board board, Square square) {
		super(color, board, square);
	}

	public String toString() {
		return super.toString() + "Q";
	}

	@Override
	public boolean CanMoveTo(Square square) {
		return false;
	}
}
