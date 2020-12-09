package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2020_09 extends Day {
    @Override
    public String part1Logic() {
        var longs = Arrays.stream(input.split("\n")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());

        long error = findError(longs, 25, 25);

        return "" + error;
    }

    private long findError(List<Long> longs, int preambleLen, int amountOfValidPres) {
        int pointer = preambleLen;

        outer:
        for (; pointer < longs.size(); ++pointer) {
            var currentVal = longs.get(pointer);
            var inIt = longs.subList(pointer - amountOfValidPres, pointer);
            for (int i = 0; i < inIt.size(); ++i) {
                for (int j = i; j < inIt.size(); ++j) {
                    if (inIt.get(i) + inIt.get(j) == currentVal) {
                        continue outer;
                    }
                }
            }
            return currentVal;
        }
        return -1;
    }

    @Override
    public String part2Logic() {
        var longs = Arrays.stream(input.split("\n")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());

        long error = findError(longs, 25, 25);

        var longsPossible = longs.subList(0, longs.indexOf(error));

        for (int i = 0; i < longsPossible.size(); ++i) {
            long sum = 0;
            int i2 = i;
            long highest = 0, lowest = Long.MAX_VALUE;
            while (sum <= error) {
                if (sum == error) {
                    return "" + (lowest + highest);
                }
                var li2 = longs.get(i2);
                sum += li2;
                if (li2 < lowest) lowest = li2;
                if (li2 > highest) highest = li2;

                i2++;
            }
        }
        return null;
    }
}
