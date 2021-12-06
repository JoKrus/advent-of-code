package net.jcom.adventofcode.aoc2019;

import net.jcom.adventofcode.Day;
import net.jcom.adventofcode.aoc2019.misc.Opcoder;

import java.util.Arrays;

public class Day2019_09 extends Day {
    @Override
    public String part1Logic() {
        return run(true);
    }

    private String run(boolean part1) {
        long[] baseArray = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
        Opcoder opcoder = new Opcoder(baseArray);
        opcoder.addInput(part1 ? 1 : 2);
        opcoder.runOpcode();
        return "" + opcoder.getOutput().element();
    }

    @Override
    public String part2Logic() {
        return run(false);
    }
}
