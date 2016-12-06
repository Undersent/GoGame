package gameLogic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * Provides game logic.
 * 
 *
 */
public class GameController {

    /**
     * Black/white stone
     *
     */
    public enum StoneColor {
        BLACK, WHITE, NONE
    }

    private int size;

    private PointOnBoard lastMove;
    private boolean isBlackMove;
    private HashMap<PointOnBoard, StoneColor> stones;
    // Previous position after black played. For "ko rule".
    private HashMap<PointOnBoard, StoneColor> previousBlackPosition;
    private HashMap<PointOnBoard, StoneColor> previousWhitePosition;
    private boolean passedPreviously;
    /**
     * True if any stone was removed this turn.
     */
    private boolean removedStone;

    public GameController(int size) {
        this.size = size;
        // Black always starts
        isBlackMove = true;
        lastMove = null;
        previousBlackPosition = new HashMap<>();
        previousWhitePosition = new HashMap<>();
        initializeBoard();
    }

    /**
     * Initializes the game map with empty PointOnBoard's.
     */
    private void initializeBoard() {
        stones = new HashMap<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                stones.put(new PointOnBoard(row, col), StoneColor.NONE);
            }
        }
    }

    /**
     * Passes the turn
     * 
     */
    public void pass() {

        savePosition();
        lastMove = null;
        passedPreviously = true;

    }

    /**
     * Processes input and handles game logic. 
     * 
     * @param row
     * @param col
     * @return true if move is possible and add it to getAllPoint(),
     * then change player. False if move is invalid
     */
    public boolean playAt(int row, int col) {
        if (isOutOfBoundary(row, col)) {
            return false;
        }
        
        PointOnBoard newStone = getPointAt(row, col);

        if (isOccupied(newStone)) {
            return false;
        }
        removedStone = false;
        addStone(newStone);

        // Suicide is legal if you remove enemy stones with it.
        if (!removedStone && isSuicide(newStone)) {
            return false;
        }
        // "ko rule": previous position can't be repeated
        if ((isBlackMove && previousBlackPosition.equals(stones))
                || (!isBlackMove && previousWhitePosition.equals(stones))) {
            stones = previousBlackPosition;
            return false;
        }
    
        savePosition();

        changePlayer();
        lastMove = newStone;

        return true;

    }

    /**
     * We can check the 'ko' rule if we save previous position
     */
    private void savePosition() {
        if (isBlackMove) {
            previousBlackPosition = new HashMap<>(stones);
        } else {
            previousWhitePosition = new HashMap<>(stones);
        }
    }


    /**
     * Returns true (and removes the Stone) if the move is suicide. You need to
     * actually add the stone first.
     * 
     * @param PointOnBoard
     * @return true if the move is suicide
     */
    private boolean isSuicide(PointOnBoard point) {
        if (isDead(point, new HashSet<PointOnBoard>())) {
            removeStone(point);
            return true;
        }
        return false;
    }

    /**
     * Adds Stone and removes dead neighbors.
     * 
     * @param point which will be added
     */
    private void addStone(PointOnBoard point) {
        StoneColor stoneColor;
        if (isBlackMove) {
            stoneColor = StoneColor.BLACK;
        } else {
            stoneColor = StoneColor.WHITE;
        }

        stones.put(point, stoneColor);

        for (PointOnBoard neighbor :  getAllValidAdjacentLocations(point)) {
            removeIfDead(neighbor);
        }

    }

    private void removeStone(PointOnBoard gp) {
        stones.put(gp, StoneColor.NONE);
    }

    private Set<PointOnBoard>  getAllValidAdjacentLocations(PointOnBoard point) {
        Set<PointOnBoard> neighbors = new HashSet<>();
        // get neighbor down
        if (point.getRow() > 0) {
            neighbors.add(getPointAt(point.getRow() - 1, point.getCol()));
        } // get neighbor up
        if (point.getRow() < size - 1) {
            neighbors.add(getPointAt(point.getRow() + 1, point.getCol()));
        } // get neighbor left
        if (point.getCol() > 0) {
            neighbors.add(getPointAt(point.getRow(), point.getCol() - 1));
        } // get neighbor right
        if (point.getCol() < size - 1) {
            neighbors.add(getPointAt(point.getRow(), point.getCol() + 1));
        }
        return neighbors;
    }

    /**
     * Removes all stones with 0 liberties.
     * @param point starting point
     * 
     */
    private void removeIfDead(PointOnBoard point) {
        Set<PointOnBoard> searchedPoints = new HashSet<>();
        if (isDead(point, searchedPoints)) {
            searchedPoints.add(point);
            if (!searchedPoints.isEmpty()) {
                removedStone = true;
            }
            for (PointOnBoard toRemove : searchedPoints) {
                removeStone(toRemove);
            }
        }
    }

    /**
     * @param pointToCheck starting point
     * @param searchedPoints
     *            set containing already searched stones (of the same color as
     *            starting point)
     * @return false if given stone is alive, but not necessarily true if given
     *         stone is dead (see full description)
     */
    private boolean isDead(PointOnBoard pointToCheck, Set<PointOnBoard> searchedPoints) {
        for (PointOnBoard neighbor :  getAllValidAdjacentLocations(pointToCheck)) {
            if (getColor(neighbor) == StoneColor.NONE) {
                return false;
            }
            if (getColor(neighbor) == getColor(pointToCheck)
                    && !searchedPoints.contains(neighbor)) {
                /*
                 * We add only neighbors that are stones of the same color
                 * because we can afford to check other neighbors more that once
                 */
                searchedPoints.add(neighbor);
                if (!isDead(neighbor, searchedPoints)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    private boolean isOccupied(PointOnBoard gp) {
        return stones.get(gp) != StoneColor.NONE;
    }

    private PointOnBoard getPointAt(int row, int col) {
        return new PointOnBoard(row, col);
    }

    private void changePlayer() {
        isBlackMove = !isBlackMove;
    }
    
    public char getPlayer()
    {
    	return isBlackMove ? 'B' : 'W';
    }

    public Iterable<PointOnBoard> getAllPoints() {
        return stones.keySet();  
    }

    public StoneColor getColor(PointOnBoard pointOnBoard) {
        return stones.get(pointOnBoard);
    }
    
    /**
     * Returns location of last move or null.
     * 
     * @return
     */
    public PointOnBoard getLastMove() {
        return lastMove;
    }
    
    private boolean isOutOfBoundary(int row, int col){
    	if(row >= size || col >= size || row < 0 || col < 0)
    		return true;
    	return false;
    }
}
