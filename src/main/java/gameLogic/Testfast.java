package gameLogic;

public class Testfast {

	public static void main(String[] args) {
		Board board = new Board(9);
		Stone stoneB = new Stone(0); //czarny
		Stone stoneW = new Stone(1);
		Stone noCOlor = new Stone();
		System.out.println(noCOlor.getColor());
		
		try {
			board.addStoneToBoard(stoneB, 1, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		board.getStoneFromBoard(1, 1).getClass();
	//	System.out.println(stoneB.getClass().equals(Stone.getClass()));
		
	//	System.out.println(board.getStoneFromBoard(2, 2));

	}

}
