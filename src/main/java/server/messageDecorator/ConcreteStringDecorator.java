package server.messageDecorator;

import java.io.PrintWriter;

public class ConcreteStringDecorator extends TextDecorator {
	
	private PrintWriter output;
	private String string;

	public ConcreteStringDecorator(Text decoratedText, PrintWriter output) {
		super(decoratedText);
		this.output = output;
	}

	@Override
	public void sendWithString(String string) {
	//	decoratedText.send();
		this.string = string;
		setMessage();
	}

	private void setMessage() {
		output.println(string);
		System.out.println(string);
	}
}

