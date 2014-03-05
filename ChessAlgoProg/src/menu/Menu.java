package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import view.MainWindow;

public class Menu extends JPanel {
	private static final long serialVersionUID = -5685652752362930028L;

	private MainWindow mw;

	private JTextField tf1;
	private JTextField tf2;

	private JButton hvh;
	private boolean hvhClicked = false;
	private JPanel hvhPanel;
	private JLabel lb1;
	private JLabel lb2;

	private JButton hvc;
	private boolean hvcClicked = false;
	private JPanel hvcPanel;

	private JButton cvc;
	private boolean cvcClicked = false;
	private JPanel cvcPanel;

	private FileBrowser fb1;
	private FileBrowser fb2;

	private JButton ok;

	private JPanel center;

	public Menu(MainWindow mw) {
		super.setLayout(new BorderLayout());
		JPanel north = new JPanel(new FlowLayout());
		center = new JPanel(new BorderLayout());

		this.mw = mw;
		this.add(north, BorderLayout.NORTH);

		hvh = new JButton("2 Player");
		hvh.addActionListener(new actionListenerhvh());
		hvc = new JButton("1 Player");
		hvc.addActionListener(new actionListenerhvc());
		cvc = new JButton("0 Player");
		cvc.addActionListener(new actionListenercvc());

		ok = new JButton("Start Game");
		ok.addActionListener(new actionListenerOk());

		hvh.setBackground(Color.GREEN);
		hvc.setBackground(Color.YELLOW);
		cvc.setBackground(Color.RED);

		north.add(hvh);
		north.add(hvc);
		north.add(cvc);

		this.add(center, BorderLayout.CENTER);

		showhvh();
		showhvc();
		showcvc();
		center.removeAll();
	}

	// This will reset the menu
	public void resetMenu() {
		showhvh();
		showhvc();
		showcvc();
		center.removeAll();
	}

