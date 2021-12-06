package net.jcom.adventofcode.aoc2020;

import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day2020_02 extends Day {
    @Override
    public String part1Logic() {
        long count = getContainerStream().filter(container -> {
            int amount = StringUtils.countMatches(container.word, container.letter);
            return container.min <= amount && container.max >= amount;
        }).count();

        return String.format("%d", count);
    }

    private Stream<Container> getContainerStream() {
        return Arrays.stream(input.split("\n")).map(s -> {
            String[] parts = s.split(" ");
            String[] nums = parts[0].split("-");
            parts[1] = parts[1].substring(0, parts[1].length() - 1);
            return new Container(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), parts[1], parts[2]);
        });
    }

    @Override
    public String part2Logic() {
        long count = getContainerStream().filter(container -> {
            String at1 = container.word.substring(container.min - 1, container.min);
            String at2 = container.word.substring(container.max - 1, container.max);
            //First one contains
            return (at1.equals(container.letter) && !at2.equals(container.letter)) ||
                    //Second one contains
                    (!at1.equals(container.letter) && at2.equals(container.letter));
        }).count();

        return String.format("%d", count);
    }


    private static class Container {
        private final int min, max;
        private final String letter, word;

        public Container(int min, int max, String letter, String word) {
            this.min = min;
            this.max = max;
            this.letter = letter;
            this.word = word;
        }
    }
}
