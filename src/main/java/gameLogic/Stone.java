package gameLogic;

public class Stone {
	/**
	 * 1 for black, 2 for white
	 */
	private int color;
	
	Stone(){}
	
	Stone(int color){
		this.color = color;
	}
	
	public int getColor(){
		
		return this.color;
	}
}
