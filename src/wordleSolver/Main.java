package wordleSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	//File Path to dictionary
	static String DICT_FILE_PARENT = System.getProperty("user.dir");
	static String DICT_FILE_NAME = "/inputs/wordle_answers_alphabetical.txt";
	
	public static void main(String args[]) {
		//generate new game with dictionary, 5 letter words, 6 attempts, with automated solver
		Game game = new Game(DICT_FILE_PARENT, DICT_FILE_NAME, 5, 6, gameType.Solver);
		
		//generate new solver using the game's dictionary, 5 letter words, 6 attempts
		Solver solver = new Solver(game);
		
		System.out.println(Arrays.toString(solver.generateGuesses())); //method still causing errors I think when empty
				
		//display solution
		System.out.println(game.getSolution());		
	}
}
