package main.java.day07;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import main.java.helper.FileReader;

public class Day07 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day07.class);

        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        int result = 0;
        Set<Integer> beams = new HashSet<>();

        beams.add(lines.getFirst().indexOf('S'));

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);

            Iterator<Integer> iterator = beams.iterator();
            Set<Integer> toAdd = new HashSet<>();

            while (iterator.hasNext()) {
                int beam = iterator.next();
                if (line.charAt(beam) == '^') {
                    toAdd.add(beam - 1);
                    toAdd.add(beam + 1);
                    iterator.remove();
                    result++;
                }
            }

            beams.addAll(toAdd);
        }

        System.out.println(result);
    }

    private static long[][] memoization;

    public static void part2(List<String> lines) {
        int rows = lines.size();
        int cols = lines.getFirst().length();

        memoization = new long[rows][cols];
        for (long[] row : memoization) {
            Arrays.fill(row, -1);
        }

        int startCol = lines.getFirst().indexOf("S");

        long result = dfs(0, startCol, lines);
        System.out.println(result);
    }


    private static long dfs(int row, int column, List<String> lines) {
        int rows = lines.size();

        if (row == rows) {
            return 1;
        }


        if (memoization[row][column] != -1) {
            return memoization[row][column];
        }

        char tile = lines.get(row).charAt(column);
        long result;

        if (tile == '^') {
            result = dfs(row + 1, column - 1, lines)
                     + dfs(row + 1, column + 1, lines);
        } else {
            result = dfs(row + 1, column, lines);
        }

        memoization[row][column] = result;

        return result;
    }
}
