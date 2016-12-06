package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import view.Go;

public class GoClient {

	private final int PORT = 8901;
	private final String SERVER_ADDRESS = "localhost";
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public GoClient() throws Exception {
		//Setup networking
		socket = new Socket(SERVER_ADDRESS, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void play() throws Exception {
		String response;
		
		try {
			response = in.readLine();
			
			if(response.startsWith("WELCOME")) {
				//TODO co sie ma dziac po polaczeniu klienta
			}
			
			while(true) {
				response = in.readLine();
				if(response.startsWith("POINTS")) {
					Go.drawDrid(response.substring(7));
				}
			}
		}
		
		finally {
			socket.close();
		}
	}
	
	public void makeMove(int col, int row) {
		out.println("MOVE " + col + "," + row);
	}
	
	public void sendBoardSize(int size) {
		out.println("SIZE " + size);
	}
}
