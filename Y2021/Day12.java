package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Day12 {
    static HashMap<String, ArrayList<String>> map = new HashMap<>();
    static ArrayDeque<String> path = new ArrayDeque<>();
    static int countPaths = 0;

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input12.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] pos = line.split("-");
            if (!map.containsKey(pos[0])) map.put(pos[0], new ArrayList<>());
            if (!map.containsKey(pos[1])) map.put(pos[1], new ArrayList<>());
            map.getOrDefault(pos[0],new ArrayList<>()).add(pos[1]);
            map.getOrDefault(pos[1],new ArrayList<>()).add(pos[0]);
        }
        reader.close();
    }

    public static boolean isLowerCase(String s) { return s.equals(s.toLowerCase()); }

    public static void recur() {
        if (path.peek().equals("end")) { countPaths++; return; }

        ArrayList<String> nextLocations = map.get(path.peek());
        for (int i=0; i<nextLocations.size(); i++) {
            String nextNode = nextLocations.get(i);
            if (path.contains(nextNode) && isLowerCase(nextNode)) continue;
            path.push(nextNode);
            recur();
            path.pop();
        }
    }

    public static void part1() {
        path.push("start");
        recur();
        System.out.println(countPaths);
    }

    public static int countOccurrences(String s) {
        int count = 0;
        for (int i=0; i<path.size(); i++) {
            if (path.toArray()[i].equals(s)) count++;
        }
        return count;
    }

    public static void recur2(boolean canRepeatLowerCase) {
        if (path.peek().equals("end")) { countPaths++; return; }

        ArrayList<String> nextLocations = map.get(path.peek());
        for (int i=0; i<nextLocations.size(); i++) {
            String nextNode = nextLocations.get(i);
            if (nextNode.equals("start")) continue;

            if (isLowerCase(nextNode)) {
                if (countOccurrences(nextNode) >= 1) {
                    if (canRepeatLowerCase) {
                        path.push(nextNode);
                        recur2(false);
                        path.pop();
                    }
                } else {
                    path.push(nextNode);
                    recur2(canRepeatLowerCase);
                    path.pop();
                }
            } else {
                path.push(nextNode);
                recur2(canRepeatLowerCase);
                path.pop();
            }
        }
    }

    public static void part2() {
        path.push("start");
        recur2(true);
        System.out.println(countPaths);
    }

    public static void main(String[] args) throws IOException {
        parse();
        // long t1=System.nanoTime();
        path.clear();
        countPaths = 0;
        part1();
        // long t2=System.nanoTime();
        path.clear();
        countPaths = 0;
        part2();
        // long t3=System.nanoTime();
        // System.out.println(t2-t1);
        // System.out.println(t3-t2);
    }
}