package pieces;

import board.Board;
import board.Square;

public class Knight extends BoardPiece {

	public Knight(Color color, Board board) {
		super(color, board);
	}

	public Knight(Color color, Board board, Square square) {
		super(color, board, square);
	}

	public String toString() {
		return super.toString() + "k";
	}

	@Override
	public boolean CanGoTo(Square square) {
		int x = this.location.GetX();
		int y = this.location.GetY();
		int x2 = square.GetX();
		int y2 = square.GetY();

		if (Math.abs(x2 - x) == 1 && Math.abs(y2 - y) == 2) {
			if (square.Contains() != null)
				if (square.Contains().GetColor() == this.color)
					return false;
			return true;
		} else if (Math.abs(y2 - y) == 1 & Math.abs(x2 - x) == 2) {
			if (square.Contains() != null)
				if (square.Contains().GetColor() == this.color)
					return false;
			return true;
		}
		return false;
	}
}
