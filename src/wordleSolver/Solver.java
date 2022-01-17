package wordleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Solver {
	private int wordLength;
	private int attemptNum = 0;
	private Dictionary dictionary;
	//Array of all guess attempted
	private String[] guesses;
	//HashMap of anagrams: Key is alphabetic sorted string
	//Value is alphabetic sorted ArrayList of words matching the key when sorted
	//Ex: Key (AET) = "ate", "eat", "tea"
	private HashMap<String, ArrayList<String>> anagramChecker = new HashMap<String, ArrayList<String>>();
	
	public HashMap<String, ArrayList<String>> getAnagramChecker() {
		return anagramChecker;
	}
	//constructor
	public Solver(Dictionary d, int w, int a) {
		this.dictionary = d;
		this.wordLength = w;
		this.guesses = new String[a];
		
		generateAnagramChecker(dictionary.getFullDictionary());
		
		guesses[0] = generateFirstGuess(2);
	}
	//to be implemented
	//generalized version of guessing taking into account 
	//previous guesses
	private String generateGuess() {
		return "";
	}
	
	//First guess choosing one anagram containing words with
	//highest frequency letters and max of vowelNum vowels
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
		return anagramChecker.get(sortWord(String.join("", characters))).get(0);
	}
	
	//Creates anagramChecker HashMap from dictionary
	private void generateAnagramChecker(ArrayList<String> dict) {
		dict.forEach((word) -> mapAdd(word));
	}
	
	//adds word to ArrayList if key exists or makes new arrayList before if not
	private void mapAdd(String w) {
		if(!anagramChecker.containsKey(sortWord(w))) {
			anagramChecker.put(sortWord(w), new ArrayList<String>());
		}
			anagramChecker.get(sortWord(w)).add(w);
	}
	
	//sorts chars of String alphabetically
	private String sortWord(String w) {
		char[] chars = w.toCharArray();
		Arrays.sort(chars);
		String word = new String(chars);
		return word;
	}
}
