package wordleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Solver {
	private int wordLength;
	private int attemptNum = 0;
	private Dictionary dictionary;
	private Game game;
	//HashMap of anagrams: Key is alphabetic sorted string
	//Value is alphabetic sorted ArrayList of words matching the key when sorted
	//Ex: Key (AET) = "ate", "eat", "tea"
	private HashMap<String, ArrayList<String>> anagramChecker = new HashMap<String, ArrayList<String>>(); 
	
	public HashMap<String, ArrayList<String>> getAnagramChecker() {
		return anagramChecker;
	}
	//constructor
	public Solver(Game g) {
		this.game = g;
		this.dictionary = this.game.getDictionary();
		this.wordLength = this.game.getWordLength();
		
		generateAnagramChecker(dictionary.getFullDictionary());
		//String guesses[] = generateGuesses(2);
		//System.out.println(Arrays.toString(guesses));
	}
	
	private String[] generateGuesses(int startingVowelNum) {
		String included = this.dictionary.getAlphabet();
		String excluded = "";
		String[] guesses = new String[this.game.getAttempts()];
		for(int i = 0; i < guesses.length; i++) {
			String currentGuess = "";
			if(i == 0)
				currentGuess = generateFirstGuess(startingVowelNum);
			else
				currentGuess = bestGuess(included, excluded);
			String[] updatedInfo = submitGuess(currentGuess, included, excluded);
			included = updatedInfo[0];
			excluded = updatedInfo[1];
			guesses[i] = currentGuess;
		}
		return guesses;
	}
	
	private String[] submitGuess(String guess, String incl, String excl) {
		this.game.processGuess(guess);
		String updIncl = incl;
		String updExcl = excl;
		System.out.println(guess);
		return new String[] {updIncl, updExcl};
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
		//add check to make sure key is in hashmap
		//generate new guess if not
		return decodeAnagram(sortWord(String.join("", characters))).get(0);
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
	
	public ArrayList<String> decodeAnagrams(ArrayList<String> anagrams) {
		ArrayList<String> decoded = new ArrayList<String>();
		for(String anagram : anagrams) {
			decoded.addAll(decodeAnagram(anagram));
		}
		return decoded;
	}
	
	public ArrayList<String> decodeAnagram(String anagram) {
		return anagramChecker.get(anagram);
	}
	
	public String bestGuess(String included, String excluded) {
		return bestGuesses(included, excluded).get(0);
	}
	
	public ArrayList<String> bestGuesses(String included, String excluded) {
		ArrayList<String> possibleGuesses = possibleWords(included, excluded);
		HashMap<String, Float> wordFreq = genWordFreq(possibleGuesses);
		HashMap.Entry<String, Float> maxEntry = null;

		for (HashMap.Entry<String, Float> entry : wordFreq.entrySet())
		{
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
		    {
		        maxEntry = entry;
		    }
		}
		return decodeAnagram(maxEntry.getKey());
	}
	
	public ArrayList<String> possibleWords(String included, String excluded) {
		ArrayList<String> sortedWords = possibleSortedWords(included, excluded);
		ArrayList<String> validKeys = new ArrayList<String>();
		sortedWords.forEach((word) -> addValidKey(word, validKeys));
		return validKeys;
	}
	
	private ArrayList<String> possibleSortedWords(String included, String excluded) {
		included = sortWord(included);
		excluded = sortWord(excluded);
		String alphabet = this.game.getDictionary().getAlphabet();
		String remaining = alphabet;
		if(!excluded.equals(""))
			remaining = alphabet.replaceAll("["+excluded+"]+","");
		
		ArrayList<String> possible = new ArrayList<String>();
		if(included.length() < wordLength) {
			for(int i = 0; i < remaining.length(); i++) {
				if((included+remaining.charAt(i)).length() == wordLength && !possible.contains(included+remaining.charAt(i))) {
					possible.add(sortWord(included+remaining.charAt(i)));
				}
				possible.addAll(possibleSortedWords(included+remaining.charAt(i), excluded));
			}
		}
		return possible;
	}
	
	private void addValidKey(String word, ArrayList<String> validKeys) {
		if(anagramChecker.containsKey(word))
			validKeys.add(word);
	}
	
	public HashMap<String, Float> genWordFreq(ArrayList<String> wordList) {
		HashMap<String, Float> wordFreq = new HashMap<String, Float>();
		wordList.forEach((word) -> genWordFreqH(word, wordFreq));
		return wordFreq;
	}
	
	private HashMap<String, Float> genWordFreqH(String word, HashMap<String, Float> wordFreq) {
		String sortedWord = sortWord(word);
		char[] chars = sortedWord.toCharArray();
		float wordFrequency = 0;
		ArrayList<CharacterFreq> letterFreqList = dictionary.getLetterFrequency();
		for(int i = 0; i < chars.length; i++) {
			int ind = 0;
			while(!letterFreqList.get(ind).equals(chars[i])) {
				ind++;
			}
			wordFrequency += letterFreqList.get(ind).getFrequency();
		}
		if(!wordFreq.containsKey(sortedWord)) {
			wordFreq.put(sortedWord, wordFrequency);
		}
		return wordFreq;
	}
	
	//Word with letters in position
}
