package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

import gameLogic.Adapter;
import gameLogic.PointOnBoard;



//GAME - PLAYER
/**
 * A two-player game.
 */
public class Player extends Thread {
    char mark;
    Player opponent;
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    Adapter adapter;

    /**
     * Constructs a handler thread for a given socket and mark
     * initializes the stream fields, displays the first two
     * welcoming messages.
     */
    public Player(Socket socket, char mark, int size) {
    	
    	adapter = new Adapter();
    	adapter.initializeBoard(size); 
    	
        this.socket = socket;
        this.mark = mark;
        try {
            input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
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
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }



    /**
     * The run method of this thread.
     */
    public void run() {
        try {
            // The thread is only started after everyone connects.
            output.println("MESSAGE All players connected");

            // Tell the first player that it is her turn.
            if (mark == 'B') {
                output.println("MESSAGE Your move");char mark2 =adapter.getPlayer();
                output.println("MESSAGE" +mark +" " +mark2);
            }

            // Repeatedly get commands from the client and process them.
            while (true) {
            	String command = input.readLine(); //tutaj powinienem dostawac row i col
                if (command.startsWith("MOVE")) {
                    int row = Integer.parseInt(command.substring(6, command.indexOf(';')));
                    int col = Integer.parseInt(command.substring(command.indexOf(';')));
                    if (adapter.playOnPoint(row, col) && mark == adapter.getPlayer()) {
                        output.println("VALID_MOVE");
                  
                    } else {
                        output.println("MESSAGE ?");
                    }
                } else if (command.startsWith("QUIT")) {
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}



