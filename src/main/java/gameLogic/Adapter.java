package gameLogic;

import java.util.HashMap;
import java.util.LinkedList;

import gameLogic.GameController.StoneColor;

public class Adapter {
	private LinkedList<PointOnBoard> blackPoints = new LinkedList<PointOnBoard>();
	private LinkedList<PointOnBoard> whitePoints = new LinkedList<PointOnBoard>();
	private LinkedList<PointOnBoard> coloredPoints = new LinkedList<PointOnBoard>();
	private GameController gameController;
	private StoneColor stone;
	

	  public void initializeBoard(int size)
	  {
		  gameController = new GameController(size);
	  }
	
	  public boolean playOnPoint(int row, int col)
	  {
		  return gameController.playAt(row, col);
	  }
	
	  public LinkedList<PointOnBoard> getBlackPoints()
	  {
		  blackPoints=new LinkedList<PointOnBoard>();
		  for (PointOnBoard point : gameController.getAllPoints()) {
			  GameController.StoneColor stoneColor = gameController.getColor(point);
	            if (stoneColor != GameController.StoneColor.NONE) {
	                if (stoneColor == GameController.StoneColor.BLACK) {
	                 blackPoints.add(point);
	                } 
	            }
	        }
		  return blackPoints;
	  }
	  
	  public LinkedList<PointOnBoard> getWhitePoints()
	  {
		  whitePoints=new LinkedList<PointOnBoard>();
		  for (PointOnBoard point : gameController.getAllPoints()) {
			  GameController.StoneColor stoneColor = gameController.getColor(point);
	            if (stoneColor != GameController.StoneColor.NONE) {
	                if (stoneColor == GameController.StoneColor.WHITE) {
	                 whitePoints.add(point);
	                } 
	            }
	        }
		  return whitePoints;
	  }
	  
	  public LinkedList<PointOnBoard> getAllColoredPoints(){
		  coloredPoints = new LinkedList<PointOnBoard>();
		  coloredPoints.addAll(getBlackPoints());
		  coloredPoints.addAll(getWhitePoints());
		  return coloredPoints;
		  
	  }
	  
	  public Iterable<PointOnBoard> getAllPoints() {

	        return gameController.getAllPoints();
	    }
	  

	  
	  public GameController getGame(){
		  return gameController;
	  }
	  
	  public StoneColor getColor(PointOnBoard pointOnBoard) {
	        return gameController.getColor(pointOnBoard);
	    }
}
