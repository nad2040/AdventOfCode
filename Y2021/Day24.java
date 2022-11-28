package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;

public class Day24 {

    static ArrayDeque<Integer> stack = new ArrayDeque<>();
    static int[][] data = new int[14][2]; // store check and offset. not my solution. https://github.com/kemmel-dev/AdventOfCode2021/blob/master/day24/AoC%20Day%2024.pdf

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input24.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int index=0;

        while ((line = reader.readLine()) != null) {
            if (index%18 == 5) data[index/18][0] = Integer.parseInt(line.split(" ")[2]); // index 5 is check
            if (index%18 == 15) data[index/18][1] = Integer.parseInt(line.split(" ")[2]); // index 15 is offset

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

    public static boolean validate(String num) {
        stack.clear();
        for (int i=0; i<14; i++) {
            int w = num.charAt(i) - '0';
            int check = data[i][0], offset = data[i][1];
            if (check > 0) stack.push(w+offset);
            else {
                int leftover = stack.pop();
                if (leftover + check != w) stack.push(w+offset);
            }
        }

        return stack.isEmpty();
    }

    public static void part1() {
        for (int d14=9; d14>0; d14--)
        for (int d13=9; d13>0; d13--)
        for (int d12=9; d12>0; d12--)
        for (int d11=9; d11>0; d11--)
        for (int d10=9; d10>0; d10--)
        for (int d9=9; d9>0; d9--)
        for (int d8=9; d8>0; d8--)
        for (int d7=9; d7>0; d7--)
        for (int d6=9; d6>0; d6--)
        for (int d5=9; d5>0; d5--)
        for (int d4=9; d4>0; d4--)
        for (int d3=9; d3>0; d3--)
        for (int d2=9; d2>0; d2--)
        for (int d1=9; d1>0; d1--)

    }

    public static void main(String[] args) throws IOException {
        parse();
        part1();
        
    }
    
}
