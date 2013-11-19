package pieces;

import board.Board;
import board.Square;

public class King extends BoardPiece {

	public King(Color color, Board board) {
		super(color, board);
	}

	public King(Color color, Board board, Square square) {
		super(color, board, square);
	}

	public String toString() {
		return super.toString() + "K";
	}

	@Override
	public boolean CanMoveTo(Square square) {
		int x = this.location.GetX();
		int y = this.location.GetY();
		int x2 = square.GetX();
		int y2 = square.GetY();
		if (x == x2 && y == y2)
			return false;
		if (x > x2 - 2 && x < x2 + 2 && y > y2 - 2 && y < y2 + 2)
			return true;
		return false;
	}
}
