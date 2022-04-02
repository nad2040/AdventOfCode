package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day5 {

    static int[][] field;

    public static void part1() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input5.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] coords = line.split(",| -> ");
            int[] nums = new int[4];
            int index=0;
            for (String s: coords) nums[index++] = Integer.parseInt(s);

            if (nums[0] != nums[2] && nums[1] != nums[3]) continue;

            int dx=0;
            int dy=0;
            if (nums[0]>nums[2]) dx=-1;
            else if (nums[0]<nums[2]) dx=1;
            if (nums[1]>nums[3]) dy=-1;
            else if (nums[1]<nums[3]) dy=1;

            int i,j;
            for (i=nums[0], j=nums[1]; i!=nums[2] || j!=nums[3]; i+=dx, j+=dy) {
                field[j][i]++;
            }
            field[j][i]++;
        }
        int sum = 0;
        for (int[] row : field) for (int val : row) if (val >= 2) sum++;

        System.out.println(sum);

        reader.close();
    }

    public static void part2() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input5.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] coords = line.split(",| -> ");
            int[] nums = new int[4];
            int index=0;
            for (String s: coords) nums[index++] = Integer.parseInt(s);

            int dx=0;
            int dy=0;
            if (nums[0]>nums[2]) dx=-1;
            else if (nums[0]<nums[2]) dx=1;
            if (nums[1]>nums[3]) dy=-1;
            else if (nums[1]<nums[3]) dy=1;

            int i,j;
            for (i=nums[0], j=nums[1]; i!=nums[2] || j!=nums[3]; i+=dx, j+=dy) {
                field[j][i]++;
            }
            field[j][i]++;
        }
        int sum = 0;
        for (int[] row : field) for (int val : row) if (val >= 2) sum++;

        System.out.println(sum);

        reader.close();
    }
    
    public static void main(String[] args) throws IOException {
        field = new int[1000][1000];
        part1();
        field = new int[1000][1000];
        part2();
    }
}
