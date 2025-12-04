package main.java.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.helper.FileReader;

public class Day04 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day04.class);

        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        char[][] matrix = parseMatrix(lines);
        int result = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (checkRoll(i, j, matrix)) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }

    public static void part2(List<String> lines) {
        char[][] matrix = parseMatrix(lines);
        int result = 0;
        List<int[]> toRemove;

        do {
            toRemove = new ArrayList<>();

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (checkRoll(i, j, matrix)) {
                        result++;
                        toRemove.add(new int[] { i, j });
                    }
                }
            }

            for (int[] p : toRemove) {
                matrix[p[0]][p[1]] = '.';
            }

        } while (!toRemove.isEmpty());

        System.out.println(result);
    }

    private static char[][] parseMatrix(List<String> lines) {
        return lines.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }


    private static boolean checkRoll(int n, int m, char[][] matrix) {
        if (matrix[n][m] != '@') {
            return false;
        }

        int[][] directions = {
                { -1, -1 }, { -1, 0 }, { -1, 1 },
                { 0, -1 }, { 0, 1 },
                { 1, -1 }, { 1, 0 }, { 1, 1 }
        };
        int count = 0;

        for (int[] direction : directions) {
            int di = n + direction[0];
            int dj = m + direction[1];

            if (di >= 0 && di < matrix.length && dj >= 0 && dj < matrix[di].length) {
                if (matrix[di][dj] == '@') {
                    count++;
                }
            }
        }

        return count < 4;
    }
}
