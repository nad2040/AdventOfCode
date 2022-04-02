package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class Day15 {
    static class Point {
        int r;
        int c;
        int value;
        int smallestRiskToA;
        public Point(int r, int c, int value) {
            this.r=r; this.c=c; this.value=value; this.smallestRiskToA = Integer.MAX_VALUE;
        }
        public Point(int r, int c) {
            this.r=r; this.c=c; this.value=0; this.smallestRiskToA = 0;
        }
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point)) return false;
            Point other = (Point)o;
            return this.r==other.r && this.c==other.c; 
        }
        @Override
        public String toString() {
            return "row: " + r + " col: " + c + " val: " + value + " risk: " + smallestRiskToA;
        }
    }
    static class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.smallestRiskToA - o2.smallestRiskToA;
        }
    }

    // static HashMap<Point,Point> prev = new HashMap<>();
    static HashSet<Point> visited;
    static ArrayList<Point> unvisited;
    // static ArrayDeque<Point> reconstruct = new ArrayDeque<>();

    public static void parse(int numR, int numC) throws IOException {
        File f = new File("./AdventOfCode/Y2021/input15.txt");
        BufferedReader reader;
        String line;

        visited = new HashSet<>();
        unvisited = new ArrayList<>();
        
        for (int tileR = 0; tileR < numR; tileR++) {
            for (int tileC = 0; tileC < numC; tileC++) {
                reader = new BufferedReader(new FileReader(f));
                int index=0;
                while ((line = reader.readLine()) != null) {
                    for (int i=0; i<line.length(); i++) {
                        int val = (line.charAt(i) - '0' + tileR + tileC - 1) % 9 + 1;
                        Point p = new Point(tileR*100 + index, tileC*100 + i, val);
                        if (tileR == 0 && tileC == 0 && p.r==0 && p.c==0) p.smallestRiskToA=0; // if 0,0 total risk = 0
                        unvisited.add(p);
                    }
                    index++;
                }
                reader.close();
            }
        }
    }

    public static void dijkstra(int numR, int numC) {
        int[] dr = {1,0,-1,0};
        int[] dc = {0,1,0,-1};
        PointComparator comp = new PointComparator();

        while (!unvisited.isEmpty()) {
            Point current = unvisited.get(0); // current vertex we are checking
            
            for (Point neighbor : unvisited) {
                for (int index=0; index<4; index++) {
                    int newR = current.r + dr[index];
                    int newC = current.c + dc[index];
                    if (newR < 0 || newR >= 100*numR || newC < 0 || newC >= 100*numC) continue;
                    Point unvisitedNeighbor = new Point(newR, newC);
                 // loop through and find neighbors
                    if (neighbor.equals(unvisitedNeighbor)) { // if neighbor exists and unvisited
                        int newRisk = current.smallestRiskToA + neighbor.value;
                        if (newRisk < neighbor.smallestRiskToA) {
                            neighbor.smallestRiskToA = newRisk;
                        }
                    }
                }
            }
            visited.add(unvisited.remove(0));
            Collections.sort(unvisited, comp); // probably very inefficient but works
        }
    }

    public static void part1() {
        dijkstra(100,100);
        for (Point p: visited) {
            if (p.r == 99 && p.c == 99) System.out.println(p.smallestRiskToA);
        }
    }

    public static void part2() {
        dijkstra(500,500);
        for (Point p: visited) {
            if (p.r == 499 && p.c == 499) System.out.println(p.smallestRiskToA);
        }
    }

    public static void main(String[] args) throws IOException {
        parse(1,1);
        part1();
        
        parse(5,5);
        part2();
    }
    
}
