package gameLogic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CapturedAlghTest {

	private Board board;
	private Stone stone;
	private Stone stoneWhite;
	private CapturedAlgh algh;
	
	@Before
	public void runBeforeEveryTest() {
		board = new Board(9);
		stone = new Stone();
		algh = new CapturedAlgh(board);

	}

	@After
	public void runAfterEveryTest() {
		board = null;
	}
	
	@Test
	public void capturedInACorner(){
		//lewy gorny rog
		try{
		board.addStoneToBoard(stone, 0, 0);
		board.addStoneToBoard(stone, 1, 0);
		board.addStoneToBoard(stone, 1, 1);
		board.addStoneToBoard(stone, 0, 1);
		
		//prwy gorny rog
		board.addStoneToBoard(stone, 0, 8);
		board.addStoneToBoard(stone, 0, 7);
		board.addStoneToBoard(stone, 1, 7);
		board.addStoneToBoard(stone, 1, 8);
		
		//lewy dolny rog
		board.addStoneToBoard(stone, 8, 0);
		board.addStoneToBoard(stone, 7, 0);
		board.addStoneToBoard(stone, 7, 1);
		board.addStoneToBoard(stone, 8, 1);
		
		//prawy dolny rog
		board.addStoneToBoard(stone, 8, 8);
		board.addStoneToBoard(stone, 7, 8);
		board.addStoneToBoard(stone, 7, 7);
		board.addStoneToBoard(stone, 8, 7);
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
	//	Stone leftUpCorner = board.getStoneFromBoard(0, 0);
	//	Stone rightDownCorner = board.getStoneFromBoard(8, 8);
		assertEquals(algh.checkCapture(0, 0), true);
		assertEquals(algh.checkCapture(8, 8), true);
		assertEquals(algh.checkCapture(8, 0), true);
		assertEquals(algh.checkCapture(0, 8), true);
			
	}
	
	@Test
	public void capturedInSides(){
		//lewa strona
				try{
				board.addStoneToBoard(stone, 2, 0);
				board.addStoneToBoard(stone, 3, 0);//zlapany
				board.addStoneToBoard(stone, 4, 0);
				board.addStoneToBoard(stone, 3, 1);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				assertEquals(algh.checkCapture(3, 0), true);
				assertEquals(algh.checkCapture(4, 0), false);
				
				//prawa strona
				try{
				board.addStoneToBoard(stone, 2, 8);
				board.addStoneToBoard(stone, 3, 8);//zlapany
				board.addStoneToBoard(stone, 4, 8);
				board.addStoneToBoard(stone, 3, 7);

				}catch(Exception e){
					e.printStackTrace();
				}
				
				assertEquals(algh.checkCapture(3, 8), true);
				assertEquals(algh.checkCapture(2, 8), false);
				
				//gora
				try{
					board.addStoneToBoard(stone, 0, 4);
					board.addStoneToBoard(stone, 0, 5);//zlapany
					board.addStoneToBoard(stone, 0, 6);
					board.addStoneToBoard(stone, 1, 5);

					}catch(Exception e){
						e.printStackTrace();
					}
				
				assertEquals(algh.checkCapture(0, 5), true);
				assertEquals(algh.checkCapture(0, 4), false);
				
				//dol
				try{
					board.addStoneToBoard(stone, 8, 4);
					board.addStoneToBoard(stone, 8, 5);//zlapany
					board.addStoneToBoard(stone, 8, 6);
					board.addStoneToBoard(stone, 7, 5);

					}catch(Exception e){
						e.printStackTrace();
					}
				
				assertEquals(algh.checkCapture(8, 5), true);
				assertEquals(algh.checkCapture(8, 6), false);
	}
	
	@Test
	public void capturedMiddle(){
		
		try{
			board.addStoneToBoard(stone, 3, 3);
			board.addStoneToBoard(stone, 4, 3);//zlapany
			board.addStoneToBoard(stone, 5, 3);
			board.addStoneToBoard(stone, 4, 2);
			board.addStoneToBoard(stone, 4, 4);
			}catch(Exception e){
				e.printStackTrace();
			}
		assertEquals(algh.checkCapture(4, 3), true);
		assertEquals(algh.checkCapture(4, 4), false);
	}

}
