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
		int x = this.location.GetX();
		int y = this.location.GetY();
		int x2 = square.GetX();
		int y2 = square.GetY();
		int dir = -1;
		int length = 0;
		if (square.Contains() != null)
			if (square.Contains().GetColor() == this.color)
				return false;

		// If queen moves as rook, reuse rook CanMove
		if (x == x2 || y == y2) {
			if (x != x2 && y != y2)
				return false;
			if (x == x2) {
				if (y2 > y)
					dir = 6;
				else if (y2 < y)
					dir = 1;
				if (dir < 0)
					return false;
				length = Math.abs(y2 - y);
			} else if (y == y2) {
				if (x2 < x)
					dir = 3;
				else if (x2 > x)
					dir = 4;
				if (dir < 0)
					return false;
				length = Math.abs(x2 - x);
			}
			Square tmp = this.location;
			for (int i = 0; i < length; i++) {
				if (tmp.GetNeighbor(dir) == null) {
					return false;
				}
				if (i + 1 == length) {
					return this.CheckMoveForCheck(square);
				}
				if (tmp.GetNeighbor(dir).Contains() != null) {
					return false;
				}
				tmp = tmp.GetNeighbor(dir);
			}
		}
		// If Queen does not move as rook, check if it moves as Bishop and reuse
		// Bishops CanMove
		else {
			if (x == x2)
				return false;
			if (Math.abs(x2 - x) == Math.abs(y2 - y)) {
				dir = -1;
				length = Math.abs(x2 - x);
				Square tmp = this.location;
				if (x2 < x) {
					if (y2 < y)
						dir = 0;
					else
						dir = 5;
				} else {
					if (y2 < y)
						dir = 2;
					else
						dir = 7;
				}
				for (int i = 0; i < length; i++) {
					if (tmp.GetNeighbor(dir) == null) {
						return false;
					}
					if (i + 1 == length) {
						return this.CheckMoveForCheck(square);
					}
					if (tmp.GetNeighbor(dir).Contains() != null) {
						return false;
					}
					tmp = tmp.GetNeighbor(dir);
				}
				return this.CheckMoveForCheck(square);
			}
		}
		return false;
	}
}
