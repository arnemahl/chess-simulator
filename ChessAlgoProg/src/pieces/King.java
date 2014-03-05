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
	public boolean MoveTo(Square square) {
		int x = this.location.GetX();
		int y = this.location.GetY();
		int x2 = square.GetX();
		int y2 = square.GetY();
		if (Math.abs(y2 - y) < 2 && Math.abs(x2 - x) < 2)
			return super.MoveTo(square);
		if (this.CanMoveTo(square)) {
			if (y == 0 || y == 7) {
				if (x2 > x) {
					this.PlaceTo(square);
					this.hasMoved = true;
					square.GetNeighbor(4).Contains().HasMoved(true);
					square.GetNeighbor(4).Contains()
							.PlaceTo(square.GetNeighbor(3));
					int coordinates[] = new int[4];
					coordinates[0] = square.GetNeighbor(4).GetX();
					coordinates[1] = square.GetNeighbor(4).GetY();
					coordinates[2] = square.GetNeighbor(3).GetX();
					coordinates[3] = square.GetNeighbor(3).GetY();
					this.board.GetViewBoard().Move(coordinates);
					return true;
				} else if (x2 < x) {
					this.PlaceTo(square);
					this.hasMoved = true;
					square.GetNeighbor(3).GetNeighbor(3).Contains()
							.HasMoved(true);
					square.GetNeighbor(3).GetNeighbor(3).Contains()
							.PlaceTo(square.GetNeighbor(4));
					int coordinates[] = new int[4];
					coordinates[0] = square.GetNeighbor(3).GetNeighbor(3)
							.GetX();
					coordinates[1] = square.GetNeighbor(3).GetNeighbor(3)
							.GetY();
					coordinates[2] = square.GetNeighbor(4).GetX();
					coordinates[3] = square.GetNeighbor(4).GetY();
					this.board.GetViewBoard().Move(coordinates);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean CanGoTo(Square square) {
		int x = this.location.GetX();
		int y = this.location.GetY();
		int x2 = square.GetX();
		int y2 = square.GetY();
		if (x == x2 && y == y2)
			return false;
		if(Math.abs(x-x2)>3||Math.abs(y-y2)>2)
			return false;
		if (x > x2 - 2 && x < x2 + 2 && y > y2 - 2 && y < y2 + 2) {
			if (square.Contains() != null)
				if (square.Contains().GetColor() == this.color)
					return false;
			return true;
		}
		// Rocade
		if(this.HasMoved())
			return false;
		if (board.IsCheck(this.color))
			return false;
		if (!this.hasMoved) {
			if (y2 == 0 || y2 == 7) {
				if (x2 == 6) {
					if (this.location.GetNeighbor(4) == null)
						return false;
					if (this.location.GetNeighbor(4).Contains() != null)
						return false;
					if (!this.MoveWithoutCheck(location.GetNeighbor(4)))
						return false;
					if (this.location.GetNeighbor(4).GetNeighbor(4) == null)
						return false;
					if (this.location.GetNeighbor(4).GetNeighbor(4).Contains() != null)
						return false;
					if (!this.MoveWithoutCheck(location.GetNeighbor(4)
							.GetNeighbor(4)))
						return false;
					if (this.location.GetNeighbor(4).GetNeighbor(4)
							.GetNeighbor(4) == null)
						return false;
					if (this.location.GetNeighbor(4).GetNeighbor(4)
							.GetNeighbor(4).Contains() == null)
						return false;
					if (this.location.GetNeighbor(4).GetNeighbor(4)
							.GetNeighbor(4).Contains().HasMoved())
						return false;
					return true;
				}
				else if(x2==2){
					if (this.location.GetNeighbor(3) == null)
						return false;
					if (this.location.GetNeighbor(3).Contains() != null)
						return false;
					if (!this.MoveWithoutCheck(location.GetNeighbor(3)))
						return false;
					if (this.location.GetNeighbor(3).GetNeighbor(3) == null)
						return false;
					if (this.location.GetNeighbor(3).GetNeighbor(3).Contains() != null)
						return false;
					if (!this.MoveWithoutCheck(location.GetNeighbor(3)
							.GetNeighbor(3)))
						return false;
					if (this.location.GetNeighbor(3).GetNeighbor(3)
							.GetNeighbor(3) == null)
						return false;
					if (this.location.GetNeighbor(3).GetNeighbor(3)
							.GetNeighbor(3).Contains() != null)
						return false;
					if(this.location.GetNeighbor(3).GetNeighbor(3).GetNeighbor(3).GetNeighbor(3)==null)
						return false;
					if(this.location.GetNeighbor(3).GetNeighbor(3).GetNeighbor(3).GetNeighbor(3).Contains()==null)
						return false;
					if (this.location.GetNeighbor(3).GetNeighbor(3)
							.GetNeighbor(3).GetNeighbor(3).Contains().HasMoved())
						return false;
					return true;
				}
			}
		}
		return false;
	}
}
