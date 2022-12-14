package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Day2022_12 extends Day {
    public static int xLen = 0;

    private static int pairToCoordinate(Pair<Integer, Integer> pair) {
        return pair.getRight() * xLen + pair.getLeft();
    }

    @Override
    public String part1Logic() {
        var array = input.split("\n");
        //Start is 83 which means 97
        //E is 69 which is 122
        int[][] map = new int[array.length][];

        Pair<Integer, Integer> start = Pair.of(0, 0);
        Pair<Integer, Integer> end = Pair.of(0, 0);

        for (int i = 0; i < array.length; i++) {
            var test = array[i].split("");
            var test2 = Arrays.stream(test).map(s -> s.charAt(0)).mapToInt(Character::charValue).toArray();
            map[i] = test2;

            //Find start and end
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 83) {
                    start = Pair.of(j, i);
                    map[i][j] = 97;
                } else if (map[i][j] == 69) {
                    end = Pair.of(j, i);
                    map[i][j] = 122;
                }
            }
        }

        xLen = map[0].length;


        int[] dist = new int[map.length * xLen];
        ArrayList<Pair<Integer, Integer>> prevs = new ArrayList<>(map.length * xLen);
        PriorityQueue<Pair<Integer, Integer>> queue =
                new PriorityQueue<>(Comparator.comparingInt(o -> dist[pairToCoordinate(o)]));

        initDijkstra(map, start, dist, prevs, queue);

        while (!queue.isEmpty()) {
            //Update queue
            var l = queue.stream().toList();
            queue = new PriorityQueue<>(Comparator.comparingInt(o -> dist[pairToCoordinate(o)]));
            queue.addAll(l);

            var u = queue.poll();
            var neighs = getNeighbours(u, map);
            for (var neigh : neighs) {
                if (queue.contains(neigh)) {
                    updateDist(u, neigh, dist, prevs);
                }
            }
        }

        return "" + dist[pairToCoordinate(end)];
    }

    private void updateDist(Pair<Integer, Integer> u, Pair<Integer, Integer> neigh, int[] dist, ArrayList<Pair<Integer, Integer>> prevs) {
        int alt = dist[pairToCoordinate(u)] + 1;
        if (alt < dist[pairToCoordinate(neigh)]) {
            dist[pairToCoordinate(neigh)] = alt;
            prevs.set(pairToCoordinate(neigh), u);
        }
    }

    private List<Pair<Integer, Integer>> getNeighbours(Pair<Integer, Integer> u, int[][] map) {
        ArrayList<Pair<Integer, Integer>> ret = new ArrayList<>();
        ret.add(checkNeigh(u, map, 0, 1));
        ret.add(checkNeigh(u, map, 0, -1));
        ret.add(checkNeigh(u, map, 1, 0));
        ret.add(checkNeigh(u, map, -1, 0));
        var trueRet = ret.stream().filter(Objects::nonNull).toList();
        return trueRet;
    }

    private List<Pair<Integer, Integer>> getNeighbours2(Pair<Integer, Integer> u, int[][] map) {
        ArrayList<Pair<Integer, Integer>> ret = new ArrayList<>();
        ret.add(checkNeigh2(u, map, 0, 1));
        ret.add(checkNeigh2(u, map, 0, -1));
        ret.add(checkNeigh2(u, map, 1, 0));
        ret.add(checkNeigh2(u, map, -1, 0));
        var trueRet = ret.stream().filter(Objects::nonNull).toList();
        return trueRet;
    }

    private Pair<Integer, Integer> checkNeigh(Pair<Integer, Integer> u, int[][] map, int dx, int dy) {
        var potNeigh = Pair.of(u.getLeft() + dx, u.getRight() + dy);
        var potVal = Integer.MAX_VALUE;
        try {
            potVal = map[potNeigh.getRight()][potNeigh.getLeft()];
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }

        var currVal = map[u.getRight()][u.getLeft()];

        if (potVal - currVal <= 1) {
            return potNeigh;
        } else {
            return null;
        }
    }

    private Pair<Integer, Integer> checkNeigh2(Pair<Integer, Integer> u, int[][] map, int dx, int dy) {
        var potNeigh = Pair.of(u.getLeft() + dx, u.getRight() + dy);
        var potVal = Integer.MAX_VALUE;
        try {
            potVal = map[potNeigh.getRight()][potNeigh.getLeft()];
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }

        var currVal = map[u.getRight()][u.getLeft()];

        if (potVal - currVal >= -1) {
            return potNeigh;
        } else {
            return null;
        }
    }

    private void initDijkstra(int[][] map, Pair<Integer, Integer> start, int[] dist, ArrayList<Pair<Integer, Integer>> prevs, PriorityQueue<Pair<Integer, Integer>> queue) {
        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE / 2;
            prevs.add(i, null);
        }
        dist[pairToCoordinate(start)] = 0;

        ArrayList<Pair<Integer, Integer>> allNodes = new ArrayList<>(prevs.size());

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                allNodes.add(Pair.of(x, y));
            }
        }

        queue.addAll(allNodes);
    }

    @Override
    public String part2Logic() {
        var array = input.split("\n");
        //Start is 83 which means 97
        //E is 69 which is 122
        int[][] map = new int[array.length][];

        Pair<Integer, Integer> start = Pair.of(0, 0);
        Pair<Integer, Integer> end = Pair.of(0, 0);

        for (int i = 0; i < array.length; i++) {
            var test = array[i].split("");
            var test2 = Arrays.stream(test).map(s -> s.charAt(0)).mapToInt(Character::charValue).toArray();
            map[i] = test2;

            //Find start and end
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 83) {
                    end = Pair.of(j, i);
                    map[i][j] = 97;
                } else if (map[i][j] == 69) {
                    start = Pair.of(j, i);
                    map[i][j] = 122;
                }
            }
        }

        xLen = map[0].length;


        int[] dist = new int[map.length * xLen];
        ArrayList<Pair<Integer, Integer>> prevs = new ArrayList<>(map.length * xLen);
        PriorityQueue<Pair<Integer, Integer>> queue =
                new PriorityQueue<>(Comparator.comparingInt(o -> dist[pairToCoordinate(o)]));

        initDijkstra(map, start, dist, prevs, queue);

        while (!queue.isEmpty()) {
            //Update queue
            var l = queue.stream().toList();
            queue = new PriorityQueue<>(Comparator.comparingInt(o -> dist[pairToCoordinate(o)]));
            queue.addAll(l);

            var u = queue.poll();

            if (map[u.getRight()][u.getLeft()] == 97) {
                end = u;
                break;
            }

            var neighs = getNeighbours2(u, map);
            for (var neigh : neighs) {
                if (queue.contains(neigh)) {
                    updateDist(u, neigh, dist, prevs);
                }
            }
        }

        return "" + dist[pairToCoordinate(end)];
    }
}
