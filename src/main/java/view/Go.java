package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Go extends Application {

		private List<Stone> stones = new ArrayList<Stone>();
	
		private GraphicsContext gc;
		private Label test;
		private Label test2;
		
		private Color currentColor = Color.BLACK;
		
		private int gridWidth = 42;
		private int margin = 22;
		private int gridSize = 19;
		
	    public static void main(String[] args) {
	        launch(args);
	    }
	 
	    @Override
	    public void start(Stage primaryStage) {
	        primaryStage.setTitle("Go Game");
	        BorderPane borderPane = new BorderPane();
	        
	        Canvas canvas = new Canvas(800,800);
	        gc = canvas.getGraphicsContext2D();
	        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
	        	public void handle(MouseEvent e) {
	        		test.setText(String.valueOf(e.getX()));
	        		test2.setText(String.valueOf(e.getY()));
	        		drawGrid(gc);
	        		int x = (int) e.getX()/gridWidth;
	        		int y = (int) e.getY()/gridWidth;
					gc.setFill(currentColor);
					gc.fillOval(1 + x*gridWidth, 1 + y*gridWidth, gridWidth, gridWidth);
				}
	        });
	        
	        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					int x = (int) e.getX()/gridWidth;
	        		int y = (int) e.getY()/gridWidth;
	        		stones.add(new Stone(1 + x*gridWidth, 1 + y*gridWidth, gridWidth, gridWidth));
	        		drawGrid(gc);
				}
	        	
	        });
	        
	        drawGrid(gc);
	        borderPane.setCenter(canvas);
	        
	        VBox left = new VBox();
	        left.setPrefWidth(320);
	        borderPane.setLeft(left);
	        
	        VBox right = new VBox();
	        right.setPrefWidth(320);
	        borderPane.setRight(right);
	        
	        test = new Label("");
	        right.getChildren().add(test);
	        
	        test2 = new Label("");
	        right.getChildren().add(test2);
	        
	        primaryStage.setScene(new Scene(borderPane));
	        primaryStage.show();
	    }

	    private void drawGrid(GraphicsContext gc) {
	    	gc.setFill(Color.BLACK);
	    	gc.fillRect(0, 0, 800, 800);
	    	gc.drawImage(new Image("img/go_board.jpg", 800, 800, false, false), 0, 0);
	    	gc.setStroke(Color.BLACK);
	        gc.setLineWidth(2);
	        for(int i=0; i <= gridSize ; i++) {
	        	gc.strokeLine((margin + i*gridWidth), margin, (margin + i*gridWidth), 778);
	        	gc.strokeLine(margin, (margin + i*gridWidth), 778, (margin + i*gridWidth));
	        	if(i == 3 || i == 9 || i == 15) {
	        		gc.fillOval((margin + i*gridWidth - 4), (margin + 3*gridWidth - 4), 8, 8);
	        		gc.fillOval((margin + i*gridWidth - 4), (margin + 9*gridWidth - 4), 8, 8);
	        		gc.fillOval((margin + i*gridWidth - 4), (margin + 15*gridWidth - 4), 8, 8);
	        	}
	        }
	    	for(Stone stone: stones) {
	    			gc.fillOval(stone.getX(), stone.getY(), stone.getW(), stone.getH());
	    	}
	    }
}
