package algorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Timer;

public class Server extends Thread {
	private ServerSocket server;			// Holds the ServerSocket
	private Socket socket;					// Holds the Socket
	private BufferedReader in;				// The reader that can read from the communication channel
	private PrintWriter out;				// The printer that can write to the communication channel
	private String line;					// String that holds the line that is just read from the channel
	private volatile ArrayList<String> listMessages;	// A list of all messages from the channel is created
	private volatile boolean newMessage;	// If the server has a new message from the channel this flag will be true
	private int port;						// Remember the port number of the socket
	private volatile boolean running = true;// This flag will be set to stop the thread thus all communication
	private Lock lock;						// Will be used to synchronized since this is a thread
	private Timer timer;					// Timer to time out the connection if no response is coming
	private volatile boolean waiting;		// Flag that will be set by the timer when waiting should stop
	private Algorithm algorithm;			// The server should be able to query the algorithm

	// Constructor, creating lists, initializing variables, etc.
	public Server(Algorithm sAlgorithm, int i) {
		lock = new ReentrantLock();
		this.algorithm = sAlgorithm;
		port = i;
		waiting = true;
		listMessages = new ArrayList<String>();
		newMessage = false;
		// Initializing and setting timer
		timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopWaiting();	// Timer tick, waiting should stop
			}
		});
		timer.setInitialDelay(500);
		timer.setRepeats(false);
		
	}
	private void stopWaiting(){
		waiting = false;
	}

	// To terminate the server
	public void terminate() {
		running = false;	// Simply stop the thread from running
	}
	// This method will run the listenSocket method, thus starting the thread
	public void run() {
		running = true;
		setupSocket(); 			// Set up the actual socket
		listenSocket();
		waiting = false;
		out.println("exit");	// If the listenSocket infinite loop is terminated, for safety send exit through the channel
		waiting = false;
	}
	// Listen socket will start listening to the channel and put messages in a message list
	public void listenSocket() {
		line = "";
		while (!line.equals("exit") && running) {
			try {
				if ((line = in.readLine()) != null) {
					lock.lock();
					listMessages.add(line);
					newMessage = true;
					lock.unlock();

					if(line.equals("time"))
						out.println(algorithm.GetTime());
					else if(line.equals("board"))
						out.println(algorithm.GetBoard());
					else if(line.equals("player"))
						out.println(algorithm.GetPlayer());
					else if(line.equals("ping"))
						out.println("pong");
				}
			} catch (IOException e1) {
				System.out.println("An error has occurred " + e1);
			}
		}
	}
	// This method method will set up the server, the socket, the writer and the reader
	// The system will exit in case of failure
	private void setupSocket() {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Could not open connection on port: " + port);
			System.exit(-1);
		}
		try {
			socket = server.accept();
		} catch (IOException e) {
			System.out.println("Accept failed on port: " + port);
			System.exit(-1);
		}
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Setup of InputStreamReader failed in server on port: " + port);
			System.exit(-1);
		}
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Creating PrintWriter failed in server on port: " + port);
			System.exit(-1);
		}
	}

	// Will print a String to the channel, and return the first response
	public String println(String stLine) {
		String st = "";
		listMessages.clear();
		newMessage = false;
		if(out==null){
			return "";
		}
		out.println(stLine);
		waiting = true;
		timer.start();
		int ct = 0;
		while(waiting){
			if(ct>999999999)
				waiting = false;
			if(newMessage)
				waiting = false;
			ct++;
		}
		timer.stop();
		if(newMessage){
			st = listMessages.get(listMessages.size()-1);
		}
		if(listMessages.isEmpty()){
			newMessage=false;
		}
		return st;
	}

	// Will ping the server and return true if response is pong.
	// The method will try twice before returning false
	public boolean ping(){
		String ret = this.println("ping");
		if(ret.equals("pong"))
			return true;
		
		ret = this.println("ping");
		return ret.equals("pong");
	}
	
	// This will give the board and the color and ask the algorithm to start calculating
	public boolean startToMove(String boardString, String color){
		// First prepare algorithm to get ready
		if(!this.println("move").equals("ready"))
			if(!this.println("move").equals("ready"))
				return false;
		// Then send the board and player color. The algorithm can also query board and color
		if(this.println(boardString+":"+color).equals("received"))
			return true;
		return this.println(boardString+":"+color).equals("received");
	}
	// This will ask for the move
	public String getMove(){
		return this.println("timesup");
	}
}
