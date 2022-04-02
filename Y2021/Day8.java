package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Day8 {

    static HashMap<String, Integer> findDigit = new HashMap<>();

    public static void part1() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input8.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        
        String line;
        int sum = 0;
        while ((line = reader.readLine()) != null) {
            String output = line.split(" \\| ")[1];
            for (String s: output.split(" ")) {
                if (s.length()==2 || s.length()==4 || s.length()==3 || s.length()==7) sum++;
            }
        }

        System.out.println(sum);

        reader.close();
    }

    public static void part2() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input8.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));

        int sum=0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] hint = line.split(" \\| ");

            sum += solve(hint[0].split(" "), hint[1].split(" "));            
        }
        System.out.println(sum);

        reader.close();
    }

    public static int solve(String[] digits, String[] numbers) {
        HashMap<Character,Character> charMap = new HashMap<>();
        int[] frequency = new int[7];
        char[] crackedChars = new char[7]; // keep track of cracked chars in the order we crack them in
        for (String s: digits) { // count frequency because e,b,f have unique counts: e appears 4 times, b appears 6 times, f appears 9 times
            for (int i=0; i<s.length(); i++) {
                switch (s.charAt(i)) {
                    case 'a': frequency[0]++; break;
                    case 'b': frequency[1]++; break;
                    case 'c': frequency[2]++; break;
                    case 'd': frequency[3]++; break;
                    case 'e': frequency[4]++; break;
                    case 'f': frequency[5]++; break;
                    case 'g': frequency[6]++; break;
                    default: break;
                }
            }
        }
        for (int i=0; i<frequency.length; i++) {
            if (frequency[i] == 4) crackedChars[0] = (char) ('a'+i); // e
            if (frequency[i] == 6) crackedChars[1] = (char) ('a'+i); // b
            if (frequency[i] == 9) crackedChars[2] = (char) ('a'+i); // f
        }

        // Now start looking for the differences in the segments, where only one segment differs.
        // Don't forget the letters you've already cracked, for example 1 is missing segment a compared to 7.
        String[] orderedDigits = new String[10]; // stores strings of each number in order 0-9
        String[] length5 = new String[3]; // stores three 5-segment numbers 2,3,5
        String[] length6 = new String[3]; // stores three 6-segment numbers 0,6,9
        int fiveIndex=0;
        int sixIndex=0;
        for (String s: digits) { // stores by length
            if (s.length() == 2) orderedDigits[1] = s;
            if (s.length() == 3) orderedDigits[7] = s;
            if (s.length() == 4) orderedDigits[4] = s;
            if (s.length() == 7) orderedDigits[8] = s;
            if (s.length() == 5) length5[fiveIndex++] = s;
            if (s.length() == 6) length6[sixIndex++] = s;
        }

        for (int i=0; i<orderedDigits[7].length(); i++) { // cracked 3 letters so far ebf
            if (!orderedDigits[1].contains(""+orderedDigits[7].charAt(i))) { // if this segment is not in 1 but in 7, which is segment A
                crackedChars[3] = orderedDigits[7].charAt(i); // a
            }
        }

        String cracked = String.valueOf(crackedChars); // store currently known characters. cracked 4 letters so far ebfa
        for (String s: length5) { // check 5-segment numbers for one unknown segment, C in the number 5 (2 missing BF) (3 missing BE) (5 missing CE)
            for (int i=0; i<orderedDigits[8].length(); i++) {
                if (!cracked.contains(""+orderedDigits[8].charAt(i)) && !s.contains(""+orderedDigits[8].charAt(i))) // if we haven't cracked this letter, and it isn't common with 8's segments
                    crackedChars[4] = orderedDigits[8].charAt(i); // c
            }
        }

        cracked = String.valueOf(crackedChars); // store currently known characters again for next segment. cracked 5 letters so far ebfac
        for (String s: length6) { // check 6-segment numbers for one unknown segment, D in the number 0 (0 missing D) (6 missing C) (9 missing E)
            for (int i=0; i<orderedDigits[8].length(); i++) {
                if (!cracked.contains(""+orderedDigits[8].charAt(i)) && !s.contains(""+orderedDigits[8].charAt(i))) // if we haven't cracked this letter, and it isn't common with 8's segments
                    crackedChars[5] = orderedDigits[8].charAt(i); // d
            }
        }

        cracked = String.valueOf(crackedChars); // store currently known characters again for next segment. cracked 6 letters so far ebfacd
        for (int i=0; i<orderedDigits[8].length(); i++) {
            if (!cracked.contains(""+orderedDigits[8].charAt(i))) // if we haven't cracked this letter
                crackedChars[6] = orderedDigits[8].charAt(i); // g
        }

        charMap.put(crackedChars[0],'e');
        charMap.put(crackedChars[1],'b');
        charMap.put(crackedChars[2],'f');
        charMap.put(crackedChars[3],'a');
        charMap.put(crackedChars[4],'c');
        charMap.put(crackedChars[5],'d');
        charMap.put(crackedChars[6],'g');

        int number = 0; // calculate the 4-digit number
        for (String s: numbers) {
            number *= 10;
            number += decode(s,charMap);
        }

        return number;
    }

    public static int decode(String digit, HashMap<Character,Character> charMap) {
        String newDigit = "";
        for (int i=0; i<digit.length(); i++) {
            newDigit += charMap.get(digit.charAt(i));
        }

        char[] arr = newDigit.toCharArray();
        Arrays.sort(arr);
        String sortedDigit = String.valueOf(arr);
        return findDigit.get(sortedDigit);
    }

    public static void main(String[] args) throws IOException {
        /* NOTES
            segments for each digit
            0: abc efg 
            1:   c  f
            2: a cde g
            3: a cd fg
            4:  bcd f
            5: ab d fg
            6: ab defg
            7: a c  f
            8: abcdefg
            9: abcd fg
            
            len
            0 - 6
            1 - 2 --
            2 - 5
            3 - 5
            4 - 4 --
            5 - 5 
            6 - 6
            7 - 3 --
            8 - 7 --
            9 - 6

            frequency
            a=8 0 23 56789
            b=6 0   456 89
            c=8 01234  789
            d=7   23456 89
            e=4 0 2   6 8
            f=9 01 3456789
            g=7 0 23 56 89
        */

        findDigit.put("abcefg",0);
        findDigit.put("cf",1);
        findDigit.put("acdeg",2);
        findDigit.put("acdfg",3);
        findDigit.put("bcdf",4);
        findDigit.put("abdfg",5);
        findDigit.put("abdefg",6);
        findDigit.put("acf",7);
        findDigit.put("abcdefg",8);
        findDigit.put("abcdfg",9);

        part1();
        part2();
    }
}