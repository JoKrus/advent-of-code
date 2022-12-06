package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;

import java.util.ArrayList;

public class Day2022_03 extends Day {
    @Override
    public String part1Logic() {
        var arr = input.split("\n");

        var duplicates = new ArrayList<String>();

        for (var backs : arr) {
            var comp1 = backs.substring(0, backs.length() / 2);
            var comp2 = backs.substring(backs.length() / 2);

            var comp1split = comp1.split("");
            for (var compi : comp1split) {
                if (comp2.contains(compi)) {
                    duplicates.add(compi);
                    break;
                }
            }
        }

        var sum = duplicates.stream().mapToInt(value -> {
            var charI = value.charAt(0);
            if (charI > 91) {
                return charI - 96;
            } else {
                return charI - 38;
            }
        }).sum();


        return "" + sum;
    }

    @Override
    public String part2Logic() {
        var arr = input.split("\n");

        String[][] myArr = new String[arr.length / 3][3];

        for (int i = 0; i < arr.length; i += 3) {
            myArr[i / 3][0] = arr[i];
            myArr[i / 3][1] = arr[i + 1];
            myArr[i / 3][2] = arr[i + 2];
        }

        var duplicates = new ArrayList<String>();

        for (var backs : myArr) {
            var comp1 = backs[0];
            var comp2 = backs[1];
            var comp3 = backs[2];

            var comp1split = comp1.split("");
            for (var compi : comp1split) {
                if (comp2.contains(compi) && comp3.contains(compi)) {
                    duplicates.add(compi);
                    break;
                }
            }
        }

        var sum = duplicates.stream().mapToInt(value -> {
            var charI = value.charAt(0);
            if (charI > 91) {
                return (charI - 96);
            } else {
                return (charI - 38);
            }
        }).sum();

        return "" + sum;
    }
}
