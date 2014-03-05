package menu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileBrowser extends JPanel {
	private static final long serialVersionUID = -5802272874482656061L;
	private JTextField tf;
	private JButton btn;

	public FileBrowser() {
		super(new FlowLayout());
		tf = new JTextField();
		tf.setEditable(false);
		btn = new JButton("Browse");
		btn.addActionListener(new actionListener(this));

		tf.setPreferredSize(new Dimension(200, 20));
		this.add(tf);
		this.add(btn);
	}

	public String GetText() {
		return tf.getText();
	}

	private class actionListener implements ActionListener {
		private JPanel parent;

		public actionListener(JPanel jp) {
			parent = jp;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(parent);

			File file = null;
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				if (!file.getPath().endsWith(".jar"))
					JOptionPane.showMessageDialog(null,"This is not a valid .jar file");
				else
					tf.setText(file.getPath());
			}
		}
	}
}
