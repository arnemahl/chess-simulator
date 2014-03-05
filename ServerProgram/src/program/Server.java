package program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	private ServerSocket server;
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private String line;
	private int port;
	private volatile boolean running = true;

	public Server(int i) {
		port = i;
		setupSocket();
	}

	public void terminate() {
		running = false;
	}

	public void run() {
		listenSocket();
		out.println("exit");
	}

	public void listenSocket() {
		line = "";
		while (!line.equals("exit") && running) {
			try {
				if ((line = in.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e1) {
				System.out.println("An error has occured " + e1);
				System.exit(-1);
			}
		}
	}

	public void println(String stLine) {
		out.println(stLine);
	}

	private void setupSocket() {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Could not listen on port " + port);
			System.exit(-1);
		}
		try {
			client = server.accept();
		} catch (IOException e) {
			System.out.println("Accept failed: " + port);
			System.exit(-1);
		}
		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
		} catch (IOException e) {
			System.out.println("Read failed");
			System.exit(-1);
		}
		try {
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Creating writer failed in server");
			System.exit(-1);
		}
	}
}
