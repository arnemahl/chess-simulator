package pieces;

import algorithm.Code;
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
	public boolean MoveTo(Square square) {
		if (square.Contains() != null)
			return super.MoveTo(square);
		int dir = -1;
		int opDir = -1;
		if (this.location.GetY() - square.GetY() == 1) {
			dir = 1;
			opDir = 6;
		} else if (square.GetY() - this.location.GetY() == 1) {
			dir = 6;
			opDir = 1;
		}
		if (dir == -1)
			return super.MoveTo(square);
		if(!this.CanGoTo(square))
			return false;
		if (square.GetX() != this.location.GetX())
			if (square.GetNeighbor(opDir).Contains() != null)
				if (square.GetNeighbor(opDir).Contains().toString()
						.contains("p")) {
					BoardPiece tmpPiece = square.GetNeighbor(opDir).Contains();
					Square tmpSquare = this.location;
					square.GetNeighbor(opDir).PlacePiece(null);

					if (!this.MoveWithoutCheck(square)) {
						square.GetNeighbor(opDir).PlacePiece(tmpPiece);
						tmpSquare.PlacePiece(this);
						return false;
					}
					int moveInt[] = new int[4];
					moveInt[0] = square.GetNeighbor(opDir).GetX();
					moveInt[1] = square.GetNeighbor(opDir).GetY();
					moveInt[2] = square.GetX();
					moveInt[3] = square.GetY();
					board.GetViewBoard().Move(moveInt);
					this.PlaceTo(square);
					this.hasMoved = true;
					return true;
				}
		return super.MoveTo(square);
	}

	@Override
	public boolean CanGoTo(Square square) {
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
					return true;
				}
				if (y == 6 && y2 == 4) {
					if (this.location.GetNeighbor(1).Contains() != null)
						return false;
					if (square.Contains() != null)
						return false;
					if(x2==x)
						return true;
				}
			} else if (y2 == y - 1 && Math.abs(x2 - x) == 1) {
				if (square.Contains() != null) 
					if (square.Contains().GetColor() != this.color)
						return true;
				
				// Checking for en passant
				if (square.Contains() == null) {
					Code code = board.GetLastMove();
					if (code == null)
						return false;
					int coor[] = code.GetCoordinates();
					if (coor[0] == coor[2] && coor[0] == square.GetX())
						if (coor[1] == square.GetY() - 1
								&& coor[3] == square.GetY() + 1)
							if (board.GetSquare(coor[2], coor[3]).Contains() != null)
								if (board.GetSquare(coor[2], coor[3])
										.Contains().toString().equals("bp"))
									return true;
				}
			}
		} else if (this.color == BoardPiece.Color.black) {
			if (x2 == x) {
				if (y2-y == 1) {
					if (square.Contains() != null)
						return false;
					return true;
				}
				if (y == 1 && y2 == 3) {
					if (this.location.GetNeighbor(6).Contains() != null)
						return false;
					if (square.Contains() != null)
						return false;
					if(x2==x)
						return true;
				}
			} else if (y2 == y + 1 && Math.abs(x2 - x) == 1) {
				if (square.Contains() != null) 
					if (square.Contains().GetColor() != this.color)
						return true;
				
				// Checking for en passant
				if (square.Contains() == null) {
					Code code = board.GetLastMove();
					if (code == null)
						return false;
					int coor[] = code.GetCoordinates();
					if (coor[0] == coor[2] && coor[0] == square.GetX())
						if (coor[1] == square.GetY() + 1
								&& coor[3] == square.GetY() - 1)
							if (board.GetSquare(coor[2], coor[3]).Contains() != null)
								if (board.GetSquare(coor[2], coor[3])
										.Contains().toString().equals("wp"))
									return true;
				}
			}
		}
		return false;
	}
}
