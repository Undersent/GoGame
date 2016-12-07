package server;

import java.net.ServerSocket;

import gameLogic.Adapter;


public class Server {


    /**
     * Runs the application. Pairs up clients that connect.
     */
    public static void main(String[] args) throws Exception {
    	int port = 8901;
        ServerSocket listener = new ServerSocket(port);
        System.out.println("Go Server is Running");
        try {
            while (true) {
               	Adapter adapter = new Adapter();
               	adapter.initializeBoard(19);
                Player playerB = new Player(listener.accept(), 'B', adapter);
                Player playerW = new Player(listener.accept(), 'W', adapter );
                playerB.setOpponent(playerW);
                playerW.setOpponent(playerB);
          
                playerB.start();
                playerW.start();
            }
        } finally {
            listener.close();
        }
    }
}

