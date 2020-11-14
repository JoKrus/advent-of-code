package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;
import de.djgames.jonas.adventofcode.aoc2019.misc.Opcoder;

import java.util.Arrays;

public class Day2019_05 extends Day {
    @Override
    public String part1Logic() {
        int[] instructions = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        return String.format("%d", Opcoder.runOpcodeLegacy(instructions, -1, 1));
    }

    @Override
    public String part2Logic() {
        int[] instructions = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        return String.format("%d", Opcoder.runOpcodeLegacy(instructions, -1, 5));
    }

}
