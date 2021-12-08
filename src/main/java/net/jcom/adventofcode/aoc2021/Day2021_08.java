package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day2021_08 extends Day {
    @Override
    public String part1Logic() {
        long count = Arrays.stream(input.split("\n")).map(s -> s.split("\\|")[1]).flatMap(s -> Arrays.stream(s.split(
                        " ")))
                .filter(s -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7).count();

        return "%d".formatted(count);
    }

    @Override
    public String part2Logic() {
        return "%d".formatted(Arrays.stream(input.split("\n")).map(s -> new Wire(s)).mapToInt(Wire::getOutput).sum());
    }

    public static final class Wire {
        public static final List<Set<String>> numberToSegment = List.of(
                Set.of("a", "b", "c", "e", "f", "g"),
                Set.of("c", "f"),
                Set.of("a", "c", "d", "e", "g"),
                Set.of("a", "c", "d", "f", "g"),
                Set.of("b", "c", "d", "f"),
                Set.of("a", "b", "d", "f", "g"),
                Set.of("a", "b", "d", "e", "f", "g"),
                Set.of("a", "c", "f"),
                Set.of("a", "b", "c", "d", "e", "f", "g"),
                Set.of("a", "b", "c", "d", "f", "g")
        );

        private int output = 0;

        public Wire(String wireString) {
            String[] split = wireString.split("\\|");

            Set<String> displaysOne =
                    Arrays.stream(split[0].split(" ")).filter(s -> s.length() == 2).flatMap(s -> Arrays.stream(s.split(""))).collect(Collectors.toSet());

            Set<String> displaysFour =
                    Arrays.stream(split[0].split(" ")).filter(s -> s.length() == 4).flatMap(s -> Arrays.stream(s.split(""))).collect(Collectors.toSet());

            Map<String, Long> occurences = Arrays.stream(split[0].split(""))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            HashMap<String, String> wireToSegment = new HashMap<>();
            wireToSegment.put(occurences.entrySet().stream()
                    .filter(stringLongEntry -> stringLongEntry.getValue() == 6)
                    .map(Map.Entry::getKey).findFirst().get(), "b");
            wireToSegment.put(occurences.entrySet().stream()
                    .filter(stringLongEntry -> stringLongEntry.getValue() == 4)
                    .map(Map.Entry::getKey).findFirst().get(), "e");
            wireToSegment.put(occurences.entrySet().stream()
                    .filter(stringLongEntry -> stringLongEntry.getValue() == 9)
                    .map(Map.Entry::getKey).findFirst().get(), "f");

            String occuredEightTimesAndInOne = occurences.entrySet().stream()
                    .filter(stringLongEntry -> stringLongEntry.getValue() == 8)
                    .map(Map.Entry::getKey).filter(displaysOne::contains).findFirst().get();

            String occuredEightTimesAndNotInOne = occurences.entrySet().stream()
                    .filter(stringLongEntry -> stringLongEntry.getValue() == 8)
                    .map(Map.Entry::getKey).filter(s -> !displaysOne.contains(s)).findFirst().get();
            wireToSegment.put(occuredEightTimesAndInOne, "c");
            wireToSegment.put(occuredEightTimesAndNotInOne, "a");

            String occuredSevenTimesAndInFour = occurences.entrySet().stream()
                    .filter(stringLongEntry -> stringLongEntry.getValue() == 7)
                    .map(Map.Entry::getKey).filter(displaysFour::contains).findFirst().get();

            String occuredSevenTimesAndNotInFour = occurences.entrySet().stream()
                    .filter(stringLongEntry -> stringLongEntry.getValue() == 7)
                    .map(Map.Entry::getKey).filter(s -> !displaysFour.contains(s)).findFirst().get();
            wireToSegment.put(occuredSevenTimesAndInFour, "d");
            wireToSegment.put(occuredSevenTimesAndNotInFour, "g");

            split[1] = split[1].trim();
            int[] outputAsArray = Arrays.stream(split[1].split(" ")).map(s -> {
                String[] split1 = s.split("");
                for (int i = 0; i < split1.length; i++) {
                    split1[i] = wireToSegment.get(split1[i]);
                }
                return Set.of(split1);
            }).mapToInt(numberToSegment::indexOf).toArray();

            for (int i = 0; i < outputAsArray.length; ++i) {
                var a = Math.pow(10, i) * outputAsArray[outputAsArray.length - i - 1];
                output += a;
            }

        }

        public int getOutput() {
            return this.output;
        }
    }
}
