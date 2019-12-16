package agario;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JFrame;

public class Agar {
	
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("Agar.io");
		Gameplay gameplay = new Gameplay();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(10,10,1000,800);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(gameplay);
		
//		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//
//		// Create a new blank cursor.
//		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//		    cursorImg, new Point(0, 0), "blank cursor");
//
//		// Set the blank cursor to the JFrame.
//		frame.getContentPane().setCursor(blankCursor);
		
		ArrayList<Integer> nw = new ArrayList<Integer>();
		File file = new File("src/AgarScores.txt");
		Scanner sc = new Scanner(file);
		while (sc.hasNextInt()) 
			nw.add(sc.nextInt());
		Gameplay.setScores(nw);	
	}
}
