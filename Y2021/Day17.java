package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day17 {

    static int lowX, highX, lowY, highY;

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input17.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = reader.readLine();
        reader.close();

        String[] coords = line.split(",| |=|\\.\\.");
        lowX = Integer.parseInt(coords[3]);
        highX = Integer.parseInt(coords[4]);
        lowY = Integer.parseInt(coords[7]);
        highY = Integer.parseInt(coords[8]);
    }

    public static int sumTo(int n) {
        return n*(n+1) / 2;
    }

    public static boolean sim(int vx, int vy) {
        int x=0, y=0;
        while (x<=highX && y>=lowY && !(x>=lowX && y<=highY)) {
            x+=vx;
            y+=vy;

            if (vx > 0) vx--;
            else if (vx < 0) vx++;
            vy--;
        }
        return (x >= lowX && x <= highX && y >= lowY && y <= highY);
    }

    public static void part1() {
        int maxY=0;
        for (int x = (int) Math.sqrt(lowX) - 1; x < lowX; x++) { // check from first triangular number of x to be in range and end at first x value in range because y would be negative at that point.
            for (int y=0; y < -lowY; y++) {
                if (sim(x,y) && sumTo(y) > maxY) maxY = sumTo(y);
            }
        }
        System.out.println(maxY);
    }
    public static void part2() {
        int count = 0;
        for (int x = 1; x <= highX; x++) {
            for (int y=lowY; y <= -lowY; y++) { // if vy < lowY or vy > -lowY the y coordinate will never land in bounds.
                if (sim(x,y)) count++;
            }
        }
        System.out.println(count);
    }
    
    public static void main(String[] args) throws IOException {
        parse();
        part1();
        part2();
    }
}
