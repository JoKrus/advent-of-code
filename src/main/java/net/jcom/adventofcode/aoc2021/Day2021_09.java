package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day2021_09 extends Day {
    @Override
    public String part1Logic() {
        String[] split = input.split("\n");
        int[][] heights = new int[split.length][];
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] split1 = s.split("");
            heights[i] = new int[split1.length];
            for (int j = 0; j < split1.length; j++) {
                String s1 = split1[j];
                heights[i][j] = Integer.parseInt(s1);
            }
        }

        int riskLevel = 0;

        for (int y = 0; y < heights.length; y++) {
            for (int x = 0; x < heights[y].length; x++) {
                int myVal = heights[y][x];
                int neigh1 = getFieldAt(y - 1, x, heights);
                int neigh2 = getFieldAt(y, x - 1, heights);
                int neigh3 = getFieldAt(y + 1, x, heights);
                int neigh4 = getFieldAt(y, x + 1, heights);

                if (neigh1 > myVal && neigh2 > myVal && neigh3 > myVal && neigh4 > myVal) {
                    riskLevel += myVal + 1;
                }
            }
        }
        return "%d".formatted(riskLevel);
    }

    private int getFieldAt(int nY, int nX, int[][] heights) {
        int neigh1 = Integer.MAX_VALUE;
        try {
            neigh1 = heights[nY][nX];
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
        return neigh1;
    }

    @Override
    public String part2Logic() {
        String[] split = input.split("\n");
        int[][] heights = new int[split.length][];
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] split1 = s.split("");
            heights[i] = new int[split1.length];
            for (int j = 0; j < split1.length; j++) {
                String s1 = split1[j];
                heights[i][j] = Integer.parseInt(s1);
            }
        }

        HashMap<Point, Integer> basinSize = new HashMap<>();

        for (int y = 0; y < heights.length; y++) {
            for (int x = 0; x < heights[y].length; x++) {
                int myVal = heights[y][x];
                int neigh1 = getFieldAt(y - 1, x, heights);
                int neigh2 = getFieldAt(y, x - 1, heights);
                int neigh3 = getFieldAt(y + 1, x, heights);
                int neigh4 = getFieldAt(y, x + 1, heights);

                if (neigh1 > myVal && neigh2 > myVal && neigh3 > myVal && neigh4 > myVal) {
                    basinSize.put(new Point(x, y), generateBasinSize(x, y, heights));
                }
            }
        }
        return "%d".formatted(basinSize.values().stream()
                .sorted(Comparator.reverseOrder())
                .limit(3).reduce(1, (a, b) -> a * b));
    }

    private int generateBasinSize(int x, int y, int[][] heights) {
        Set<Point> points = new HashSet<>();

        addBasinNeighbors(x, y, heights, points);

        return points.size();
    }

    private void addBasinNeighbors(int x, int y, int[][] heights, Set<Point> points) {
        int myVal = -145;
        try {
            myVal = heights[y][x];
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
        if (myVal == -145) {
            return;
        }
        if (points.contains(new Point(x, y))) {
            return;
        }

        points.add(new Point(x, y));

        int neigh1 = getFieldAt(y - 1, x, heights);
        if (neigh1 < 9 && neigh1 >= myVal) {
            addBasinNeighbors(x, y - 1, heights, points);
        }

        int neigh2 = getFieldAt(y, x - 1, heights);
        if (neigh2 < 9 && neigh2 >= myVal) {
            addBasinNeighbors(x - 1, y, heights, points);
        }

        int neigh3 = getFieldAt(y + 1, x, heights);
        if (neigh3 < 9 && neigh3 >= myVal) {
            addBasinNeighbors(x, y + 1, heights, points);
        }

        int neigh4 = getFieldAt(y, x + 1, heights);
        if (neigh4 < 9 && neigh4 >= myVal) {
            addBasinNeighbors(x + 1, y, heights, points);
        }
    }
}
