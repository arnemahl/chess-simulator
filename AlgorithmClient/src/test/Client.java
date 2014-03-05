package test;

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

	// Initializing the client on the given socket and interface
	public Client(String portNumber, String host) {
		this.port = portNumber;
		try {
			client = new Socket(host, Integer.parseInt(portNumber));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Client; Could not listen to port: " + portNumber + " on server: "
							+ host + "." + "\n" + "Terminating client" + "\n\n"
							+ e);
			System.exit(-1);
		}
		try {
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Client; Could not create writer." + "\n"
							+ "Terminating client on port" + portNumber + "\n\n" + e);
			System.exit(-1);
		}
		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Client; Could not create reader." + "\n"
							+ "Terminating client on port" + portNumber + "\n\n" + e);
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

	// Will run in a thread, listening to the socket
	public void listenSocket() {
		line = "";
		while (!line.equals("exit")) {
			try {
				if ((line = in.readLine()) != null) {
					if (line.equals("ping"))
						this.println("pong");
					else if (line.equals("exit")) {
						this.println("exit");
						System.exit(-1);
					}else if(line.equals("move")){
						this.println("ready");
						ready = true;
					}
					else if(ready == true){
						ready = false;
						this.println("received");
					}
					else if(line.equals("timesup")){
						this.println("1,1x1,2");
					}
					else
						System.out.println(line);
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
