package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day4 {
    
    public static class Cell {
        int num;
        boolean marked;

        public Cell() {
            num = 0; marked = false;
        }
        public Cell(int num, boolean marked) {
            this.num = num; this.marked = marked;
        }

        public String toString() {
            return "[" + this.num + ", " + marked + "]";
        }
    }
    
    protected static Cell[][][] boards;
    protected static int[] nums;
    protected static boolean[] winning;

    public static void fillBoards() throws IOException {
        File f = new File("./AdventOfCode/Y2021/input4.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;

        line = reader.readLine();
        nums = new int[100];
        int i=0;
        for (String n : line.split(",")) {
            nums[i++] = Integer.parseInt(n);
        }
        boards = new Cell[100][5][5];
        winning = new boolean[100];

        for (int boardnum=0; boardnum<100; boardnum++) {
            line = reader.readLine();
            for (int j=0; j<5 && (line=reader.readLine()) != null; j++) {
                String[] row = line.trim().split("\\s+");
                for (int k=0; k<5; k++) {
                    boards[boardnum][j][k] = new Cell(Integer.parseInt(row[k]),false);
                }
            }
        }
        reader.close();
    }

    public static void part1() {
        for (int index=0; index<100; index++) {
            for (int i=0; i<100; i++) {
                for (int j=0; j<5; j++) {
                    for (int k=0; k<5; k++) {
                        if (boards[i][j][k].num == nums[index]) boards[i][j][k].marked = true;
                    }
                }
            }
            if (index >= 4) {
                for (int i=0; i<100; i++) {
                    if (checkBoard(boards[i])) {
                        int sum = 0;
                        for (int r=0; r<5; r++) {
                            for (int c=0; c<5; c++) {
                                if (!boards[i][r][c].marked) sum += boards[i][r][c].num;
                            }
                        }
                        System.out.println(sum * nums[index]);
                        return;
                    }
                }
            }
        }
    }

    public static boolean checkColumn(Cell[][] board, int c) {
        for (int i=0; i<5; i++) {
            if (!board[i][c].marked) return false;
        }
        return true;
    }

    public static boolean checkRow(Cell[][] board, int r) {
        for (int i=0; i<5; i++) {
            if (!board[r][i].marked) return false;
        }
        return true;
    }

    public static boolean checkBoard(Cell[][] board) {
        for (int i=0; i<5; i++) if (checkColumn(board,i) || checkRow(board,i)) return true;
        return false;
    }

    public static void displayBoard(Cell[][] board) {
        for (int i=0; i<5; i++) {
            System.out.print("[");
            for (int j=0; j<5; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println("]");
        }
    }

    public static void part2() {
        int countWinning = 0;
        int lastToWin = -1;
       
        for (int index=0; index<100; index++) { // loop through bingo numbers
            for (int i=0; i<100; i++) { // mark on each board the number
                for (int j=0; j<5; j++) {
                    for (int k=0; k<5; k++) {
                        if (boards[i][j][k].num == nums[index]) boards[i][j][k].marked = true;
                    }
                }
            }

            if (index >= 4) { // after marking check. begin checking after 5 bingo numbers because it is impossible to do so before hand
                for (int i=0; i<100; i++) { // loop through every board
                    if (!winning[i] && checkBoard(boards[i])) { // if not already winning and is winning this round, mark it winning and increase winning count.
                        winning[i] = true;
                        countWinning++;
                        if (countWinning == 99) {
                            for (int j=0; j<100; j++) if (!winning[j]) lastToWin=j;
                        }
                    }
                    if (countWinning == 100) {
                        int sum = 0;
                        for (int r=0; r<5; r++) {
                            for (int c=0; c<5; c++) {
                                if (!boards[lastToWin][r][c].marked) sum += boards[lastToWin][r][c].num;
                            }
                        }
                        System.out.println(sum * nums[index]);
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        fillBoards();
        part1();
        fillBoards();
        part2();
    }
}
