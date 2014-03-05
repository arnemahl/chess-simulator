package algorithm;

import client.Link;

public class Algorithm extends Thread {
	private Link link;				// Will hold the link reference
	private volatile boolean stop;	// Will hold the stop flag
	private String[][] board;		// Will hold the layout of the board
	private String color;			// Will hold the color you are

	// Constructor, setting link reference and setting stop flag to false
	public Algorithm(Link sLink) {
		stop = false;
		this.link = sLink;
	}
	// Setting the stop flag. If set to true, the algorithm should stop.
	public void SetStop(boolean sStop) {
		this.stop = sStop;
	}
	// This should be called to set the move string.
	// Make sure it is according to the specified rules of the move string.
	private void SetMove(String move) {
		link.SetMove(move);
	}
	// This will be called to query the board before the algorithm start
	private void GetBoard(){
		this.board = link.GetBoard();
	}
	// This will be called to query the color before the algorithm start
	private void GetColor(){
		this.color = link.GetColor();
	}
	// This will be called to start the algorithm on a new thread
	// Make sure to call SetMove when a move is calculated
	// !Make sure to check for the stop flag so the algorithm will stop calculating when the time is up
	public void run() {
		GetBoard();	// Will set this.board
		GetColor();	// Will set this.color
		while (!stop) {
			this.Calculate();
		}
	}

	private void Calculate() {
		while (!stop) {
			// Be sure to check for the stop flag so not to end up in infinite loop
			/** 
			 * 	Add calculation algorithm here
			 * 	Then call:
			 *	this.SetMove(moveString);
			 *	to store the calculated move to the link
			 **/
		}
	}
}
