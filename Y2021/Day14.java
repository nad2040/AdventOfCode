package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day14 {

    static ArrayList<Character> polymer;
    static HashMap<String,Character> pairInsertions = new HashMap<>();
    static long[] countLetter;
    static HashMap<String,Long> countPairs;

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input14.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        
        polymer = new ArrayList<>();
        countLetter = new long[26];
        countPairs = new HashMap<>();

        line = reader.readLine();
        for (char c : line.toCharArray()) polymer.add(c); 
        line = reader.readLine(); // skip empty line

        while ((line = reader.readLine()) != null) {
            String[] insertion = line.split(" -> ");
            pairInsertions.put(insertion[0], insertion[1].charAt(0));
        }

        reader.close();

        for (char c:polymer) countLetter[c-'A']++; // get initial atom counts
        
        for (int i=0; i<polymer.size()-1; i++) {
            String pair = "" + polymer.get(i) + polymer.get(i+1);
            countPairs.merge(pair, 1l, Long::sum); // log each pair count
        }
    }

    public static void getmax() {
        long min = Long.MAX_VALUE;
        long max = 0;
        for (long i: countLetter) {
            if (i>max) max=i;
            if (i<min && i>0) min=i;
        }
        
        System.out.println(max-min);
    }

    public static void part1() {
        for (int i=0; i<10; i++) {
            HashMap<String,Long> copy = (HashMap<String, Long>) countPairs.clone(); // take copy to not use new data while editing and to avoid concurrent modification
            copy.forEach((k,v) -> {
                long count = v;
                if (pairInsertions.containsKey(k)) {
                    char c = pairInsertions.get(k);
                    countLetter[c-'A'] += count;
                    countPairs.merge(k, -count, Long::sum); // [count] # of pairs are broken when an atom is added in the middle
                    countPairs.merge("" + k.charAt(0) + c, count, Long::sum); // [count] # of pairs are added with the atom to the left
                    countPairs.merge("" + c + k.charAt(1), count, Long::sum); // [count] # of pairs are added with the atom to the right
                }
            });
        }
        getmax();
    }

    public static void part2() {
        for (int i=0; i<40; i++) {
            HashMap<String,Long> copy = (HashMap<String, Long>) countPairs.clone(); // take copy to not use new data while editing and to avoid concurrent modification
            copy.forEach((k,v) -> {
                long count = v;
                if (pairInsertions.containsKey(k)) {
                    char c = pairInsertions.get(k);
                    countLetter[c-'A'] += count;
                    countPairs.merge(k, -count, Long::sum); // [count] # of pairs are broken when an atom is added in the middle
                    countPairs.merge("" + k.charAt(0) + c, count, Long::sum); // [count] # of pairs are added with the atom to the left
                    countPairs.merge("" + c + k.charAt(1), count, Long::sum); // [count] # of pairs are added with the atom to the right
                }
            });
        }
        getmax();
    }

    public static void main(String[] args) throws IOException {
        parse();        
        part1();

        parse();
        part2();
    }
}