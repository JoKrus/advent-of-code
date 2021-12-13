package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Day2021_13 extends Day {
    @Override
    public String part1Logic() {
        String[] split = input.split("\n\n");
        String coordinates = split[0];
        String foldInstr = split[1];

        HashSet<Point> points = new HashSet<>();

        Arrays.stream(coordinates.split("\n")).map(s -> {
            String[] split1 = s.split(",");
            return new Point(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]));
        }).forEach(points::add);

        var foldInstructions = Arrays.stream(foldInstr.split("\n")).map(s -> s.substring(11)).toList();

        handleInstruction(foldInstructions.get(0), points);

        return "%d".formatted(points.size());
    }

    private void handleInstruction(String s, HashSet<Point> points) {
        int foldCoordinate = Integer.parseInt(s.substring(2));
        switch (s.charAt(0)) {
            case 'x' -> {
                for (var entry :
                        points.stream().filter(point -> point.x > foldCoordinate).collect(Collectors.toSet())) {
                    int diff = entry.x - foldCoordinate;
                    points.remove(entry);
                    entry.x = entry.x - 2 * diff;
                    points.add(entry);
                }
            }
            case 'y' -> {
                for (var entry :
                        points.stream().filter(point -> point.y > foldCoordinate).collect(Collectors.toSet())) {
                    int diff = entry.y - foldCoordinate;
                    points.remove(entry);
                    entry.y = entry.y - 2 * diff;
                    points.add(entry);
                }
            }
        }
    }

    @Override
    public String part2Logic() {
        String[] split = input.split("\n\n");
        String coordinates = split[0];
        String foldInstr = split[1];

        HashSet<Point> points = new HashSet<>();

        Arrays.stream(coordinates.split("\n")).map(s -> {
            String[] split1 = s.split(",");
            return new Point(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]));
        }).forEach(points::add);

        var foldInstructions = Arrays.stream(foldInstr.split("\n")).map(s -> s.substring(11)).toList();

        for (var instruction :
                foldInstructions) {
            handleInstruction(instruction, points);
        }

        int maxX = 6 * 8;
        int maxY = 6;

        return renderPoints(points, maxX, maxY);
    }

    private String renderPoints(HashSet<Point> points, int maxX, int maxY) {
        StringBuilder sb = new StringBuilder("\n");
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                sb.append(points.contains(new Point(j, i)) ? "#" : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
