package main.java.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.helper.FileReader;

public class Day11 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day11.class);

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        Map<String, List<String>> paths = parsePaths(lines);
        Map<String, Set<String>> reachabilityMap = getReachabilityMap(paths);

        System.out.println(countPaths("you", "out", paths, new HashSet<>(), reachabilityMap));
    }

    private static void part2(List<String> lines) {}

    private static Map<String, List<String>> parsePaths(List<String> lines) {
        Map<String, List<String>> paths = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(":");
            String key = split[0].trim();
            List<String> values = Arrays.asList(split[1].trim().split(" "));
            paths.put(key, values);
        }

        return paths;
    }

    private static Map<String, Set<String>> getReachabilityMap(Map<String, List<String>> map) {
        Map<String, Set<String>> reachable = new HashMap<>();

        for (String node : map.keySet()) {
            Set<String> visited = new HashSet<>();
            dfsReach(node, map, visited);
            reachable.put(node, visited);
        }
        return reachable;
    }

    private static void dfsReach(String key, Map<String, List<String>> map, Set<String> visited) {
        for (String value : map.getOrDefault(key, List.of())) {
            if (!visited.contains(value)) {
                visited.add(value);
                dfsReach(value, map, visited);
            }
        }
    }

    private static int countPaths(String start, String end, Map<String, List<String>> map, Set<String> visited,
            Map<String, Set<String>> reachabilityMap) {
        if (!reachabilityMap.getOrDefault(start, Set.of()).contains(end)
            && !start.equals(end)) {
            return 0;
        }

        if (start.equals(end)) {
            return 1;
        }

        visited.add(start);
        int total = 0;

        for (String target : map.getOrDefault(start, List.of())) {
            if (!visited.contains(target)) {
                total += countPaths(target, end, map, visited, reachabilityMap);
            }
        }

        visited.remove(start);
        return total;
    }
}