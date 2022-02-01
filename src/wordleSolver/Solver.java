package wordleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

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
		generateGuesses();
	}
	
	public String[] generateGuesses() {
		String included = "";
		String excluded = "";
		String[] guesses = new String[this.game.getAttempts()];
		boolean wrongGuess = true;
		for(int i = 0; i < guesses.length; i++) {
			if(wrongGuess) {
				String currentGuess = "";
				currentGuess = bestGuess(included, excluded);
				guesses[i] = currentGuess;

				int[] guessLocs = this.game.processGuess(guesses[i]);
				
				if(Arrays.stream(guessLocs).sum() == this.wordLength) {
					wrongGuess = false;
				}
				
				String[] updatedInfo = submitGuess(guessLocs, currentGuess, included, excluded);
				
				included = updatedInfo[0];
				excluded = updatedInfo[1];	
				System.out.println("include "+included);
				System.out.println("exclude "+excluded);
			}
		}
		return guesses;
	}
	
	private String[] submitGuess(int[] guessLocs, String guess, String incl, String excl) {
		String updIncl = incl;
		String updExcl = excl;
		for(int i = 0; i < guess.length(); i++) {
			if(guessLocs[i] == 0 && !updExcl.contains(""+guess.charAt(i))) {
				updExcl += guess.charAt(i);
			}
			else if(!updIncl.contains(""+guess.charAt(i))) {
				updIncl += guess.charAt(i);
			}
		}
		return new String[] {sortWord(updIncl), sortWord(updExcl)};
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
			System.out.println(entry+" Max: "+maxEntry);
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
		    {
		        maxEntry = entry;
		    }
		}
		System.out.println("FINAL MAX: "+maxEntry);
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
		HashSet<Character> letterSet = new HashSet<Character>();
		word.chars().forEach(c -> letterSet.add((char) c));
		ArrayList<Character> chars = new ArrayList<Character>();
		letterSet.forEach(c -> chars.add((char) c));
		Collections.sort(chars);
		String sortedWord = sortWord(word);
		//char[] chars = sortedWord.toCharArray();
		float wordFrequency = 0;
		ArrayList<CharacterFreq> letterFreqList = dictionary.getLetterFrequency();
		for(int i = 0; i < chars.size(); i++) {
			int ind = 0;
			while(!letterFreqList.get(ind).equals(chars.get(i))) {
				ind++;
			}
			wordFrequency += letterFreqList.get(ind).getFrequency();
		}
		if(!wordFreq.containsKey(sortedWord)) {
			wordFreq.put(sortedWord, wordFrequency);
		}
		return wordFreq;
	}
	
}
