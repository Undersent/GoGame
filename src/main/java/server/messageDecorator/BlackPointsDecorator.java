package server.messageDecorator;

import java.io.PrintWriter;

public class BlackPointsDecorator extends TextDecorator {
	private int blackCaptured;
	private PrintWriter output;

	public BlackPointsDecorator(Text decoratedText, PrintWriter output) {
		super(decoratedText);
		this.output = output;
	}

	@Override
	public void sendWithInt(int blackCaptured) {
		this.blackCaptured = blackCaptured;
	//	decoratedText.sendWithInt(blackCaptured);
		setMessage();
	}

	private void setMessage() {
		output.println("BLACK_POINTS "+ blackCaptured);
		System.out.println("BLACK_POINTS "+ blackCaptured);

	}
}

