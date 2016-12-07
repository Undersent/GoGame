package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import network.Client;
import network.NetworkConnection;
import network.Server;

public class App extends Application {

	private static GraphicsContext gc;
	
	private int gridWidth = 42;
	private int margin = 22;
	private int gridSize = 19;
	
	private boolean isServer = false;
	
	private NetworkConnection connection = isServer ? createServer() : createClient();
	
	String[] blackPoints;
	String[] whitePoints;
	
	@Override
	public void init() throws Exception {
		connection.startConnection();
	}

    public static void main(String[] args) throws Exception {
        launch(args);

    }
	
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Go Game");
        primaryStage.setResizable(false);
        BorderPane borderPane = new BorderPane();
        
        Canvas canvas = new Canvas(800,800);
        gc = canvas.getGraphicsContext2D();
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent e) {

        		drawGrid(gc);
        		int x = (int) e.getX()/gridWidth;
        		int y = (int) e.getY()/gridWidth;
				gc.strokeOval(1 + x*gridWidth, 1 + y*gridWidth, gridWidth, gridWidth);
			}
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int x = (int) e.getX()/gridWidth;
        		int y = (int) e.getY()/gridWidth;
        		try {
					connection.send("MOVE " + y + "," + x);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });
        
        drawGrid(gc);
        borderPane.setCenter(canvas);
        
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
        String[] point = null;
        if(blackPoints != null) {
        	for(int i=0; i<blackPoints.length;i++) {
        		gc.setFill(Color.BLACK);
        		point = blackPoints[i].split(",");
        		if(point != null) {
	        		int y=Integer.parseInt(point[0]);
	        		int x=Integer.parseInt(point[1]);
	        		gc.fillOval(1 + x*gridWidth, 1 + y*gridWidth, gridWidth, gridWidth);
        		}
        	}
        }
        if(whitePoints != null) {
        	for(int i=0; i<whitePoints.length;i++) {
        		gc.setFill(Color.WHITE);
        		point = whitePoints[i].split(",");
        		if(!point[0].equals("") && !point[1].equals("")) {
	        		int y=Integer.parseInt(point[0]);
	        		int x=Integer.parseInt(point[1]);
	        		gc.fillOval(1 + x*gridWidth, 1 + y*gridWidth, gridWidth, gridWidth);
        		}
        	}
        }
    }
	
	@Override
	public void stop() throws Exception {
		connection.closeConnection();
	}
	
	private Server createServer() {
		try {
			return new Server(8901, data -> {
				Platform.runLater(() -> {
					System.out.println(data.toString());
				});
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	private Client createClient() {
		try {
			return new Client("localhost", 8901, data -> {
				Platform.runLater(() -> {
					System.out.println(data.toString());
					if(data.toString().startsWith("POINTS")) {
						updatePoints(data.toString().substring(7));
					}
				});
			});
		} catch (Exception e) {
			return null;
		}
	}

	public void updatePoints(String points) {
		int endOfBlackPoints = points.indexOf('B') - 1;
		blackPoints = points.substring(0, endOfBlackPoints).split(";");
		whitePoints = points.substring(endOfBlackPoints + 2).split(";");
		drawGrid(gc);
	}
	
}
