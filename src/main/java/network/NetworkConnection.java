package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {

	private ConnectionThread connThread = new ConnectionThread();
	private Consumer<Serializable> onReceiveCallback;
	
	/*
	 * Sign consumer
	 */
	public NetworkConnection(Consumer<Serializable> onReceiveCallback) throws Exception {
		this.onReceiveCallback = onReceiveCallback;
		connThread.setDaemon(true);
	}
	
	/*
	 * Starts connection thread
	 */
	public void startConnection() {
		connThread.start();
	}
	
	/*
	 * Sends data
	 */
	public void send(Serializable data) throws Exception {
		connThread.out.println(data);
	}
	
	/*
	 * Closes connection
	 */
	public void closeConnection() throws Exception {
		connThread.socket.close();
	}
	
	/*
	 * Returns ip
	 */
	protected abstract String getIP();
	
	/*
	 * Returns port
	 */
	protected abstract int getPort();
	
	/*
	 * Custom thread class for connection
	 */
	private class ConnectionThread extends Thread {
		private Socket socket;
		private PrintWriter out;
		
		@Override
		public void run() {
			try (Socket socket =  new Socket(getIP(), getPort());
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
				
				this.socket = socket;
				this.out = out;
				socket.setTcpNoDelay(true);
				
				while(true) {
					Serializable data = (Serializable) in.readLine();
					onReceiveCallback.accept(data);
				}
			} catch(Exception e) {
				onReceiveCallback.accept("Connection closed");
			}
		}
	}
	
}
