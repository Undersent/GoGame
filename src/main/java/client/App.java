package client;

import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	private int height;
	private int gridSize = 19;
	
	private Button vsPlayerButton;
	private Button vsBotButton;
	private Button quitButton;
	
	private Image blackStone;
	private Image whiteStone;
	
	private TextArea messages = new TextArea();
	
	private boolean isServer = false;
	
	private NetworkConnection connection = isServer ? createServer() : createClient();
	
	private String[] blackPoints;
	private String[] whitePoints;
	
	@Override
	public void init() throws Exception {
		connection.startConnection();

	}

    public static void main(String[] args) throws Exception {
        launch(args);
    }
	
    /*
     * Tworzy scene z menu startowym
     */
    private Scene makeMenuScene() {
		BorderPane borderPane = new BorderPane();
    	
		vsPlayerButton = new Button("Gracz vs Gracz");
		vsBotButton = new Button("Gracz vs Komputer");
		quitButton = new Button("KONIEC");
		
		VBox menu = new VBox(20, vsPlayerButton, vsBotButton, quitButton);
		
		borderPane.setCenter(menu);
		
		Scene menuScene = new Scene(borderPane, height, height);
    	
		return menuScene;
    }
    
    @Override
    public void start(Stage primaryStage) {
    	Rectangle2D screen = Screen.getPrimary().getBounds();
    	double firstHeight = screen.getHeight() * 0.6;
    	gridWidth = (int) firstHeight/20;
    	height = gridWidth * 20;

        blackStone = new Image("img/blackStone.png", gridWidth, gridWidth, false, false);
        whiteStone = new Image("img/whiteStone.png", gridWidth, gridWidth, false, false);
    	
    	primaryStage.getIcons().add(new Image("img/icon.jpg"));
    	
        primaryStage.setTitle("Go Game");
        primaryStage.setResizable(false);
        BorderPane borderPane = new BorderPane();
        
        Canvas canvas = new Canvas(height,height);
        gc = canvas.getGraphicsContext2D();
        canvas.setCursor(Cursor.NONE);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent e) {

        		drawGrid(gc);
        		int x = (int) e.getX()/gridWidth;
        		int y = (int) e.getY()/gridWidth;
				gc.strokeOval(gridWidth/2 + x*gridWidth, gridWidth/2 + y*gridWidth, gridWidth, gridWidth);
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
        
        messages.setPrefSize(250, height - 40);
        TextField input = new TextField();
        input.setOnAction(event -> {
        	String message = input.getText();
        	input.clear();
        	
        	try {
				connection.send("CHAT " + message);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	messages.appendText(message + "\n");
        });
        messages.setWrapText(true);
        VBox chat = new VBox(20, messages, input);
        chat.setPrefSize(250, height);
        borderPane.setRight(chat);
        
        HBox playersLabels = new HBox(50, new Label("gracz1"), new Label("gracz2"));
        HBox playersIcons = new HBox(50, new ImageView(blackStone), new ImageView(whiteStone));
        
        Button passButton = new Button("PASS");
        Button resignButton = new Button("RESIGN");
        HBox bottomButoons = new HBox(100, passButton, resignButton);
        borderPane.setBottom(bottomButoons);
        
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					connection.send("PASS");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        });
        

        
        VBox infoPanel = new VBox(20, playersLabels, playersIcons);
        borderPane.setLeft(infoPanel);

        Scene gameScene = new Scene(borderPane);
        
        Scene menuScene = makeMenuScene();
        
        resignButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					connection.send("QUIT");
					connection.closeConnection();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				primaryStage.setScene(menuScene);
			}
        	
        });
        
        vsPlayerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				primaryStage.setScene(gameScene);
				try {
					connection.send("NIEBOT");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
        	
        });
        
        vsBotButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				primaryStage.setScene(gameScene);
				try {
					connection.send("BOT");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		
        	
        });
        
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Platform.exit();
				
			}
        	
        });
        
        primaryStage.setOnCloseRequest(e -> {
    		try {
				connection.closeConnection();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	Platform.exit();
        });
        
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void drawGrid(GraphicsContext gc) {
    	gc.setFill(Color.BLACK);
    	gc.drawImage(new Image("img/go_board.jpg", height, height, false, false), 0, 0);
    	gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        for(int i=0; i <= gridSize ; i++) {
        	gc.strokeLine((gridWidth + i*gridWidth), gridWidth, (gridWidth + i*gridWidth), gridWidth*19);
        	gc.strokeLine(gridWidth, (gridWidth + i*gridWidth), gridWidth*19, (gridWidth + i*gridWidth));
        	if(i == 3 || i == 9 || i == 15) {
        		gc.fillOval((gridWidth + i*gridWidth - 4), (gridWidth + 3*gridWidth - 4), 8, 8);
        		gc.fillOval((gridWidth + i*gridWidth - 4), (gridWidth + 9*gridWidth - 4), 8, 8);
        		gc.fillOval((gridWidth + i*gridWidth - 4), (gridWidth + 15*gridWidth - 4), 8, 8);
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
	        		gc.drawImage(blackStone, gridWidth/2 + x*gridWidth, gridWidth/2 + y*gridWidth);
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
	        		gc.drawImage(whiteStone, gridWidth/2 + x*gridWidth, gridWidth/2 + y*gridWidth);
        		}
        	}
        }
    }
	
	@Override
	public void stop() throws Exception {
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
					} else if(data.toString().startsWith("CHAT")) {
						messages.appendText(data.toString().substring(5) + "\n");
					} else if(data.toString().startsWith("MESSAGE")) {
						messages.appendText("Server: "+ data.toString().substring(8) + "\n");
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
