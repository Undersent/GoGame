package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bots.JustPut;
import gameLogic.Adapter;



//GAME - PLAYER
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
    private JustPut bot;
    boolean isBot=false;

    /**
     * Constructs a handler thread for a given socket and mark
     * initializes the stream fields, displays the first two
     * welcoming messages.
     */
    public Player(Socket socket, char mark, int size) {
    	
    	adapter = new Adapter();
    
    	
        this.socket = socket;
        this.mark = mark;
        try {
            input = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
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
     * Server sends few messages;
     * POINTS<row>
     */
    public void run() {
        try {
        	String command = input.readLine();
        	if(command.startsWith("BOT")){
        	bot = new JustPut(adapter);
        	isBot = true;
        	}
            // The thread is only started after everyone connects.
        	
        	if (command.startsWith("SIZE")){
        		adapter.initializeBoard(Integer.parseInt(command.substring(5)));
        	}
            output.println("MESSAGE All players connected");
         
            // Tell the first player that it is her turn.
            if (mark == 'B') {
                output.println("MESSAGE Your move");
 
            }

            // Repeatedly get commands from the client and process them.
            while (true) {
            	command = input.readLine(); //row and col
                if (command.startsWith("MOVE")) {
                    int row = Integer.parseInt(command.substring(5, command.indexOf(',')));
                    int col = Integer.parseInt(command.substring(command.indexOf(',')));
                    if (adapter.playOnPoint(row, col) && mark == adapter.getPlayer()) {
                        output.println("POINTS "+adapter.toString());
                  
                    	} else {
                    		output.println("MESSAGE Move is impossible");
                    	}
                	} else if (command.startsWith("QUIT")) {
                    return;
                    
                	} else if (isBot && 'B' == adapter.getPlayer()){
                	bot.findBestMove();
                	output.println("MESSAGE Bot moved");
                	output.println("POINTS "+adapter.toString());
                	
                	} else if ( command.startsWith("PASS")){
                		adapter.pass();
                		output.println("MESSAGE Pass");
                	}
                	
            }
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}



