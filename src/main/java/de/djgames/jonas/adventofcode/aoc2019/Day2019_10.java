package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;

public class Day2019_10 extends Day {
    @Override
    public String part1Logic() {
        return exec(true);
    }

    private String exec(boolean part1) {
        boolean[][] cometNet = generateCometNet();

        int maxSeen = 0;
        int maxX = 0, maxY = 0;
        TreeSet<Vector> maxCometVectors = null;

        for (int y = 0; y < cometNet[0].length; y++) {
            for (int x = 0; x < cometNet.length; x++) {
                if (cometNet[x][y]) {
                    TreeSet<Vector> cometVectors = cometVectors(x, y, cometNet);
                    int seen = cometVectors.size();
                    if (seen > maxSeen) {
                        maxSeen = seen;
                        maxX = x;
                        maxY = y;
                        maxCometVectors = cometVectors;
                    }
                }
            }
        }

        if (part1) return String.format("%d", maxSeen);

        cometNet[maxX][maxY] = false;

        int destroyed = 0;
        if (maxCometVectors == null || maxCometVectors.size() < 200) throw new RuntimeException("NA Aufgabe");
        int comet200X = 0;
        int comet200Y = 0;

        outer:
        while (true) {
            for (Vector v : maxCometVectors) {
                destroyed++;
                //sort by angle, dann ganze zeit nur array durchgehen immer in i * vector, wenn was findet zaehler + 1 und asteroid entfernen
                //cometNet durchgehen von maxX und maxY , i * vector bis was gefunden wurde oder rand

                if (destroyed == 200) break outer;
            }
        }


        return String.format("%d", 100 * comet200X + comet200Y);
    }


    @Override
    public String part2Logic() {
        return exec(false);
    }

    //first param x, second y
    private boolean[][] generateCometNet() {
        String[] lines = input.split("\n");
        boolean[][] cometNet = new boolean[lines[0].length()][lines.length];
        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < cometNet.length; x++) {
                String s = line.substring(x, x + 1);
                boolean isComet;
                if (s.equals("#")) {
                    isComet = true;
                } else if (s.equals(".")) {
                    isComet = false;
                } else {
                    throw new RuntimeException("NA Parser");
                }
                cometNet[x][y] = isComet;
            }
        }
        return cometNet;
    }

    private TreeSet<Vector> cometVectors(int fromX, int fromY, boolean[][] cometNet) {
        Vector base = Vector.of(0, 1);
        TreeSet<Vector> seen = new TreeSet<>(Comparator.comparingDouble(o -> o.getAngle(base)));

        for (int x = 0; x < cometNet.length; ++x) {
            for (int y = 0; y < cometNet[x].length; ++y) {
                if (cometNet[x][y] && !(fromX == x && fromY == y)) {
                    Vector vector = Vector.of(x - fromX, y - fromY);
                    if (vector != null) seen.add(vector);
                }
            }
        }

        return seen;
    }

    public static class Vector {
        private int x, y;

        private Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double getAngle(Vector base) {
            double dot = this.x * base.x + this.y * base.y;
            double det = this.x * base.y - this.y * base.x;
            return Math.atan2(det, dot);
        }

        public static Vector of(int x, int y) {
            if (x == 0 && y == 0) return Vector.of(0, 0);
            int gcd = gcd(Math.abs(x), Math.abs(y));
            int newX = x / gcd;
            int newY = y / gcd;

            if (Math.abs((1. * x / gcd) - newX) >= 0.0001) throw new RuntimeException("RIPX " + x + "," + y);
            if (Math.abs((1. * y / gcd) - newY) >= 0.0001) throw new RuntimeException("RIPY " + x + "," + y);

            return new Vector(newX, newY);
        }

        private static int gcd(int a, int b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector vector = (Vector) o;
            return x == vector.x &&
                    y == vector.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
