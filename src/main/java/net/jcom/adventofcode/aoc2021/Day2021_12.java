package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.util.*;

public class Day2021_12 extends Day {
    @Override
    public String part1Logic() {
        Map<String, List<String>> canGoFromTo = new HashMap<>();
        Arrays.stream(input.split("\n")).forEach(s -> {
            var splits = s.split("-");
            //link 1
            var lis = canGoFromTo.getOrDefault(splits[0], new ArrayList<>());
            lis.add(splits[1]);
            canGoFromTo.put(splits[0], lis);

            var lis2 = canGoFromTo.getOrDefault(splits[1], new ArrayList<>());
            lis2.add(splits[0]);
            canGoFromTo.put(splits[1], lis2);
        });

        ArrayList<String> myWays = new ArrayList<>();
        recursiveMode("start", canGoFromTo, new ArrayList<>(List.of("start")), myWays);

        return "%d".formatted(myWays.size());
    }

    private void recursiveMode(String currentNode, Map<String, List<String>> canGoFromTo, ArrayList<String> currentWay,
                               ArrayList<String> bucket) {
        if (currentNode.equals("end")) {
            bucket.add(String.join(",", currentWay));
            return;
        }

        for (var nextPossNode : canGoFromTo.getOrDefault(currentNode, new ArrayList<>())) {
            if (Character.isLowerCase(nextPossNode.charAt(0)) && currentWay.contains(nextPossNode)) {
                continue;
            }
            var newArr = new ArrayList<>(currentWay);
            newArr.add(nextPossNode);
            recursiveMode(nextPossNode, canGoFromTo, newArr, bucket);
        }
    }


    @Override
    public String part2Logic() {
        Map<String, List<String>> canGoFromTo = new HashMap<>();
        Arrays.stream(input.split("\n")).forEach(s -> {
            var splits = s.split("-");
            //link 1
            var lis = canGoFromTo.getOrDefault(splits[0], new ArrayList<>());
            lis.add(splits[1]);
            canGoFromTo.put(splits[0], lis);

            var lis2 = canGoFromTo.getOrDefault(splits[1], new ArrayList<>());
            lis2.add(splits[0]);
            canGoFromTo.put(splits[1], lis2);
        });

        List<String> bucket = new ArrayList<>();
        //need to change to bfs with a switch if this should be the important small cave
        for (String importantCave :
                canGoFromTo.keySet().stream().filter(s -> Character.isLowerCase(s.charAt(0))).filter(s -> !s.equals(
                        "start") && !s.equals("end")).toList()) {
            recursiveMode2("start", importantCave, 0, canGoFromTo, new ArrayList<>(List.of("start")), bucket);
        }

        bucket = bucket.stream().distinct().toList();

        return "%d".formatted(bucket.size());
    }

    private void recursiveMode2(String currentNode, String importantSmallCave, int importantVisits,
                                Map<String, List<String>> canGoFromTo,
                                ArrayList<String> currentWay,
                                List<String> bucket) {
        if (currentNode.equals("end")) {
            bucket.add(String.join(",", currentWay));
            return;
        }

        for (var nextPossNode : canGoFromTo.getOrDefault(currentNode, new ArrayList<>())) {
            if (!nextPossNode.equals(importantSmallCave)) {
                if (Character.isLowerCase(nextPossNode.charAt(0)) && currentWay.contains(nextPossNode)) {
                    continue;
                }
                var newArr = new ArrayList<>(currentWay);
                newArr.add(nextPossNode);
                recursiveMode2(nextPossNode, importantSmallCave, importantVisits, canGoFromTo, newArr, bucket);
            } else {
                if (importantVisits < 2) {
                    var newArr = new ArrayList<>(currentWay);
                    newArr.add(nextPossNode);
                    recursiveMode2(nextPossNode, importantSmallCave, importantVisits + 1, canGoFromTo, newArr, bucket);
                }
            }
        }
    }
}
