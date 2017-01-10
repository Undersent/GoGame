package server.messageDecorator;

import java.io.PrintWriter;

public class WhitePointsDecorator extends TextDecorator {
	private int whiteCaptured;
	private PrintWriter output;

	public WhitePointsDecorator(Text decoratedText, PrintWriter output) {
		super(decoratedText);
		this.output = output;
	}

	@Override
	public void sendWithInt(int whiteCaptured) {
		this.whiteCaptured = whiteCaptured;
	//	decoratedText.sendWithInt(whiteCaptured);
		setMessage();
	}

	private void setMessage() {
		output.println("WHITE_POINTS "+ whiteCaptured);
		System.out.println("WHITE_POINTS "+ whiteCaptured);

	}
}
