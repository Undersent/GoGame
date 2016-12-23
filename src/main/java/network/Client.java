package network;

import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends NetworkConnection {

	private String ip;
	private int port;
	
	/*
	 * Creates client on specific ip and port
	 */
	public Client(String ip, int port, Consumer<Serializable> onReceiveCallback) throws Exception {
		super(onReceiveCallback);
		this.ip = ip;
		this.port = port;
	}

	@Override
	protected String getIP() {
		return ip;
	}

	@Override
	protected int getPort() {
		return port;
	}

}
