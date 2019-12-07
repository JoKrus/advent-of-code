package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

public class Day2019_4 extends Day {
    @Override
    public String part1Logic() {
        return run(true);
    }

    @Override
    public String part2Logic() {
        return run(false);
    }

    public String run(boolean part1) {
        int[] inputInt = Arrays.stream(input.split("-")).mapToInt(Integer::parseInt).toArray();
        return String.format("%d", IntStream.rangeClosed(inputInt[0], inputInt[1]).filter(value -> {
            String s = String.valueOf(value);
            for (int i = 0; i < s.length() - 1; ++i) {
                if (s.charAt(i) > s.charAt(i + 1)) return false;
            }
            return true;
        }).filter(value -> {
            HashMap<Character, Integer> occurences = new HashMap<>();
            for (char c : String.valueOf(value).toCharArray()) {
                occurences.put(c, occurences.getOrDefault(c, 0) + 1);
            }

            return part1 ? occurences.values().stream().mapToInt(Integer::intValue).max().orElse(0) >= 2 : occurences.containsValue(2);
        }).count());
    }
}
