package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClient {
	public static void main(String[] args) {
		Client client = null;
		try {
			client = new Client(args[0], args[1]);
		} catch (ArrayIndexOutOfBoundsException aobe) {
			System.exit(-1);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		client.start();

		String st = "";
		while (!st.equals("exit")) {
			try {
				st = br.readLine();
			} catch (IOException e) {
				System.out.println("Error occurred \n\n" + e);
			}
			client.println(st);
		}
	}
}
