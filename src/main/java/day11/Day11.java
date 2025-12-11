package main.java.day11;

import java.util.ArrayList;
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

        System.out.println(
                countPaths("you", paths, reachabilityMap, new HashSet<>())
        );
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

    private static int countPaths(String start,
            String end,
            Map<String, List<String>> map,
            Set<String> visited,
            Map<String, Set<String>> reachabilityMap,
            List<String> currentPath,
            Set<String> currentNodes,
            Set<String> requirements) {

        visited.add(start);
        currentPath.add(start);
        currentNodes.add(start);

        int total = 0;

        if (start.equals(end)) {
            if (requirements.isEmpty() || currentNodes.containsAll(requirements)) {
                total = 1;
            }

            currentPath.removeLast();
            currentNodes.remove(start);
            visited.remove(start);
            return total;
        }

        Set<String> reachableFromCurrent = reachabilityMap.getOrDefault(start, Set.of());
        if (!reachableFromCurrent.contains(end)) {
            currentPath.removeLast();
            currentNodes.remove(start);
            visited.remove(start);
            return 0;
        }

        if (!requirements.isEmpty()) {
            for (String req : requirements) {
                if (!currentNodes.contains(req) && !reachableFromCurrent.contains(req)) {
                    currentPath.removeLast();
                    currentNodes.remove(start);
                    visited.remove(start);
                    return 0;
                }
            }
        }

        for (String next : map.getOrDefault(start, List.of())) {
            if (!visited.contains(next)) {
                total += countPaths(next, end, map, visited, reachabilityMap,
                        currentPath, currentNodes, requirements);
            }
        }

        currentPath.removeLast();
        currentNodes.remove(start);
        visited.remove(start);

        return total;
    }

    private static int countPaths(String start, Map<String, List<String>> map,
            Map<String, Set<String>> reachabilityMap, Set<String> requirements) {
        return countPaths(start,
                "out",
                map,
                new HashSet<>(),
                reachabilityMap,
                new ArrayList<>(),
                new HashSet<>(),
                requirements);

    }
}
