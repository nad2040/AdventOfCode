package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {
    public static void main(String[] args) throws IOException {
        File f = new File("./AdventOfCode/Y2021/input2.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int h = 0, d = 0, aim = 0;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            String[] parts = line.split(" ");
            int i = Integer.parseInt(parts[1]);
            switch (parts[0].charAt(0)) {
                case 'f':
                    h += i;
                    d += aim * i;
                    break;
                case 'u':
                    aim -= i;
                    break;
                case 'd':
                    aim += i;
                    break;
                default:
                    break;
            }
        }
        System.out.println(h);
        System.out.println(d);
        System.out.println(h * d);
        reader.close();
    }
}
