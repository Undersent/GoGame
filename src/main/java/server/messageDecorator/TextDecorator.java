package server.messageDecorator;

import java.io.PrintWriter;

public abstract class TextDecorator implements Text {
    protected Text decoratedText;
    protected PrintWriter output;
    
    public TextDecorator(Text decoratedText){
	   this.decoratedText = decoratedText;
	}

    @Override
	public void send(){
		decoratedText.send();
	}

	@Override
	public void sendWithInt(int number) {
		decoratedText.sendWithInt(number);
		
	}

	@Override
	public void sendWithString(String text) {
		decoratedText.sendWithString(text);
		
	}	
	}