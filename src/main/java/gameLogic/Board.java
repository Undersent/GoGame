package gameLogic;

import java.awt.Point;

public class Board {
	private int size;
	private Stone[][] boardArray;
	
	Board(int size){
		this.size=size;
		boardArray = new Stone[size][size];
		for(Stone[] x : boardArray)
		{
			for(Stone element : x )
			{
				element = null;
			}
		}
	}
	
	public void addStoneToBoard(Stone stone, int x, int y) throws Exception{
		if(boardArray[x][y] == null){
			boardArray[x][y] = stone;
		}else
			throw new Exception("Field is Taken");
	}
	


	public Stone getStoneFromBoard(int x, int y)  {
	
			return boardArray[x][y];
			
	}
	
	public int getSize(){
		return boardArray.length;
	}
	
	
}
