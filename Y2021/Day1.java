package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        ArrayList<Integer> data = new ArrayList<>();

        File f = new File("./AdventOfCode/Y2021/input.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(Integer.parseInt(line));
        }
        int sum,prev=0,count=0;

        for (int i=0; i<data.size()-2; ++i) {
            sum = data.get(i) + data.get(i+1) + data.get(i+2);
            if (i==0) {
                prev = sum;
            } else {
                if (sum > prev) count++;
                prev = sum;
            }
        }

        System.out.println(count);
        reader.close();
    }
}