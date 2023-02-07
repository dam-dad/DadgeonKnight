package nx.engine;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import nx.game.App;
import nx.util.CSV;

public class TextAnimation {
	
	private final List<String> texts;
	private String actualText;
	
	private int selectedLetter = 0;
	private int selectedText = 0;
	
	private int speed = 1;
	
	private double totalTimeLetters = 0;
	private double totalTimeTexts = 0;
	private final double timeBeetWeenLetters;
	private final double timeBeetWeenTexts;
	
	private boolean play = false;
	
	public TextAnimation(List<String> texts) {
		this.texts = texts;
		timeBeetWeenLetters = 0.1;
		timeBeetWeenTexts = 1;
		actualText = texts.get(selectedText).substring(0,selectedLetter);
	}
	
	public TextAnimation(String texts) throws URISyntaxException, Exception {
		this(CSV.readAllLinesTogether(Paths.get(CSV.class.getResource(texts).toURI())));
		for(int i = 0; i < this.texts.size(); i++) {
			int numOfSplits = (int)((this.texts.get(i).length() * Game.font.getSize()) / (Game.screenWidth - 100));
			if(numOfSplits > 0) {
				List<String> divide = splitText(this.texts.get(i),numOfSplits + 1);
				this.texts.remove(i);
				this.texts.addAll(i, divide);
				i += divide.size();
			}
		}
	}
	
	public List<String> splitText(String text, int numOfSplits) {
	    List<String> toReturn = new ArrayList<String>();
	    String[] words = text.split(" ");

	    int partLength = words.length / numOfSplits;
	    int lastPoint = 0;
	    for(int i = 1; i <= numOfSplits; i++) {
	        int endPoint = lastPoint + partLength;
	        if(i == numOfSplits) {
	            endPoint = words.length;
	        }
	        StringBuilder part = new StringBuilder();
	        for(int j = lastPoint; j < endPoint; j++) {
	            part.append(words[j] + " ");
	        }
	        toReturn.add(part.toString());
	        lastPoint = endPoint;
	    }
	    return toReturn;
	}

	
	public void update(double timeDifference) {
		
		if(play) {
			totalTimeLetters += timeDifference;
			
			if(totalTimeLetters > timeBeetWeenLetters) {
				totalTimeLetters = 0;
				if(selectedLetter < texts.get(selectedText).length()) {
					if((selectedLetter + speed) < texts.get(selectedText).length()) {
						selectedLetter += speed;
					}else {
						selectedLetter = texts.get(selectedText).length();
					}
					App.mixer.addGameSound("text_regular.mp3").play();
				}
					
			}
			
			if(selectedLetter >= texts.get(selectedText).length()) {
				totalTimeTexts += timeDifference;
				if(totalTimeTexts > timeBeetWeenTexts && selectedText < texts.size() - 1) {
					selectedText++;
					selectedLetter = 0;
					totalTimeTexts = 0;
				}
			}
			actualText = texts.get(selectedText).substring(0,selectedLetter);
		}

	}
	public boolean hasEnded() {
		return this.selectedText >= this.texts.size() - 1 && this.selectedLetter >= this.texts.get(selectedText).length() - 1;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void play() {
		this.play = true;
	}
	public void pause() {
		this.play = false;
	}
	
	public String getCurrentLine() {
		return this.texts.get(selectedText);
	}
	
	public String getCurrentFrame() {
		return this.actualText;
	}

}
