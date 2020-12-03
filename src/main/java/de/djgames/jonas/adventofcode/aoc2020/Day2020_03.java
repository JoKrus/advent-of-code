package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2020_03 extends Day {
    @Override
    public String part1Logic() {

        List<String[]> collect = Arrays.stream(input.split("\n")).map(s -> s.split("")).collect(Collectors.toList());
        int x = 0, y = 0;
        int cnt = 0;
        for (int i = 0; i < collect.size() - 1; ++i) {
            if (move(collect, x, y, 3, 1)) {
                cnt++;
            }
            x += 3;
            y += 1;
        }

        return "" + cnt;
    }

    private boolean move(List<String[]> collect, int posx, int posy, int dx, int dy) {
        int newY = posy + dy;
        int newX = (posx + dx) % collect.get(newY).length;

        return collect.get(newY)[newX].equals("#");
    }

    @Override
    public String part2Logic() {
        List<String[]> collect = Arrays.stream(input.split("\n")).map(s -> s.split("")).collect(Collectors.toList());
        int x = 0, y = 0;
        long finalcnt = 1;
        int cnt = 0;
        for (int i = 0; i < collect.size() - 1; ++i) {
            if (move(collect, x, y, 1, 1)) {
                cnt++;
            }
            x += 1;
            y += 1;
        }
        x = 0;
        y = 0;
        finalcnt *= cnt;
        cnt = 0;
        for (int i = 0; i < collect.size() - 1; ++i) {
            if (move(collect, x, y, 3, 1)) {
                cnt++;
            }
            x += 3;
            y += 1;
        }
        x = 0;
        y = 0;
        finalcnt *= cnt;
        cnt = 0;
        for (int i = 0; i < collect.size() - 1; ++i) {
            if (move(collect, x, y, 5, 1)) {
                cnt++;
            }
            x += 5;
            y += 1;
        }
        x = 0;
        y = 0;
        finalcnt *= cnt;
        cnt = 0;
        for (int i = 0; i < collect.size() - 1; ++i) {
            if (move(collect, x, y, 7, 1)) {
                cnt++;
            }
            x += 7;
            y += 1;
        }
        x = 0;
        y = 0;
        finalcnt *= cnt;
        cnt = 0;
        for (int i = 0; i < collect.size() - 1; i += 2) {
            if (move(collect, x, y, 1, 2)) {
                cnt++;
            }
            x += 1;
            y += 2;
        }
        finalcnt *= cnt;
        return "" + finalcnt;
    }
}
