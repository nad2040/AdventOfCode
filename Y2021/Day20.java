package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day20 {
    static final int OFFSET = 55;
    static char[] map;
    static boolean[][] image;
    static boolean[][] copy;

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input20.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int ind=0;

        line = reader.readLine();
        map = line.toCharArray();
        line = reader.readLine();

        image = new boolean[100+2*OFFSET][100+2*OFFSET];

        while ((line = reader.readLine()) != null) {
            for (int j=0; j<line.length(); j++) {
                image[ind+OFFSET][j+OFFSET] = (line.charAt(j)=='#');
            }
            ind++;
        }
        reader.close();
    }
    
    public static void part1() {
        for (int enhance=0; enhance<2; enhance++) {
            copy = new boolean[100+2*OFFSET][100+2*OFFSET];

            for (int i=0; i<image.length; i++) {
                for (int j=0; j<image[0].length; j++) {
                    if (i==0 || i==image.length-1 || j==0 || j==image[0].length-1) {
                        copy[i][j] = !image[i][j];
                    } else {
                        int mapIndex = ((image[i-1][j-1]?1:0) << 8 |
                                        (image[i-1][j]?1:0) << 7 | 
                                        (image[i-1][j+1]?1:0) << 6 | 
                                        (image[i][j-1]?1:0) << 5 | 
                                        (image[i][j]?1:0) << 4 | 
                                        (image[i][j+1]?1:0) << 3 | 
                                        (image[i+1][j-1]?1:0) << 2 | 
                                        (image[i+1][j]?1:0) << 1 | 
                                        (image[i+1][j+1]?1:0));

                        copy[i][j] = (map[mapIndex]=='#');
                    }
                }
            }
            image = copy;
        }
        // for (boolean[] r:image) {
        //     for (boolean b:r) System.out.print(b?'#':'.');
        //     System.out.println();
        // }
        int count = 0;
        for (boolean[] r:image) {
            for (boolean b:r) if (b) count++;
        }
        System.out.println(count);
    }

    public static void part2() {
        for (int enhance=0; enhance<50; enhance++) {
            copy = new boolean[100+2*OFFSET][100+2*OFFSET];

            for (int i=0; i<image.length; i++) {
                for (int j=0; j<image[0].length; j++) {
                    if (i==0 || i==image.length-1 || j==0 || j==image[0].length-1) {
                        copy[i][j] = !image[i][j];
                    } else {
                        int mapIndex = ((image[i-1][j-1]?1:0) << 8 |
                                        (image[i-1][j]?1:0) << 7 | 
                                        (image[i-1][j+1]?1:0) << 6 | 
                                        (image[i][j-1]?1:0) << 5 | 
                                        (image[i][j]?1:0) << 4 | 
                                        (image[i][j+1]?1:0) << 3 | 
                                        (image[i+1][j-1]?1:0) << 2 | 
                                        (image[i+1][j]?1:0) << 1 | 
                                        (image[i+1][j+1]?1:0));

                        copy[i][j] = (map[mapIndex]=='#');
                    }
                }
            }
            image = copy;
        }
        // for (boolean[] r:image) {
        //     for (boolean b:r) System.out.print(b?'#':'.');
        //     System.out.println();
        // }
        int count = 0;
        for (boolean[] r:image) {
            for (boolean b:r) if (b) count++;
        }
        System.out.println(count);
    }


    public static void main(String[] args) throws IOException {
        parse();
        part1();
        parse();
        part2();
    }
}
