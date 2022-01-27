package wordleSolver;

import java.util.ArrayList;

//Game (Human), Solver (Automated)
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
	
	//Default Wordle Game Constructor
	public Game(String dictFilePath, String dictFileName, gameType type) {
		this.dictionary = new Dictionary(dictFilePath, dictFileName);
		ArrayList<ArrayList<String>> dict = dictionary.getDictionary();
		
		this.wordLength = 5;
		this.attempts = 6;
		
		this.solution = generateSolution(wordLength);
	}
	
	//Generalized game constructor
	public Game(String dictFilePath, String dictFileName, int wordLength, int attempts, gameType type) {
		this.dictionary = new Dictionary(dictFilePath, dictFileName);
		ArrayList<ArrayList<String>> dict = dictionary.getDictionary();
		
		this.wordLength= wordLength;
		this.attempts = attempts;
		
		if(this.wordLength <= 0)
			this.wordLength = 1;
		if(this.attempts <= 0)
			this.attempts = 1;
				
		this.solution = generateSolution(wordLength);		
	}

	//Generates solution word based on wordLength
	private String generateSolution(int wordLength) {
		return dictionary.getDictionary().get(wordLength).get((int) (Math.random()*dictionary.getDictionary().get(wordLength).size()));
	}
	
	//to be implemented
	//Process a guess from a user/solver
	//Tells what letters are in the correct spot, 
	//what letters are in the wrong spot but in the word,
	//what letters are not in the word
	public boolean processGuess(String guess) {
		int[] guessLocs = evalLocations(guess);
		boolean correctGuess = true;
		for(int i = 0; i < guess.length(); i++) {
			if(guessLocs[i] != 1) {
				correctGuess = false;
			}
		}
		return correctGuess;
	}
	
	public int[] evalLocations(String guess) {
int[] guessLetters = new int[this.wordLength];
		
		for(int i = 0; i < guess.length(); i++) {
			if(this.solution.contains(""+guess.charAt(i))) {
				guessLetters[i] = -1;
				//System.out.print(ConsoleColors.YELLOW + " " + guess.charAt(i) + " " + ConsoleColors.RESET);
			}
			else if(guess.charAt(i) == this.solution.charAt(i)) {
				guessLetters[i] = 1;
				//System.out.print(ConsoleColors.GREEN + " " + guess.charAt(i) + " " + ConsoleColors.RESET);
			}
			else {
				//System.out.print(ConsoleColors.RED + " " + guess.charAt(i) + " " + ConsoleColors.RESET);
			}
		}
		
		return guessLetters;
	}
}
