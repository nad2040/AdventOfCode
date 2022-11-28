package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day22 {

    static class BoundingBox {
        public int x1,x2,y1,y2,z1,z2;
        public boolean action;
        
        public BoundingBox(int[] coords, boolean action) {
            this.x1=coords[0];
            this.x2=coords[1];
            this.y1=coords[2];
            this.y2=coords[3];
            this.z1=coords[4];
            this.z2=coords[5];
            this.action = action;
        }

        public boolean containsX(int x) { return x >= x1 && x <= x2; }
        public boolean containsY(int y) { return y >= y1 && y <= y2; }
        public boolean containsZ(int z) { return z >= z1 && z <= z2; }
        public boolean collidesWith(BoundingBox other) {
            return (this.x1 <= other.x2 && this.x2 >= other.x1) &&
                   (this.y1 <= other.y2 && this.y2 >= other.y1) &&
                   (this.z1 <= other.z2 && this.z2 >= other.z1);
        }

        public int volume() { return action? (x2-x1+1)*(y2-y1+1)*(z2-z1+1) : 0; }
        
        public String toString() { return (action? "on " : "off ") + "x=" + x1 + ".." + x2 + ",y=" + y1 + ".." + y2 + ",z=" + z1 + ".." + z2; }
    }

    static boolean[][][] core = new boolean[101][101][101];
    static BoundingBox[] boxes = new BoundingBox[420];
    
    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input22.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int index=0;

        while ((line = reader.readLine()) != null) {
            String[] instr = line.split(" ");

            boolean action = instr[0].equals("on");
            int[] coords = Arrays.stream(instr[1].substring(2).split("[a-z]=|\\.\\.|,[a-z]=")).mapToInt(Integer::parseInt).toArray();  

            boxes[index] = new BoundingBox(coords, action);
            index++;
        }
        
        reader.close();
    }

    public static ArrayList<BoundingBox> collide(ArrayList<BoundingBox> collision, BoundingBox newBox) {
        int current_count = collision.size();

        for (int i=0; i<current_count; i++) {
            BoundingBox b = collision.remove(0);

            if (!newBox.collidesWith(b)) { collision.add(b); continue; }
            
        }

        return collision;
    }
    
    public static void part1() {
        for (int i=0; i<20; i++) {
            BoundingBox box = boxes[i];

            for (int x=box.x1; x<=box.x2; x++) {
                for (int y=box.y1; y<=box.y2; y++) {
                    for (int z=box.z1; z<=box.z2; z++) {
                        core[50+x][50+y][50+z]=box.action;
                    }
                }
            }
        }
        int count = 0;
        for (boolean[][] layer:core)
            for (boolean[] row:layer)
                for (boolean b:row) {
                    if (b) count++;
                }

        System.out.println(count);
    }

    public static void part2() {
        // for (BoundingBox b : boxes) {
        //     System.out.println(b);
        // }
        ArrayList<BoundingBox> intersections;
    }

    public static void main(String[] args) throws IOException {
        parse();
        // part1();
        part2();
    }
}
