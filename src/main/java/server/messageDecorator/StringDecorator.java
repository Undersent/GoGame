package server.messageDecorator;

public interface StringDecorator {
	void sendWelcome(char mark);
	void sendMessage(String MESSAGE);
	void sendPoints(String POINTS);
	void sendBlackPoints(int BLACK_POINTS);
	void sendWhitePoints(int WHITE_POINTS);
	void sendQuit();
	void sendPass();
	void sendCountTerritory();
	void sendConcreteString(String string);

}
