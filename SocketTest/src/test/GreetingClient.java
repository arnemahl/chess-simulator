package test;

import javax.swing.JOptionPane;

public class GreetingClient
{
	/* args[0] - port
	 * args[1] - server
	 */
	public static void main(String [] args)
	{
		try{
		Client client = new Client(args[0],args[1]);
		client.writeSocket(args[2]);
		}
		catch (ArrayIndexOutOfBoundsException aobe){
			JOptionPane.showMessageDialog(null,"Index args out of bounds");
			System.exit(-1);
			
		}
	}
}