package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import gameLogic.Adapter;


public class Server {


    /**
     * Runs the application. Pairs up clients that connect.
     */
    public static void main(String[] args) throws Exception {
    	int port = 8901;
        ServerSocket listener = new ServerSocket(port);
        System.out.println("Go Server is Running");
        int size =9;
        try {
            while (true) {
            
                Player playerB = new Player(listener.accept(), 'B', size);
                Player playerW = new Player(listener.accept(), 'W', size );
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

