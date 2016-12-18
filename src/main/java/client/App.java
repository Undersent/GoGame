package client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import view.Stone;

public class App extends Application {

	private static GraphicsContext gc;
	
	private int win = 0;
	
	private String mark;
	
	private int gridWidth = 42;
	private int height;
	private int gridSize = 19;
	
	private boolean countTerritory = false;
	private boolean territoryChecked = false;
	
	private Label xl= new Label("Punkty B");
	private Label yl = new Label("Punkty W");
	private Label pB= new Label("");
	private Label pW = new Label("");
	
	private List<Stone> territoryStones = new ArrayList<Stone>();
	private List<Stone> stones = new ArrayList<Stone>();
	
	private Button vsPlayerButton;
	private Button vsBotButton;
	private Button quitButton;
	private Button territory= new Button("Potwiedz");
	
	private Image blackStone;
	private Image whiteStone;
	
	private TextArea messages = new TextArea();
	
	private Client connection;
	
	private String[] blackPoints;
	private String[] whitePoints;

	private List<Stone> enemyStones = new ArrayList<Stone>();
	
	@Override
	public void init() throws Exception {

	}

    public static void main(String[] args) throws Exception {
        launch(args);
    }
	
    /*
     * Tworzy scene z menu startowym
     */
    private Scene makeMenuScene() {
		BorderPane borderPane = new BorderPane();
		
		Label name = new Label("Go Game");
    	
		vsPlayerButton = new Button("Gracz vs Gracz");
		vsPlayerButton.setPrefWidth(300);
		
		vsBotButton = new Button("Gracz vs Komputer");
		vsBotButton.setPrefWidth(300);
		
		quitButton = new Button("KONIEC");
		quitButton.setPrefWidth(300);
		
		VBox menu = new VBox(20, name, vsPlayerButton, vsBotButton, quitButton);
		menu.setAlignment(Pos.CENTER);
		VBox.setMargin(name, new Insets(15, 0, 0, 0));
		VBox.setMargin(quitButton, new Insets(0, 30, 30, 30));
		
		borderPane.setCenter(menu);
		
		Scene menuScene = new Scene(borderPane);
    	
		menuScene.getStylesheets().add("client/style.css");
		
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
        //canvas.setCursor(Cursor.NONE);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent e) {

        		
        		drawGrid(gc);
        		int x = (int) (e.getX()-gridWidth/2)/gridWidth;
        		int y = (int) (e.getY()-gridWidth/2)/gridWidth;
        		if(x==19) x=18;
        		if(y==19) y=18;
				gc.strokeOval(gridWidth/2 + x*gridWidth, gridWidth/2 + y*gridWidth, gridWidth, gridWidth);
			}
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
        		int x = (int) (e.getX()-gridWidth/2)/gridWidth;
        		int y = (int) (e.getY()-gridWidth/2)/gridWidth;
        		if(x==19) x=18;
        		if(y==19) y=18;
        		if(!countTerritory) {
	        		try {
						connection.send("MOVE " + y + "," + x);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		} else {
        			boolean canAddStone = true;
        			for(Stone s: territoryStones) {
        				Rectangle2D r = new Rectangle2D(gridWidth/2 + s.getX()*gridWidth, gridWidth/2 + s.getY()*gridWidth, s.getW(), s.getH());
        				if(r.contains(e.getX(), e.getY())) {
        					canAddStone = false;
        					territoryStones.remove(s);
        					break;
        				}
        			}
        			for(Stone s: stones) {
        				Rectangle2D r = new Rectangle2D(gridWidth/2 + s.getX()*gridWidth, gridWidth/2 + s.getY()*gridWidth, s.getW(), s.getH());
        				if(r.contains(e.getX(), e.getY())) {
        					canAddStone = false;
        					break;
        				}
        			}
        			if(canAddStone && !territoryChecked) territoryStones.add(new Stone(x, y, gridWidth, gridWidth));
        			drawGrid(gc);
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
        territory.setVisible(false);
        territory.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				String message = "TERRITORY_" + mark + " ";
				for(Stone s: territoryStones) {
					message += s.getY() + "," + s.getX() + ";";
				}
				message+= "!"+ territoryStones.size();
				try {
					connection.send(message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				territoryChecked = true;
				territory.setVisible(false);
			}});
        HBox bottomButoons = new HBox(100, passButton, resignButton, territory);
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
        

        
        VBox infoPanel = new VBox(20, playersLabels, playersIcons, xl, yl, pB, pW);
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
					connection = createClient();

				} catch (Exception e) {

				}
				if(!connection.startConnection()) {
					primaryStage.setScene(menuScene);
	        		Alert alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("ERROR");
	        		alert.setHeaderText(null);
	        		alert.setContentText("Nie udalo polaczys sie z serwerem");
	        		alert.showAndWait();
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
    			connection.send("QUIT");
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
        for(Stone s: territoryStones) {
        	gc.setFill(Color.RED);
        	gc.fillOval(gridWidth/2 + s.getX()*gridWidth, gridWidth/2 + s.getY()*gridWidth, s.getW(), s.getH());
        }
        for(Stone s: enemyStones) {
        	gc.setFill(Color.BLUE);
        	gc.fillOval(gridWidth/2 + s.getX()*gridWidth, gridWidth/2 + s.getY()*gridWidth, s.getW(), s.getH());
        }
    }
	
