package net.jcom.adventofcode.aoc2019;

import net.jcom.adventofcode.Day;

import java.util.Arrays;

import static net.jcom.adventofcode.aoc2019.misc.Opcoder.runOpcodeLegacy;

public class Day2019_02 extends Day {
    @Override
    public String part1Logic() {
        int[] instructions = Arrays.stream(input.split("\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();
        instructions[1] = 12;
        instructions[2] = 2;

        return String.format("%d", runOpcodeLegacy(instructions, 0));
    }

    @Override
    public String part2Logic() {
        int wantedResult = Integer.parseInt(input.split("\n")[1]);
        int[] instructionsOriginal =
                Arrays.stream(input.split("\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();

        for (int noun = 0; noun < 100; ++noun) {
            for (int verb = 0; verb < 100; ++verb) {
                int[] instructions = new int[instructionsOriginal.length];
                System.arraycopy(instructionsOriginal, 0, instructions, 0, instructions.length);
                instructions[1] = noun;
                instructions[2] = verb;
                if (runOpcodeLegacy(instructions, 0) == wantedResult)
                    return String.format("%d", 100 * noun + verb);
            }
        }
        return "";
    }


}
