package gameLogic;

import static org.junit.Assert.*;

import org.junit.Test;

import gameLogic.PointOnBoard;

public class PointOnBoardTest {
	
	@Test
	public void test() {
		PointOnBoard point = new PointOnBoard(2,3);
		assertEquals(point.getRow(), 2);
		assertEquals(point.getCol(), 3);
	}

}
