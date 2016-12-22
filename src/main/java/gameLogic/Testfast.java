package gameLogic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import gameLogic.Adapter;
import server.messageDecorator.*;
import bots.JustPut;

public class Testfast {

	public static void main(String[] args) {
		
	/*	
		//GameBoard gameBoard = new GameBoard(9);
		GameController gameController = gameBoard.getGameState();
		 int row = Math.round((float) (5));
		 int col = Math.round((float) (5));
		 gameController.playAt(2, 3);
		 gameController.playAt(2, 4);
		 gameController.playAt(0, 0);
		//System.out.println(gameState.getAllPoints());
		System.out.println(gameController.playAt(row,col));
		
		  System.out.println("Czarny");
		  LinkedList<PointOnBoard> czarne = new LinkedList<PointOnBoard>();
		  LinkedList<PointOnBoard> biale = new LinkedList<PointOnBoard>();
		  for (PointOnBoard gp : gameController.getAllPoints()) {
	            StoneColor stoneColor = gameController.getColor(gp);
	            if (stoneColor != StoneColor.NONE) {
	                if (stoneColor == StoneColor.BLACK) {
	                 czarne.add(gp);
	                } else {
	                	 biale.add(gp);
	                }
	            }
	        }
		  System.out.println(czarne.toString());
		
		*/
	//	Adapter adapter;
	/*	Adapter adapter = new Adapter();
		 adapter.initializeBoard(19);
		 adapter.playOnPoint(0, 0);
			adapter.playOnPoint(1, 1);
			adapter.playOnPoint(2, 2);
			adapter.playOnPoint(3, 2);
			adapter.playOnPoint(4, 2);
			
			System.out.println(adapter.toString());
			
			
		JustPut bot = new JustPut(adapter);
		System.out.println(adapter.getAllColoredPoints().toString());
		System.out.println(adapter.getAllPoints().toString());
		System.out.println(adapter.getBlackPoints().toString());
		bot.findBestMove();
		System.out.println(adapter.getAllColoredPoints().toString());
		*/
	//	Stream stream = new Stream();  
	//	Text blackPointsText = new BlackPointsDecorator(new Stream()); 
	//	int blackCaptured = 5;
	//	blackPointsText.sendWithInt(blackCaptured);
	/*	System.out.println("start");
		Text messageText;
		messageText = new MessageDecorator(new Stream());
		messageText.sendWithString("Waiting for opponent to connect");
		*/
	/*	char mark = 'b';
		int strMark = mark;			
	//	System.out.println(String.valueOf(mark));
		System.out.println("start");
	 	int port = 8901;
        ServerSocket listener;
		try {
			listener = new ServerSocket(port);
			System.out.println("start");
        PrintWriter output;
        System.out.println("start");
			output = new PrintWriter("A");
			System.out.println("start");
		Stream stream = new Stream();
		Text welcomeText = new WelcomeDecorator(stream, output);
		System.out.println("start2");
		welcomeText = new WelcomeDecorator(stream, output);
		System.out.println("start3");
		welcomeText.sendWithString(String.valueOf(mark));
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		Adapter adapter = new Adapter();
		adapter.initializeBoard(19);
		JustPut bot = new JustPut(adapter);
		bot.findBestMove();
	}
}


