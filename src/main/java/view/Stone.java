package view;

public class Stone {
	private int x;
	private int y;
	private int w;
	private int h;
	
	public Stone(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
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
	
	@Override
	public String toString() {
		return x + " " + y + "\n";
	}
}
