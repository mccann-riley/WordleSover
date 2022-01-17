package wordleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Solver {
	private int wordLength;
	private int attemptMax;
	private int attemptNum = 0;
	private Dictionary dictionary;
	private String guess;
	private HashMap<String, ArrayList<String>> anagramChecker = new HashMap<String, ArrayList<String>>();
	
	
	public Solver(Dictionary d, int w, int a) {
		this.dictionary = d;
		this.wordLength = w;
		this.attemptMax = a;
		
		generateAnagramChecker(dictionary.getFullDictionary());
		
		guess = generateFirstGuess(2);
	}

	private String generateFirstGuess(int vowelNum) {
		String[] characters = new String[wordLength];
		int vowelCount = 0;
		int filledIndex = 0;
		int i = 0;
		while(filledIndex < wordLength && filledIndex < dictionary.getLetterFrequency().size()) {
			CharacterFreq c = dictionary.getLetterFrequency().get(i);
			if(c.getIsVowel() && vowelCount < vowelNum) {
				characters[filledIndex] = c.getCharacter()+"";
				vowelCount++;
				filledIndex++;
			}
			else if(!c.getIsVowel()) {
				characters[filledIndex] = c.getCharacter()+"";
				filledIndex++;
			}
			i++;
		}
		System.out.println("Characters: "+Arrays.toString(characters));
		System.out.println(sortWord(String.join("", characters)));
		System.out.println(anagramChecker.get(sortWord(String.join("", characters))));
		return "";
	}
	
	private void generateAnagramChecker(ArrayList<String> dict) {
		dict.forEach((word) -> mapAdd(word));
	}
	
	private void mapAdd(String w) {
		if(!anagramChecker.containsKey(sortWord(w))) {
			anagramChecker.put(sortWord(w), new ArrayList<String>());
		}
			anagramChecker.get(sortWord(w)).add(w);
	}
	
	private String sortWord(String w) {
		char[] chars = w.toCharArray();
		Arrays.sort(chars);
		String word = new String(chars);
		return word;
	}
}
