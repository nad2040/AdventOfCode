package AdventOfCode.Y2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day9 {

    static int[][] caves = new int[100][100];
    static boolean[][] visited;

    public static void part1() {
        int sum = 0;

        for (int i=0; i<caves.length; i++) {
            for (int j=0; j<caves[0].length; j++) {
                boolean isLowest = true;
                if (i!=0 && caves[i][j] >= caves[i-1][j]) isLowest = false;
                if (i!=caves.length-1 && caves[i][j] >= caves[i+1][j]) isLowest = false;
                if (j!=0 && caves[i][j] >= caves[i][j-1]) isLowest = false;
                if (j!=caves[0].length-1 && caves[i][j] >= caves[i][j+1]) isLowest = false;

                if (isLowest) sum += caves[i][j] + 1;
            }
        }

        System.out.println(sum);
    }

    public static void part2() {
        ArrayList<Integer[]> coordsList = new ArrayList<>(); // find all low points
        for (int i=0; i<caves.length; i++) {
            for (int j=0; j<caves[0].length; j++) {
                boolean isLowest = true;
                if (i!=0 && caves[i][j] >= caves[i-1][j]) isLowest = false;
                if (i!=caves.length-1 && caves[i][j] >= caves[i+1][j]) isLowest = false;
                if (j!=0 && caves[i][j] >= caves[i][j-1]) isLowest = false;
                if (j!=caves[0].length-1 && caves[i][j] >= caves[i][j+1]) isLowest = false;

                if (isLowest) coordsList.add(new Integer[]{i,j});
            }
        }

        int numcoords = coordsList.size();
        int[] countsPerBasin = new int[numcoords];

        for (int i=0; i<numcoords; i++) {
            visited = new boolean[100][100];
            countsPerBasin[i] = basinFill(coordsList.get(i)[0], coordsList.get(i)[1]);
        }
        
        Arrays.sort(countsPerBasin);

        System.out.println(countsPerBasin[numcoords-1] * countsPerBasin[numcoords-2] * countsPerBasin[numcoords-3]);
    }

    public static int basinFill(int x, int y) {
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};

        int sum = 1;
        visited[x][y] = true;

        for (int i=0; i<4; i++) {
            int newx = x+dx[i];
            int newy = y+dy[i];
            if (newx >= 0 && newx < 100 && newy >= 0 && newy < 100 && !visited[newx][newy] && caves[newx][newy] < 9) sum += basinFill(newx,newy);
        }

        return sum;
    }
    
    public static void main(String[] args) throws IOException {
        File f = new File("./AdventOfCode/Y2021/input9.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        int linecount = 0;
        while ((line = reader.readLine()) != null) {
            for (int i=0; i<line.length(); i++) caves[linecount][i] = line.charAt(i) - '0';
            linecount++;
        }
        reader.close();

        part1();
        part2();
    }
}
