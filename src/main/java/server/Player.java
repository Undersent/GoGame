package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import bots.JustPut;
import gameLogic.Adapter;

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
			output.println("WELCOME " + mark);

			output.println("MESSAGE Waiting for opponent to connect");
				
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		}
	}

	/**
	 * Accepts notification of who the opponent is.
	 */
	public void sendMessage(String command) {
		output.println(command);
	}

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
			output.println("MESSAGE All players connected");
	


			// Repeatedly get commands from the client and process them.
			while (true) {
				String command = input.readLine();

				if (command.startsWith("MOVE") ) {
					int row = Integer.parseInt(command.substring(5, command.indexOf(',')));
					int col = Integer.parseInt(command.substring(command.indexOf(',') + 1));

					if (mark == adapter.getPlayer()) {
						if (adapter.playOnPoint(row, col)) {
							adapter.setPasses(0);
							
							opponent.sendMessage("POINTS " + adapter.toString());
							output.println("POINTS " + adapter.toString());
							if(mark == 'B'){
								blackCaptured += adapter.getCaptured();
								output.println("BLACK_POINTS "+ blackCaptured);
								opponent.sendMessage("BLACK_POINTS "+ blackCaptured);
							}else {
								whiteCaptured += adapter.getCaptured();
								output.println("WHITE_POINTS "+ whiteCaptured);
								opponent.sendMessage("WHITE_POINTS "+ whiteCaptured);
							}
						} else {
							output.println("MESSAGE move is impossible");
						}
					} else {
						output.println("MESSAGE Wrong player");
					}
				} else if (command.startsWith("QUIT")) {
					return;

				}  else if (command.startsWith("PASS")) {
					
					if(adapter.getPlayer() == mark){
					if(adapter.pass()){
						output.println("COUNT_TERRITORY");
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
					output.println("BLACK_POINTS "+ blackPoints);
					opponent.sendMessage("BLACK_POINTS "+ blackPoints);
					opponent.sendMessage(command.substring(0, command.indexOf("!")));
				} else if (command.startsWith("TERRITORY_W")){
					int territoryPoints = Integer.parseInt(command.substring(command.indexOf('!')+1));
					int whitePoints = territoryPoints + whiteCaptured;
					output.println("WHITE_POINTS "+ whitePoints);
					opponent.sendMessage("WHITE_POINTS "+ whitePoints);
					opponent.sendMessage(command.substring(0, command.indexOf("!")));
				} else if (command.startsWith("CHAT")){
					opponent.sendMessage(command);
				} else if (command.startsWith("WIN")) {
					opponent.sendMessage(command);
					output.println(command);
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