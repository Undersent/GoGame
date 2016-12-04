
package gameLogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Provides I/O.
 * 
 *
 */
public class GameBoard  {


    public int size;
    private final GameController gameController;

    /**
     * 
     * @param size
     *            number of rows/columns
     */
    public GameBoard(int size) {
        this.size = size;
        gameController = new GameController(size);
    }
    


    public GameController getGameState() {
        return gameController;
    }

}