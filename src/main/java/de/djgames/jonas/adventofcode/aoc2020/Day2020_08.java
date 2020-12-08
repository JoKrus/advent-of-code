package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;
import de.djgames.jonas.adventofcode.aoc2020.misc.InstructionEngine;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day2020_08 extends Day {
    @Override
    public String part1Logic() {

        var engine = new InstructionEngine(Arrays.stream(input.split("\n")).collect(Collectors.toList()));
        engine.run(InstructionEngine.HaltMode.INFINITE_LOOP);
        return "" + engine.getAccumulator();
    }

    @Override
    public String part2Logic() {
        return null;
    }
}
