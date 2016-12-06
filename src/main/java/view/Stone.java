package view;

import javafx.scene.paint.Paint;

public class Stone {
	private int x;
	private int y;
	private int w;
	private int h;
	private Paint fillColor;
	
	public Stone(int x, int y, int w, int h, Paint fillColor) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.fillColor = fillColor;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	public Paint getFillColor() {
		return fillColor;
	}
}
