package main.java.day03;

import java.util.Arrays;
import java.util.List;

import main.java.helper.FileReader;

public class Day03 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day03.class);

        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        long result = 0L;

        for (String line : lines) {
            List<Integer> arr = getDigitsFromLine(line);

            int needed = 2;
            result += getNumberWithNeededLength(needed, arr);
        }

        System.out.println(result);
    }

    public static void part2(List<String> lines) {
        long result = 0L;

        for (String line : lines) {
            List<Integer> arr = getDigitsFromLine(line);

            int needed = 12;
            result += getNumberWithNeededLength(needed, arr);
        }

        System.out.println(result);
    }


    private static List<Integer> getDigitsFromLine(String line) {
        return Arrays.stream(line.split(""))
                .map(Integer::parseInt)
                .toList();
    }

    private static long getNumberWithNeededLength(int needed, List<Integer> arr) {
        int start = 0;
        StringBuilder sb = new StringBuilder();

        while (needed > 0) {
            int maxStart = arr.size() - needed;
            int bestDigit = -1;
            int bestIndex = -1;

            for (int i = start; i <= maxStart; i++) {
                if (arr.get(i) > bestDigit) {
                    bestDigit = arr.get(i);
                    bestIndex = i;
                }
            }

            sb.append(bestDigit);
            needed--;
            start = bestIndex + 1;
        }

        return Long.parseLong(sb.toString());
    }

}
