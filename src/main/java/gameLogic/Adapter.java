package gameLogic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import gameLogic.GameController.StoneColor;

public class Adapter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 593744289599985759L;
	private LinkedList<PointOnBoard> blackPoints = new LinkedList<PointOnBoard>();
	private LinkedList<PointOnBoard> whitePoints = new LinkedList<PointOnBoard>();
	private LinkedList<PointOnBoard> coloredPoints = new LinkedList<PointOnBoard>();
	private GameController gameController;
	private int passes = 0;
	private boolean canBlackPass = true;
	private boolean canWhitePass = true;
	
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
	  
	  public char getPlayer()
	  {
		  return gameController.getPlayer();
	  }
	  
	  public boolean pass(){
		  gameController.pass();
		  passes+=1;
		  if(passes == 2)
			  return true;
		  return false;
	  }
	  
	  
	  public int getPasses(){
		  return passes;
	  }
	  
	  public void setPasses(int n){
		  passes = n;
	  }
	  
	/*  public void allowBlackPass(boolean canPass){	
		canBlackPass = canPass;
	  }
	  
	  public void allowWhitePass(boolean canPass){
		  canWhitePass = canPass;
	  }
	  
	  public boolean canBlackPass(){
		  return canBlackPass;
	  }
	  
	  public boolean canWhitePass(){
		  return canWhitePass;
	  }
	  */
	  

	  
	  public GameController getGame(){
		  return gameController;
	  }
	  
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
	  
	  public int getCaptured(){
		  return gameController.getCaptured();
	  }
	  

}
