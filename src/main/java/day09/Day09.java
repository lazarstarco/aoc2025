package main.java.day09;

import java.util.List;
import java.util.Objects;

import main.java.helper.FileReader;

public class Day09 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day09.class);

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        List<Point> points = toPoints(lines);
        Rectangle largest = null;

        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point a = points.get(i);
                Point b = points.get(j);
                long area = Math.abs(a.x - b.x + 1) * java.lang.Math.abs(a.y - b.y + 1);

                if (largest == null || largest.area < area) {
                    largest = new Rectangle(a, b, area);
                }
            }
        }

        Objects.requireNonNull(largest);
        System.out.println(largest.area);
    }

    private static void part2(List<String> lines) {}

    private record Point(long x, long y) {}

    private record Rectangle(Point a, Point b, long area) {}

    private static List<Point> toPoints(List<String> lines) {
        return lines.stream()
                .map(l -> l.split(","))
                .map(l -> new Point(Long.parseLong(l[0]), Long.parseLong(l[1])))
                .toList();
    }

}
