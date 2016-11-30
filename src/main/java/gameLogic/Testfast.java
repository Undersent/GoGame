package gameLogic;

import java.util.LinkedList;

import gameLogic.GameController.StoneColor;

public class Testfast {

	public static void main(String[] args) {
		
		
		GameBoard gameBoard = new GameBoard(9);
		GameController gameController = gameBoard.getGameState();
		 int row = Math.round((float) (5));
		 int col = Math.round((float) (5));
		 gameController.playAt(2, 3);
		 gameController.playAt(2, 4);
		 gameController.playAt(0, 0);
		//System.out.println(gameState.getAllPoints());
		System.out.println(gameController.playAt(row,col));
		
		  System.out.println("Czarny");
		  LinkedList<PointOnBoard> czarne = new LinkedList<PointOnBoard>();
		  LinkedList<PointOnBoard> biale = new LinkedList<PointOnBoard>();
		  for (PointOnBoard gp : gameController.getAllPoints()) {
	            StoneColor stoneColor = gameController.getColor(gp);
	            if (stoneColor != StoneColor.NONE) {
	                if (stoneColor == StoneColor.BLACK) {
	                 czarne.add(gp);
	                } else {
	                	 biale.add(gp);
	                }
	            }
	        }
		  System.out.println(czarne.toString());
		
		
	
	}

}
