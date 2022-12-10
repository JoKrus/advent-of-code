package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2022_10 extends Day {
    @Override
    public String part1Logic() {
        List<String> strings = new ArrayList<>(Arrays.stream(input.split("\n")).toList());

        for (int i = strings.size() - 1; i >= 0; --i) {
            var sOrig = strings.get(i);
            var s = sOrig.split(" ")[0];
            if (s.equals("addx")) {
                strings.set(i, "noop");
                strings.add(i + 1, "add " + sOrig.split(" ")[1]);
            }
        }

        int strength = 0;

        int x = 1;

        var cycles = Arrays.stream(new int[]{20, 60, 100, 140, 180, 220}).boxed().toList();

        for (int i = 1; i <= strings.size(); ++i) {
            if (cycles.contains(i)) {
                strength += i * x;
            }

            var sOrig = strings.get(i - 1);
            var s = sOrig.split(" ")[0];

            if (s.equals("add")) {
                x += Integer.parseInt(sOrig.split(" ")[1]);
            }

        }

        return "" + strength;
    }

    @Override
    public String part2Logic() {
        return null;
    }
}
