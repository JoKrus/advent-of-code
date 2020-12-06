package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day2020_06 extends Day {
    @Override
    public String part1Logic() {
        // split \n\n for a stream of each group
        // remove \n with split and then join on ""
        // split on "" to get every letter
        // collect everything in a set per group
        // map set sizes and sum
        var sum = Arrays.stream(input.split("\n\n")).map(s ->
                Arrays.stream(StringUtils.join(s.split("\n"), "").split(""))
                        .collect(Collectors.toSet()))
                .mapToInt(Set::size).sum();
        return String.format("%d", sum);
    }

    @Override
    public String part2Logic() {
        // split \n\n for a stream of each group
        // all answers in a unique set per group
        // every unique answer by a person in an array
        // for every group, return count of each answer in allAnswers that is part of every answer in uniqueAnswerers
        //map this count to long and sum
        var sum = Arrays.stream(input.split("\n\n")).map(s -> {
            var allAnswers = Arrays.stream(StringUtils.join(s.split("\n"), "").split(""))
                    .collect(Collectors.toSet());
            String[] uniqueAnswerers = s.split("\n");

            return allAnswers.stream().filter(s1 -> {
                boolean isValid = true;
                for (var answer : uniqueAnswerers) {
                    isValid &= answer.contains(s1);
                }
                return isValid;
            }).count();
        }).mapToLong(Long::longValue).sum();
        return String.format("%d", sum);
    }
}
