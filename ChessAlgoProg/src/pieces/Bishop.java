package pieces;

import board.Board;
import board.Square;

public class Bishop extends BoardPiece {

	public Bishop(Color color, Board board) {
		super(color, board);
	}

	public Bishop(Color color, Board board, Square square) {
		super(color, board, square);
	}

	public String toString() {
		return super.toString() + "B";
	}

	@Override
	public boolean CanMoveTo(Square square) {
		int x = this.location.GetX();
		int y = this.location.GetY();
		int x2 = square.GetX();
		int y2 = square.GetY();
		
		if (x == x2)
			return false;
		if (Math.abs(x2 - x) == Math.abs(y2 - y)) {
			int dir = -1;
			int length = Math.abs(x2-x);
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
			for(int i=0;i<length;i++){
				if(tmp.GetNeighbor(dir)==null){
					System.out.println("No neighbor!");
					return false;
				}
					
				if(tmp.GetNeighbor(dir).Contains()!=null){
					System.out.println("Square is occupied!");
					return false;
				}
				tmp=tmp.GetNeighbor(dir);
			}
			System.out.println("Returning true");
			return true;
		}
		return false;
	}
}
