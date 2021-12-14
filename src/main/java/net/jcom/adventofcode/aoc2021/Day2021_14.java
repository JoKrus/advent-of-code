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

        Map<String, Long> currentOccurences =
                Arrays.stream(polymer.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> currentPairs = new HashMap<>();
        for (int i = 0; i < polymer.length() - 1; i++) {
            String subSequence = polymer.substring(i, i + 2);
            currentPairs.put(subSequence, currentPairs.getOrDefault(subSequence, 0L) + 1);
        }

        for (int i = 0; i < 10; i++) {
            polyInteration2(currentOccurences, currentPairs, polyInstrMap);
        }

        var ret = currentOccurences.values().stream().mapToLong(Long::longValue).max().getAsLong() -
                currentOccurences.values().stream().mapToLong(Long::longValue).min().getAsLong();
        return "%d".formatted(ret);
    }

    private String polyInteration(String polymer, HashMap<String, String> polyInstrMap) {
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

        Map<String, Long> currentOccurences =
                Arrays.stream(polymer.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> currentPairs = new HashMap<>();
        for (int i = 0; i < polymer.length() - 1; i++) {
            String subSequence = polymer.substring(i, i + 2);
            currentPairs.put(subSequence, currentPairs.getOrDefault(subSequence, 0L) + 1);
        }

        for (int i = 0; i < 40; i++) {
            polyInteration2(currentOccurences, currentPairs, polyInstrMap);
        }

        var ret = currentOccurences.values().stream().mapToLong(Long::longValue).max().getAsLong() -
                currentOccurences.values().stream().mapToLong(Long::longValue).min().getAsLong();
        return "%d".formatted(ret);
    }

    private void polyInteration2(Map<String, Long> currentOccurences, Map<String, Long> currentPairs, HashMap<String, String> polyInstrMap) {
        HashMap<String, Long> currentPairsCopy = new HashMap<>(currentPairs);
        for (var entryPair : currentPairsCopy.entrySet()) {
            var chars = entryPair.getKey().split("");
            var result = polyInstrMap.get(entryPair.getKey());
            currentOccurences.put(result, currentOccurences.getOrDefault(result, 0L) + entryPair.getValue());
            currentPairs.put(entryPair.getKey(), Math.max(0,
                    currentPairs.get(entryPair.getKey()) - entryPair.getValue()));
            currentPairs.put(chars[0] + result, currentPairs.getOrDefault(chars[0] + result, 0L) + entryPair.getValue());
            currentPairs.put(result + chars[1], currentPairs.getOrDefault(result + chars[1], 0L) + entryPair.getValue());
        }
    }
}
