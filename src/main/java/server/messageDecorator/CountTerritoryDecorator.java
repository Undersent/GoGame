package server.messageDecorator;

import java.io.PrintWriter;

public class CountTerritoryDecorator extends TextDecorator {
	
	private PrintWriter output;

	public CountTerritoryDecorator(Text decoratedText, PrintWriter output) {
		super(decoratedText);
		this.output = output;
	}

	@Override
	public void send() {
	//	decoratedText.send();
		setMessage();
	}

	private void setMessage() {
		output.println("COUNT_TERRITORY");
		System.out.println("COUNT_TERRITORY");
	}
}
