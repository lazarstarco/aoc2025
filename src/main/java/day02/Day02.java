package main.java.day02;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import main.java.helper.FileReader;

public class Day02 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day02.class);

        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        List<Range> ranges = splitToRanges(lines.getFirst());
        long result = 0L;

        for (Range range : ranges) {
            for (long i = Long.parseLong(range.begin); i <= Long.parseLong(range.end); i++) {
                result += checkForDuplicated(i) ? i : 0;
            }
        }

        System.out.println(result);
    }

    public static void part2(List<String> lines) {
        List<Range> ranges = splitToRanges(lines.getFirst());
        long result = 0L;

        for (Range range : ranges) {
            for (long i = Long.parseLong(range.begin); i <= Long.parseLong(range.end); i++) {
                result += checkForRepetition(i) ? i : 0;
            }
        }

        System.out.println(result);
    }

    private record Range(String begin, String end) {

        Range(String[] range) {
            this(range[0], range[1]);
        }
    }

    private static List<Range> splitToRanges(String line) {
        return Stream.of(line.split(",")).map(x -> x.split("-")).map(Range::new).toList();
    }

    private static boolean checkForDuplicated(long i) {
        String value = String.valueOf(i);
        if (value.length() % 2 != 0) {
            return false;
        }
        return (value.substring(0, value.length() / 2).equals(value.substring(value.length() / 2)));
    }

    private static boolean checkForRepetition(long i) {
        String value = String.valueOf(i);
        return Pattern.compile("^(.+?)\\1+$").matcher(value).find();
    }
}
