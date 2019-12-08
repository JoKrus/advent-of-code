package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;
import de.djgames.jonas.adventofcode.misc.Opcoder;

import java.util.Arrays;

public class Day2019_5 extends Day {
    @Override
    public String part1Logic() {
        int[] instrs = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        return String.format("%d", Opcoder.runOpcodeLegacy(instrs, -1, 1));
    }

    @Override
    public String part2Logic() {
        int[] instrs = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        return String.format("%d", Opcoder.runOpcodeLegacy(instrs, -1, 5));
    }

}
