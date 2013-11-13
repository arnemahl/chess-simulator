package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
	private Socket client;
	private PrintWriter out;
	
	public Client(String s, String s2){
		try{
			client = new Socket(s2,Integer.parseInt(s)); 
		}
		catch (IOException e) {
			System.out.println("Client, Could not listen on port "+s+", server "+s2);
			System.exit(-1);
		}
	}

	public void writeSocket(String outPrint){
		try{
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
		    System.out.println("Read failed");
		    System.exit(-1);
		}
		out.println(outPrint);
	}
}
