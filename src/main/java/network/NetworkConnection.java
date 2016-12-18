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
	private boolean error = false;
	
	public NetworkConnection(Consumer<Serializable> onReceiveCallback) throws Exception {
		this.onReceiveCallback = onReceiveCallback;
		connThread.setDaemon(true);
	}
	
	public boolean startConnection() {
		connThread.start();
		if(error) {
			return false;
		} else {
			return true;
		}
	}
	
	public void send(Serializable data) throws Exception {
		connThread.out.println(data);;
	}
	
	public void closeConnection() throws Exception {
		connThread.socket.close();
	}
	
	protected abstract String getIP();
	protected abstract int getPort();
	
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
				error = true;
			}
		}
	}
	
}
