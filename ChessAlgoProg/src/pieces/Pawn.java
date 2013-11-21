package pieces;

import board.Board;
import board.Square;

public class Pawn extends BoardPiece {

	public Pawn(Color color, Board board) {
		super(color, board);
	}

	public Pawn(Color color, Board board, Square square) {
		super(color, board, square);
	}

	public String toString() {
		return super.toString() + "p";
	}

	@Override
	public boolean CanMoveTo(Square square) {
		int x = this.location.GetX();
		int y = this.location.GetY();
		int x2 = square.GetX();
		int y2 = square.GetY();

		if (this.color == BoardPiece.Color.white) {
			if (x2 == x) {
				if (y == 6 && y2 < 6 && y2 > 3)
					return true;
				else if(y2==y-1)
					return true;
			} else if (y2 == y - 1 && Math.abs(x2 - x) == 1) {
				if (square.Contains() != null) {
					if (square.Contains().GetColor() != this.color)
						return true;
				}
			}
		} else if (this.color == BoardPiece.Color.black) {
			if (x2 == x) {
				if (y == 1 && y2 < 4 && y2 > 1)
					return true;
				else if(y2==y+1)
					return true;
			} else if (y2 == y + 1 && Math.abs(x2 - x) == 1) {
				if (square.Contains() != null) {
					if (square.Contains().GetColor() != this.color)
						return true;
				}
			}
		}
		return false;
	}
}
