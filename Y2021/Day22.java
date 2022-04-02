package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day22 {

    static class BoundingBox {
        int x1,x2,y1,y2,z1,z2;


    }

    static boolean[][][] core = new boolean[101][101][101];
    static int[][] instructions = new int[420][6];
    static boolean[] whatToDo = new boolean[420]; 
    
    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input22.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int index=0;

        while ((line = reader.readLine()) != null) {
            String[] instr = line.split(" ");
            whatToDo[index] = instr[0].equals("on");
            String[] coords = instr[1].split("[a-z]=|\\.\\.|,[a-z]=");

            for (int i=0; i<6; i++) {
                instructions[index][i] = Integer.parseInt(coords[i+1]);
            }
            index++;
        }

        // for (int i=0; i<instructions.length; i++) {
        //     for (int j=0; j<instructions[0].length; j++) {
        //         System.out.print(instructions[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        
        reader.close();
    }
    
    public static void part1() {
        for (int i=0; i<20; i++) {
            int lowX = instructions[i][0];
            int highX = instructions[i][1];
            int lowY = instructions[i][2];
            int highY = instructions[i][3];
            int lowZ = instructions[i][4];
            int highZ = instructions[i][5];

            for (int x=lowX; x<=highX; x++) {
                for (int y=lowY; y<=highY; y++) {
                    for (int z=lowZ; z<=highZ; z++) {
                        core[50+x][50+y][50+z]=whatToDo[i];
                    }
                }
            }
        }
        int count = 0;
        for (boolean[][] layer:core)
            for (boolean[] row:layer)
                for (boolean b:row) {
                    if (b) count++;
                }

        System.out.println(count);
    }

    public static void part2() {
        
    }

    public static void main(String[] args) throws IOException {
        parse();
        part1();
        part2();
    }
}
