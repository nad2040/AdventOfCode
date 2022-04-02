package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day3 {
    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    public static void part1() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input3.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int total = 0;
        int gamma = 0;
        int[] countOne = new int[12];
        while ((line = reader.readLine()) != null) {
            for (int i=0; i<line.length(); i++) if (line.charAt(i) == '1') countOne[i]++;
            total++;
        }
        for (int i=0; i<countOne.length; ++i) {
            if (total < countOne[i] << 1) gamma |= (1 << (11-i));
        }

        System.out.println(gamma * ((~gamma) & 0b111111111111));
        
        reader.close();
    }

    public static void part2() throws IOException {
        File f = new File("./AdventOfCode2021/input3.txt");
        
        int O2 = recur(0, true, "", f);
        int CO2 = recur(0, false, "", f);

        System.out.println(O2 * CO2);
    }

    static int recur(int index, boolean mostCommon, String search, File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int total=0, countOne=0;
        while ((line = reader.readLine()) != null) {
            if (line.substring(0,index).equals(search)) {
                if (line.charAt(index) == '1') countOne++;
                total++;
            }
        }

        if (total == 1) { 
            BufferedReader r = new BufferedReader(new FileReader(f));
            String l;
            while ((l = r.readLine()) != null) if (l.substring(0,index).equals(search)) { r.close(); return Integer.parseInt(l, 2); }
            r.close();
        }

        // System.out.println("\nindex: " + index + "\nsearch: " + search + "\ntotal: " + total + "\ncountOne: " + countOne);
        reader.close();

        if (total == countOne << 1) return recur(index+1, mostCommon, search+(mostCommon ? '1':'0'), f);
        if (mostCommon) return recur(index+1, mostCommon, search+((total < countOne << 1) ? '1':'0'), f);
        else return recur(index+1, mostCommon, search+((total > countOne << 1) ? '1':'0'), f);
    }
}
