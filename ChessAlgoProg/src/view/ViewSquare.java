package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import board.Square;

public class ViewSquare extends JPanel implements MouseListener {
	private static final long serialVersionUID = -3355604400075589851L;

	private ViewBoard viewBoard;
	private ViewPiece contains;
	private int[] coordinates;
	private Square square;
	private boolean clicked;
	private boolean highLight;
	private boolean enabled;

	public ViewSquare(ViewBoard vb, int x, int y) {
		super(new BorderLayout());
		enabled = true;
		clicked = false;
		highLight = false;
		this.viewBoard = vb;
		coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
		square = viewBoard.GetBoard().GetSquare(x, y);

		this.addMouseListener(this);
	}

	public void disableMove(){
		this.enabled = false;
	}
	public void enableMove(){
		this.enabled = true;
	}

	public void SetClicked(boolean bool) {
		this.clicked = bool;
		if (bool == false) {
			this.removeAll();
			if (contains != null)
				this.add(contains);
			this.repaint();
		}
	}

	public void HighLight(boolean bool) {
		this.highLight = bool;
		if (this.contains != null)
			this.contains.HighLight(bool);
		this.removeAll();
		if (this.contains != null)
			this.add(this.contains);
		this.repaint();
	}

	public ViewPiece Contains() {
		return contains;
	}

	public int[] GetCoordinates() {
		return coordinates;
	}

	public void SetContent(ViewPiece vp) {
		contains = vp;
		this.removeAll();
		if (contains != null) {
			this.add(vp);
			contains.SetBackgroundColor(this.getBackground());
		}
		this.revalidate();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (contains != null) {
			contains.setBackground(this.getBackground());
		}
		if (this.highLight) {
			g.setColor(Color.BLUE);
			g.drawRect(1, 1, this.getWidth() - 3, this.getHeight() - 3);
		}
		if (this.clicked) {
			g.setColor(Color.yellow);
			g.drawRect(1, 1, this.getWidth() - 3, this.getHeight() - 3);
		}
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
		//this.PerformeMouseClick(me);
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		if(enabled)
			this.PerformeMouseClick(me);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		//this.PerformeMouseClick(me);
	}

	protected void PerformeMouseClick(MouseEvent me) {
		if(!viewBoard.GetBoard().InPlay())
			return;
		if (!viewBoard.IsClicked())
			if (square.Contains() != null) {
				if (square.Contains().GetColor() != viewBoard.GetBoard()
						.GetPlayer())
					return;
				if (!square.Contains().CanMove())
					return;
			}

		if (!viewBoard.IsClicked()) { // clicked first
			if (this.contains == null)
				return;
			this.clicked = true;
			viewBoard.SetClicked(true);
			viewBoard.SetClickedSquare(this);
			this.removeAll();
			this.repaint();
		} else { // clicked second
			viewBoard.TryToMove(this);
			viewBoard.SetClicked(false);
		}
	}
}
