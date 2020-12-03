package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day2020_03 extends Day {
    @Override
    public String part1Logic() {
        return work(true, Collections.singletonList(new MoveDir(3, 1)));
    }

    private String work(boolean part1, List<MoveDir> moves) {
        List<String[]> collect = getMap();
        long total = part1 ? 0 : 1;
        for (var move : moves) {
            int x = 0, y = 0;
            int cnt = 0;
            while (y < collect.size() - 1) {
                if (move(collect, x, y, move.dx, move.dy)) {
                    cnt++;
                }
                x += move.dx;
                y += move.dy;
            }
            if (part1) {
                total += cnt;
            } else {
                total *= cnt;
            }
        }

        return String.format("%d", total);
    }

    private List<String[]> getMap() {
        return Arrays.stream(input.split("\n")).map(s -> s.split("")).collect(Collectors.toList());
    }

    private boolean move(List<String[]> collect, int posx, int posy, int dx, int dy) {
        int newY = posy + dy;
        int newX = (posx + dx) % collect.get(newY).length;

        return collect.get(newY)[newX].equals("#");
    }

    @Override
    public String part2Logic() {
        return work(false, List.of(new MoveDir(1, 1), new MoveDir(3, 1), new MoveDir(5, 1),
                new MoveDir(7, 1), new MoveDir(1, 2)));
    }

    private static class MoveDir {
        private final int dx, dy;

        private MoveDir(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}
