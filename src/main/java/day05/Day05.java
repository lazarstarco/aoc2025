package main.java.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import main.java.helper.FileReader;

public class Day05 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day05.class);

        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        List<long[]> ranges = lines.stream()
                .takeWhile(x -> !x.isEmpty())
                .map(line -> Arrays.stream(line.split("-"))
                        .mapToLong(Long::parseLong)
                        .toArray())
                .toList();

        List<Long> ingredients = lines.stream()
                .dropWhile(x -> !x.isEmpty())
                .skip(1)
                .map(Long::parseLong)
                .toList();

        int result = 0;

        for (Long ingredient : ingredients) {
            List<long[]> subRanges = ranges.stream()
                    .filter(x -> x[0] <= ingredient)
                    .filter(x -> x[1] >= ingredient)
                    .toList();

            if (!subRanges.isEmpty()) {
                result++;
            }
        }

        System.out.println(result);
    }

    public static void part2(List<String> lines) {
        List<long[]> sortedPairs = lines.stream()
                .takeWhile(x -> !x.isEmpty())
                .map(line -> line.split("-"))
                .map(pair -> new long[] { Long.parseLong(pair[0]), Long.parseLong(pair[1]) })
                .sorted(Comparator.comparingLong(range -> range[0]))
                .toList();

        ArrayList<long[]> merged = new ArrayList<>();
        for (long[] range : sortedPairs) {
            merge(merged, range);
        }

        long result = 0;
        for (long[] range : merged) {
            result += range[1] - range[0] + 1;
        }

        System.out.println(result);
    }

    private static void merge(ArrayList<long[]> accumulator, long[] range) {
        if (accumulator.isEmpty()) {
            accumulator.add(range);
            return;
        }

        long[] last = accumulator.getLast();
        if (range[0] <= last[1] + 1) {
            accumulator.set(accumulator.size() - 1, new long[] { last[0], Math.max(last[1], range[1]) });
        } else {
            accumulator.add(range);
        }
    }
}

