package server;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A client for the TicTacToe game, modified and extended from the
 * class presented in Deitel and Deitel "Java How to Program" book.
 * I made a bunch of enhancements and rewrote large sections of the
 * code.  In particular I created the TTTP (Tic Tac Toe Protocol)
 * which is entirely text based.  Here are the strings that are sent:
 *
 *  Client -> Server           Server -> Client
 *  ----------------           ----------------
 *  MOVE <n>  (0 <= n <= 8)    WELCOME <char>  (char in {X, O})
 *  QUIT                       VALID_MOVE
 *                             OTHER_PLAYER_MOVED <n>
 *                             VICTORY
 *                             DEFEAT
 *                             TIE
 *                             MESSAGE <text>
 *
 */
public class TestServer {

    private static int PORT = 8901;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    int i=0;
    /**
     * Constructs the client by connecting to a server, laying out the
     * GUI and registering GUI listeners.
     */
    public TestServer(String serverAddress) throws Exception {

        // Setup networking
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("lol");

    }

    /**
     * The main thread of the client will listen for messages
     * from the server.  The first message will be a "WELCOME"
     * message in which we receive our mark.  Then we go into a
     * loop listening for "VALID_MOVE", "OPPONENT_MOVED", "VICTORY",
     * "DEFEAT", "TIE", "OPPONENT_QUIT or "MESSAGE" messages,
     * and handling each message appropriately.  The "VICTORY",
     * "DEFEAT" and "TIE" ask the user whether or not to play
     * another game.  If the answer is no, the loop is exited and
     * the server is sent a "QUIT" message.  If an OPPONENT_QUIT
     * message is recevied then the loop will exit and the server
     * will be sent a "QUIT" message also.
     */
  /*  public void play() throws Exception {
        String response;
        try {
            response = in.readLine();
       //     System.out.println(response);
            i++;
            System.out.println(i);
            Scanner odczyt = new Scanner(System.in);
            boolean condition = false;
            while (true) {
            	//out.println("123");
            	
                String text = odczyt.nextLine();
                	if(response.startsWith("MESSAGE")) {
                    System.out.println(response.substring(8));                    
                }
                	if(text.startsWith("MOVE")){
                		out.println(text);
                	}
            }
            
        }
        finally {
            socket.close();
        }
    }*/
    
    public void play() throws Exception {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
            	System.out.println("welcome");
            
            }
            while (true) {
                response = in.readLine();
             if (response.startsWith("MESSAGE")) {
                    System.out.println(response.substring(8));
                }
            }
          
        }
        finally {
            socket.close();
        }
    }




    /**
     * Runs the client as an application.
     */
    public static void main(String[] args) throws Exception {
        while (true) {
            String serverAddress = (args.length == 0) ? "localhost" : args[1];
            System.out.println(serverAddress);
            TestServer client = new TestServer(serverAddress);

            client.play();
            
        /*	int port = 8901;
            ServerSocket listener = new ServerSocket(port);
            System.out.println("Go Server is Running");
            int size =9;
            try {
                while (true) {
                    Player playerB = new Player(listener.accept(), 'B');
                    Player playerW = new Player(listener.accept(), 'W' );
                    playerB.setOpponent(playerW);
                    playerW.setOpponent(playerB);
                    playerB.start();
                    playerW.start();
                }
            } finally {
                listener.close();
            }
            */

        }
    }
}