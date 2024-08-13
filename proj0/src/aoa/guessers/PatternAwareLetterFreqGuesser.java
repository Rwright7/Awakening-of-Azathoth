package aoa.guessers;

import aoa.utils.FileUtils;
import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {

    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
       /* List<String> */ this.words = FileUtils.readWords(dictionaryFile);
    }
    public List<String> keepOnlyWordsThatMatchPattern(List<String> words, String pattern) {
        List<String> matchingwords = new ArrayList<>();
        for (String word: words){
            if (wordMatchesPattern(word, pattern)){
                matchingwords.add(word);
            }
        }
        return matchingwords;
    }
    private boolean wordMatchesPattern(String word, String pattern){
        if (word.length() != pattern.length()) {
            return false;
        }
        for (int i = 0;  i < pattern.length(); i++) {
            char patternChar = pattern.charAt(i);
            char wordChar = word.charAt(i);

            if (patternChar != '-' && patternChar != wordChar){
                return false;
            }
        }
        return true;
    }
    @Override
    /* Returns the most common letter in the set of valid words based on the current
       PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        List<String> matchingWords = keepOnlyWordsThatMatchPattern(this.words, pattern);
        TreeMap<Character,Integer>freqMap = new TreeMap<>();
        for (String word : matchingWords) {
            for (char letter : word.toCharArray()) {
                if (!guesses.contains(letter)) {
                    freqMap.put(letter, freqMap.getOrDefault(letter, 0) + 1);
                }
            }
        }
        char bestGuess = '?';
        int maxFrequency = 0;

        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            char letter = entry.getKey();
            int frequency = entry.getValue();

            if (frequency > maxFrequency) {
                bestGuess = letter;
                maxFrequency = frequency;
            }
        }

        return bestGuess;
    }
    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));

        List<String> matchingWords = palfg.keepOnlyWordsThatMatchPattern(palfg.words, "-e--");
        System.out.println(matchingWords);
    }

}