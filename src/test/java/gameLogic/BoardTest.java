package gameLogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board;
	private Stone stoneBlack;
	private Stone stoneWhite;


@Before
public void runBeforeEveryTest() {
	board = new Board(9);
	stoneBlack = new Stone(1);
	stoneWhite = new Stone(2);
}

@After
public void runAfterEveryTest() {
	board = null;
}

	@Test
	public void addStoneToBoard() {
		try {
			board.addStoneToBoard(stoneBlack, 0,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			board.addStoneToBoard(stoneWhite, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			assertEquals(board.getStoneFromBoard(0,0).getColor(), stoneBlack.getColor());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	@Test 
	public void fieldWithoutStone() 	{		
		
			assertEquals(board.getStoneFromBoard(2, 2), null);
	
	} 
	

	@Test(expected = ArrayIndexOutOfBoundsException.class) 
	public void addStoneOutOfBounds() throws Exception 	{		
		
		board.addStoneToBoard(stoneBlack, 9, 9);
	
	} 

}
