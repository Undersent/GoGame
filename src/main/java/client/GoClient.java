package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
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
import network.NetworkConnection;
import view.Go;

public class GoClient extends Application {

	private final int PORT = 8901;
	private final String SERVER_ADDRESS = "localhost";
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	private static GraphicsContext gc;
	private Label test;
	private Label test2;
	private Thread client;
	
	private int gridWidth = 42;
	private int margin = 22;
	private int gridSize = 19;
	
	public GoClient() throws Exception {
<<<<<<< HEAD
		client = new Thread() {
			@Override
			public void run() {
				try {
					socket = new Socket(SERVER_ADDRESS, PORT);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					out = new PrintWriter(socket.getOutputStream(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					sendBoardSize();
					play();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		client.start();
=======
		//Setup networking
		System.out.println(3);
		socket = new Socket(SERVER_ADDRESS, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
>>>>>>> branch 'master' of https://github.com/Undersent/GoGame.git
	}
	
    public static void main(String[] args) throws Exception {
<<<<<<< HEAD
        launch(args);

=======
    	System.out.println(1);
        
        System.out.println(2);

        
        launch(args);
>>>>>>> branch 'master' of https://github.com/Undersent/GoGame.git
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {System.out.println(4);
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setTitle("Go Game");
        primaryStage.setResizable(false);
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
				gc.strokeOval(1 + x*gridWidth, 1 + y*gridWidth, gridWidth, gridWidth);
			}
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int x = (int) e.getX()/gridWidth;
        		int y = (int) e.getY()/gridWidth;
        		makeMove(y, x);
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
        
        GoClient client = new GoClient();
        client.play();
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
    }
	
	public void play() throws Exception {
		String response;
		
		try {
<<<<<<< HEAD
			response = in.readLine();
			
			if(response.startsWith("WELCOME")) {
				System.out.println("dostalem wiadomosc");
			}
=======
			   response = in.readLine();
	            if (response.startsWith("WELCOME")) {
	                System.out.println(response);
	            }
>>>>>>> branch 'master' of https://github.com/Undersent/GoGame.git
			
			while(true) {
				response = in.readLine();
				if(response.startsWith("POINTS")) {
					System.out.println("dostalem punkty");
				}
				 else if (response.startsWith("MESSAGE")) {
	                    System.out.println(response.substring(8));
	                }
			}
		}
		
		finally {
			socket.close();
		}
	}
	
	public void makeMove(int col, int row) {
		System.out.println("MOVE " + col + "," + row);
		out.println("MOVE " + col + "," + row);
	}
	
	public void sendBoardSize() {
		System.out.println("SIZE " + gridSize);
		out.println("SIZE " + gridSize);
	}
}
