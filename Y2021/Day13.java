package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day13 {
    static boolean[][] paper = new boolean[895][1311];
    static int[][] coordinates = new int[743][2];
    static ArrayList<Integer[]> foldInstructions = new ArrayList<>();

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input13.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int index=0;
        while (!(line = reader.readLine()).isEmpty()) {
            String[] coord = line.split(",");
            int x = Integer.parseInt(coord[0]);
            int y = Integer.parseInt(coord[1]);
            coordinates[index][0] = y; // y = row of array
            coordinates[index][1] = x; // x = column of array
            index++;
        }

        while ((line = reader.readLine()) != null) {
            String[] fold = line.split(" ")[2].split("=");
            if (fold[0].equals("x")) foldInstructions.add(new Integer[] {0, Integer.parseInt(fold[1])}); // if fold x set column to be folded 
            else foldInstructions.add(new Integer[] {Integer.parseInt(fold[1]), 0}); // else set row to be folded
        }

        for (int i=0; i<coordinates.length; i++) {
            paper[coordinates[i][0]][coordinates[i][1]] = true;
        }
        
        reader.close();
    }

    public static void part1() {
        Integer[] foldinstr = foldInstructions.get(0);
        int line;
        int count = 0;
        boolean[][] folded;
        if (foldinstr[0] > 0) { // horz fold
            folded = new boolean[paper.length/2][paper[0].length];
            line = foldinstr[0];
            for (int i=0; i<paper[0].length; i++) {
                for (int j=0; j<line; j++) {
                    folded[j][i] = paper[j][i] || paper[2*line-j][i];
                }
            }
        } else { // vert vold
            folded = new boolean[paper.length][paper[0].length/2];
            line = foldinstr[1];
            for (int i=0; i<paper.length; i++) {
                for (int j=0; j<line; j++) {
                    folded[i][j] = paper[i][j] || paper[i][2*line-j];
                }
            }
        }
        for (boolean[] r:folded)
            for (boolean b:r)
                if (b) count++;

        System.out.println(count);
    }

    public static void part2() {
        boolean[][] save = paper;
        for (Integer[] foldinstr : foldInstructions) {
            int line;
            boolean[][] folded;
            if (foldinstr[0] > 0) { // horz fold
                folded = new boolean[save.length/2][save[0].length];
                line = foldinstr[0];
                for (int i=0; i<save[0].length; i++) {
                    for (int j=0; j<line; j++) {
                        folded[j][i] = save[j][i] || save[2*line-j][i];
                    }
                }
                save = folded;
            } else { // vert vold
                folded = new boolean[save.length][save[0].length/2];
                line = foldinstr[1];
                for (int i=0; i<save.length; i++) {
                    for (int j=0; j<line; j++) {
                        folded[i][j] = save[i][j] || save[i][2*line-j];
                    }
                }
                save = folded;
            }
        }
        for (boolean[] row: save) {
            for (boolean b: row) {
                System.out.print(b?'\u2588':' ');
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        parse();
        part1();
        part2();
    }
}
