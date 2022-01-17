package wordleSolver;

import java.util.regex.Pattern;

public class CharacterFreq implements Comparable<CharacterFreq> {

		private char character = 0;
		private float frequency = 0;
		private boolean isVowel = false;
		
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

		@Override
		public int compareTo(CharacterFreq other) {
			return (int) Math.floor(this.frequency-other.getFrequency());
		}
	}
