package gameLogic;

import bots.JustPut;

public class Testfast {

	public static void main(String[] args) {
		
	/*	
		//GameBoard gameBoard = new GameBoard(9);
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
		
		*/
	//	Adapter adapter;
		Adapter adapter = new Adapter();
		 adapter.initializeBoard(19);
		 adapter.playOnPoint(0, 0);
			adapter.playOnPoint(1, 1);
			adapter.playOnPoint(2, 2);
			
		JustPut bot = new JustPut(adapter);
		System.out.println(adapter.getAllColoredPoints().toString());
		System.out.println(adapter.getAllPoints().toString());
		System.out.println(adapter.getBlackPoints().toString());
		bot.findBestMove();
		System.out.println(adapter.getAllColoredPoints().toString());
	
	}

}
