package gameLogic;

public final class PointOnBoard {

	private final int row;
	private final int col;

	public PointOnBoard(int row, int col) {
	    this.row = row;
	    this.col = col;
	}
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + col;
        result = prime * result + row;
        return result;
    }
    /**
     * Important for hashArrays
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PointOnBoard other = (PointOnBoard) obj;
        if (col != other.col)
            return false;
        if (row != other.row)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return row + "," + col + ";";
    }
}