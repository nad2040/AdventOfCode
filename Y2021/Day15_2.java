package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Day15_2 {
    static class Node {
        int row;
        int col;
        int smallestRisk;
        int distanceFromEnd;
        public Node(int distance) {
            this.smallestRisk = Integer.MAX_VALUE;
            this.distanceFromEnd = distance;
        }
    }
    static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return (o1.smallestRisk + o1.distanceFromEnd) - (o2.smallestRisk + o2.distanceFromEnd);
        }
    }

    static HashSet<Node> closedList;
    static PriorityQueue<Node> openList;

    static int[][] risks;
    static Node[][] nodes;

    public static int distanceHeuristic(int a, int b, int c, int d) { // direct distance formula. manhattan-dist is inaccurate for part 2
        return (int) Math.sqrt((double)(a-b)*(a-b) + (c-d)*(c-d));
    }

    public static void parse(int numR, int numC) throws IOException {
        File f = new File("./AdventOfCode/Y2021/input15.txt");
        BufferedReader reader;
        String line;
        
        risks = new int[100*numR][100*numC];
        nodes = new Node[100*numR][100*numC];
        
        for (int tileR = 0; tileR < numR; tileR++) {
            for (int tileC = 0; tileC < numC; tileC++) {
                reader = new BufferedReader(new FileReader(f));
                int index=0;
                while ((line = reader.readLine()) != null) {
                    for (int i=0; i<line.length(); i++) {
                        int risk = (line.charAt(i) - '0' + tileR + tileC - 1) % 9 + 1;
                        risks[tileR*100 + index][tileC*100 + i] = risk;

                        Node p = new Node(distanceHeuristic(numR*100, tileR*100 + index, numC*100, tileC*100+i));
                        if (tileR == 0 && tileC == 0 && index==0 && i==0) {
                            p.smallestRisk=0; // if 0,0 total risk = 0
                            p.row=0;
                            p.col=0;
                        }
                        nodes[tileR*100 + index][tileC*100 + i] = p;
                    }
                    index++;
                }
                reader.close();
            }
        }
    }

    public static void a_star(int numR, int numC) {
        int[] dr = {1,0,-1,0};
        int[] dc = {0,1,0,-1};

        closedList = new HashSet<>();
        openList = new PriorityQueue<>(new NodeComparator());
        openList.add(nodes[0][0]);

        while (true) {
            Node current = openList.poll();
            if (current.equals(nodes[numR-1][numC-1])) break;
            closedList.add(current);

            for (int index=0; index<4; index++) {
                int newR = current.row + dr[index];
                int newC = current.col + dc[index];
                if (newR < 0 || newR >= numR || newC < 0 || newC >= numC) continue;

                if (nodes[newR][newC].smallestRisk > current.smallestRisk + risks[newR][newC] && closedList.contains(nodes[newR][newC]))
                    nodes[newR][newC].smallestRisk = current.smallestRisk + risks[newR][newC];
                else if (nodes[newR][newC].smallestRisk > current.smallestRisk + risks[newR][newC] && openList.contains(nodes[newR][newC]))
                    nodes[newR][newC].smallestRisk = current.smallestRisk + risks[newR][newC];
                else if (!openList.contains(nodes[newR][newC]) && !closedList.contains(nodes[newR][newC])) {
                    nodes[newR][newC].smallestRisk = current.smallestRisk + risks[newR][newC];
                    nodes[newR][newC].row = newR;
                    nodes[newR][newC].col = newC;
                    openList.add(nodes[newR][newC]);
                }
            }
        }
    }

    public static void part1() {
        a_star(nodes.length,nodes[0].length);
        System.out.println(nodes[nodes.length-1][nodes[0].length-1].smallestRisk);
    }

    public static void part2() {
        a_star(nodes.length,nodes[0].length);
        System.out.println(nodes[nodes.length-1][nodes[0].length-1].smallestRisk);
    }

    public static void main(String[] args) throws IOException {
        parse(1,1);
        part1();
        
        parse(5,5);
        part2();
    }    
}