package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;

public class Day19 {
    
    static class Vec3i {
        int x,y,z;

        public Vec3i(int x, int y, int z) {
            this.x=x; this.y=y; this.z=z;
        }

        public Vec3i orient(int o) {
            return switch (o) {
                case 0  -> new Vec3i( x, y, z); // x faces +x
                case 1  -> new Vec3i( x, z,-y); 
                case 2  -> new Vec3i( x,-y,-z);
                case 3  -> new Vec3i( x,-z, y);

                case 4  -> new Vec3i(-x, y,-z); // x faces -x
                case 5  -> new Vec3i(-x,-z,-y);
                case 6  -> new Vec3i(-x,-y, z);
                case 7  -> new Vec3i(-x, z, y);

                case 8  -> new Vec3i( y,-z,-x); // x faces +y
                case 9  -> new Vec3i( y,-x, z);
                case 10 -> new Vec3i( y, z, x);
                case 11 -> new Vec3i( y, x,-z);

                case 12 -> new Vec3i(-y,-x,-z); // x faces -y
                case 13 -> new Vec3i(-y,-z, x);
                case 14 -> new Vec3i(-y, x, z);
                case 15 -> new Vec3i(-y, z,-x);

                case 16 -> new Vec3i( z, y,-x); // x faces +z
                case 17 -> new Vec3i( z,-x,-y);
                case 18 -> new Vec3i( z,-y, x);
                case 19 -> new Vec3i( z, x, y);

                case 20 -> new Vec3i(-z, y, x); // x faces -z
                case 21 -> new Vec3i(-z, x,-y);
                case 22 -> new Vec3i(-z,-y,-x);
                case 23 -> new Vec3i(-z,-x, y);
                default -> this;
            };
        }
        
        public String toString() {
            return "["+x+", "+y+", "+z+"]";
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Vec3i)) return false;
            Vec3i o = (Vec3i) obj;
            return (this.x==o.x && this.y==o.y && this.z==o.z);
        }
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + x;
            hash = 31 * hash + y;
            hash = 31 * hash + z;
            return hash;
        }
    }

    static class BeaconScanner {
        int x,y,z,o;
        ArrayList<Vec3i> beacons;
        ArrayList<Vec3i> relativePositions;

        public BeaconScanner() {
            this.x=0; this.y=0; this.z=0; this.o=0;
            beacons = new ArrayList<>();
            relativePositions = new ArrayList<>();
        }

        public void add(Vec3i coords) {
            beacons.add(coords);
        }
        public void setRelativeDistances() {
            relativePositions = new ArrayList<>();
            for (int i=0; i<beacons.size(); i++) {
                for (int j=0; j<beacons.size(); j++) {
                    if (i==j) continue;
                    int dx=beacons.get(j).x - beacons.get(i).x;
                    int dy=beacons.get(j).y - beacons.get(i).y;
                    int dz=beacons.get(j).z - beacons.get(i).z;
                    this.relativePositions.add(new Vec3i(dx, dy, dz));
                }
            }
        }

        public boolean match(BeaconScanner root) {
            int count,orient,index=0;
            Vec3i lastMatch=null;
            boolean found = false;
            for (orient=0; orient<24; orient++) {
                count = 0;
                // System.out.println("orient "+orient);
                for (int i=0; i<this.relativePositions.size(); i++) {
                    if (root.relativePositions.contains(this.relativePositions.get(i).orient(orient))) {
                        count++;
                        // System.out.println(root.relativePositions);
                        // System.out.println(relativePositions.get(i).orient(orient));
                    }
                    if (count == 66) {
                        System.out.println("success");
                        index=i;
                        System.out.println("found index of last matching orientation pair " + index);
                        found = true;
                        lastMatch=relativePositions.get(i).orient(orient);
                        break;
                    }
                }
                if (found) break;
            }
            if (orient == 24) return false;

            this.o = orient; // set orientation. then reorient all beacons
            System.out.println("This scanner has orientation "+o);
            for (int i=0; i<beacons.size(); i++) {
                beacons.set(i, beacons.get(i).orient(o));
            }
            this.setRelativeDistances(); // reset all relative distances for new orientation.

            System.out.println(beacons.size());

            int pos = index/(beacons.size()-1);
            System.out.println("index of beacon in this scanner is "+pos);
            Vec3i bc = beacons.get(pos);
            System.out.println("Found beacon that matches: " + bc);

            System.out.println("Last relPos match is " + lastMatch);
            int index2 = root.beacons.indexOf(lastMatch);
            int pos2 = index2/(root.beacons.size()-1);
            System.out.println("index of beacon in root scanner is "+pos2);
            Vec3i bc2 = root.beacons.get(pos2);

            this.x=bc2.x-bc.x;
            this.y=bc2.y-bc.y;
            this.z=bc2.z-bc.z;

            for (int i=0; i<beacons.size(); i++) {
                beacons.set(i, new Vec3i(x+beacons.get(i).x, y+beacons.get(i).y, z+beacons.get(i).z));
            }
            return true;
        }

        public String toString() {
            return beacons.toString();
        }
    }

    static ArrayList<BeaconScanner> scanners = new ArrayList<>();
    static ArrayList<BeaconScanner> matched = new ArrayList<>();

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input19_1.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        BeaconScanner bs = new BeaconScanner();
        while ((line = reader.readLine()) != null) {
            if (line.contains("--")) {
                bs = new BeaconScanner();
            } else if (line.isEmpty()) {
                bs.setRelativeDistances();
                scanners.add(bs);
            } else {
                String[] coords = line.split(",");
                bs.add(new Vec3i(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]),Integer.parseInt(coords[2])));
            }
        }
        scanners.add(bs);
        reader.close();
    }

    public static void part1() {
        matched.add(scanners.remove(0));

        for (int i=0; i<matched.size(); i++) {
            for (int j=0; j<scanners.size(); j++) {
                if (scanners.get(j).match(matched.get(i))) {
                    matched.add(scanners.remove(j));
                    j--;
                }
            }
        }
        
        // System.out.println("Before");
        // System.out.println(matched.get(0));
        // System.out.println();
        // System.out.println(scanners.get(21));
        // scanners.get(21).match(matched.get(0));
        // System.out.println("After");
        // System.out.println(matched.get(0));
        // System.out.println();
        // System.out.println(scanners.get(21));

        // 0 and 22 match
        HashSet<Vec3i> beacons = new HashSet<>();
        for (BeaconScanner bs:matched) {
            for (Vec3i bc:bs.beacons) {
                beacons.add(bc);
            }
        }

        System.out.println(beacons.size());
        
        for (Vec3i vec3i : beacons) {
            System.out.println(vec3i);
        }
    }

    public static void main(String[] args) throws IOException {
        parse();
        part1();
    }
}
