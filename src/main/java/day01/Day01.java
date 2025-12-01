package main.java.day01;

import java.util.List;

import main.java.helper.FileReader;

public class Day01 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day01.class);

        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        int current = 50;
        int result = 0;

        for (String line : lines) {
            String direction = line.substring(0, 1);
            int count = Integer.parseInt(line.substring(1));

            if (direction.equals("L")) {
                count *= -1;
            }

            int value = current + count;
            value = (value % 100 + 100) % 100;

            current = value;
            if (current == 0) {
                result++;
            }
        }

        System.out.println(result);
    }

    public static void part2(List<String> lines) {
        int current = 50;
        int result = 0;

        for (String line : lines) {
            String direction = line.substring(0, 1);
            int count = Integer.parseInt(line.substring(1));

            int step = direction.equals("L") ? -1 : 1;

            for (int i = 0; i < Math.abs(count); i++) {
                current += step;

                if (current < 0) {
                    current = 99;
                } else if (current >= 100) {
                    current = 0;
                }

                if (current == 0) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }
}
