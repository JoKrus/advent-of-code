package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;

import java.util.Arrays;

public class Day2022_04 extends Day {
    @Override
    public String part1Logic() {

        var i = Arrays.stream(input.split("\n")).filter(s -> {
            var arr = s.split(",");
            var one = arr[0];
            var two = arr[1];

            var oneSplit = one.split("-");
            var twoSplit = two.split("-");

            var oneL = Integer.parseInt(oneSplit[0]);
            var oneR = Integer.parseInt(oneSplit[1]);

            var twoL = Integer.parseInt(twoSplit[0]);
            var twoR = Integer.parseInt(twoSplit[1]);

            return oneL <= twoL && oneR >= twoR || twoL <= oneL && twoR >= oneR;
        }).count();
        return "" + i;
    }

    @Override
    public String part2Logic() {
        var i = Arrays.stream(input.split("\n")).filter(s -> {
            var arr = s.split(",");
            var one = arr[0];
            var two = arr[1];

            var oneSplit = one.split("-");
            var twoSplit = two.split("-");

            var oneL = Integer.parseInt(oneSplit[0]);
            var oneR = Integer.parseInt(oneSplit[1]);

            var twoL = Integer.parseInt(twoSplit[0]);
            var twoR = Integer.parseInt(twoSplit[1]);

            return oneL <= twoL && oneR >= twoR || twoL <= oneL && twoR >= oneR || twoR >= oneR && oneR >= twoL || oneR >= twoR && twoR >= oneL;
        }).count();
        return "" + i;

    }
}
