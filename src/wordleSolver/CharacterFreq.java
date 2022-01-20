package wordleSolver;

import java.util.regex.Pattern;

public class CharacterFreq implements Comparable<CharacterFreq> {
		//Contains information about Characters
		//char, percent frequency, and if it is a vowel
		private char character = 0;
		private float frequency = 0;
		private boolean isVowel = false;
		
		//Constructor
		public CharacterFreq(char c, float f) {
			this.character = c;
			this.frequency = f;
			this.isVowel = Pattern.matches("[aeiouy]", ""+c);
		}

		public char getCharacter() {
			return character;
		}

		public float getFrequency() {
			return frequency;
		}
		public boolean getIsVowel() {
			return this.isVowel;
		}
		
		public String toString() {
			String v = "Consonant: ";
			if(this.isVowel)
				v = "Vowel '";
			return v+this.character+"': "+this.frequency*100+"%";
		}
		
		public boolean isChar(char c) {
			return this.character == c;
		}

		@Override
		public int compareTo(CharacterFreq o) {
			return (int) Math.floor(this.frequency-o.getFrequency());
		}
		
		@Override
		public boolean equals(Object o) {
			if(!(o instanceof CharacterFreq) && !(o instanceof Character)) {
				return false;
			}
			else if(o instanceof CharacterFreq){
				return ((CharacterFreq) o).getCharacter() == this.character;
			}
			else {
				return (Character) o == this.character;
			}
		}
	}
