package server.messageDecorator;

import java.io.PrintWriter;

public class WelcomeDecorator extends TextDecorator{
	private String mark;
	private PrintWriter output;

	public WelcomeDecorator(Text decoratedText, PrintWriter output) {
		super(decoratedText);
		this.output = output;
		//System.out.println("WELCOME " + mark);
	}

	@Override
	public void sendWithString(String mark) {System.out.println("WELCOME " + mark);
		this.mark = mark;
		decoratedText.sendWithString(mark);
		setMessage();
	}

	private void setMessage() {
		output.println("WELCOME " + mark);
		System.out.println("WELCOME " + mark);
	}
}
