package gameLogic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import gameLogic.GameController.StoneColor;

public class Adapter{
	/**
	 * 
	 */
	private static final long serialVersionUID = 593744289599985759L;
	private LinkedList<PointOnBoard> blackPoints = new LinkedList<PointOnBoard>();
	private LinkedList<PointOnBoard> whitePoints = new LinkedList<PointOnBoard>();
	private LinkedList<PointOnBoard> coloredPoints = new LinkedList<PointOnBoard>();
	private GameController gameController;
	private int passes = 0;

	
	  /**
	   * 
	   * @param size of the board size x size 
	   * 
	   */
	  public void initializeBoard(int size)
	  {
		  gameController = new GameController(size);
	  }

	  /**
	   * Processes input and handles game logic. 
	   * @param row of the board
	   * @param col of the board
	   * @return true if move is possible else false (eyes, same move as previous)
	   */
	  public synchronized boolean playOnPoint(int row, int col)
	  {
		  return gameController.playAt(row, col);
	  }
	
	  /**
	   * get all black points which are on a board
	   * @return list of blackPoints
	   */
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
	  /**
	   * get all white points which are on a board
	   * @return list of whitePoints
	   */
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
	  
	  /**
	   * get all points on a board
	   * @return list of PointsOnBoard
	   */
	  public LinkedList<PointOnBoard> getAllColoredPoints(){
		  coloredPoints = new LinkedList<PointOnBoard>();
		  coloredPoints.addAll(getBlackPoints());
		  coloredPoints.addAll(getWhitePoints());
		  return coloredPoints;
		  
	  }
	  
	  /**
	   * get all posible points on board where you can put PointsOnBoard
	   * @return Iterable<PointOnBoard>
	   */
	  public Iterable<PointOnBoard> getAllPoints() {

	        return gameController.getAllPoints();
	    }
	  
	  /**
	   * get player whi can make move
	   * @return player B == BlackPlayer, W == WhitePlayer
	   */
	  public char getPlayer()
	  {
		  return gameController.getPlayer();
	  }
	  
	  /**
	   * pass a move
	   * @return true if game is passeed 3 times else false
	   */
	  public boolean pass(){
		  gameController.pass();
		  passes+=1;
		  if(passes == 2)
			  return true;
		  return false;
	  }
	  
	  
	  /**
	   * set number of passes. look function pass()
	   * @param n number of passes
	   */
	  public void setPasses(int n){
		  passes = n;
	  }


	  /**
	   * get actual playable game
	   * @return gameController
	   */
	  public GameController getGame(){
		  return gameController;
	  }
	  
	  /**
	   * get color of points which exist on a specific pointsOnBoard
	   * @param pointOnBoard information about position
	   * @return color of point
	   */
	  public StoneColor getColor(PointOnBoard pointOnBoard) {
	        return gameController.getColor(pointOnBoard);
	    }
	  

	  /**
	   * It is necessary to send easy interpretable messages to client 
	   */
	  @Override
	  public String toString(){
		  String text ="";
		  for(PointOnBoard pob : getBlackPoints()){
			  text+= pob.toString();
		  }
		  text+="B";
		  
		  for(PointOnBoard pob : getWhitePoints()){
			  text+= pob.toString();
		  }
		  
		return text;
		  
	  }
	  
	  /**
	   * 
	   * @return number of captured points after move
	   */
	  public int getCaptured(){
		  return gameController.getCaptured();
	  }
	  

}
