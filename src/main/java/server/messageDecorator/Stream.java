package server.messageDecorator;

import java.io.PrintWriter;

public class Stream implements StringDecorator {

	private PrintWriter output;

	public Stream(PrintWriter output) {
		this.output = output;
	}
	
	@Override
	public void sendWelcome(char mark) {
		output.println("WELCOME " + mark);
		
	}

	@Override
	public void sendMessage(String MESSAGE) {
		output.println("MESSAGE "+ MESSAGE);
		
	}

	@Override
	public void sendPoints(String POINTS) {
		output.println("POINTS "+ POINTS);
		
	}

	@Override
	public void sendBlackPoints(int blackCaptured) {
		output.println("BLACK_POINTS "+ blackCaptured);
		
	}

	@Override
	public void sendWhitePoints(int WHITE_POINTS) {
		output.println("WHITE_POINTS "+ WHITE_POINTS);
		
	}

	@Override
	public void sendQuit() {
		output.println("QUIT");
		
	}

	@Override
	public void sendPass() {
		output.println("PASS");
		
	}

	@Override
	public void sendCountTerritory() {
		output.println("COUNT_TERRITORY");

	}

	@Override
	public void sendConcreteString(String string) {
		output.println(string);
		
	}
}