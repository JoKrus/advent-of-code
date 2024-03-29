package net.jcom.adventofcode.aoc2019;

import net.jcom.adventofcode.Day;

import java.util.HashMap;
import java.util.Objects;

public class Day2019_03 extends Day {
    @Override
    public String part1Logic() {
        return run(true);
    }

    @Override
    public String part2Logic() {
        return run(false);
    }

    public String run(boolean part1) {
        String[] paths = input.split("\n");
        HashMap<Pos, Pos> nextLevelPositionSet = new HashMap<>();
        HashMap<Pos, Integer> intersections = new HashMap<>();
        int pathID = 0;
        for (String path : paths) {
            pathID++;
            int actx = 0, acty = 0, dist = 0;
            int difx, dify;
            for (String instruct : path.split(",")) {
                switch (instruct.substring(0, 1)) {
                    case "R":
                        difx = 1;
                        dify = 0;
                        break;
                    case "L":
                        difx = -1;
                        dify = 0;
                        break;
                    case "U":
                        difx = 0;
                        dify = 1;
                        break;
                    case "D":
                        difx = 0;
                        dify = -1;
                        break;
                    default:
                        throw new RuntimeException("NA Parser");
                }
                for (int i = 0; i < Integer.parseInt(instruct.substring(1)); ++i) {
                    actx += difx;
                    acty += dify;
                    dist += Math.abs(difx) + Math.abs(dify);
                    Pos pos = new Pos(actx, acty, pathID, dist);
                    Pos oldPos = nextLevelPositionSet.getOrDefault(pos, new Pos(actx, acty, -100, dist));
                    if (pos.id != oldPos.id && oldPos.id != -100) {
                        int value = part1 ? Math.abs(actx) + Math.abs(acty) : dist + oldPos.dist;
                        intersections.putIfAbsent(pos, value);
                    } else if (oldPos.id == -100) {
                        nextLevelPositionSet.put(pos, pos);
                    }
                }
            }
        }

        return String.format("%d", intersections.values().stream().mapToInt(Integer::intValue).min().orElse(-1));
    }

    public static class Pos {
        private final int dist, x, y, id;

        public Pos(int x, int y) {
            this(x, y, 0, 0);
        }

        public Pos(int x, int y, int id) {
            this(x, y, id, 0);
        }

        public Pos(int x, int y, int id, int dist) {
            this.x = x;
            this.y = y;
            this.id = id;
            this.dist = dist;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return x == pos.x &&
                    y == pos.y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getId() {
            return id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
