package bots;

import java.util.LinkedList;

import gameLogic.Adapter;
import gameLogic.GameController;
import gameLogic.GameController.StoneColor;
import gameLogic.PointOnBoard;

public class JustPut implements Strategy {
	
	private Adapter adapter;
	private GameController game;
	
	public JustPut(Adapter adapter){
		this.adapter=adapter;
		//game = adapter.getGame();
	}
	
	/**
	 * Function which find just first possible position and put there point
	 */
	@Override
	public void findBestMove() {
		StoneColor stoneColor = null;
	//	LinkedList<PointOnBoard> coloredPoints = new LinkedList<PointOnBoard>();
	//	coloredPoints = adapter.getAllPoints();
		//System.out.println(adapter.getAllPoints().toString());
		for(PointOnBoard point : adapter.getAllPoints()){
			stoneColor = adapter.getColor(point);
			if (stoneColor.equals(GameController.StoneColor.NONE)) {
				if(adapter.playOnPoint(point.getRow(), point.getCol())){
					break;			
				}else
					continue;
				
				}
			}
		}
	}

