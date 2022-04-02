package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day11 {

    static int[][] octopuses;
    static boolean[][] flashed;

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input11.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int index=0;
        while ((line = reader.readLine()) != null) {
            for (int i=0; i<line.length(); i++) {
                octopuses[index][i] = line.charAt(i) - '0';
            }
            index++;
        }
        reader.close();
    }
    
    public static void print() {
        for (int[] row:octopuses) {
            for (int i:row) System.out.print(i+" ");
            System.out.println();
        }
    }

    public static void flash(int x, int y) {
        int[] dx = {0,1,1, 1, 0,-1,-1,-1};
        int[] dy = {1,1,0,-1,-1,-1, 0, 1};

        flashed[x][y] = true;

        for (int i=0; i<8; i++) {
            int newX = x+dx[i];
            int newY = y+dy[i];
            if (newX>=0 && newX<10 && newY>=0 && newY<10) {
                octopuses[newX][newY]++;
                if (octopuses[newX][newY] > 9 && !flashed[newX][newY]) {
                    flash(newX,newY);
                }
            }
        }
    }

    public static void part1() throws IOException {
        parse();

        int totalFlashes = 0;

        for (int step=0; step<100; step++) {
            for (int i=0; i<10; i++) {
                for (int j=0; j<10; j++) {
                    octopuses[i][j]++;
                }
            }

            for (int i=0; i<10; i++) {
                for (int j=0; j<10; j++) {
                    if (octopuses[i][j] > 9 && !flashed[i][j]) {
                        flash(i,j);
                    }
                }
            }

            for (int i=0; i<10; i++) {
                for (int j=0; j<10; j++) {
                    if (flashed[i][j]) {
                        totalFlashes++;
                        flashed[i][j] = false;
                        octopuses[i][j] = 0;
                    }
                }
            }
        }

        System.out.println(totalFlashes);
    }

    public static void part2() throws IOException {
        parse();

        int count = 0;
        int step = 0;
        while (count < 100) {
            count = 0;
            step++;
            for (int i=0; i<10; i++) {
                for (int j=0; j<10; j++) {
                    octopuses[i][j]++;
                }
            }

            for (int i=0; i<10; i++) {
                for (int j=0; j<10; j++) {
                    if (octopuses[i][j] > 9 && !flashed[i][j]) {
                        flash(i,j);
                    }
                }
            }

            for (int i=0; i<10; i++) {
                for (int j=0; j<10; j++) {
                    if (flashed[i][j]) {
                        count++;
                        flashed[i][j] = false;
                        octopuses[i][j] = 0;
                    }
                }
            }

            // System.out.println("Step "+step);
            // print();
            // System.out.println();
        }

        System.out.println(step);
    }
    
    public static void main(String[] args) throws IOException {
        octopuses = new int[10][10];
        flashed = new boolean[10][10];
        part1();

        octopuses = new int[10][10];
        flashed = new boolean[10][10];
        part2();
    }
}
