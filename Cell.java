package agario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Cell {
	
	private int size, xVel, yVel, width;
	private double speed, x, y;
	private Color color;

	public Cell(int x, int y, int size, Color color) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.width = size;
		color = Color.black;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		updateSpeed();

		g.setColor(color);
		g.drawOval((int) x - size / 2, (int) y - size / 2, size, size);
		g.fillOval((int) x - size / 2, (int) y - size / 2, size, size);

	}

	public void updateSpeed() {
		speed = (size / Math.pow(size, 1.14)) * 10;
	}

//	public void movePlayer(int x, int y) {
//		this.x = x;
//		this.y = y;
//	}

	public void movePlayer(double mx, double my) { // y cant you follow close???

		double absolute = Math.sqrt(Math.pow((mx - x), 2) + Math.pow((my - y), 2));
		
		
		
		if (Math.abs(mx - x) > 5)
			this.x += speed * (mx - x) / absolute;
		else
			this.x += 0;
		if (Math.abs(my - y) > 5)
			this.y += speed * (my - y) / absolute;
		else
			this.y += 0;
	}

	public void randomize() {
		Random rand = new Random();

		int pooper = rand.nextInt(4);
		if (pooper == 0) { // top
			x = rand.nextInt(1000);
			y = -35 - size / 2;
			xVel = rand.nextInt(3) + 1;
			yVel = rand.nextInt(3) + 1;
		} else if (pooper == 1) { // left
			x = -20 - size;
			y = rand.nextInt(800);
			xVel = rand.nextInt(3) + 1;
			yVel = rand.nextInt(3) + 1;
		} else if (pooper == 2) { // bottom
			x = rand.nextInt(1000);
			y = 800 + 20 + size / 2;
			xVel = rand.nextInt(3) - 5;
			yVel = rand.nextInt(3) - 5;
		} else if (pooper == 3) { // right
			x = 1000 + 30 + size;
			y = rand.nextInt(800);
			xVel = rand.nextInt(3) - 5;
			yVel = rand.nextInt(3) - 5;
		}

		size = (int) rand.nextInt((int) (45 + 2 * Gameplay.score)) + 5; // work on for AI
		width = size;

		color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
		color = color.darker();
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public void move() {
		x += xVel;
		y += yVel;
	}

	public int getSize() {
		return size;
	}

	public void grow() {
		size++;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
