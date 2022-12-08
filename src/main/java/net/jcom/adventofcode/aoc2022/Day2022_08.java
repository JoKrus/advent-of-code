package net.jcom.adventofcode.aoc2022;

import com.google.common.primitives.Booleans;
import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2022_08 extends Day {
    @Override
    public String part1Logic() {
        var inp = input.split("\n");

        var trees = new int[inp.length][inp.length];

        for (int y = 0; y < inp.length; y++) {
            var s = inp[y];
            var xs = s.split("");
            for (int x = 0; x < xs.length; x++) {
                trees[y][x] = Integer.parseInt(xs[x]);
            }
        }

        var visible = new boolean[trees.length * trees.length];

        for (int y = 0; y < trees.length; y++) {
            for (int x = 0; x < trees[y].length; x++) {
                boolean toSet;
                if (x == 0 || x == trees[y].length - 1) {
                    toSet = true;
                } else if (y == 0 || y == trees.length - 1) {
                    toSet = true;
                } else {
                    toSet = isVisible(trees, y, x);
                }
                visible[y * trees.length + x] = toSet;
            }
        }

        return "" + Booleans.asList(visible).stream().filter(aBoolean -> aBoolean).count();
    }

    private boolean isVisible(int[][] trees, int y, int x) {
        var dirs = List.of(Pair.of(0, 1),
                Pair.of(0, -1),
                Pair.of(1, 0),
                Pair.of(-1, 0));

        int treeSize = trees[y][x];

        for (var dir : dirs) {
            int dy = dir.getLeft(), dx = dir.getRight();
            try {
                int i = 0;
                while (true) {
                    i++;

                    var toCheck = trees[y + i * dy][x + i * dx];
                    if (treeSize <= toCheck) {
                        break;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String part2Logic() {
        var inp = input.split("\n");

        var trees = new int[inp.length][inp.length];

        for (int y = 0; y < inp.length; y++) {
            var s = inp[y];
            var xs = s.split("");
            for (int x = 0; x < xs.length; x++) {
                trees[y][x] = Integer.parseInt(xs[x]);
            }
        }

        var viewingDistance = new int[trees.length * trees.length];

        for (int y = 0; y < trees.length; y++) {
            for (int x = 0; x < trees[y].length; x++) {
                int toSet = 0;
                if ((x != 0 && x != trees[y].length - 1) && (y != 0 && y != trees.length - 1)) {
                    toSet = getViewingDistScore(trees, y, x);
                }
                viewingDistance[y * trees.length + x] = toSet;
            }
        }

        return "" + Arrays.stream(viewingDistance).max().getAsInt();
    }

    private int getViewingDistScore(int[][] trees, int y, int x) {
        var dirs = List.of(Pair.of(0, 1),
                Pair.of(0, -1),
                Pair.of(1, 0),
                Pair.of(-1, 0));

        int treeSize = trees[y][x];

        ArrayList<Integer> scores = new ArrayList<>();

        for (var dir : dirs) {
            int dy = dir.getLeft(), dx = dir.getRight();
            int i = 0;
            try {
                while (true) {
                    i++;

                    var toCheck = trees[y + i * dy][x + i * dx];
                    if (treeSize <= toCheck) {
                        scores.add(i);
                        break;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                scores.add(i - 1);
            }
        }

        return scores.stream().mapToInt(Integer::intValue).reduce((left, right) -> left * right).getAsInt();
    }
}
