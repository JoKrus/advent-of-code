package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;

import static de.djgames.jonas.adventofcode.misc.Opcoder.runOpcodeLegacy;

public class Day2019_2 extends Day {
    @Override
    public String part1Logic() {
        int[] instructions = Arrays.stream(input.split("\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();
        instructions[1] = 12;
        instructions[2] = 2;

        return String.format("%d", runOpcodeLegacy(instructions, 0));
    }

    @Override
    public String part2Logic() {
        for (int noun = 0; noun < 100; ++noun) {
            for (int verb = 0; verb < 100; ++verb) {
                int[] instructions = Arrays.stream(input.split("\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();
                instructions[1] = noun;
                instructions[2] = verb;
                if (runOpcodeLegacy(instructions, 0) == Integer.parseInt(input.split("\n")[1]))
                    return String.format("%d", 100 * noun + verb);
            }
        }
        return "";
    }


}
