package server.messageDecorator;

import java.io.PrintWriter;

public class PointsDecorator extends TextDecorator {
	private String points;
	private PrintWriter output;

	public PointsDecorator(Text decoratedText, PrintWriter output) {
		super(decoratedText);
		this.output = output;
	}

	@Override
	public void sendWithString(String points) {
		this.points = points;
		//decoratedText.sendWithString(points);
		setMessage();
	}

	private void setMessage() {
		output.println("POINTS "+ points);
		System.out.println("POINTS " + points);

	}
}
