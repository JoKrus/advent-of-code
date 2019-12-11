package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day2019_01 extends Day {

    @Override
    public String part1Logic() {
        return String.format("%d", getStream().map(val -> val / 3 - 2).sum());
    }

    @Override
    public String part2Logic() {
        return String.format("%d", getStream().map(val -> {
            int newVal = 0;
            int nextVal = val;
            while (nextVal > 0) {
                nextVal = nextVal / 3 - 2;
                if (nextVal > 0) newVal += nextVal;
            }
            return newVal;
        }).sum());
    }

    private IntStream getStream() {
        return Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt);
    }
}
