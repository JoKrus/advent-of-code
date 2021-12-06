package net.jcom.adventofcode.aoc2020;

import net.jcom.adventofcode.Day;

import java.util.Arrays;


public class Day2020_01 extends Day {
    @Override
    public String part1Logic() {
        var ints = parseInput();

        for (int i = 0; i < ints.length; ++i) {
            for (int j = i; j < ints.length; ++j) {
                if (ints[i] + ints[j] == 2020) {
                    return String.format("%d", ints[i] * ints[j]);
                }
            }
        }

        return "No solution found";
    }

    private int[] parseInput() {
        return Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    public String part2Logic() {
        var ints = parseInput();

        for (int i = 0; i < ints.length; ++i) {
            for (int j = i; j < ints.length; ++j) {
                for (int k = j; k < ints.length; ++k) {
                    if (ints[i] + ints[j] + ints[k] == 2020) {
                        return String.format("%d", ints[i] * ints[j] * ints[k]);
                    }
                }
            }
        }

        return "No solution found";
    }
}