package main.java.day10;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Pattern;

import main.java.helper.FileReader;

public class Day10 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day10.class);

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        List<Machine> machines = parseMachines(lines);
        int result = 0;

        for (Machine machine : machines) {
            result += minButtonPresses(machine);
        }

        System.out.println(result);
    }

    private static void part2(List<String> lines) {}

    private record Machine(String lights, List<List<Integer>> buttons, List<Integer> joltage) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Machine(
                    String lights1, List<List<Integer>> buttons1, List<Integer> joltage1
            ))) {
                return false;
            }
            return Objects.equals(lights, lights1) && Objects.equals(buttons, buttons1) && Objects.equals(joltage,
                    joltage1);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lights, buttons, joltage);
        }

        @Override
        public String toString() {
            return "Machine{" + "lights=" + lights + ", buttons=" + buttons + ", joltage=" + joltage + '}';
        }
    }

    private static List<Machine> parseMachines(List<String> lines) {
        List<Machine> machines = new ArrayList<>();

        for (String line : lines) {
            String lights = line.substring(line.indexOf("[") + 1, line.indexOf("]"));

            List<List<Integer>> buttons = Pattern.compile("\\(([^)]*)\\)").matcher(
                    line.substring(line.indexOf("]") + 1, line.indexOf("{")).trim()).results().map(m -> {
                String content = m.group(1).trim();
                if (content.isEmpty()) {
                    return List.<Integer>of();
                }
                return Arrays.stream(content.split(",")).map(String::trim).map(Integer::parseInt).toList();
            }).toList();

            List<Integer> joltage = Arrays.stream(
                    line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(",")).map(String::trim).map(
                    Integer::parseInt).toList();

            machines.add(new Machine(lights, buttons, joltage));
        }

        return machines;
    }

    private static int minButtonPresses(Machine machine) {
        String targetState = machine.lights();
        int n = targetState.length();

        String startState = ".".repeat(n);
        if (startState.equals(targetState)) {
            return 0;
        }

        List<List<Integer>> buttons = machine.buttons();

        Queue<String> queue = new ArrayDeque<>();
        Queue<Integer> steps = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.add(startState);
        steps.add(0);
        visited.add(startState);

        while (!queue.isEmpty()) {
            String state = queue.poll();

            int step = Objects.requireNonNull(steps.poll());

            for (List<Integer> button : buttons) {
                String nextState = toggle(state, button);

                if (!visited.add(nextState)) {
                    continue;
                }
                if (nextState.equals(targetState)) {
                    return step + 1;
                }

                queue.add(nextState);
                steps.add(step + 1);
            }
        }

        return 0;
    }

    private static String toggle(String state, List<Integer> indices) {
        char[] chars = state.toCharArray();
        for (int idx : indices) {
            chars[idx] = (chars[idx] == '.') ? '#' : '.';
        }
        return new String(chars);
    }
}
