package main;

import client.Link;

public class Main {
	/**
	 ** Main will only instantiate a link object and keep the process alive as
	 ** long as the link is not in exit state
	 **/
	public static void main(String[] args) {
		Link link = null;
		try {
			link = new Link(args[0], args[1]);
		} catch (ArrayIndexOutOfBoundsException aobe) {
			System.out.println("ArrayIndexOutOfBondsException");
			System.exit(-1);
		}
		while (!link.GetExit())
			;
	}

}
