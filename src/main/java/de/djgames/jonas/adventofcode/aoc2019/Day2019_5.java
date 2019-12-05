package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day2019_5 extends Day {
    @Override
    public String part1Logic() {
        int[] instrs = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        return String.format("%d", runOpcode(instrs, 1, -1));
    }

    @Override
    public String part2Logic() {
        int[] instrs = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        return String.format("%d", runOpcode(instrs, 5, -1));
    }

    static int runOpcode(int[] instrs, int progInput, int progOutputAddress) {
        Function<Integer, Integer> firstPMode = opCodeEntry -> (opCodeEntry / 100) % 10;
        Function<Integer, Integer> secondPMode = opCodeEntry -> (opCodeEntry / 1000);
        BiFunction<Integer, Integer, Integer> value = (mode, val) -> mode != 0 ? val : instrs[val];
        int ret = 0;
        int i = 0;
        while (i < instrs.length && instrs[i] != 99) {
            switch (instrs[i] % 100) {
                case 1:
                    var num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    var num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    instrs[instrs[i + 3]] = num1 + num2;
                    i += 4;
                    break;
                case 2:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    instrs[instrs[i + 3]] = num1 * num2;
                    i += 4;
                    break;
                case 3:
                    instrs[instrs[i + 1]] = progInput;
                    i += 2;
                    break;
                case 4:
                    ret = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    i += 2;
                    break;
                case 5:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    i = num1 == 0 ? i + 3 : num2;
                    break;
                case 6:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    i = num1 != 0 ? i + 3 : num2;
                    break;
                case 7:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    instrs[instrs[i + 3]] = num1 < num2 ? 1 : 0;
                    i += 4;
                    break;
                case 8:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    instrs[instrs[i + 3]] = num1.equals(num2) ? 1 : 0;
                    i += 4;
                    break;
            }
        }
        return progOutputAddress < 0 ? ret : instrs[progOutputAddress];
    }

}
