package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

public class Day2021_17 extends Day {
    HashSet<Point> validTrajectory = new HashSet<>();
    HashMap<Point, Integer> highestPoint = new HashMap<>();

    @Override
    public String part1Logic() {
        if (validTrajectory.isEmpty()) {
            performIteration(-350, 350);
        }
        return "%d" .formatted(highestPoint.entrySet().stream().filter(pointIntegerEntry -> validTrajectory.contains(pointIntegerEntry.getKey()))
                .map(Map.Entry::getValue).mapToInt(Integer::intValue).max().getAsInt());
    }

    private void performIteration(int minCheck, int maxCheck) {
        String[] coords = input.split("target area: ")[1].split(", ");
        int minX = Integer.parseInt(coords[0].split("=")[1].split(Pattern.quote(".."))[0]);
        int minY = Integer.parseInt(coords[1].split("=")[1].split(Pattern.quote(".."))[0]);
        int maxX = Integer.parseInt(coords[0].split(Pattern.quote(".."))[1]);
        int maxY = Integer.parseInt(coords[1].split(Pattern.quote(".."))[1]);

        HashSet<Point> validTrajectory = new HashSet<>();
        HashMap<Point, Integer> highestPoint = new HashMap<>();

        for (int initXVel = minCheck; initXVel <= maxCheck; initXVel++) {
            for (int initYVel = minY; initYVel <= maxCheck; initYVel++) {
                Point velPair = new Point(initXVel, initYVel);
                var init = Triple.of(new Point(0, 0), initXVel, initYVel);
                while (!(init.getLeft().y < minY && init.getRight() < 0)) {
                    init = iterate(init);
                    highestPoint.put(velPair, Math.max(init.getLeft().y,
                            highestPoint.getOrDefault(velPair, 0)));
                    if (init.getLeft().x >= minX && init.getLeft().x <= maxX
                            && init.getLeft().y >= minY && init.getLeft().y <= maxY) {
                        validTrajectory.add(velPair);
                        break;
                    }
                }
            }
        }

        this.validTrajectory = validTrajectory;
        this.highestPoint = highestPoint;
    }

    private Triple<Point, Integer, Integer> iterate(Triple<Point, Integer, Integer> triple) {
        return iterate(triple.getLeft(), triple.getMiddle(), triple.getRight());
    }

    private Triple<Point, Integer, Integer> iterate(Point init, int xVel, int yVel) {
        int newX = init.x + xVel;
        int newY = init.y + yVel;
        int newXVel = xVel - Integer.signum(xVel);
        int newYVel = yVel - 1;
        return Triple.of(new Point(newX, newY), newXVel, newYVel);
    }

    @Override
    public String part2Logic() {
        if (validTrajectory.isEmpty()) {
            performIteration(-92, 400);
        }
        return "%d" .formatted(validTrajectory.size());
    }
}
