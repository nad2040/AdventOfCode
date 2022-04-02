package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Day10 {
    
    static int i;
    static String line;
    static HashMap<Character, Character> map = new HashMap<>();

    public static void part1() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input10.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));

        int sum=0;

        ArrayDeque<Character> chunk = new ArrayDeque<>();
        while ((line = reader.readLine()) != null) {
            chunk.clear();

            for (int i=0; i<line.length(); i++) {
                char c = line.charAt(i);
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    chunk.push(c);
                } else if (!chunk.peek().equals(map.get(c))) {
                    sum += switch (c) {
                        case ')'->3;
                        case ']'->57;
                        case '}'->1197;
                        case '>'->25137;
                        default->0;
                    };
                    break;
                } else c = chunk.pop();
            }
        }
        System.out.println(sum);
        reader.close();
    }

    public static void part2() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input10.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        
        ArrayList<Long> points = new ArrayList<>();

        ArrayDeque<Character> chunk = new ArrayDeque<>();
        while ((line = reader.readLine()) != null) {
            chunk.clear();
            long local = 0;
            int i;
            for (i=0; i<line.length(); i++) {
                char c = line.charAt(i);
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    chunk.push(c);
                } else if (!chunk.peek().equals(map.get(c))) {
                    break;
                } else {
                    c = chunk.pop();
                }
            }
            if (i<line.length()-1) continue;

            while (!chunk.isEmpty()) {
                char c = chunk.pop();
                local *= 5;
                local += switch (c) {
                    case '('->1;
                    case '['->2;
                    case '{'->3;
                    case '<'->4;
                    default->0;
                };
            }
            points.add(local);
        }

        points.sort(null);
        System.out.println(points.get(points.size()/2));

        reader.close();
    }

    public static void main(String[] args) throws IOException {
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        map.put('>', '<');
        part1();
        part2();
    }
}
