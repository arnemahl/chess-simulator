package client;

import algorithm.Algorithm;

public class Link {
	private Client client;						// Client object responsible for communication
	private Algorithm algorithm;				// The algorithm that will calculate moves
	private volatile String[][] board;			// Representation of the board
	private volatile String move;				// The move that has been calculated by algorithm
	private volatile String color;				// What color am I (white or black)
	private volatile boolean exit;				// If the program should exit

	// Constructor, will create an algorithm thread
	public Link(String port, String host) {
		this.exit = false;						// Exit flag is initially false
		this.client = new Client(port, host);	// Create the client that will listen to socket
		this.client.SetLink(this);				// The client should know this link object
		this.algorithm = new Algorithm(this);
		this.move = "4,6x4,4";					// Default move (white king pawn out two steps)
		this.color = "white";					// Default color white
		this.board = new String[8][8];			// Create an array of strings to hold the board
		client.start();							// Starting the client thread
	}

	// Called by the client
	// Returning true to indicate that it is ready after setting up the algorithm
	public boolean ReadyToMove() {
		this.algorithm.SetStop(true);
		this.algorithm = new Algorithm(this);
		return true;
	}

	// Called by the client
	// Set the stop flag and start the algorithm
	public void StartCalculation() {
		this.algorithm.SetStop(false);
		this.algorithm.start();
	}

	// Called by the client
	// Set the stop flag stopping the algorithm and return the calculated move
	public String TimesUp(){
		this.algorithm.SetStop(true);
		return this.move;
	}

	// Called by the client
	// Returns the move
	public String GetMove() {
		return this.move;
	}

	// Called by the client
	// Sets the board and color.
	public void SetBoardAndColor(String sString) {
		String[] splitter;
		splitter = sString.split(":");
		if (splitter.length != 2)
			return;
		this.SetBoard(splitter[0]);
		this.SetColor(splitter[1]);
	}

	// Called by the client or SetBoardAndColor
	// Set the board
	public void SetBoard(String sBoard) {
		if (sBoard.length() != 135)
			return;
		String[] splitter = sBoard.split(",");
		if (splitter.length != 8)
			return;
		for (int i = 0; i < 8; i++) {
			if (splitter[i].length() != 16)
				return;
		}
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				this.board[x][y] = "";
				this.board[x][y] += splitter[y].charAt(2 * x);
				this.board[x][y] += splitter[y].charAt(2 * x + 1);
			}
		}
	}

	// Called by the client or SetBoardAndColor
	// Set the color
	public void SetColor(String sColor) {
		if(sColor.equals("black")||sColor.equals("white"))
			this.color = sColor;
	}

	// Called by the algorithm
	// Set the move string to the so far best calculated move
	public void SetMove(String sMove) {
		// This method will do some easy checking to see if the string is a valid string.
		// However even if passing these "tests" it might still not be valid.
		if (sMove.length() != 7)
			return;
		String[] splitter = sMove.split("x");
		if (splitter.length != 2)
			return;
		if (splitter[0].length() != 3)
			return;
		if (splitter[1].length() != 3)
			return;
		this.move = sMove;	// Set the move string
	}

	// Called by the algorithm
	// Returns the board
	public String[][] GetBoard() {
		return this.board;
	}

	// Called by the algorithm
	// Returns the color
	public String GetColor() {
		return this.color;
	}
	
	// Set the exit and stop flags, will stop the algorithm, the client and exit
	public void Exit() {
		this.algorithm.SetStop(true);
		client.setStop(true);
		this.exit = true;
	}

	// Returns the exit flag
	public boolean GetExit() {
		return this.exit;
	}

}
