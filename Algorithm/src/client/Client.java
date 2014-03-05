package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client extends Thread {
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private String line;
	private String port;
	private boolean ready = false;
	private Link link;
	private boolean stop = false;

	// Set the link reference
	public void SetLink(Link sLink) {
		this.link = sLink;
	}

	// Initializing the client on the given socket and interface
	public Client(String portNumber, String host) {
		this.port = portNumber;
		// Set up the socket
		try {
			client = new Socket(host, Integer.parseInt(portNumber));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Client; Could not listen to port: " + portNumber
							+ " on server: " + host + "." + "\n"
							+ "Terminating client" + "\n\n" + e);
			System.exit(-1);
		}
		// Set up the PrintWriter
		try {
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Client; Could not create writer." + "\n"
							+ "Terminating client on port" + portNumber
							+ "\n\n" + e);
			System.exit(-1);
		}
		// Set up the BufferedReader
		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Client; Could not create reader." + "\n"
							+ "Terminating client on port" + portNumber
							+ "\n\n" + e);
			System.exit(-1);
		}
	}

	// Will be called by Thread.start()
	public void run() {
		listenSocket();
	}

	// Print a line to the stream of the socket interface
	public void println(String outPrint) {
		out.println(outPrint);
	}
	
	// Will set the Stop flag
	public void setStop(boolean sStop){
		this.stop = sStop;
	}

	// Will run in a thread, listening to the socket, will exit when line "exit" is read etc.
	public void listenSocket() {
		line = "";
		while (!this.stop) {
			try {
				if ((line = in.readLine()) != null) {
					if (line.equals("ping"))
						this.println("pong");
					
					else if (line.equals("exit")) {
						this.println("exit");
						link.Exit();
						this.stop = true;
						
					} else if (line.equals("move")) {
						this.println("ready");
						ready = link.ReadyToMove();
						
					} else if (ready == true) {
						this.println("received");
						ready = false;
						link.SetBoardAndColor(line);
						link.StartCalculation();
						
					} else if (line.equals("timesup")) {
						this.println(link.TimesUp());
						
					} else{
						link.SetBoard(line);	// Will not work if the format does not match
						link.SetColor(line);	// Will not work if the format does not match
						System.out.println(line);
					}
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Client; Listening to socket failed on port: " + port
								+ "\n\n" + e);
				System.exit(-1);
			}
		}
	}
}
