package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day21 {
    static int p1pos, p2pos; // store as mod 10 for easy math. remember to add 1
    static long p1wins=0,p2wins=0;
    static int[] sums = new int[10];

    public static void parse() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input21.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;

        line = reader.readLine();
        p1pos = line.charAt(line.length()-1) - '0' - 1;
        line = reader.readLine();
        p2pos = line.charAt(line.length()-1) - '0' - 1;

        reader.close();
    }
    
    public static void part1() {
        int p1score=0,p2score=0;
        for (int dice=0; dice<10000; dice++) { // total num of dice rolls
            int roll = dice%100;
            if (dice / 3 % 2 == 0) { // p1 turn
                p1pos += roll+1;
                p1pos %= 10;
            } else { // p2 turn
                p2pos += roll+1;
                p2pos %= 10;
            }

            if (dice % 3 == 2) {
                if (dice / 3 % 2 == 0) { // add to p1score
                    p1score += p1pos+1;
                } else { // add to p2score
                    p2score += p2pos+1;
                }
            }

            if (p1score >= 1000) { // p1 wins
                System.out.println("p1 wins and value is " + (p2score * (dice+1)));
                break;
            } else if (p2score >= 1000) {
                System.out.println("p2 wins and value is " + (p1score * (dice+1)));
                break;
            }
        }
    }

    public static void part2() {
        for (int i=1; i<=3; i++) // set # of possibilities (universes) for each sum of 3 consecutive rolls a.k.a. a single turn. There is a total of 27 universes for 3 rolls.
            for (int j=1; j<=3; j++)
                for (int k=1; k<=3; k++) {
                    sums[i+j+k]++;
                }
        
        recur(true,p1pos,p2pos,0,0,1);
        if (p1wins > p2wins) System.out.println("p1 wins in " + p1wins + " universes");
        else System.out.println("p2 wins in " + p2wins + " universes");
    }

    public static void recur(boolean player1, int p1, int p2, int p1score, int p2score, long numUniverses) {
        if (p1score >= 21) { p1wins += numUniverses; return; }
        else if (p2score >= 21) { p2wins += numUniverses; return; }

        for (int turn=3; turn<=9; turn++) { // loop through values of 3 consecutive rolls. sums[turn] gives num universes generated for that sum.
            if (player1) recur(!player1, (p1+turn)%10, p2, p1score + (p1+turn)%10 + 1, p2score, numUniverses*sums[turn]);
            else recur(!player1, p1, (p2+turn)%10 , p1score, p2score + (p2+turn)%10 + 1, numUniverses*sums[turn]);
        }
    }

    public static void main(String[] args) throws IOException {
        parse();
        part1();
        parse();
        part2();
    }
}
