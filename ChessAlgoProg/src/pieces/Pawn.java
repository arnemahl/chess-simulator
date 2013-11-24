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

		if (square.Contains() != null)
			if (square.Contains().GetColor() == this.color)
				return false;

		if (this.color == BoardPiece.Color.white) {
			if (x2 == x) {
				if (y - y2 == 1) {
					if (square.Contains() != null)
						return false;
					return this.CheckMoveForCheck(square);
				}
				if (y == 6 && y2 == 4) {
					if (this.location.GetNeighbor(1).Contains() != null)
						return false;
					if (square.Contains() != null)
						return false;
					return this.CheckMoveForCheck(square);
				} else if (y2 == y - 1) {
					if (square.Contains() != null)
						return false;
					return this.CheckMoveForCheck(square);
				}
			} else if (y2 == y - 1 && Math.abs(x2 - x) == 1) {
				if (square.Contains() != null) {
					if (square.Contains().GetColor() != this.color)
						return this.CheckMoveForCheck(square);
				}
			}
		} else if (this.color == BoardPiece.Color.black) {
			if (x2 == x) {
				if (y2 - y == 1) {
					if (square.Contains() != null)
						return false;
					return this.CheckMoveForCheck(square);
				}
				if (y == 1 && y2 == 3) {
					if (this.location.GetNeighbor(6).Contains() != null)
						return false;
					if (square.Contains() != null)
						return false;
					return this.CheckMoveForCheck(square);
				}
			} else if (y2 == y + 1 && Math.abs(x2 - x) == 1) {
				if (square.Contains() != null) {
					if (square.Contains().GetColor() != this.color)
						return this.CheckMoveForCheck(square);
				}
			}
		}
		return false;
	}
}