	@Override
	public void stop() throws Exception {
	}
	
	private Client createClient() {
		try {
			return new Client("localhost", 8901, data -> {
				Platform.runLater(() -> {
					if(data.toString().startsWith("WELCOME")) {
						mark = data.toString().substring(8);
					} else if(data.toString().startsWith("POINTS")) {
						updatePoints(data.toString().substring(7));
					} else if(data.toString().startsWith("CHAT")) {
						messages.appendText(data.toString().substring(5) + "\n");
					} else if(data.toString().startsWith("MESSAGE")) {
						messages.appendText("Server: "+ data.toString().substring(8) + "\n");
					} else if (data.toString().startsWith("COUNT_TERRITORY")) {
						messages.appendText("Server: zaznacz terytorium" + "\n");
						countTerritory = true;
						territory.setVisible(true);
						updateStones();
					} else if(data.toString().startsWith("TERRITORY") ) {
						if(!territoryChecked) territory.setVisible(true);
						countTerritory = true;
						String points = data.toString().substring(12);
						String [] enemy = points.split(";");
						String[] point = null;
						updateStones();
			        	for(int i=0; i<enemy.length;i++) {
			        		point = enemy[i].split(",");
			        		if(point != null) {
				        		int y=Integer.parseInt(point[0]);
				        		int x=Integer.parseInt(point[1]);
				        		enemyStones.add(new Stone(x, y, gridWidth, gridWidth));
			        		}
			        	}
			        	drawGrid(gc);
			        	if(win == 2) {
			        		try {
								connection.send("WIN");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	}
					} else if(data.toString().startsWith("BLACK_POINTS")) {
						pB.setText(data.toString().substring(13));
						if(territoryChecked) {
							win++;
						}
					} else if(data.toString().startsWith("WHITE_POINTS")) {
						pW.setText(data.toString().substring(13));
						if(territoryChecked) {
							win++;
						}
					} else if(data.toString().startsWith("WIN")) {
		        		Alert alert = new Alert(AlertType.INFORMATION);
		        		alert.setTitle("WYNIKI");
		        		alert.setHeaderText(null);
		        		int black = Integer.parseInt(pB.getText());
		        		int white = Integer.parseInt(pW.getText());
		        		if(black > white) {
		        			alert.setContentText("BLACK WINS!");
		        		} else if(black < white) {
		        			alert.setContentText("WHITE WINS!");
		        		} else {
		        			alert.setContentText("DRAW!");
		        		}
		        		alert.showAndWait();
					}
				});
			});
		} catch (Exception e) {
			System.out.println("nie udalo sie polaczyc");
			return null;
		}
	}
	
	public void updateStones() {
		String[] point = null;
    	for(int i=0; i<blackPoints.length;i++) {
    		point = blackPoints[i].split(",");
    		if(point != null) {
        		int y=Integer.parseInt(point[0]);
        		int x=Integer.parseInt(point[1]);
        		stones.add(new Stone(x, y, gridWidth, gridWidth));
    		}
    	}
    	for(int i=0; i<whitePoints.length;i++) {
    		point = whitePoints[i].split(",");
    		if(!point[0].equals("") && !point[1].equals("")) {
        		int y=Integer.parseInt(point[0]);
        		int x=Integer.parseInt(point[1]);
        		stones.add(new Stone(x, y, gridWidth, gridWidth));
    		}
    	}
	}
	
	public void updatePoints(String points) {
		int endOfBlackPoints = points.indexOf('B') - 1;
		blackPoints = points.substring(0, endOfBlackPoints).split(";");
		whitePoints = points.substring(endOfBlackPoints + 2).split(";");
		drawGrid(gc);
	}
	
}
