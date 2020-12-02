package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class Day2020_02 extends Day {
    @Override
    public String part1Logic() {
        return "" + Arrays.stream(input.split("\n")).map(s -> {
            String[] parts = s.split(" ");
            String[] nums = parts[0].split("-");
            parts[1] = parts[1].substring(0, parts[1].length() - 1);
            return new Container(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), parts[1], parts[2]);
        }).filter(container -> {
            int amount = StringUtils.countMatches(container.word, container.letter);
            return container.min <= amount && container.max >= amount;
        }).count();
    }

    @Override
    public String part2Logic() {
        return "" + Arrays.stream(input.split("\n")).map(s -> {
            String[] parts = s.split(" ");
            String[] nums = parts[0].split("-");
            parts[1] = parts[1].substring(0, parts[1].length() - 1);
            return new Container(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), parts[1], parts[2]);
        }).filter(container -> {
            return
                    //First one contains
                    (container.word.substring(container.min - 1, container.min).equals(container.letter) &&
                            !container.word.substring(container.max - 1, container.max).equals(container.letter)) ||
                            //Second one contains
                            (!container.word.substring(container.min - 1, container.min).equals(container.letter) &&
                                    container.word.substring(container.max - 1, container.max).equals(container.letter));
        }).count();
    }

    private static class Container {
        int min, max;
        String letter;
        String word;

        public Container(int min, int max, String letter, String word) {
            this.min = min;
            this.max = max;
            this.letter = letter;
            this.word = word;
        }
    }
}
