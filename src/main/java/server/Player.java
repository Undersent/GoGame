package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import gameLogic.Adapter;
import server.messageDecorator.Stream;

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
	private Stream stream;

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
			stream = new Stream(output);
			stream.sendWelcome(mark);
			stream.sendMessage("Waiting for opponent to connect");

		} catch (IOException e) {
			System.out.println("Player died: " + e);
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
			stream.sendMessage("All players connected");
	


			// Repeatedly get commands from the client and process them.
			while (true) {
				String command = input.readLine();

				if (command.startsWith("MOVE") ) {
					int row = Integer.parseInt(command.substring(5, command.indexOf(',')));
					int col = Integer.parseInt(command.substring(command.indexOf(',') + 1));

					if (mark == adapter.getPlayer()) {
						if (adapter.playOnPoint(row, col)) {
							adapter.setPasses(0);
							opponent.stream.sendPoints(adapter.toString());
							stream.sendPoints(adapter.toString());
							if(mark == 'B'){
								blackCaptured += adapter.getCaptured();
								stream.sendBlackPoints(blackCaptured);
								opponent.stream.sendBlackPoints(blackCaptured);
							}else {
								whiteCaptured += adapter.getCaptured();
								stream.sendWhitePoints(whiteCaptured);
								opponent.stream.sendWhitePoints(whiteCaptured);
							}
						} else {
							stream.sendMessage("move is impossible");
						}
					} else {
						stream.sendMessage("Wrong player");
					}
				} else if (command.startsWith("QUIT")) {
					return;

				}  else if (command.startsWith("PASS")) {
					
					if(adapter.getPlayer() == mark){
					if(adapter.pass()){
						stream.sendCountTerritory();
					}
					}
					/*
					 * TUTAJ WYSYLAM PRZECIWNIKOWI TERYTORIUM 
					 *  LICZE territoryPoints CZYLI TYLE ILE DOSTAL PO WYKRZYKNIKU
					 *  i dodaje do capturedPoints czyli tyle ile przejal wrogow
					 *   NASTEPNIE WYSYLAM JE Tobie jako laczna sume punktow
					 * "POINTS_W <liczba punktow zdobytych w calej grze>
					 * Zastanawiam sie jeszcze aby wysylac Ci kto wygral, ale
					 * to juz chyba mozna w kliencie jebnac po prostu porownujesz kto wiecej punktow
					 * ma i wyswietlasz odpowiedni komunikat
					 */
				} else if (command.startsWith("TERRITORY_B")) { 
					int territoryPoints = Integer.parseInt(command.substring(command.indexOf('!')+1));
					int blackPoints = territoryPoints + blackCaptured;
					stream.sendBlackPoints(blackPoints);
					opponent.stream.sendBlackPoints(blackCaptured);
					opponent.stream.sendConcreteString(command.substring(0, command.indexOf("!")));
				} else if (command.startsWith("TERRITORY_W")){
					int territoryPoints = Integer.parseInt(command.substring(command.indexOf('!')+1));
					int whitePoints = territoryPoints + whiteCaptured;
					stream.sendWhitePoints(whitePoints);
					opponent.stream.sendWhitePoints(whitePoints);
					opponent.stream.sendConcreteString(command.substring(0, command.indexOf("!")));
				} else if (command.startsWith("CHAT")){
					opponent.stream.sendConcreteString(command);
				} else if (command.startsWith("WIN")) {
					opponent.stream.sendConcreteString(command);
					stream.sendConcreteString(command);
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