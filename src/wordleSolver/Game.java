package wordleSolver;

import java.util.ArrayList;
import java.util.Random;

enum gameType {
	Game,
	Solver
}

public class Game {
	private String solution = "";
	private int wordLength;
	private int attempts;
	private Dictionary dictionary;
	
	public String getSolution() {
		return this.solution;
	}
	public int getWordLength() {
		return wordLength;
	}

	public int getAttempts() {
		return attempts;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}
	
	public Game(String dictFilePath, String dictFileName, gameType type) {
		this.dictionary = new Dictionary(dictFilePath, dictFileName);
		ArrayList<ArrayList<String>> dict = dictionary.getDictionary();
		
		this.wordLength = 5;
		this.attempts = 6;
		
		this.solution = generateSolution(wordLength);
	}
	
	public Game(String dictFilePath, String dictFileName, int wordLength, int attempts, gameType type) {
		this.dictionary = new Dictionary(dictFilePath, dictFileName);
		ArrayList<ArrayList<String>> dict = dictionary.getDictionary();
		
		this.wordLength= wordLength;
		this.attempts = attempts;
		
		this.solution = generateSolution(wordLength);		
	}

	private String generateSolution(int wordLength) {
		return dictionary.getDictionary().get(wordLength).get((int) (Math.random()*dictionary.getDictionary().get(wordLength).size()));
	}
	
}
