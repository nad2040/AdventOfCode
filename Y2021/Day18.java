package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day18 {
    static class SnailfishNum {
        static final int NUM = 0, PAIR = 1;
        private static int indexingHelper;
        int depth;
        int order;
        int maxOrder;

        int number;
        int type;
        SnailfishNum left;
        SnailfishNum right;

        public SnailfishNum(int number) {
            this.number=number; this.type=NUM;
        }
        public SnailfishNum(SnailfishNum left, SnailfishNum right) {
            this.left=left; this.right=right;
            this.type=PAIR;
            indexingHelper=1;
            setDepthAndIndex(this, 0);
            this.maxOrder = indexingHelper-1;
        }
        public static void setDepthAndIndex(SnailfishNum sfn, int d) { // find the order and depth of each number inside sfn for easy parsing later
            sfn.depth = d;
            if (sfn.type == NUM) sfn.order = indexingHelper++;
            else {
                setDepthAndIndex(sfn.left, d+1);
                setDepthAndIndex(sfn.right, d+1);
            }
        }
        
        public int magnitude() { // return magnitued of this sfn
            if (type == NUM) return number;
            else return 3*left.magnitude() + 2*right.magnitude();
        }

        public static void find(SnailfishNum sfn, int pos, int add) {
            boolean found = false;
            if (sfn.type == PAIR && sfn.left.type == NUM && sfn.left.order == pos) {
                found = true;
                sfn.left = new SnailfishNum(sfn.left.number + add);
            }
            if (sfn.type == PAIR && sfn.right.type == NUM && sfn.right.order == pos) {
                found = true;
                sfn.right = new SnailfishNum(sfn.right.number + add);
            }
            if (sfn.type == PAIR && !found) {
                find(sfn.left,pos,add);
                find(sfn.right,pos,add);
            }
        }
        public SnailfishNum getExplosionPair() {
            SnailfishNum pair=null;
            if (type == PAIR && depth == 4) return this; // pair that has depth 4 will explode
            if (type == PAIR) {
                if (left.getExplosionPair() != null) pair = left.getExplosionPair();
                else if (right.getExplosionPair() != null) pair = right.getExplosionPair();
            }
            return pair;
        }
        public void removeExplodedPair(int pos) {
            if (type == PAIR && left.type == PAIR && left.left.type == NUM && left.left.order == pos) left = new SnailfishNum(0);
            else if (type == PAIR && right.type == PAIR && right.left.type == NUM && right.left.order == pos) right = new SnailfishNum(0);
            if (type == PAIR) {
                left.removeExplodedPair(pos);
                right.removeExplodedPair(pos);
            }
        }
        public static void explode(SnailfishNum sfn) {
            SnailfishNum explodingPair = sfn.getExplosionPair();
            if (explodingPair.left.order > 1) find(sfn,explodingPair.left.order-1, explodingPair.left.number);
            if (explodingPair.left.order < sfn.maxOrder-1) find(sfn,explodingPair.left.order+2, explodingPair.right.number);
            sfn.removeExplodedPair(explodingPair.left.order);
            indexingHelper = 1;
            setDepthAndIndex(sfn, 0);
        }
        public SnailfishNum getSplittingNum() {
            SnailfishNum num=null;
            if (type == NUM && number > 9) return this; // num >= 10 -> split
            if (type == PAIR) {
                if (left.getSplittingNum() != null) num = left.getSplittingNum();
                else if (right.getSplittingNum() != null) num = right.getSplittingNum();
            }
            return num;
        }
        public void addSplitNumber(int pos) {
            boolean found = false;
            if (type == PAIR && left.type == NUM && left.order == pos) {
                found = true;
                left = new SnailfishNum(new SnailfishNum(left.number / 2), new SnailfishNum((left.number + 1) / 2));
            }
            else if (type == PAIR && right.type == NUM && right.order == pos) {
                found = true;
                right = new SnailfishNum(new SnailfishNum(right.number / 2), new SnailfishNum((right.number + 1) / 2));
            }
            if (type == PAIR && !found) {
                left.addSplitNumber(pos);
                right.addSplitNumber(pos);
            }
        }
        public static void split(SnailfishNum sfn) {
            SnailfishNum splittingNum = sfn.getSplittingNum();
            sfn.addSplitNumber(splittingNum.order);
            indexingHelper = 1;
            setDepthAndIndex(sfn, 0);
        }

        public void reduce() {
            while (getExplosionPair() != null || getSplittingNum() != null) {
                if (getExplosionPair() != null) {
                    explode(this);
                } else if (getSplittingNum() != null) {
                    split(this);
                }
                // System.out.println(this);
            }
        }
        public SnailfishNum add(SnailfishNum other) {
            SnailfishNum sfn = new SnailfishNum(this, other);
            sfn.reduce();
            return sfn;
        }

        public static SnailfishNum fromString(String s) {
            if (!s.contains("[")) return new SnailfishNum(Integer.parseInt(s));

            int i=0;
            int depth = 0;
            while (i<s.length()) {
                if (s.charAt(i) == '[') depth++;
                if (s.charAt(i) == ']') depth--;
                if (s.charAt(i) == ',' && depth == 1) break;
                i++;
            }
            SnailfishNum leftSFN, rightSFN;
            leftSFN = SnailfishNum.fromString(s.substring(1, i));
            rightSFN = SnailfishNum.fromString(s.substring(i+1, s.length()-1));
            return new SnailfishNum(leftSFN, rightSFN);
        }
        public String toString() {
            if (type == NUM) return ""+number;
            else return "[" + left.toString() + "," + right.toString() + "]";
        }
        public String toLongString() {
            if (type == NUM) return "num: "+number + ", depth: " + depth + ", order: " + order;
            else return "[" + left.toLongString() + "," + right.toLongString() + "]";
        }

        public boolean equalsOther(SnailfishNum o) {
            if (this.type != o.type) return false;
            if (this.number != o.number) return false;
            
            if (type == PAIR) {
                return left.equalsOther(o.left) && right.equalsOther(o.right);
            } else return (this.type == o.type && this.number == o.number);
        }
    }

    static ArrayList<String> sfns = new ArrayList<>();

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input18.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        while ((line = reader.readLine()) != null) {
            sfns.add(line);
        }
        reader.close();
    }

    public static void part1() {
        SnailfishNum sum = SnailfishNum.fromString(sfns.get(0));
        for (int i=1; i<sfns.size(); i++) {
            sum = sum.add(SnailfishNum.fromString(sfns.get(i)));
            // System.out.println(sum.magnitude());
        }
        System.out.println(sum.magnitude());
    }

    public static void part2() {
        int max = 0;
        for (int i=0; i<sfns.size(); i++) {
            for (int j=0; j<sfns.size(); j++) {
                if (j==i) continue;
                SnailfishNum sfn1 = SnailfishNum.fromString(sfns.get(i));
                // System.out.println(sfn1);
                SnailfishNum sfn2 = SnailfishNum.fromString(sfns.get(j));
                // System.out.println(sfn2);
                SnailfishNum sum = sfn1.add(sfn2);
                // System.out.println(sum);
                int mag = sum.magnitude();
                // System.out.println("Magnitude: "+mag);
                if (mag > max) max = mag;
            }
        }
        System.out.println(max);
    }

    public static void main(String[] args) throws IOException {
        parse();
        
        part1();
        part2(); 

        // [[[8,7],[8,7]],[[7,8],[8,8]]]
        // [[[8,0],[9,9]],[[9,9],[9,5]]]
        // [[[[8,7],[8,7]],[[7,8],[8,8]]],[[[8,0],[9,9]],[[9,9],[9,5]]]]
        // 4664
    }
}
