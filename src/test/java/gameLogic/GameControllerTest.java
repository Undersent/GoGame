package gameLogic;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gameLogic.GameController.StoneColor;

public class GameControllerTest {
	 private int size;
	 private  GameController gameController;
	 GameController.StoneColor stone;
	@Before
	public void runBeforeEveryTest() {
		 this.size = 9;
	     gameController = new GameController(size);
	}

	@After
	public void runAfterEveryTest() {
		gameController=null;
	}
	
	@Test
	public void constructorTest(){
		  LinkedList<PointOnBoard> none = new LinkedList<PointOnBoard>();
		  for (PointOnBoard point : gameController.getAllPoints()) {
	            GameController.StoneColor stoneColor = gameController.getColor(point);
	            if (stoneColor == GameController.StoneColor.NONE) {
	              none.add(point);
	            }
	        }
		  assertEquals(none.size(),81);
	}
	
	
	@Test
	public void playAtTest() {
		assertEquals(gameController.playAt(0,1), true);
		assertEquals(gameController.playAt(0,1), false);
		assertEquals(gameController.playAt(0,8), true);
		assertEquals(gameController.playAt(0,9), false);
		assertEquals(gameController.playAt(0,7), true);
		assertEquals(gameController.playAt(1,8), true);
		assertEquals(gameController.playAt(0,8), false);
		
		  LinkedList<PointOnBoard> black = new LinkedList<PointOnBoard>();
		  LinkedList<PointOnBoard> white = new LinkedList<PointOnBoard>();
		  for (PointOnBoard gp : gameController.getAllPoints()) {
			  GameController.StoneColor stoneColor = gameController.getColor(gp);
	            if (stoneColor != GameController.StoneColor.NONE) {
	                if (stoneColor == GameController.StoneColor.BLACK) {
	                 black.add(gp);
	                } else {
	                	 white.add(gp);
	                }
	            }
	        }
		  assertEquals(black.size(),2);
		  
		  assertEquals(gameController.playAt(7,0), true);
		  assertEquals(gameController.playAt(8,0), true);
		  assertEquals(gameController.playAt(8,1), true);
		  
		  assertEquals(gameController.getColor(new PointOnBoard(8,0)), GameController.StoneColor.NONE);
		
	}
	
	@Test
	public void getLastMoveTest(){
		gameController.playAt(0,1);
		gameController.playAt(0,8);
		assertEquals(gameController.getLastMove().getCol(),8);
		
	}
	
	@Test
	public void passTest(){
		gameController.playAt(0,1);
		gameController.playAt(0,8);
		gameController.pass();
		assertEquals(gameController.getLastMove(),null);
	}
	

}
