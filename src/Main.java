import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Loading dictionary...");
        HashSet<String> words = loadDict();

        System.out.println("Dictionary loaded, "+formatNumber(words.size())+" words");

        Scanner reader = new Scanner(System.in);
        boolean run = true;

//        while(run){
            System.out.println("Finding letters...");
//            String ownLeters = reader.nextLine();
            String ownLeters = "lzzai";
            LettersOnBoard freeLetters = new LettersOnBoard();
//            freeLetters.setMagicPattern("a.");
//            freeLetters.forceMagicString = true;

//            freeLetters.setStartingLetters("ces");
            freeLetters.setMiddleLetters("r");
//            freeLetters.setEndingLetters("nm");

            Map<String, Integer> wordsScored = words.stream().collect(Collectors.toMap(s -> s, s -> generateWordScore(s, ownLeters, freeLetters)));
            wordsScored.entrySet().stream()
                    .filter(stringIntegerEntry -> stringIntegerEntry.getValue() > 1)
                    .sorted(Comparator.comparingInt((Map.Entry<String, Integer> entry) -> entry.getValue()).reversed())
                    .limit(10)
                    .forEach(System.out::println);
//            words.stream().filter(s -> s.contains())
//            System.out.println("What letters are free on board (to start with)");
//            String startLetters = reader.nextLine();
//            System.out.println("What letters are free on board (for middle of word)");
//            String middleLetters = reader.nextLine();
//            System.out.println("What letters are free on board (to end with)");
//            String endLetters = reader.nextLine();
//        }

    }

    private static int generateWordScore(String word1, String searchString, LettersOnBoard freeLetters){
        LinkedList<Character> wordCharList = stringToList(word1);
        int wordScore = 0;
        boolean skipFreeLetters = false;
        if(freeLetters.hasStartingLetters() && !freeLetters.hasMagicPattern()){
            for(Character c : freeLetters.startingLetters){
                if(wordCharList.getFirst().equals(c)/* || wordCharList.get(1).equals(c)*/){
                    wordScore += getPointsForChar(c);
                    wordCharList.remove(c);
                    skipFreeLetters = true;
                    break;
                }
            }
        }
        if(freeLetters.hasEndingLetters() && !skipFreeLetters && !freeLetters.hasMagicPattern()){
            for(Character c : freeLetters.endingLetters){
                if(wordCharList.getLast().equals(c)){
                    wordScore += getPointsForChar(c);
                    wordCharList.remove(c);
                    skipFreeLetters = true;
                    break;
                }
            }
        }
        if(freeLetters.hasMiddleLetters() && !skipFreeLetters && !freeLetters.hasMagicPattern()){
            for(Character c : freeLetters.middleLetters){
                if(wordCharList.contains(c)){
                    wordScore += getPointsForChar(c);
                    wordCharList.remove(c);
                    break;
                }
            }
        }
        if(freeLetters.hasMagicPattern()){
            Matcher regex = Pattern.compile(freeLetters.generateMagicRegex()).matcher(word1);
            if(regex.matches()){
                int start = regex.start(1);
                String magicString = freeLetters.getMagicString();
                for (int i = magicString.length() - 1; i >= 0; i--) {
                    if(magicString.charAt(i) != '.'){
                        wordCharList.remove(start + i);
                    }
                }
            }else if(freeLetters.forceMagicString) {
                return 0;
            }
        }


        for(Character c : searchString.toCharArray()){
            if(wordCharList.contains(c)){
                wordScore += getPointsForChar(c);
                wordCharList.remove(c);
            }
        }
        if(searchString.contains(" ")){
            if(wordCharList.size() == 1){
                return wordScore;
            }
        }
        if(!wordCharList.isEmpty()){
            return 0;
        }
        return wordScore;
    }

    private static int getPointsForChar(Character c){
        return Util.LETTER_POINTS.get(Character.toUpperCase(c));
    }

    protected static LinkedList<Character> stringToList(String word){
        return word.chars().mapToObj(c -> (char) c).collect(Collectors.toCollection(LinkedList::new));
    }

    private static HashSet<String> loadDict() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("slowa.txt"));
        return br.lines()
                .filter(s -> s.length() < 10)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private static String formatNumber(long number) {
        if (number >= 1_000_000) {
            return String.format("%.1f million", number / 1_000_000.0);
        } else if (number >= 1_000) {
            return String.format("%.1f thousand", number / 1_000.0);
        } else {
            return String.valueOf(number);
        }
    }

}

class LettersOnBoard{
    public LinkedList<Character> startingLetters;
    public LinkedList<Character> endingLetters;
    public LinkedList<Character> middleLetters;
    public LinkedList<Character> magicPattern;
    public boolean forceMagicString = false;

    public LettersOnBoard() {
    }

    public void setStartingLetters(String letters){
        this.startingLetters = Main.stringToList(letters);
    }

    public void setEndingLetters(String letters){
        this.endingLetters = Main.stringToList(letters);
    }

    public void setMiddleLetters(String letters){
        this.middleLetters = Main.stringToList(letters);
    }

    public void setMagicPattern(String letters){
        this.magicPattern = Main.stringToList(letters);
    }

    public boolean hasMagicPattern(){
        return this.magicPattern != null && !this.magicPattern.isEmpty();
    }

    public boolean hasStartingLetters(){
        return this.startingLetters != null && !this.startingLetters.isEmpty();
    }

    public boolean hasMiddleLetters(){
        return this.middleLetters != null && !this.middleLetters.isEmpty();
    }

    public boolean hasEndingLetters(){
        return this.endingLetters != null && !this.endingLetters.isEmpty();
    }

    public String generateMagicRegex(){
        return ".*(" +
                this.getMagicString() +
                ").*";
    }

    public String getMagicString(){
        StringBuilder sb = new StringBuilder();
        for(Character c : this.magicPattern){
            sb.append(c);
        }
        return sb.toString();
    }
}