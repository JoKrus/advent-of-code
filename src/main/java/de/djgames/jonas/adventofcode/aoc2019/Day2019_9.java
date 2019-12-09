package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;
import de.djgames.jonas.adventofcode.misc.Opcoder;

import java.util.Arrays;

public class Day2019_9 extends Day {
    @Override
    public String part1Logic() {
        long[] baseArray = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
        Opcoder opcoder = new Opcoder(baseArray);
        opcoder.addInput(1);

        opcoder.runOpcode();
        return "" + opcoder.getOutput().toString();
    }

    @Override
    public String part2Logic() {
        return null;
    }
}
