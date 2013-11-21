package view;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ViewSquare extends JPanel {
	private static final long serialVersionUID = -3355604400075589851L;

	private ViewBoard viewBoard;
	private ViewPiece contains;
	private int[] coordinates;

	public ViewSquare(ViewBoard vb, int x, int y) {
		super(new BorderLayout());
		this.viewBoard = vb;
		coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
		this.addMouseListener(viewBoard);
	}

	public ViewPiece Contains() {
		return contains;
	}
	
	public int[] GetCoordinates(){
		return coordinates;
	}

	public void SetContent(ViewPiece vp) {
		contains = vp;
		this.removeAll();
		if(contains!=null){
			contains.SetBackgroundColor(this.getBackground());
			this.add(vp);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (contains != null) {
			contains.setBackground(this.getBackground());
		}
	}
}
