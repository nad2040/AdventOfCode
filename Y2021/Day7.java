package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day7 {

    static int[] crabPositions = new int[1000];

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input7.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String[] crab = reader.readLine().split(",");
        reader.close();

        int index=0;
        for (String s: crab) crabPositions[index++] = Integer.parseInt(s);
    }

    public static void sort() {
        int n = crabPositions.length;  
        int temp = 0;  
        for(int i=0; i<n; i++){  
            for(int j=1; j<(n-i); j++){  
                if(crabPositions[j-1] > crabPositions[j]){  
                    //swap elements  
                    temp = crabPositions[j-1];  
                    crabPositions[j-1] = crabPositions[j];  
                    crabPositions[j] = temp;  
                }    
            }  
         }  
    }
    
    public static void part1() {
        // for(int i:crabPositions) System.out.println(i);
        int pos = (crabPositions[499] + crabPositions[500])/2;

        int sum = 0;
        for (int i:crabPositions) sum += Math.abs(i-pos);

        System.out.println(sum);
    }

    public static void part2() {
        int total = 0;
        for (int i:crabPositions) total += i;

        double avg = (double) total / 1000;

        int lower = (int) Math.floor(avg);
        int upper = (int) Math.ceil(avg);

        int sumL = 0, sumH = 0;

        for (int i:crabPositions) {
            int diff = Math.abs(i-lower);
            sumL += diff*(diff+1)/2;
        }
        for (int i:crabPositions) {
            int diff = Math.abs(i-upper);
            sumH += diff*(diff+1)/2;
        }

        System.out.println(sumL);
        System.out.println(sumH);

    }
    
    public static void main(String[] args) throws IOException {
        parse();
        sort();
        part1();
        part2();
    }
}
