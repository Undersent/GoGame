package gameLogic;

public class CapturedAlgh {
	
	Board board;
	
	CapturedAlgh(Board board){
		this.board = board;
	}
	
	/**
	 *  Checks to see if the grid point passed in is captured.
	 *  @param x,y positions on a board
	 * @return 
	 **/
	public boolean checkCapture(int x, int y) {
		if (!isOccupied(x,y)) {
		     return false;//can't be captured; no one is there!
			}
				
				
		return true;
	}

	private boolean isOccupied(int x, int y) {
		int maxArrayNum = board.getSize() - 1;
		//sprawdzamy rogi najpierw lewa strone potem prawa
		if(x==0 && y == 0){
			if((board.getStoneFromBoard(1,0) != null)
					&& (board.getStoneFromBoard(1, 1)) != null
					&& (board.getStoneFromBoard(0, 1)) != null){
				return true;
			}return false;
		}
		else if(x == maxArrayNum && y == 0){
			if(board.getStoneFromBoard(maxArrayNum-1, 0) != null
					&& board.getStoneFromBoard(maxArrayNum-1, 1) != null
					&& board.getStoneFromBoard(maxArrayNum, 1) != null){
				return true;
			}return false;
		}
		else if(x==0 && y == maxArrayNum){
			if(board.getStoneFromBoard(x, maxArrayNum-1) !=null
					&& board.getStoneFromBoard(1, maxArrayNum-1) != null
				    && board.getStoneFromBoard(1, maxArrayNum) != null){
				 return true;
			}return false;
		}
		else if(x == maxArrayNum && y == maxArrayNum){
			if(board.getStoneFromBoard(maxArrayNum, maxArrayNum - 1) != null
					&& board.getStoneFromBoard(maxArrayNum - 1, maxArrayNum - 1) !=null
					&& board.getStoneFromBoard(maxArrayNum - 1, maxArrayNum) !=null){
				return true;
			}return false;
		}
		//lewa strona
		if(x>=1 && x<= maxArrayNum-1 && y == 0){
			
				if(board.getStoneFromBoard(x-1, y) != null &&
						board.getStoneFromBoard(x, y+1) != null &&
						board.getStoneFromBoard(x+1, y) != null){
					return true;
				}return false;		
		}
		
		//prawa strona
		if(x>=1 && x<= maxArrayNum-1 && y == maxArrayNum){
				if(board.getStoneFromBoard(x-1, maxArrayNum) != null &&
						board.getStoneFromBoard(x, maxArrayNum - 1) != null &&
						board.getStoneFromBoard(x+1, maxArrayNum) != null){
					return true;
				}return false;
			
		}
		
		
		//dol
		if(y>=1 && y<= maxArrayNum-1 && x == maxArrayNum){
			if(board.getStoneFromBoard(maxArrayNum, y-1) != null &&
					board.getStoneFromBoard(maxArrayNum, y+1) != null &&
					board.getStoneFromBoard(maxArrayNum - 1, y) != null){
				return true;
			}return false;
		}
		
		
		//gora
		if(y>=1 && y<= maxArrayNum-1 && x == 0){
			if(board.getStoneFromBoard(0, y-1) != null &&
					board.getStoneFromBoard(0, y+1) != null &&
					board.getStoneFromBoard(1, y) != null){
				return true;
			}return false;
		}
		
		// reszta- srodek
		
		else{
			if(board.getStoneFromBoard(x-1, y) != null
					&& board.getStoneFromBoard(x+1, y) != null
					&& board.getStoneFromBoard(x, y-1) != null
					&& board.getStoneFromBoard(x, y+1) != null){
				return true;
			}return false;
		}
	}
}
		
		
		
	