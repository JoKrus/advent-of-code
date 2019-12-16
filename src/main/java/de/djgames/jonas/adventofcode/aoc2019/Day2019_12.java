package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.math3.util.ArithmeticUtils.lcm;

public class Day2019_12 extends Day {

    @Override
    public String part1Logic() {
        return execPart1();
    }

    private ArrayList<Moon> parseInput() {
        String[] array = input.split("\n");
        ArrayList<Moon> moons = new ArrayList<>();
        for (String moonInput : array) {
            int x, y, z;

            String[] split = Pattern.compile("=-?[0-9]+")
                    .matcher(moonInput).results().map(MatchResult::group).toArray(String[]::new);
            x = Integer.parseInt(split[0].substring(1));
            y = Integer.parseInt(split[1].substring(1));
            z = Integer.parseInt(split[2].substring(1));

            Moon moon = new Moon(x, y, z);
            moons.add(moon);
        }
        return moons;
    }

    private String execPart1() {
        ArrayList<Moon> moons = parseInput();
        long steps = 0;

        do {
            HashMap<Moon, Vector3> moonToVelDiff = new HashMap<>();
            steps++;
            for (Moon moon : moons) {
                Vector3 velDiff = new Vector3(0, 0, 0);
                for (Moon moonRel : moons) {
                    if (moon.equals(moonRel)) continue;
                    Vector3 velDiffOne = new Vector3(compareIntSmooth(moon.pos.x, moonRel.pos.x), compareIntSmooth(moon.pos.y, moonRel.pos.y), compareIntSmooth(moon.pos.z, moonRel.pos.z));

                    velDiff.add(velDiffOne);
                }
                moonToVelDiff.put(moon, velDiff);
            }


            for (Moon moon : moonToVelDiff.keySet()) {
                moon.vel.add(moonToVelDiff.get(moon));
                moon.pos.add(moon.vel);
            }

        } while (steps != 1000);


        return String.format("%d", moons.stream().mapToInt(Moon::getTotalEnergy).sum());
    }

    public static int compareIntSmooth(int i, int i2) {
        int comp = Integer.compare(i, i2);
        if (comp == 0) return comp;
        return -1 * comp / Math.abs(comp);
    }

    @Override
    public String part2Logic() {
        ArrayList<Moon> moons = parseInput();

        ArrayList<Coordinate> xs = new ArrayList<>();
        ArrayList<Coordinate> ys = new ArrayList<>();
        ArrayList<Coordinate> zs = new ArrayList<>();

        for (Moon moon : moons) {
            xs.add(new Coordinate(moon.pos.x));
            ys.add(new Coordinate(moon.pos.y));
            zs.add(new Coordinate(moon.pos.z));
        }

        long xloops = findLoop(xs), yloops = findLoop(ys), zloops = findLoop(zs);

        long lcmxy = lcm(xloops, yloops);
        long lcmyz = lcm(yloops, zloops);
        long lcmzx = lcm(zloops, xloops);

        return String.format("%d", lcm(lcmxy, lcmyz));
    }

    private static List<Coordinate> copyDeep(List<Coordinate> list) {
        return list.stream().map(integerRef -> new Coordinate(integerRef.pos)).collect(Collectors.toList());
    }

    private static long findLoop(List<Coordinate> state) {
        List<Coordinate> copy = copyDeep(state);

        //System.out.println(state);
        long steps = 0;
        do {
            HashMap<Coordinate, Integer> coordToVelDiff = new HashMap<>();
            for (Coordinate coordinate : state) {
                int velDiff = 0;
                for (Coordinate coordinate2 : state) {
                    if (coordinate.equals(coordinate2)) continue;
                    velDiff += compareIntSmooth(coordinate.pos, coordinate2.pos);
                }
                coordToVelDiff.put(coordinate, velDiff);
            }

            for (Coordinate coordinate : coordToVelDiff.keySet()) {
                coordinate.vel += coordToVelDiff.get(coordinate);
                coordinate.pos += coordinate.vel;
            }
            steps++;
            // System.out.println(state);
        } while (!equals(state, copy));
        return steps;
    }

    private static <T extends Comparable<T>> boolean equals(List<T> list, List<T> list2) {
        if (list.size() != list2.size()) return false;

        List<T> listCpy = new ArrayList<>(list);
        List<T> list2Cpy = new ArrayList<>(list2);
        Collections.sort(listCpy);
        Collections.sort(list2Cpy);

        for (int i = 0; i < listCpy.size(); ++i) {
            if (!listCpy.get(i).equals(list2Cpy.get(i))) return false;
        }
        return true;
    }

    private static class Coordinate implements Comparable<Coordinate> {
        public int pos;
        public int vel;

        public Coordinate(int pos) {
            this.pos = pos;
            this.vel = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return this.compareTo(that) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, vel);
        }

        @Override
        public String toString() {
            return String.format("pos=%d, vel=%d", pos, vel);
        }

        @Override
        public int compareTo(Coordinate o) {
            int posEq = this.pos - o.pos;
            int velEq = this.vel - o.vel;
            if (Math.abs(posEq) + Math.abs(velEq) == 0) return 0;
            if (posEq == 0) return velEq;
            return posEq;
        }
    }

    private static class Moon {
        private Vector3 pos;
        private Vector3 vel;

        public Moon(int posx, int posy, int posz) {
            pos = new Vector3(posx, posy, posz);
            vel = new Vector3(0, 0, 0);
        }

        public Moon(Moon copy) {
            pos = new Vector3(copy.pos);
            vel = new Vector3(copy.vel);
        }

        public int getPotentialEnergy() {
            return Math.abs(pos.getX()) + Math.abs(pos.getY()) + Math.abs(pos.getZ());
        }

        public int getKineticEnergy() {
            return Math.abs(vel.getX()) + Math.abs(vel.getY()) + Math.abs(vel.getZ());
        }

        public int getTotalEnergy() {
            return getKineticEnergy() * getPotentialEnergy();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Moon moon = (Moon) o;
            return Objects.equals(pos, moon.pos) &&
                    Objects.equals(vel, moon.vel);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, vel);
        }

        @Override
        public String toString() {
            return "pos=" + pos +
                    ", vel=" + vel;
        }
    }

    public static class Vector3 {
        private int x, y, z;

        public Vector3(Vector3 vector3) {
            this.x = vector3.getX();
            this.y = vector3.getY();
            this.z = vector3.getZ();
        }

        public Vector3(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public void add(Vector3 vector3) {
            this.setX(this.getX() + vector3.getX());
            this.setY(this.getY() + vector3.getY());
            this.setZ(this.getZ() + vector3.getZ());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector3 vector3 = (Vector3) o;
            return x == vector3.x &&
                    y == vector3.y &&
                    z == vector3.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return String.format("<x=%3d, y=%3d, z=%3d>", x, y, z);
        }
    }
}
