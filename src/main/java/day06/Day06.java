package main.java.day06;

import java.util.ArrayList;
import java.util.List;

import main.java.helper.FileReader;

public class Day06 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day06.class);

        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        List<List<String>> equations = toEquations(lines);
        List<Long> results = new ArrayList<>();

        for (List<String> equation : equations) {
            int sign = equation.getLast().equals("+") ? 0 : 1;
            long result = sign;

            equation.removeLast();
            for (String value : equation) {
                long longValue = Long.parseLong(value);
                result = sign == 0 ? result + longValue : result * longValue;
            }

            results.add(result);
        }

        System.out.println(results.stream().mapToLong(Long::longValue).sum());
    }

    public static void part2(List<String> lines) {
        char[][] matrix = lines.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        List<List<String>> groups = getGroups(matrix);

        List<Long> results = new ArrayList<>();

        for (List<String> group : groups) {
            String lastValue = group.getLast();
            int lastIndex = lastValue.length() - 1;
            int sign = lastValue.charAt(lastIndex) == '+' ? 0 : 1;
            long result = sign;

            group.set(group.size() - 1, lastValue.substring(0, lastIndex));
            for (String value : group) {
                long longValue = Long.parseLong(value.trim());
                result = sign == 0 ? result + longValue : result * longValue;
            }
            results.add(result);
        }

        System.out.println(results.stream().mapToLong(Long::longValue).sum());

    }

    private static List<List<String>> getGroups(char[][] matrix) {
        List<String> hardRead = new ArrayList<>();

        for (int col = matrix[0].length - 1; col >= 0; col--) {
            StringBuilder currentCol = new StringBuilder();
            for (char[] chars : matrix) {
                char current = chars[col];
                currentCol.append(current);
            }
            hardRead.add(currentCol.toString());
        }

        List<List<String>> groups = new ArrayList<>();
        List<String> current = new ArrayList<>();

        for (String line : hardRead) {
            if (line.isBlank()) {
                groups.add(current);
                current = new ArrayList<>();

            } else {
                current.add(line);
            }
        }
        groups.add(current);
        return groups;
    }

    private static List<List<String>> toEquations(List<String> lines) {
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < lines.getFirst().trim().split("\\s+").length; i++) {
            lists.add(new ArrayList<>());
        }

        for (String line : lines) {
            String[] temp = line.trim().split("\\s+");
            int i = 0;
            for (String t : temp) {
                lists.get(i++).add(t);
            }
        }

        return lists;
    }

}

