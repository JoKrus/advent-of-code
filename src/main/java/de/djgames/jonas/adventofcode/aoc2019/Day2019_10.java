package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;

import java.util.*;

public class Day2019_10 extends Day {
    @Override
    public String part1Logic() {
        input = "#.#...#.#.\n" +
                ".###....#.\n" +
                ".#....#...\n" +
                "##.#.#.#.#\n" +
                "....#.#.#.\n" +
                ".##..###.#\n" +
                "..#...##..\n" +
                "..##....##\n" +
                "......#...\n" +
                ".####.###.";
        return exec(true);
    }

    private String exec(boolean part1) {
        boolean[][] cometNet = generateCometNet();

        int maxSeen = 0;
        int maxX = 0, maxY = 0;
        Set<Vector> maxCometVectors = null;

        for (int y = 0; y < cometNet[0].length; y++) {
            for (int x = 0; x < cometNet.length; x++) {
                System.out.println(x + "," + y + " | " + cometNet[x][y]);
                if (cometNet[x][y]) {
                    Set<Vector> cometVectors = cometVectors(x, y, cometNet);
                    int seen = cometVectors.size();
                    if (seen > maxSeen) {
                        maxSeen = seen;
                        maxX = x;
                        maxY = y;
                        maxCometVectors = cometVectors;
                        System.out.println(maxSeen);
                        System.out.println(maxX + "," + maxY);
                    }
                }
            }
        }

        if (part1) return String.format("%d", maxSeen);

        cometNet[maxX][maxY] = false;


        int destroyed = 0;
        //if (maxCometVectors == null || maxCometVectors.size() < 200) throw new RuntimeException("NA Aufgabe");
        int comet200X = 0;
        int comet200Y = 0;

        outer:
        while (true) {
            vectorLoop:
            for (Vector v : maxCometVectors) {
                destroyed++;
                //sort by angle, dann ganze zeit nur array durchgehen immer in i * vector, wenn was findet zaehler + 1 und asteroid entfernen
                //cometNet durchgehen von maxX und maxY , i * vector bis was gefunden wurde oder rand
                int diffx = 0, diffy = 0, basex = v.x, basey = v.y;
                try {
                    while (true) {
                        diffx += basex;
                        diffy += basey;
                        if (cometNet[diffx + maxX][diffy + maxY]) {
                            cometNet[diffx + maxX][diffy + maxY] = false;
                            break;
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }

                System.out.printf("%3d : (%2d,%2d) with Vector %s from Point (%2d,%2d)%n", destroyed, diffx + maxX, diffy + maxY, v, maxX, maxY);
                if (destroyed == 200) {
                    comet200X = diffx + maxX;
                    comet200Y = diffy + maxY;
                    break outer;
                }
            }
        }


        return String.format("%d", 100 * comet200X + comet200Y);
    }


    @Override
    public String part2Logic() {
        input = ".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##";
        return exec(false);
    }

    //first param x, second y
    private boolean[][] generateCometNet() {
        String[] lines = input.split("\n");
        Collections.reverse(Arrays.asList(lines));
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

    private Set<Vector> cometVectors(int fromX, int fromY, boolean[][] cometNet) {
        Vector compareAngle = Vector.of(0, 1);
        //TreeSet<Vector> seen = new TreeSet<>(Comparator.comparingDouble(o -> o.getAngle(fromX, fromY, compareAngle)));
        Set<Vector> seen = new HashSet<>();
        for (int x = 0; x < cometNet.length; ++x) {
            for (int y = 0; y < cometNet[x].length; ++y) {
                if (cometNet[x][y] && !(fromX == x && fromY == y)) {
                    Vector vector = Vector.of(x - fromX, y - fromY);
                    seen.add(vector);
                }
            }
        }

        seen.remove(Vector.of(0, 0));
        System.out.println(seen.size());
        return seen;
    }

    public static class Vector {
        private int x, y;

        private Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double getAngle(int basex, int basey, Vector angle0) {
            Vector newBase = new Vector(this.x - basex, this.y - basey);
            double dot = newBase.x * angle0.x + newBase.y * angle0.y;
            double det = newBase.x * angle0.y - newBase.y * angle0.x;
            return Math.atan2(det, dot);
        }

        public static Vector of(int x, int y) {
            if (x == 0 && y == 0) return new Vector(0, 0);
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

        @Override
        public String toString() {
            return String.format("(%2d,%2d)", x, y);
        }
    }

}
