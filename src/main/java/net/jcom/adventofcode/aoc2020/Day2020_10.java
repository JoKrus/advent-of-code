package net.jcom.adventofcode.aoc2020;

import net.jcom.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2020_10 extends Day {
    @Override
    public String part1Logic() {

        List<Integer> sortedAdapters = Arrays.stream(input.split("\n")).map(Integer::parseInt).sorted().collect(Collectors.toList());
        sortedAdapters.add(sortedAdapters.get(sortedAdapters.size() - 1) + 3);
        sortedAdapters.add(0, 0);

        //jump 0,1,2,3
        int[] joltJumps = new int[4];

        for (int i = 0; i < sortedAdapters.size() - 1; ++i) {
            var diff = sortedAdapters.get(i + 1) - sortedAdapters.get(i);
            joltJumps[diff]++;
        }

        var sol = joltJumps[1] * joltJumps[3];
        return "" + sol;
    }

    @Override
    public String part2Logic() {
        List<Integer> sortedAdapters = Arrays.stream(input.split("\n")).map(Integer::parseInt).sorted().collect(Collectors.toList());
        sortedAdapters.add(sortedAdapters.get(sortedAdapters.size() - 1) + 3);
        sortedAdapters.add(0, 0);

        //maps index to value
        long[] array = new long[sortedAdapters.size()];
        array[0] = 1;

        for (int i = 0; i < sortedAdapters.size(); ++i) {
            int j = i + 1;
            int myVal = sortedAdapters.get(i);
            while (j < sortedAdapters.size() && sortedAdapters.get(j) - myVal <= 3) {
                array[j] = array[j] + array[i];
                j++;
            }
        }

        return "" + array[sortedAdapters.size() - 1];
    }
}
