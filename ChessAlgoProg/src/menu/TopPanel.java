package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import view.MainWindow;
import view.ViewPiece;
import algorithm.Algorithm;
import algorithm.Code;
import board.Board;

public class TopPanel extends JPanel {
	private static final long serialVersionUID = -8287608037799327329L;
	private MainWindow mw;
	private Board board;
	private JComboBox<String> comboBox;
	private JLabel playerWhite;
	private JLabel playerBlack;
	private JLabel timerlb;
	private JButton btMainMenu;
	private ViewPiece whiteImage;
	private ViewPiece blackImage;
	private Timer timer;
	private Algorithm whiteAlgo;
	private Algorithm blackAlgo;
	private ArrayList<Integer> portNumbers;

	public TopPanel(MainWindow mw, Board sBoard) {
		super(new BorderLayout());
		this.mw = mw;
		this.board = sBoard;
		portNumbers = new ArrayList<Integer>();

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stepTimer();
			}
		});
		timer.setInitialDelay(1000);

		String[] comboTypes = { "00:05", "00:10", "00:15", "00:20", "00:30",
				"00:45", "01:00", "01:30", "02:00" };
		comboBox = new JComboBox<String>(comboTypes);

		playerWhite = new JLabel("White");
		playerBlack = new JLabel("Black");
		Font player = new Font(playerWhite.getFont().getName(), Font.BOLD,
				playerWhite.getFont().getSize() + 6);
		playerWhite.setFont(player);
		playerBlack.setFont(player);
		this.noGame();
		btMainMenu = new JButton("Main Menu");

		timerlb = new JLabel("00:05");
		Font font = timerlb.getFont();
		timerlb.setForeground(Color.BLACK);
		Font font2 = new Font(font.getName(), font.getStyle(),
				font.getSize() + 20);
		timerlb.setFont(font2);

		whiteImage = new ViewPiece("bin/Resources/wp.png");
		blackImage = new ViewPiece("bin/Resources/bp.png");

		whiteImage.setPreferredSize(new Dimension(55, 50));
		blackImage.setPreferredSize(new Dimension(55, 50));

		JPanel jpCenter = new JPanel(new FlowLayout());
		jpCenter.add(timerlb);
		this.add(jpCenter, BorderLayout.CENTER);

		JPanel jpWest = new JPanel(new FlowLayout());
		jpWest.add(whiteImage);
		jpWest.add(playerWhite);
		this.add(jpWest, BorderLayout.WEST);

		JPanel jpEast = new JPanel(new FlowLayout());
		jpEast.add(playerBlack);
		jpEast.add(blackImage);
		this.add(jpEast, BorderLayout.EAST);

		JPanel jpSouth = new JPanel(new BorderLayout());
		JPanel comboPanel = new JPanel(new FlowLayout());
		comboPanel.add(comboBox);
		jpSouth.add(comboPanel, BorderLayout.WEST);
		jpSouth.add(btMainMenu, BorderLayout.EAST);
		this.add(jpSouth, BorderLayout.SOUTH);

		comboBox.addActionListener(new ComboActionListener());

		btMainMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				stopTimer();
				timerlb.setForeground(Color.BLACK);
				String t = comboBox.getSelectedItem().toString();
				timerlb.setText(t);
				noGame();
				gotoMainMenu();
			}
		});
	}

	private class ComboActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			timerlb.setForeground(Color.BLACK);
			String t = comboBox.getSelectedItem().toString();
			timerlb.setText(t);
		}
	}

	public void startTimer() {
		timerlb.setForeground(Color.BLACK);
		String t = comboBox.getSelectedItem().toString();
		timerlb.setText(t);
		timer.start();
		AskAlgorithm();
	}

	public void stopTimer() {
		timer.stop();
	}

	public void stepTimer() {
		String t = timerlb.getText();
		String t2[] = t.split(":");
		int timeSec = Integer.parseInt(t2[1]) + Integer.parseInt(t2[0]) * 60;
		timeSec -= 1;
		if(whiteAlgo!=null)
			whiteAlgo.SetTime(timeSec);
		if(blackAlgo!=null)
			blackAlgo.SetTime(timeSec);
		int timerSec = timeSec;
		t2[0] = t2[1] = "";
		if (timeSec % 60 < 10)
			t2[1] = Integer.toString(0);
		t2[1] += Integer.toString(timeSec % 60);
		timeSec /= 60;
		if (timeSec < 10)
			t2[0] = Integer.toString(0);
		t2[0] += Integer.toString(timeSec);
		timerlb.setText(t2[0] + ":" + t2[1]);
		if (timerSec == 0) {
			timerlb.setForeground(Color.RED);
			stopTimer();
			if(playerWhite.getForeground()==Color.BLACK){
				if(whiteAlgo != null){
					String moveString = whiteAlgo.GetMove();
					if(!this.TryToMove(moveString)){
						JOptionPane.showMessageDialog(null, playerWhite.getText()+" was not able to move"+"\n"+playerBlack.getText()+" wins");
						mw.disableMove();
						this.noGame();
					}
				}
			}
			else{
				if(blackAlgo != null){
					String moveString = blackAlgo.GetMove();
					if(!this.TryToMove(moveString)){
						JOptionPane.showMessageDialog(null, playerBlack.getText()+" was not able to move"+"\n"+playerWhite.getText()+" wins");
						mw.disableMove();
						this.noGame();
					}
				}
			}
		} else
			timerlb.setForeground(Color.BLACK);
	}

	private void AskAlgorithm(){
		if(playerWhite.getForeground()==Color.BLACK){
			if(whiteAlgo!=null){
				mw.disableMove();
				if(!whiteAlgo.StartToMove()){
					this.noGame();
					mw.disableMove();
					JOptionPane.showMessageDialog(null,playerWhite.getText()+" was not able to start calculating a move."+"\n"+playerBlack.getText()+" wins!");
				}
			}
			else
				mw.enableMove();
		}
		else{
			if(blackAlgo!=null){
				mw.disableMove();
				if(!blackAlgo.StartToMove()){
					this.noGame();
					mw.disableMove();
					JOptionPane.showMessageDialog(null,playerBlack.getText()+" was not able to start calculating a move."+"\n"+playerWhite.getText()+" wins!");
					
				}
			}
			else
				mw.enableMove();
		}
	}

	public boolean TryToMove(String moveString){
		Code code = new Code(moveString);
		if(code.ValidCode()){
			if(!mw.TryToMoveAlgo(code)){
				JOptionPane.showMessageDialog(null, "Could not performe move");
				// Ask the algo again?
				return false;
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Move code not valid, code should be in the form 1,1x2,1"+"\n"+code.GetCode());
			return false;
		}
		return true;
	}

	public void noGame() {
		this.clearAlgorithms();
		playerWhite.setForeground(Color.GRAY);
		playerBlack.setForeground(Color.GRAY);
		stopTimer();
	}

	public void initGame() {
		mw.enableMove();
		playerWhite.setForeground(Color.BLACK);
		playerBlack.setForeground(Color.GRAY);
		if(whiteAlgo!=null)
			mw.disableMove();
	}

	public void nextPlayer() {
		Color col = playerWhite.getForeground();
		playerWhite.setForeground(playerBlack.getForeground());
		playerBlack.setForeground(col);
		this.validate();
		this.repaint();
		stopTimer();
		if(playerWhite.getForeground()==Color.BLACK||playerBlack.getForeground()==Color.BLACK)
			startTimer();
	}

	public void setNames(String st1, String st2) {
		playerWhite.setText(st1);
		playerBlack.setText(st2);
	}

	public void winner(String winnerst) {
		this.clearAlgorithms();
		if (winnerst.equals("white")) {
			JOptionPane.showMessageDialog(null, "White wins!" + "\n\n"
					+ playerWhite.getText());
		} else if (winnerst.equals("black")) {
			JOptionPane.showMessageDialog(null, "Black wins!" + "\n\n"
					+ playerBlack.getText());
		} else if (winnerst.equals("draw")) {
			JOptionPane.showMessageDialog(null, "Game over!" + "\n\n"
					+ "Game drawed");
		}
	}

	private void gotoMainMenu() {
		this.clearAlgorithms();
		mw.gotoMainMenu();
	}

	private int getPortNumber(){
		int portN = 4321;
		while(portNumbers.contains(portN)){
			portN++;
		}
		portNumbers.add(portN);
		return portN;
	}
	public Algorithm createAlgorithm(String path){
		Algorithm algo = null;
		algo = new Algorithm(this.getPortNumber(),"localhost",path,board);
		return algo;
	}
	public boolean createAlgorithmWhite(String path){
		if(this.whiteAlgo!=null)
			this.whiteAlgo.StopAlgorithm();
		Algorithm algo = this.createAlgorithm(path);
		if(algo==null)
			return false;
		if(algo.TestAlgorithm()){
			whiteAlgo = algo;
			return true;
		}
		return false;
	}
	public boolean createAlgorithmBlack(String path){
		if(this.blackAlgo!=null)
			this.blackAlgo.StopAlgorithm();
		Algorithm algo = this.createAlgorithm(path);
		if(algo==null)
			return false;
		if(algo.TestAlgorithm()){
			blackAlgo = algo;
			return true;
		}
		return false;
	}

	public void clearAlgorithms(){
		if(whiteAlgo != null){
			whiteAlgo.StopAlgorithm();
			whiteAlgo = null;
		}
		if(blackAlgo != null){
			blackAlgo.StopAlgorithm();
			blackAlgo = null;
		}
	}
}
