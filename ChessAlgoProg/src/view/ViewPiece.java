package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ViewPiece extends JPanel {
	private static final long serialVersionUID = 6559921411353403213L;
	protected BufferedImage image;
	protected Color bcgColor;

	public ViewPiece(String path) {
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException ex) {
			System.out.println(ex
					+ "ERROR WITH THE IMAGE OF THE ELEMENT (ViewPiece)");
		}
		this.repaint();
	}
	
	public void SetBackgroundColor(Color color){
		this.bcgColor = color;
	}

	public BufferedImage GetImage() {
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0, null);
		this.setBackground(bcgColor);
	}
}
