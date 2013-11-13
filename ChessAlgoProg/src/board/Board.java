package board;

public class Board {
	Square[][] squares;

	public Board() {
		// Initialize all the squares 8*8
		squares = new Square[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				squares[i][j] = new Square();
		// Temporary list of squares to make it easier to set the neighbors
		Square[][] tmp = new Square[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (i == 0 || i == 9 || j == 0 || j == 9)
					tmp[i][j] = null;
				else
					tmp[i][j] = squares[i - 1][j - 1];

		// Set the neighbors of all the squares on the board
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				squares[i][j].UpdateNeighbors(tmp[i + 1 - 1][j + 1 - 1],
						tmp[i + 1][j + 1 - 1], tmp[i + 1 + 1][j + 1 - 1],
						tmp[i + 1 - 1][j + 1], tmp[i + 1 + 1][j + 1],
						tmp[i + 1 - 1][j + 1 + 1], tmp[i + 1][j + 1 + 1],
						tmp[i + 1 + 1][j + 1 + 1]);
	}

}