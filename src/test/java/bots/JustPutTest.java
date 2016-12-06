package bots;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gameLogic.Adapter;

public class JustPutTest {

	
	private Adapter adapter;
	private JustPut bot;
	
	@Before
	public void runBeforeEveryTest() {
		 adapter = new Adapter();
		 adapter.initializeBoard(19);
		 bot = new JustPut(adapter);
	}

	@After
	public void runAfterEveryTest() {
		adapter=null;
		bot = null;
	}
	@Test
	public void FindBestMoveTest() {
		adapter.playOnPoint(0, 0);
		bot.findBestMove();
		assertEquals(adapter.getAllColoredPoints().size(),2);
	}

}
