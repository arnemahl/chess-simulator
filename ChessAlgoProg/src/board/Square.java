package board;

public class Square {
	// 0-upleft, 1-up, 2-upright, 3-left, 4-right, 5-downleft, 6-down,
	// 7-downright
	private Square[] neighbors;

	public Square() {
		neighbors = new Square[8];
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

}
