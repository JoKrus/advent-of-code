package de.djgames.jonas.adventofcode.aoc2021;

import de.djgames.jonas.adventofcode.Day;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day2021_05 extends Day {
    @Override
    public String part1Logic() {
        List<Vent> straightVents = Arrays.stream(input.split("\n")).map(s -> {
            var coordSplit = Arrays.stream(s.split("->")).map(String::trim)
                    .map(s1 -> Arrays.stream(s1.split(",")).mapToInt(Integer::parseInt).toArray())
                    .map(ints -> new Point(ints[0], ints[1])).collect(Collectors.toList());
            return new Vent(coordSplit.get(0), coordSplit.get(1));
        }).filter(Vent::isStraight).collect(Collectors.toList());

        Map<Point, Long> collect = straightVents.stream().flatMap(vent -> vent.getPointsOfPipes().stream()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long count = collect.entrySet().stream().filter(pointLongEntry -> pointLongEntry.getValue() > 1).count();

        return String.format("%d", count);
    }

    @Override
    public String part2Logic() {
        List<Vent> straightVents = Arrays.stream(input.split("\n")).map(s -> {
            var coordSplit = Arrays.stream(s.split("->")).map(String::trim)
                    .map(s1 -> Arrays.stream(s1.split(",")).mapToInt(Integer::parseInt).toArray())
                    .map(ints -> new Point(ints[0], ints[1])).collect(Collectors.toList());
            return new Vent(coordSplit.get(0), coordSplit.get(1));
        }).collect(Collectors.toList());

        Map<Point, Long> collect = straightVents.stream().flatMap(vent -> vent.getPointsOfPipes().stream()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long count = collect.entrySet().stream().filter(pointLongEntry -> pointLongEntry.getValue() > 1).count();

        return String.format("%d", count);
    }

    public static class Vent {
        private Point from;
        private Point to;

        public Vent(Point from, Point to) {
            this.from = from;
            this.to = to;
        }

        public boolean isStraight() {
            return from.x == to.x || from.y == to.y;
        }

        public HashSet<Point> getPointsOfPipes() {
            HashSet<Point> ret = new HashSet<>();

            int distX = to.x - from.x;
            int distY = to.y - from.y;

            int dx = Integer.signum(distX);
            int dy = Integer.signum(distY);

            int amounts = (Math.max(Math.abs(distX), Math.abs(distY))) + 1;

            for (int i = 0; i < amounts; i++) {
                int px = from.x + i * dx;
                int py = from.y + i * dy;
                var point = new Point(px, py);
                ret.add(point);
            }

            return ret;
        }
    }
}