	// Show the human vs. human menu
	private void showhvh() {
		center.removeAll();
		this.validate();
		this.repaint();
		tf1 = new JTextField();
		tf1.setDocument(new JTextFieldLimit(25));
		tf2 = new JTextField();
		tf2.setDocument(new JTextFieldLimit(25));
		tf1.setText("White player");
		tf2.setText("Black player");

		hvhPanel = new JPanel();
		hvhPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 15, 0));
		hvhPanel.add(new JLabel("White"));
		hvhPanel.add(tf1);
		hvhPanel.add(new JLabel("Black"));
		hvhPanel.add(tf2);

		tf1.setPreferredSize(new Dimension(93, 20));
		tf2.setPreferredSize(new Dimension(93, 20));

		center.add(hvhPanel, BorderLayout.NORTH);
		JPanel jp = new JPanel(new FlowLayout());
		jp.add(ok);
		center.add(jp, BorderLayout.CENTER);
		this.validate();
	}

	// Show the human vs. computer menu
	private void showhvc() {
		center.removeAll();
		this.validate();
		this.repaint();
		tf1 = new JTextField();
		tf1.setDocument(new JTextFieldLimit(25));
		tf1.setText("Player");
		tf2 = new JTextField();
		tf2.setDocument(new JTextFieldLimit(25));
		tf2.setText("Algorithm");

		fb1 = new FileBrowser();
		lb1 = new JLabel("White");
		lb2 = new JLabel("Black");
		JPanel dummy1 = new JPanel(new FlowLayout());
		JPanel dummy2 = new JPanel(new BorderLayout());
		JButton chng = new JButton("Switch");
		chng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				labelSwitch();
			}
		});

		hvcPanel = new JPanel(new BorderLayout());
		hvcPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 15, 0));
		dummy1.add(lb1);
		dummy1.add(tf1);

		JPanel dum21 = new JPanel(new FlowLayout());
		dum21.add(lb2);
		dum21.add(tf2);
		JPanel dum22 = new JPanel(new FlowLayout());
		dum22.add(fb1);
		dummy2.add(dum21, BorderLayout.NORTH);
		dummy2.add(dum22, BorderLayout.SOUTH);

		hvcPanel.add(dummy1, BorderLayout.NORTH);
		hvcPanel.add(dummy2, BorderLayout.CENTER);
		JPanel temp = new JPanel(new FlowLayout());
		temp.add(chng);
		hvcPanel.add(temp, BorderLayout.SOUTH);

		tf1.setPreferredSize(new Dimension(93, 20));
		tf2.setPreferredSize(new Dimension(93, 20));

		center.add(hvcPanel, BorderLayout.NORTH);
		JPanel jp = new JPanel(new FlowLayout());
		jp.add(ok);
		center.add(jp, BorderLayout.CENTER);
		this.validate();
	}

	private void labelSwitch() {
		String ttx = lb1.getText();
		lb1.setText(lb2.getText());
		lb2.setText(ttx);
	}

	// Show the computer vs. computer menu
	private void showcvc() {
		center.removeAll();
		this.validate();
		this.repaint();
		tf1 = new JTextField();
		tf1.setDocument(new JTextFieldLimit(25));
		tf1.setText("Algorithm 1");
		tf2 = new JTextField();
		tf2.setDocument(new JTextFieldLimit(25));
		tf2.setText("Algorithm 2");
		fb1 = new FileBrowser();
		fb2 = new FileBrowser();
		JPanel dummy1 = new JPanel(new BorderLayout());
		JPanel dum11 = new JPanel(new FlowLayout());
		JPanel dum12 = new JPanel(new FlowLayout());

		JPanel dummy2 = new JPanel(new BorderLayout());
		JPanel dum21 = new JPanel(new FlowLayout());
		JPanel dum22 = new JPanel(new FlowLayout());

		cvcPanel = new JPanel(new BorderLayout());
		cvcPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 15, 0));

		dum11.add(new JLabel("AI 1"));
		dum11.add(tf1);
		dum12.add(fb1);
		dummy1.add(dum11, BorderLayout.NORTH);
		dummy1.add(dum12, BorderLayout.SOUTH);

		dum21.add(new JLabel("AI 2"));
		dum21.add(tf2);
		dum22.add(fb2);
		dummy2.add(dum21, BorderLayout.NORTH);
		dummy2.add(dum22, BorderLayout.SOUTH);

		cvcPanel.add(dummy1, BorderLayout.NORTH);
		cvcPanel.add(dummy2, BorderLayout.SOUTH);

		tf1.setPreferredSize(new Dimension(93, 20));
		tf2.setPreferredSize(new Dimension(93, 20));
		;

		center.add(cvcPanel, BorderLayout.NORTH);
		JPanel jp = new JPanel(new FlowLayout());
		jp.add(ok);
		center.add(jp, BorderLayout.CENTER);
		this.validate();
	}

	// This document will be added to the text fields in order not to have too
	// many characters
	@SuppressWarnings("serial")
	private class JTextFieldLimit extends PlainDocument {
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}

	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		Dimension dim = new Dimension(d.width * 5 / 16, 30);
		hvh.setPreferredSize(dim);
		hvc.setPreferredSize(dim);
		cvc.setPreferredSize(dim);
		ok.setPreferredSize(dim);
	}

	// Action listeners for the menu buttons
	private class actionListenerhvh implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			showhvh();
			hvhClicked = true;
			hvcClicked = false;
			cvcClicked = false;
		}
	}
	private class actionListenerhvc implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			showhvc();
			hvhClicked = false;
			hvcClicked = true;
			cvcClicked = false;
		}
	}
	private class actionListenercvc implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			showcvc();
			hvhClicked = false;
			hvcClicked = false;
			cvcClicked = true;
		}
	}

	// Action listener for the OK Button. Will react differently according to
	// what menu item is choosen
	private class actionListenerOk implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (hvhClicked) {
				mw.setNames(tf1.getText(), tf2.getText());
				mw.StartGame();

			} else if (hvcClicked) {
				mw.clearAlgorithms();
				if(lb2.getText().equals("Black")){
					mw.setNames(tf1.getText(), tf2.getText());
					if(!mw.createAlgorithmBlack(fb1.GetText())){
						JOptionPane.showMessageDialog(null, "A problem occurred with this algorithm!" + "\n"
								+"Please make sure you choose the correct file in the file browser and try again.");
						return;
					}
				}
				else if(lb2.getText().equals("White")){
					mw.setNames(tf2.getText(), tf1.getText());
					if(!mw.createAlgorithmWhite(fb1.GetText())){
						JOptionPane.showMessageDialog(null, "A problem occurred with this algorithm!" + "\n"
								+"Please make sure you choose the correct file in the file browser and try again.");
						return;
					}
				}
				mw.StartGame();

			} else if (cvcClicked) {
				mw.clearAlgorithms();
				mw.setNames(tf1.getText(), tf2.getText());
				if(!mw.createAlgorithmWhite(fb1.GetText())){
					JOptionPane.showMessageDialog(null, "A problem occurred with algorithm " + tf1.getText() + "\n"
							+"Please make sure you choose the correct file in the file browser and try again.");
					return;
				}
				if(!mw.createAlgorithmBlack(fb2.GetText())){
					JOptionPane.showMessageDialog(null, "A problem occurred with  algorithm " + tf2.getText() + "\n"
							+"Please make sure you choose the correct file in the file browser and try again.");
					return;
				}
				mw.StartGame();
			}
		}
	}
}
