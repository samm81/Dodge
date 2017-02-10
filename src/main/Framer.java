package main;
import java.awt.Color;

import javax.swing.JFrame;


public class Framer {

	public Framer(String title, int x, int y, int width, int height, DoubleBufferedCanvas canvas) {
		JFrame f = new JFrame(title);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(x, y, width, height);
		
		canvas.setBackground(Color.BLACK);

		f.add(canvas);
		
		f.setVisible(true);

		canvas.start();
		
		canvas.requestFocus();
	}
}
