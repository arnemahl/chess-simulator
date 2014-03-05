package algorithm;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

import board.Board;

public class Algorithm {
	private String path;	// Will remember the path to where the algorithm is stored
	private int portNumber;	// Will remember the port number of the socket of this specific algorithm
	private String host;	// Will remember the host to the server (for now localhost)
	private Process process;// Will hold the process of the opened algorithm
	private Server server;	// The Server object will be responsible for the actual socket communication
	private Board board;	// The algorithm should know the board so it can send information about it
	private int time;		// The algorithm will know how much time it has to finish the calculations
	private Lock lock;		// Used for synchronization reasons

	// Constructor
	public Algorithm(int sPortNumber, String sHost, String sPath, Board sBoard) {
		lock = new ReentrantLock();
		this.portNumber = sPortNumber;
		this.host = sHost;
		this.path = sPath;
		this.board = sBoard;
		time = 5;
		server = new Server(this,sPortNumber);	//Create the server with this portNumber
	}
	// Starting the algorithm will run the command for running the client jar file, and start the server
	public void StartAlgorithm() {
		this.server.start();
		try {
			process = Runtime.getRuntime().exec("java -jar " + path + " " + portNumber + " "+host);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problem with creating process" + "\n\n" + e);
		}
	}
	// This will test the algorithm to see if it is a good algorithm
	public boolean TestAlgorithm() {
		// First check if the path is null
		if (path == null) {
			return false;
		}
		// Check if the file exists
		if(!(new File(path).exists())){
			return false;
		}
		// Check if the extension of the file is .jar
		String fileExtension = this.path.substring(this.path.lastIndexOf('.') + 1);
		if (!fileExtension.equals("jar")) {
			return false;
		}
		// Start the algorithm and check if it returns ping, if not; destroy process and return false
		this.StartAlgorithm();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!server.ping()){
			server.terminate();		// terminate the server
			process.destroy();		// Destroy the process
			return false;
		}
		return true;
	}
	// This will stop the algorithm and the opened server
	public void StopAlgorithm(){
		// First try to stop it through its normal communication channels
		if(!server.println("exit").equals("exit")){
			server.terminate();
		}
		process.destroy();	// Destroy the process
	}

	// Will ping the algorithm to see if it responds correctly
	public boolean Ping(){
		return server.ping();
	}
	// Setting the time of the algorithm
	public void SetTime(int sTime){
		lock.lock();
		this.time = sTime;
		lock.unlock();
	}
	// Querying the time of the algorithm
	public String GetTime(){
		int rTime = 0;
		lock.lock();
		rTime = this.time;
		lock.unlock();
		return Integer.toString(rTime);
	}
	// Query the board
	public String GetBoard(){
		return board.PrintBoardSocket();
	}
	// Query the player
	public String GetPlayer(){
		return board.GetPlayer().toString();
	}
	// Will send the print of the board and ask for the algorithm to start calculating a move
	public boolean StartToMove(){
		return server.startToMove(this.GetBoard(),this.GetPlayer());
	}
	// Query the next move (StartToMove should be called first!)
	public String GetMove(){
		return server.getMove();
	}
}
