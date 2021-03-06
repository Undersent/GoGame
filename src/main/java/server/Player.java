package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import gameLogic.Adapter;
import server.messageDecorator.*;

//GAME - PLAYER
//LINIA 122 DODANY KOMENTARZ wyjasniajacy jak dzialaja terytoria
/**
 * A two-player game.
 */
public class Player extends Thread {
	char mark;
	private Player opponent;
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private Adapter adapter;
	private int blackCaptured=0, whiteCaptured =0; //blackCaptured - ile zlapal bialych 
	//private Text stream;
	
	private Text blackPointsText;
	private Text whitePointsText;
	private Text welcomeText;
	private Text messageText;
	private Text pointsText;
	private Text countTerritoryText;
	private Text concreteStringText;

	/**
	 * Constructs a handler thread for a given socket and mark initializes the
	 * stream fields, displays the first two welcoming messages.
	 */
	public Player(Socket socket, char mark, Adapter adapter) {

		this.adapter = adapter;
		this.socket = socket;
		this.mark = mark;
		
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			welcomeText = new WelcomeDecorator(new Stream(), output);
			messageText = new MessageDecorator(new Stream(), output);

		//	 output.println("WELCOME " + mark);
	      //      output.println("MESSAGE Waiting for opponent to connect");
						
			welcomeText.sendWithString(String.valueOf(mark));
			messageText.sendWithString("Waiting for opponent to connect");

		} catch (IOException e) {
			System.out.println("Played: " + e);
		}
	}

	/**
	 * Accepts notification of who the opponent is.
	 */

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	/**
	 * The run method of this thread. All server logic is here.
	 * Server get:
	 * MOVE <row>,<col>;
	 * QUIT
	 * PASS
	 * TERRITORY_B <blankPoints> number of points which were chosen by player
	 * TERRITORY_W <blankPoints> number of points which were chosen by player
	 * BOT inform about bot
	 * 
	 * Server send
	 * POINTS
	 * 
	 */
	public void run() {
		try {
			
			 
			blackPointsText = new BlackPointsDecorator(new Stream(), output);
			whitePointsText = new WhitePointsDecorator(new Stream(), output);
			pointsText = new PointsDecorator(new Stream(), output);
			countTerritoryText = new CountTerritoryDecorator(new Stream(), output);
			concreteStringText = new ConcreteStringDecorator(new Stream(), output);
			
			messageText.sendWithString("All players connected");
			
			
			// Repeatedly get commands from the client and process them.
			while (true) {
				String command = input.readLine();
				
				if (command.startsWith("MOVE") ) {
					int row = Integer.parseInt(command.substring(5, command.indexOf(',')));
					int col = Integer.parseInt(command.substring(command.indexOf(',') + 1));

					if (mark == adapter.getPlayer()) {
						if (adapter.playOnPoint(row, col)) {
							adapter.setPasses(0);
							opponent.pointsText.sendWithString(adapter.toString());
							pointsText.sendWithString(adapter.toString());
							if(mark == 'B'){
								blackCaptured += adapter.getCaptured();
								blackPointsText.sendWithInt(blackCaptured);
								opponent.blackPointsText.sendWithInt(blackCaptured);

							}else {
								whiteCaptured += adapter.getCaptured();
								whitePointsText.sendWithInt(whiteCaptured);
								opponent.whitePointsText.sendWithInt(whiteCaptured);
							}
						} else {
							messageText.sendWithString("move is impossible");
						}
					} else {
						messageText.sendWithString("Wrong player");
					}
				} else if (command.startsWith("QUIT")) {
					return;

				}  else if (command.startsWith("PASS")) {
					
					if(adapter.getPlayer() == mark){
						if(adapter.pass()){
							countTerritoryText.send();
						}
					}
				} else if (command.startsWith("TERRITORY_B")) { 
					int territoryPoints = Integer.parseInt(command.substring(command.indexOf('!')+1));
					int blackPoints = territoryPoints + blackCaptured;
					blackPointsText.sendWithInt(blackPoints);
					opponent.blackPointsText.sendWithInt(blackPoints);
					opponent.concreteStringText.sendWithString(command.substring(0, command.indexOf("!")));
				} else if (command.startsWith("TERRITORY_W")){
					int territoryPoints = Integer.parseInt(command.substring(command.indexOf('!')+1));
					int whitePoints = territoryPoints + whiteCaptured;
					whitePointsText.sendWithInt(whitePoints);
					opponent.whitePointsText.sendWithInt(whitePoints);
					opponent.concreteStringText.sendWithString(command.substring(0, command.indexOf("!")));
				} else if (command.startsWith("CHAT")){
					opponent.concreteStringText.sendWithString(command);
				} else if (command.startsWith("WIN")) {
					opponent.concreteStringText.sendWithString(command);
					concreteStringText.sendWithString(command);
				}

 
			}
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		} catch (NullPointerException e) {
			System.out.println("Null" + e);
		}
		finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

}