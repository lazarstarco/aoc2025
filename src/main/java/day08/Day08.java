package main.java.day08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import main.java.helper.FileReader;

public class Day08 {

    public static void main(String[] args) {
        List<String> lines = FileReader.readForClass(Day08.class);

        part1(lines);
        part2(lines);
    }

    private record JunctionBox(double a, double b, double c) {
    }

    private record Circuit(JunctionBox a, JunctionBox b, double distance) {
    }

    static class DSU {

        int[] parent;
        int[] size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int a, int b) {
            int elementA = find(a);
            int elementB = find(b);
            if (elementA == elementB) {
                return;
            }

            if (size[elementA] < size[elementB]) {
                int tmp = elementA;
                elementA = elementB;
                elementB = tmp;
            }
            parent[elementB] = elementA;
            size[elementA] += size[elementB];
        }
    }

    public static void part1(List<String> lines) {
        List<JunctionBox> boxes = parseBoxes(lines);
        int n = boxes.size();

        Map<JunctionBox, Integer> indexOf = getIndicesMap(n, boxes);

        List<Circuit> smallest = getCircuits(n, boxes);
        smallest.sort(Comparator.comparingDouble(c -> c.distance));

        DSU dsu = new DSU(n);

        for (Circuit circuit : smallest) {
            int circuitA = indexOf.get(circuit.a);
            int circuitB = indexOf.get(circuit.b);

            int dsuA = dsu.find(circuitA);
            int dsuB = dsu.find(circuitB);

            if (dsuA != dsuB) {
                dsu.union(dsuA, dsuB);
            }
        }

        Map<Integer, Integer> componentSizes = new java.util.HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = dsu.find(i);
            componentSizes.merge(root, 1, Integer::sum);
        }

        List<Integer> sizes = new ArrayList<>(componentSizes.values());
        sizes.sort(Collections.reverseOrder());

        System.out.println((long) sizes.get(0) * sizes.get(1) * sizes.get(2));
    }

    public static void part2(List<String> lines) {
        List<JunctionBox> boxes = parseBoxes(lines);
        int n = boxes.size();

        List<Circuit> circuits = getCircuitList(n, boxes);

        circuits.sort(Comparator.comparingDouble(c -> c.distance));

        Map<JunctionBox, Integer> indexOf = getIndicesMap(n, boxes);

        DSU dsu = new DSU(n);

        int components = n;
        Circuit lastUsed = null;

        for (Circuit circuit : circuits) {
            int circuitA = indexOf.get(circuit.a);
            int circuitB = indexOf.get(circuit.b);

            int dsuA = dsu.find(circuitA);
            int dsuB = dsu.find(circuitB);

            if (dsuA != dsuB) {
                dsu.union(dsuA, dsuB);
                components--;
                lastUsed = circuit;

                if (components == 1) {
                    break;
                }
            }
        }

        if (lastUsed == null) {
            return;
        }

        long x1 = (long) lastUsed.a.a;
        long x2 = (long) lastUsed.b.a;
        System.out.println(x1 * x2);
    }

    private static List<JunctionBox> parseBoxes(List<String> lines) {
        return lines.stream()
                .map(x -> x.split(","))
                .map(x -> new double[] { Double.parseDouble(x[0]), Double.parseDouble(x[1]), Double.parseDouble(x[2]) })
                .map(x -> new JunctionBox(x[0], x[1], x[2]))
                .toList();
    }

    private static Map<JunctionBox, Integer> getIndicesMap(int n, List<JunctionBox> boxes) {
        Map<JunctionBox, Integer> indexOf = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indexOf.put(boxes.get(i), i);
        }
        return indexOf;
    }

    private static double calculateDistance(JunctionBox boxA, JunctionBox boxB) {
        return Math.sqrt(
                Math.pow(boxA.a - boxB.a, 2) +
                Math.pow(boxA.b - boxB.b, 2) +
                Math.pow(boxA.c - boxB.c, 2)
        );
    }

    private static List<Circuit> getCircuits(int n, List<JunctionBox> boxes) {
        final int nClosestPairs = 1000;

        PriorityQueue<Circuit> heap = new PriorityQueue<>(
                nClosestPairs,
                Comparator.<Circuit>comparingDouble(c -> c.distance).reversed()
        );

        for (int i = 0; i < n - 1; i++) {
            JunctionBox boxA = boxes.get(i);
            for (int j = i + 1; j < n; j++) {
                JunctionBox boxB = boxes.get(j);

                double distance = calculateDistance(boxA, boxB);

                Circuit edge = new Circuit(boxA, boxB, distance);

                if (heap.size() < nClosestPairs) {
                    heap.add(edge);
                } else if (distance < heap.peek().distance) {
                    heap.poll();
                    heap.add(edge);
                }
            }
        }

        return new ArrayList<>(heap);
    }

    private static List<Circuit> getCircuitList(int n, List<JunctionBox> boxes) {
        List<Circuit> circuits = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            JunctionBox boxA = boxes.get(i);
            for (int j = i + 1; j < n; j++) {
                JunctionBox boxB = boxes.get(j);

                double distance = calculateDistance(boxA, boxB);
                circuits.add(new Circuit(boxA, boxB, distance));
            }
        }
        return circuits;
    }
}
