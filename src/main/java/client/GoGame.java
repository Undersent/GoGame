package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GoGame extends Application {
	
	private Button vsPlayerButton;
	private Button vsBotButton;
	private Button quitButton;
	
	private Stage stage;
	
	private int gridWidth;
	private int height;
	
	
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
	
    public static void main(String[] args){
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
    	Rectangle2D screen = Screen.getPrimary().getBounds();
    	double firstHeight = screen.getHeight() * 0.6;
    	gridWidth = (int) firstHeight/20;
    	height = gridWidth * 20;
    	
		stage = primaryStage;
		
		Scene scene = makeMenuScene();
		
        stage.setOnCloseRequest(e -> {
        	Platform.exit();
        });
        
        stage.getIcons().add(new Image("img/icon.jpg"));	
		stage.setResizable(false);
		stage.setScene(scene);
        stage.show();
	}

}
