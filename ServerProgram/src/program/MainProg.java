package program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainProg {

	public static void main(String[] args) {
		Server st = new Server(4321);
		st.start();
		String inputLine = "start";

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (!inputLine.equals("exit")) {
			try {
				inputLine = br.readLine();
			} catch (IOException e) {
				System.out.println("Reading in server failed");
			}
			st.println(inputLine);
		}
	}
}
