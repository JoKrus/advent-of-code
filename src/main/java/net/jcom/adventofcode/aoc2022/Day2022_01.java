package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day2022_01 extends Day {
    @Override
    public String part1Logic() {
        List<Integer> elves = getElvenCalories();

        return "" + elves.stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    @Override
    public String part2Logic() {
        List<Integer> elves = getElvenCalories();

        //Compare to has lowest at 0 so reverse it to get the highest 3
        elves.sort(Integer::compareTo);
        Collections.reverse(elves);

        var newL = elves.subList(0, 3);

        return "" + newL.stream().mapToInt(Integer::intValue).sum();
    }

    private List<Integer> getElvenCalories() {
        List<String> elves = Arrays.stream(input.split("\n\n")).toList();

        List<Integer> ints = new ArrayList<>();
        for (var elf : elves) {
            ints.add(Arrays.stream(elf.split("\n")).mapToInt(Integer::parseInt).sum());
        }
        return ints;
    }
}
