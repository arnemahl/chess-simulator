package board;

import pieces.BoardPiece;

public class Square {
	// 0-upleft, 1-up, 2-upright, 3-left, 4-right, 5-downleft, 6-down,
	// 7-downright
	private Square[] neighbors;
	private BoardPiece piece;
	private int x;
	private int y;

	public Square(int x, int y) {
		neighbors = new Square[8];
		this.x = x;
		this.y = y;
		piece = null;
	}

	// Neighbor will be null if there is no neighbor to that direction
	public void UpdateNeighbors(Square upleft, Square up, Square upright,
			Square left, Square right, Square downleft, Square down,
			Square downright) {
		neighbors[0] = upleft;
		neighbors[1] = up;
		neighbors[2] = upright;
		neighbors[3] = left;
		neighbors[4] = right;
		neighbors[5] = downleft;
		neighbors[6] = down;
		neighbors[7] = downright;
	}

	// Returns the BoardPiece of the square, null if it is unoccupied
	public BoardPiece Contains() {
		return piece;
	}

	// Will place the BoardPiece on this square
	public void PlacePiece(BoardPiece piece) {
		this.piece = piece;
	}

	// Will return the coordinates of the squares
	public int GetX() {
		return this.x;
	}

	public int GetY() {
		return this.y;
	}

	public void MoveInto(BoardPiece bp) {
		if (this.Contains() != null) {
			piece.IsTaken();
		}
		this.piece = bp;
	}
	public Square GetNeighbor(int i){
		if(i>=0||i<8)
			return neighbors[i];
		else return null;
	}

	public String toString() {
		if (piece == null)
			return "  ";
		else
			return piece.toString();
	}
}
