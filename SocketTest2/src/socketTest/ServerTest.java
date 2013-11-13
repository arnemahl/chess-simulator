package socketTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerTest extends Thread {
	private ServerSocket server;
	private Socket client;
	private BufferedReader in;
	private String line;

	public void run() {
		listenSocket();
	}

	public void listenSocket() {
		try {
			server = new ServerSocket(4321);
		} catch (IOException e) {
			System.out.println("Could not listen on port 4321");
			System.exit(-1);
		}
		try {
			client = server.accept();
		} catch (IOException e) {
			System.out.println("Accept failed: 4321");
			System.exit(-1);
		}
		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
		} catch (IOException e) {
			System.out.println("Read failed");
			System.exit(-1);
		}

		while (true) {
			try {
				if (in.ready()) {
					line = in.readLine();

					// Send data back to client
					System.out.println(line);
					JOptionPane.showMessageDialog(null, line);
					System.exit(0);
				}

			} catch (IOException e) {
				System.out.println("Read failed");
				System.exit(-1);
			}
		}
	}
}
