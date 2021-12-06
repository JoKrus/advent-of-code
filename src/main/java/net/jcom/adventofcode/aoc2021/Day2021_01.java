package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.util.Arrays;

public class Day2021_01 extends Day {
    @Override
    public String part1Logic() {
        var inputArray = parseInput();

        int counter = countAmountOfIncreases(inputArray);

        return String.format("%d", counter);
    }

    private int[] parseInput() {
        return Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    public String part2Logic() {
        var inputArray = parseInput();

        var inputArrayModified = modifyInput(inputArray);

        int counter = countAmountOfIncreases(inputArrayModified);

        return "" + counter;
    }

    private int countAmountOfIncreases(int[] inputArray) {
        var counter = 0;
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > inputArray[i - 1])
                counter++;
        }
        return counter;
    }

    private int[] modifyInput(int[] inputArray) {
        int[] ret = new int[inputArray.length - 2];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = inputArray[i] + inputArray[i + 1] + inputArray[i + 2];
        }
        return ret;
    }
}
