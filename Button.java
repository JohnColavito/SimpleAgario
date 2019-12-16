package agario;

import java.awt.Color;
import java.awt.Graphics;

public class Button {

	private int x, y, width, height;
	private String text;
	private Color color;
	
	public Button(int x, int y, int width, int height, Color color, String text) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	public boolean clicked() {
		if (Gameplay.mouseClicked && Gameplay.x < x + width && Gameplay.x > x && Gameplay.y < y + height && Gameplay.y > y) {
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawString(text, x, y + height - 2);
		g.drawRect(x, y, width, height);
	}
	
	public void setText(String text) {
		this.text = text;
	}
}