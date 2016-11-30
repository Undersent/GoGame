package bots;

import java.util.LinkedList;

import gameLogic.Adapter;
import gameLogic.GameController;
import gameLogic.GameController.StoneColor;
import gameLogic.PointOnBoard;

public class JustPut implements Strategy {
	
	private Adapter adapter;
	private GameController game;
	
	JustPut(){
		adapter = new Adapter();
		game = adapter.getGame();
	}
	
	@Override
	public void findBestMove() {
		StoneColor stoneColor = null;
		LinkedList<PointOnBoard> coloredPoints = new LinkedList<PointOnBoard>();
		for(PointOnBoard point : game.getAllPoints()){
			stoneColor = game.getColor(point);
			if (stoneColor.equals(GameController.StoneColor.NONE)) {
				if(game.playAt(point.getRow(), point.getCol())){
					break;			
				}else
					continue;
				
				}
			}
		}
	}


