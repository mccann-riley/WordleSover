package wordleSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	//File Path to dictionary
	static String DICT_FILE_PARENT = "C:\\Users\\riley\\Documents";
	static String DICT_FILE_NAME = "english_dictionary.txt";
	
	public static void main(String args[]) {
		//generate new game with dictionary, 5 letter words, 6 attempts, with automated solver
		Game game = new Game(DICT_FILE_PARENT, DICT_FILE_NAME, 5, 6, gameType.Solver);
		
		//generate new solver using the game's dictionary, 5 letter words, 6 attempts
		Solver solver = new Solver(game.getDictionary(), game.getWordLength(), game.getAttempts());
		
		//display solution
		System.out.println(game.getSolution());
		
	}
}
