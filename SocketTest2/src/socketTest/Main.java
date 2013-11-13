package socketTest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		
		ServerTest st = new ServerTest();
		st.start();
		
		try {
			Process p = Runtime.getRuntime().exec("java -jar C:\\Users\\Anders\\Desktop\\Client.jar 4321 localhost \"hello world exit\"");
			
			System.out.println("Etter kommando");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e);
		}
		
		
	}

}
