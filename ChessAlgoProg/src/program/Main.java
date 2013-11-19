package program;

import java.util.Scanner;

import algorithm.Code;
import board.Board;


public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		board.InitBoard();
		Scanner a = new Scanner(System.in);
		String string = null;
		board.PrintBoard();
		string = a.nextLine();
		while(!string.equals("exit")){
			
			Code c = new Code(string);
			board.Move(c);
			board.PrintBoard();
			
			string = a.nextLine();
		}
	}

}
