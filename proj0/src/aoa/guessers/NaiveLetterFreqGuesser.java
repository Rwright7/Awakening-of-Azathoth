package aoa.guessers;

import aoa.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
       // words = FileUtils.readWords(example.txt);

    }

    @Override
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.
        Map<Character, Integer> FrequencyMap = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                // Convert to lowercase to ensure case-insensitivity
                c = Character.toLowerCase(c);

                // If the character is a letter
                if (Character.isLetter(c)) {
                    FrequencyMap.put(c, FrequencyMap.getOrDefault(c, 0) + 1);
                }
            }
        }
       return FrequencyMap;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.
        TreeMap<Character, Integer>  sortFrequencyMap = new TreeMap<>();
        for (char letter : getFrequencyMap().keySet()) {
            if (!guesses.contains(letter)) {
                sortFrequencyMap.put(letter, getFrequencyMap().get(letter));
            }
        }
        if (getFrequencyMap().isEmpty()) {
            return '?';
        }
        return sortFrequencyMap.firstKey();
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("proj0/data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
