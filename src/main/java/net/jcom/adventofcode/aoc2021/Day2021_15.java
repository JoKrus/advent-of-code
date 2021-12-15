package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Queue;
import java.util.*;

public class Day2021_15 extends Day {
    @Override
    public String part1Logic() {
        int[][] riskLevel = parseInput(this.input);

        var risk = getRiskLevel(0, 0, riskLevel.length - 1, riskLevel.length - 1, riskLevel);

        return "%d".formatted(risk);
    }

    private int[][] parseInput(String input) {
        String[] split = input.split("\n");
        int[][] riskLevel = new int[split.length][];
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] split1 = s.split("");
            riskLevel[i] = new int[split1.length];
            for (int j = 0; j < split1.length; j++) {
                String s1 = split1[j];
                riskLevel[i][j] = Integer.parseInt(s1);
            }
        }
        return riskLevel;
    }

    private long getRiskLevel(int startX, int startY, int targetX, int targetY, int[][] riskLevels) {
        Point start = new Point(startX, startY);
        Point target = new Point(targetX, targetY);

        Map<Point, Pair<Point, Long>> shortestDist = new HashMap<>();
        Set<Point> alreadyChecked = new HashSet<>();
        Queue<Pair<Point, Long>> queue = new PriorityQueue<>(Comparator.comparingLong(Pair::getRight));
        queue.add(Pair.of(start, 0L));
        while (!queue.isEmpty()) {
            var pointPair = queue.poll();
            var point = pointPair.getLeft();
            alreadyChecked.add(point);

            // Have we reached the target? --> Build and return the path
            if (point.equals(target)) {
                //        return pointPair.getRight();
            }

            // Iterate over all neighbors
            Set<Point> neighbors = getNeighbors(point, riskLevels);
            for (var neighbor : neighbors) {
                // Ignore neighbor if shortest path already found
                if (alreadyChecked.contains(neighbor)) {
                    continue;
                }

                // Calculate total distance to neighbor via current node
                int distance = riskLevels[neighbor.y][neighbor.x];
                var totalDistance = pointPair.getRight() + distance;

                // Neighbor not yet discovered?
                var shortest = shortestDist.get(neighbor);
                if (shortest == null) {
                    shortest = Pair.of(neighbor, totalDistance);
                    shortestDist.put(neighbor, shortest);
                    queue.add(shortest);
                }

                // Neighbor discovered, but total distance via current node is shorter?
                // --> Update total distance and predecessor
                else if (totalDistance < shortest.getRight()) {
                    var newShortest = Pair.of(neighbor, totalDistance);
                    shortestDist.put(neighbor, newShortest);

                    // The position in the PriorityQueue won't change automatically;
                    // we have to remove and reinsert the node
                    queue.remove(shortest);
                    queue.add(newShortest);
                }
            }
        }
        return shortestDist.getOrDefault(target, Pair.of(target, -1L)).getRight();
    }

    private Set<Point> getNeighbors(Point point, int[][] riskLevels) {
        Set<Point> ret = new HashSet<>();

        Point left = new Point(point.x - 1, point.y);
        if (left.x >= 0) {
            ret.add(left);
        }
        Point right = new Point(point.x + 1, point.y);
        if (right.x < riskLevels[0].length) {
            ret.add(right);
        }
        Point up = new Point(point.x, point.y - 1);
        if (up.y >= 0) {
            ret.add(up);
        }
        Point bottom = new Point(point.x, point.y + 1);
        if (bottom.y < riskLevels.length) {
            ret.add(bottom);
        }
        return ret;
    }

    @Override
    public String part2Logic() {
        //create string 1\n2\n3\n4\n5, concat with 2-6, 3-7, 4-8,5-9
        String[] cols = new String[5];
        for (int i = 0; i < cols.length; i++) {
            cols[i] = incrementEveryone(input, i) + incrementEveryone(input, i + 1)
                    + incrementEveryone(input, i + 2) + incrementEveryone(input, i + 3)
                    + incrementEveryone(input, i + 4);
        }
        String superInput = cols[0];
        for (int i = 1; i < cols.length; i++) {
            superInput = concatLines(superInput, cols[i]);
        }

        int[][] riskLevel = parseInput(superInput);

        var risk = getRiskLevel(0, 0, riskLevel.length - 1, riskLevel.length - 1, riskLevel);

        return "%d".formatted(risk);
    }

    public String incrementEveryone(String incre, int increment) {
        String[] split = incre.split("\n");
        int[][] riskLevel = new int[split.length][];
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] split1 = s.split("");
            riskLevel[i] = new int[split1.length];
            for (int j = 0; j < split1.length; j++) {
                String s1 = split1[j];
                riskLevel[i][j] = Integer.parseInt(s1);
                for (int k = 0; k < increment; k++) {
                    riskLevel[i][j] = riskLevel[i][j] % 9 + 1;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < riskLevel.length; i++) {
            for (int j = 0; j < riskLevel[i].length; j++) {
                sb.append(riskLevel[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String concatLines(String original, String addRight) {
        String[] split = original.split("\n");
        String[] splitRight = addRight.split("\n");

        String[] concat = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            concat[i] = split[i] + splitRight[i];
        }
        return String.join("\n", concat);
    }
}
