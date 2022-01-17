package wordleSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Dictionary {
	
	private ArrayList<ArrayList<String>> lenDictionary = new ArrayList<ArrayList<String>>();
	private ArrayList<String> dictionary = new ArrayList<String>();
	private int[] letterCounts = new int[26];
	private ArrayList<CharacterFreq> sortedFreq = new ArrayList<CharacterFreq>();
	private int maxLength = 0;
	
	public ArrayList<String> getFullDictionary() {
		return dictionary;
	}
	public int getMaxWordLength() {
		return maxLength;
	}
	public ArrayList<ArrayList<String>> getDictionary() {
		return lenDictionary;
	}
	public int[] getLetterCounts() {
		return letterCounts;
	}
	public ArrayList<CharacterFreq> getLetterFrequency() {
		return sortedFreq;
	}
	public Dictionary(String dictionary_file_name) {
		new Dictionary(null, dictionary_file_name);
	}
	
	public Dictionary(String dictionary_directory_path, String dictionary_file_name) {
		try {
			File full_dictionary = new File(dictionary_directory_path, dictionary_file_name);
			Scanner reader = new Scanner(full_dictionary);
			
			
			while (reader.hasNextLine()) {
				String word = reader.nextLine();
				if(word.length() > maxLength) {
					maxLength = word.length();
				}
				if(word.matches("[a-z]+")) {
					dictionary.add(word);
					addLetterCounts(word);
				}
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i <= maxLength; i++) {
			lenDictionary.add(new ArrayList<String>());
		}
		
		dictionary.forEach((word) -> lengthAdd(word, lenDictionary));
		
		int totalLetters = Arrays.stream(letterCounts).sum();
		for(int i = 0; i < letterCounts.length; i++) {
			float freq = (float) letterCounts[i]/(float) totalLetters;
			char c = (char) ((char) i+97);
			sortedFreq.add(new CharacterFreq(c, freq));
		}
		Collections.sort(sortedFreq, Collections.reverseOrder());
	}
	
	private static void lengthAdd(String word, ArrayList<ArrayList<String>> dict) {
		dict.get(word.length()).add(word);
	}
	
	public void testOutput(int minWordLength, int maxWordLength, int maxWordCount) {
		if(minWordLength < 1)
			minWordLength = 1;
		if(maxWordLength > maxLength)
			maxWordLength = maxLength;
		for(int j = minWordLength; j <= maxWordLength; j++) {
			int maxWordCountTemp = maxWordCount; 
			if(lenDictionary.get(j).size() < maxWordCount) {
				maxWordCountTemp = lenDictionary.get(j).size();
			}
			for(int i = 0; i < maxWordCountTemp; i++) {
				System.out.println(lenDictionary.get(j).get(i));
			}
		}
	}
	
	private void addLetterCounts(String word) {
		for(int i = 0; i < word.length(); i++) {
			int index = (int) (word.charAt(i)-97);
			letterCounts[index]++;
		}
	}
}