package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day2021_14 extends Day {
    @Override
    public String part1Logic() {
        String[] split = input.split("\n\n");
        String polymer = split[0];
        String polyInstr = split[1];

        HashMap<String, String> polyInstrMap = new HashMap<>();
        Arrays.stream(polyInstr.split("\n")).forEach(s -> {
            String[] split1 = s.split(" -> ");
            polyInstrMap.put(split1[0], split1[1]);
        });

        Map<String, Long> currentOccurrences =
                Arrays.stream(polymer.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> currentPairs = new HashMap<>();
        for (int i = 0; i < polymer.length() - 1; i++) {
            String subSequence = polymer.substring(i, i + 2);
            currentPairs.put(subSequence, currentPairs.getOrDefault(subSequence, 0L) + 1);
        }

        for (int i = 0; i < 10; i++) {
            polyIteration2(currentOccurrences, currentPairs, polyInstrMap);
        }

        var ret = currentOccurrences.values().stream().mapToLong(Long::longValue).max().getAsLong() -
                currentOccurrences.values().stream().mapToLong(Long::longValue).min().getAsLong();
        return "%d".formatted(ret);
    }

    private String polyIteration(String polymer, HashMap<String, String> polyInstrMap) {
        StringBuilder sb = new StringBuilder(polymer);
        for (int i = 0; i < polymer.length() - 1; i++) {
            String sub = polymer.substring(i, i + 2);
            String insert = polyInstrMap.get(sub);
            sb.insert(2 * i + 1, insert);
        }
        return sb.toString();
    }

    @Override
    public String part2Logic() {
        String[] split = input.split("\n\n");
        String polymer = split[0];
        String polyInstr = split[1];

        HashMap<String, String> polyInstrMap = new HashMap<>();
        Arrays.stream(polyInstr.split("\n")).forEach(s -> {
            String[] split1 = s.split(" -> ");
            polyInstrMap.put(split1[0], split1[1]);
        });

        Map<String, Long> currentOccurrences =
                Arrays.stream(polymer.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> currentPairs = new HashMap<>();
        for (int i = 0; i < polymer.length() - 1; i++) {
            String subSequence = polymer.substring(i, i + 2);
            //increment by one or set to one
            currentPairs.merge(subSequence, 1L, Long::sum);
        }

        for (int i = 0; i < 40; i++) {
            polyIteration2(currentOccurrences, currentPairs, polyInstrMap);
        }

        var ret = currentOccurrences.values().stream().mapToLong(Long::longValue).max().getAsLong() -
                currentOccurrences.values().stream().mapToLong(Long::longValue).min().getAsLong();
        return "%d".formatted(ret);
    }

    private void polyIteration2(Map<String, Long> currentOccurrences, Map<String, Long> currentPairs, HashMap<String, String> polyInstrMap) {
        HashMap<String, Long> currentPairsCopy = new HashMap<>(currentPairs);
        for (var entryPair : currentPairsCopy.entrySet()) {
            var chars = entryPair.getKey().split("");
            var result = polyInstrMap.get(entryPair.getKey());
            currentOccurrences.merge(result, entryPair.getValue(), Long::sum);
            currentPairs.merge(entryPair.getKey(), -entryPair.getValue(), Long::sum);
            currentPairs.merge(chars[0] + result, entryPair.getValue(), Long::sum);
            currentPairs.merge(result + chars[1], entryPair.getValue(), Long::sum);
        }
    }
}
