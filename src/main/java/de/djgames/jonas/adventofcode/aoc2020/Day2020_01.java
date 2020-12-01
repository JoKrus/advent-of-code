package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;


public class Day2020_01 extends Day {
    @Override
    public String part1Logic() {
        var ints = Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt).toArray();

        for (var inte : ints) {
            for (var inte2 : ints) {
                if (inte + inte2 == 2020) {
                    return "" + (inte * inte2);
                }
            }
        }

        return null;
    }

    @Override
    public String part2Logic() {
        var ints = Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt).toArray();

        for (var inte : ints) {
            for (var inte2 : ints) {
                for (var inte3 : ints) {
                    if (inte + inte2 + inte3 == 2020) {
                        return "" + (inte * inte2 * inte3);
                    }
                }
            }
        }
        return null;
    }
}