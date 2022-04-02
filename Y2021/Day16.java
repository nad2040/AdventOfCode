package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day16 {
    static class Packet {
        static final int SUM = 0, PRODUCT = 1, MIN = 2, MAX = 3, LITERAL = 4, GREATER = 5, LESS = 6, EQUAL = 7;
        
        int version;
        int type;
        long value;
        ArrayList<Packet> subPackets;

        public Packet(int version, int type, long value) {
            this.version = version;
            this.type = type;
            if (type == LITERAL) {
                this.value = value;
            } else {
                this.value = 0;
            }
            subPackets = new ArrayList<>();
        }

        public void add(Packet subp) {
            subPackets.add(subp);
        }

        int getVersionNumberSum() {
            if (type == LITERAL) return version;
            int sum = version;
            for (Packet p: subPackets) sum+=p.getVersionNumberSum();
            return sum;
        }

        long getValue() {
            return switch (type) {
                case SUM -> {
                    long sum = 0;
                    for (Packet p: subPackets) sum += p.getValue();
                    yield sum;
                }
                case PRODUCT -> {
                    long product = 1;
                    for (Packet p: subPackets) product *= p.getValue();
                    yield product;
                }
                case MIN -> {
                    long min = Long.MAX_VALUE;
                    for (Packet p: subPackets) {
                        long val = p.getValue();
                        if (val < min) min = val;
                    }
                    yield min;
                }
                case MAX -> {
                    long max = Long.MIN_VALUE;
                    for (Packet p: subPackets) {
                        long val = p.getValue();
                        if (val > max) max = val;
                    }
                    yield max;
                }
                case LITERAL -> value;
                case GREATER -> (subPackets.get(0).getValue() > subPackets.get(1).getValue()) ? 1 : 0;
                case LESS -> (subPackets.get(0).getValue() < subPackets.get(1).getValue()) ? 1 : 0;
                case EQUAL -> (subPackets.get(0).getValue() == subPackets.get(1).getValue()) ? 1 : 0;
                default -> 0;
            };
        }

        public String toString() {
            String ty = switch (type) {
                case SUM -> "SUM";
                case PRODUCT -> "PRODUCT";
                case MIN -> "MIN";
                case MAX -> "MAX";
                case LITERAL -> "LITERAL";
                case GREATER -> "GREATER";
                case LESS -> "LESS";
                case EQUAL -> "EQUAL";
                default -> "";
            };
            String s = "[ version: "+version + ", "+ty + ", value: "+value + " \n";
            for (Packet p: subPackets) s += p.toString();
            s += "]";
            return s;
        }
    }

    static HashMap<Character, String> hexStringTranslator = new HashMap<>();
    static String fullPacket;
    static Packet parsedPacket;
    static int i;

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input16.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = reader.readLine();
        reader.close();

        toBinaryString(line);
    }

    public static void toBinaryString(String hexString) {
        fullPacket = "";
        for (int index=0; index<hexString.length(); index++) {
            fullPacket += hexStringTranslator.get(hexString.charAt(index));
        }
    }

    public static Packet readPacket() {
        // header
        int v = Integer.parseInt(fullPacket.substring(i, i+3),2);
        int t = Integer.parseInt(fullPacket.substring(i+3, i+6),2);
        i+=6;
        // content
        if (t==Packet.LITERAL) { // parse literal
            String literalValue = "";
            while (fullPacket.charAt(i) == '1') {
                literalValue += fullPacket.substring(i+1, i+5); i+=5;
            }
            literalValue += fullPacket.substring(i+1, i+5); i+=5;

            return new Packet(v, Packet.LITERAL, Long.parseLong(literalValue,2));

        } else { // parse operator
            Packet operator = new Packet(v, t, 0);
            char lengthTypeID = fullPacket.charAt(i++);
            String operatorHint;
            if (lengthTypeID == '0') { // total length in bits
                operatorHint = fullPacket.substring(i,i+15);
                i+=15;
                int targetPosition = i + Integer.parseInt(operatorHint,2);
                while (i < targetPosition) operator.add(readPacket());
                return operator;
            } else { // number of subpackets
                operatorHint = fullPacket.substring(i,i+11);
                i+=11;
                int countSubPackets = Integer.parseInt(operatorHint,2);
                for (int subp=0; subp<countSubPackets; subp++) operator.add(readPacket());
                return operator;
            }
        }
    }
    
    public static void part1() {
        System.out.println(parsedPacket.getVersionNumberSum());
    }

    public static void part2() {
        System.out.println(parsedPacket.getValue());
    }

    public static void main(String[] args) throws IOException {
        for (int nib=16; nib<32; nib++) { // do 16 - 31 to include leading zeros for binary version [1]0000 - [1]1111
            hexStringTranslator.put(Integer.toString(nib,16).toUpperCase().charAt(1), Integer.toString(nib,2).substring(1));
        }

        parse();

        i=0;
        parsedPacket = readPacket();
        System.out.println(parsedPacket);

        part1();
        part2();
    }
}
