package agario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

	private boolean play = true;
	public static int score = 0;
	public static boolean mouseClicked = false;

	private Timer timer;
	private int delay = 0;

	Button b, exit, clear, clean;

	int n = 50;
	private Cell[] cells = new Cell[n + 1];
	private Cell player;
	private boolean inScoreBoard = false;
	public static int x, y;
	private static ArrayList<Integer> scores;

	public Gameplay() {
		addMouseMotionListener(this);
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		b = new Button(450, 200, 100, 25, Color.black, "HIGHSCORES");
		exit = new Button(700, 10, 100, 25, Color.black, "EXIT");
		clear = new Button(700, 700, 100, 25, Color.black, "CLEAR");
		clean = new Button(700, 650, 100, 25, Color.black, "CLEAN");
		player = new Cell(500, 400, 20, Color.black);
		for (int i = 0; i < n; i++) {
			cells[i] = new Cell(0, 0, 0, null);
			cells[i].randomize();
		}
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (play) {
			checkExistance();
			checkCollisions();
			sortCells();
			for (int i = 0; i < n; i++) {
				cells[i].draw(g);
			}

//			player.movePlayer(x, y);
			g.setColor(Color.black);
			player.draw(g);

			g.setColor(Color.black);
			g.drawString("Score " + score, 500, 10);
		} else if (inScoreBoard) {
			g.drawString("Place: ", 350, 100);
			for (int i = 0; i < scores.size(); i++) {
				g.drawString(String.valueOf(i + 1), 350, 113 + i * 13);
			}
			g.drawString("Score: ", 400, 100);
			for (int i = 0; i < scores.size(); i++) {
				g.drawString(String.valueOf(scores.get(i)), 400, 113 + i * 13);
			}

			if (exit.clicked()) {
				play = false;
				inScoreBoard = false;
			}
			if (clear.clicked()) {
				play = false;
				inScoreBoard = true;
				scores.clear();// TODO: maybe clear text file too?
			}
			if (clean.clicked()) {
				play = false;
				inScoreBoard = true;
				for (int i = 0; i < scores.size(); i++) {
					if (scores.get(i) == 0) {
						scores.remove(i);
					}
				}
			}
			exit.draw(g);
			clear.draw(g);
			clean.draw(g);
		} else {
			g.setColor(Color.BLACK);
			g.drawString("You Lose", 500, 380);
			g.drawString("Score " + score, 500, 400);
			g.drawString("Click to play again", 500, 430);

			b.draw(g);
			if (b.clicked()) {
				play = false;
				inScoreBoard = true;
			}
		}
//		repaint(); // dont redraw untill now
		mouseClicked = false;
	}

	public void sortCells() {
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n; j++) {
				if (cells[i].getSize() < cells[i + 1].getSize()) {
					Cell tempCell;
					tempCell = cells[i];
					cells[i] = cells[i + 1];
					cells[i + 1] = tempCell;
				}
			}
		}
	}

	public void checkExistance() {
		for (int i = 0; i < n; i++) {
			if (cells[i].getX() < -30 - cells[i].getSize() || cells[i].getX() > 1000 + 30 + cells[i].getSize()) {
				cells[i] = newCell();
			}
			if (cells[i].getY() < -30 - cells[i].getSize() || cells[i].getY() > 800 + 30 + cells[i].getSize()) {
				cells[i] = newCell();
			}
		}
	}

	public Cell newCell() {
		Cell newCell = new Cell(0, 0, 0, null);
		newCell.randomize();
		return newCell;
	}

//	public void warpTime() {
//		for (int i = 0; i < n; i++) {
//			double distance = Math.hypot(Math.abs(player.getX() - cells[i].getX()),
//					Math.abs(player.getY() - cells[i].getY()));
//			double max = (player.getSize() + cells[i].getSize()) / 2;
//			if (distance < max) {
//				player.setWarp((int) (max - distance));
//				cells[i].setWarp((int) (max - distance));
//			} else {
//				player.setWarp(0);
//				cells[i].setWarp(0);
//			}
//		}
//	}

	public void checkCollisions() {
		for (int i = 0; i < n; i++) {
			if (Math.hypot(Math.abs(player.getX() - cells[i].getX()), Math.abs(player.getY() - cells[i].getY())) < .75
					* (player.getSize() + cells[i].getSize()) / 2) {
				if (Math.abs(player.getSize() - cells[i].getSize()) > .15
						* Math.min(player.getSize(), cells[i].getSize())) {
					if (player.getSize() > cells[i].getSize()) {
						cells[i] = newCell();
						player.grow();
						score++;

					} else {
						play = false;
						player.setSize(20);

						scores.add(score);

						File log = new File("src/AgarScores.txt");
						try {
							if (log.exists() == false) {
								System.out.println("We had to make a new file.");
								log.createNewFile();
							}
							PrintWriter out = new PrintWriter(new FileWriter(log, true));
							String temp = String.valueOf(score);
							for (int k = 0; k < temp.length(); k++) {
								out.append(temp.charAt(k));
							}
							out.append('\n');
							out.close();
						} catch (IOException e) {
							System.out.println("COULD NOT LOG!!");
						}

						Collections.sort(scores);
						scores = reverseArrayList(scores);
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		player.movePlayer(x, y);
		for (int i = 0; i < n; i++) {
			cells[i].move();
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClicked = true;
		if (!play && !b.clicked() && !exit.clicked() && !clear.clicked() && !inScoreBoard && !clean.clicked()) {
			play = true;
			score = 0;
			for (int j = 0; j < n; j++) {
				cells[j] = newCell();
			}
		}
//		for (int i = 0; i < n; i++) { // troubleshoot mode
//			cells[i].move();
//		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public static void setScores(ArrayList<Integer> nw) {
		scores = new ArrayList<Integer>();
		for (int i = 0; i < nw.size(); i++) {
			scores.add(nw.get(i));
		}
		Collections.sort(scores);
		scores = reverseArrayList(scores);
	}

	public static ArrayList<Integer> reverseArrayList(ArrayList<Integer> alist) {
		ArrayList<Integer> revArrayList = new ArrayList<Integer>();
		for (int i = alist.size() - 1; i >= 0; i--) {

			revArrayList.add(alist.get(i));
		}
		return revArrayList;
	}
}
