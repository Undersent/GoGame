package gameLogic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdapterTest {

	private Adapter adapter;
	
	
	@Before
	public void runBeforeEveryTest() {
		 adapter = new Adapter();
		 adapter.initializeBoard(19);
	}

	@After
	public void runAfterEveryTest() {
		adapter=null;
	}
	
	
	@Test
	public void test() {
		assertEquals(adapter.getPlayer(), 'B');
		adapter.playOnPoint(0, 0);
		assertEquals(adapter.getPlayer(), 'W');
		adapter.playOnPoint(1, 1);
		assertEquals(adapter.getPlayer(), 'B');
		adapter.playOnPoint(2, 2);


		assertEquals(adapter.getWhitePoints().size(),1);
		//check that whites disappear
		adapter.playOnPoint(1, 2);
		adapter.playOnPoint(2, 1);
		adapter.playOnPoint(0, 4);
		adapter.playOnPoint(1, 0);
		adapter.playOnPoint(1, 4);
		adapter.playOnPoint(1, 3);
		adapter.playOnPoint(2, 4);
		adapter.playOnPoint(0, 2);
		adapter.playOnPoint(3, 4);
		adapter.playOnPoint(0, 1);
	//	System.out.println((adapter.getWhitePoints().toString()));
		assertEquals(adapter.getBlackPoints().size(),7);
	//	System.out.println(adapter.getWhitePoints().toString());
		assertEquals(adapter.getWhitePoints().size(),4);
		assertEquals(adapter.getAllColoredPoints().size(),11);
		assertEquals(adapter.getAllColoredPoints().size(),11);
	//	assertEquals(adapter.getAllPoints().size(),361);

		
	}

}
