package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day6 {
    
    static ArrayList<Long> populationByAge;

    public static void part1() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input6.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String[] ages = reader.readLine().split(",");
        
        reader.close();

        long[] ageCounts = new long[9];
        for (String s: ages) ageCounts[Integer.parseInt(s)]++;
        for (long a: ageCounts) populationByAge.add(a);

        for (int day=0; day<80; day++) {
            long zeroTimer = populationByAge.remove(0);
            populationByAge.add(zeroTimer);
            populationByAge.set(6, populationByAge.get(6) + zeroTimer);
        }

        long sum = 0;
        for (long i:populationByAge) sum+=i;
        System.out.println(sum);
    }

    public static void part2() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input6.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String[] ages = reader.readLine().split(",");
        
        reader.close();

        long[] ageCounts = new long[9];
        for (String s: ages) ageCounts[Integer.parseInt(s)]++;
        for (long a: ageCounts) populationByAge.add(a);

        for (int day=0; day<256; day++) {
            long zeroTimer = populationByAge.remove(0);
            populationByAge.add(zeroTimer);
            populationByAge.set(6, populationByAge.get(6) + zeroTimer);
        }

        long sum = 0;
        for (long i:populationByAge) sum+=i;
        System.out.println(sum);
    }

    public static void main(String[] args) throws IOException {
        populationByAge = new ArrayList<>();
        part1();
        populationByAge = new ArrayList<>();
        part2();
    }
}
