package server.messageDecorator;

import java.io.PrintWriter;

public class MessageDecorator extends TextDecorator {
	private String message;
	private PrintWriter output;

	public MessageDecorator(Text decoratedText, PrintWriter output) {
		super(decoratedText);
		this.output=output;
	}

	@Override
	public void sendWithString(String message) {
		this.message = message;
	//	decoratedText.sendWithString(message);
		setMessage();
	}

	private void setMessage() {
		output.println("MESSAGE "+ message);
		System.out.println("MESSAGE " + message);
	}
}
