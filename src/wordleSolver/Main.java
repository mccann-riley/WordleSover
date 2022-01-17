package wordleSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static String DICT_FILE_PARENT = "C:\\Users\\riley\\Documents";
	static String DICT_FILE_NAME = "english_dictionary.txt";
	
	public static void main(String args[]) {
		Game game = new Game(DICT_FILE_PARENT, DICT_FILE_NAME, 6, 5, gameType.Solver);
		
		Solver solver = new Solver(game.getDictionary(), game.getWordLength(), game.getAttempts());
				
		System.out.println(game.getSolution());
		System.out.println(Arrays.toString(game.getDictionary().getLetterCounts()));
		System.out.println(game.getDictionary().getLetterFrequency());
	}
}
