package socketTest;

import java.io.IOException;

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
		try {
			Thread.sleep(500);
		} catch (InterruptedException e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
		JOptionPane.showMessageDialog(null, "WHAT!?");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		JOptionPane.showMessageDialog(null, "What2");
		try {
			Process p = Runtime.getRuntime().exec("java -jar C:\\Users\\Anders\\Desktop\\Client.jar 4321 localhost \"hello world exit\"");
			
			System.out.println("Etter kommando");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e);
		}
		
		
	}

}
