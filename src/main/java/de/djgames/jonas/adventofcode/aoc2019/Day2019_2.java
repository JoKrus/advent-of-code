package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;

import static de.djgames.jonas.adventofcode.aoc2019.Day2019_5.runOpcode;

public class Day2019_2 extends Day {
    @Override
    public String part1Logic() {
        int[] instrs = Arrays.stream(input.split("\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();
        instrs[1] = 12;
        instrs[2] = 2;

        return String.format("%d", runOpcode(instrs, 0, 0));
    }

    @Override
    public String part2Logic() {
        for (int noun = 0; noun < 100; ++noun) {
            for (int verb = 0; verb < 100; ++verb) {
                int[] instrs = Arrays.stream(input.split("\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();
                instrs[1] = noun;
                instrs[2] = verb;
                if (runOpcode(instrs, 0, 0) == Integer.parseInt(input.split("\n")[1]))
                    return String.format("%d", 100 * noun + verb);
            }
        }
        return "";
    }


}
