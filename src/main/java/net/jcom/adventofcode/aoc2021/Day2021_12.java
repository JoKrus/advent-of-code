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

        ArrayList<String> myWays = new ArrayList<>();
        //need to change to bfs with a switch if this should be the important small cave
        recursiveMode2("start", canGoFromTo, new ArrayList<>(List.of("start")), myWays);

        return "%d".formatted(myWays.size());
    }

    private void recursiveMode2(String currentNode, Map<String, List<String>> canGoFromTo, ArrayList<String> currentWay,
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
            recursiveMode2(nextPossNode, canGoFromTo, newArr, bucket);
        }
    }
}
