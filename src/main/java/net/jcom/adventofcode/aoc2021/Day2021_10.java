package net.jcom.adventofcode.aoc2021;

import com.google.common.collect.HashBiMap;
import net.jcom.adventofcode.Day;

import java.util.*;

public class Day2021_10 extends Day {
    @Override
    public String part1Logic() {
        var split = Arrays.stream(input.split("\n")).toList();

        int totalScore = 0;
        for (var s : split) {
            totalScore += checkCorruptedChunks(s);
        }

        return "%d".formatted(totalScore);
    }

    private int checkCorruptedChunks(String s) {
        HashBiMap<String, String> matchingBrackets = HashBiMap.create(4);
        matchingBrackets.put("(", ")");
        matchingBrackets.put("[", "]");
        matchingBrackets.put("{", "}");
        matchingBrackets.put("<", ">");

        HashMap<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put(")", 3);
        scoreMap.put("]", 57);
        scoreMap.put("}", 1197);
        scoreMap.put(">", 25137);

        Stack<String> bracketStack = new Stack<>();

        for (var character : s.split("")) {
            if (isOpeningChar(character)) {
                bracketStack.push(character);
            } else {
                var potentialChar = matchingBrackets.inverse().get(character);
                var realChar = bracketStack.peek();
                if (realChar.equals(potentialChar)) {
                    bracketStack.pop();
                } else {
                    return scoreMap.get(character);
                }
            }
        }

        return 0;
    }

    private boolean isOpeningChar(String character) {
        return "([{<".contains(character);
    }

    @Override
    public String part2Logic() {
        var split = Arrays.stream(input.split("\n")).toList();
        //only incompleted ones
        split = new ArrayList<>(split);
        split.removeIf(s -> checkCorruptedChunks(s) != 0);

        List<Long> totalScores = new ArrayList<>();

        for (var s : split) {
            totalScores.add(completeChunks(s));
        }

        Collections.sort(totalScores);

        return "%d".formatted(totalScores.get(totalScores.size() / 2));
    }

    private long completeChunks(String s) {
        HashBiMap<String, String> matchingBrackets = HashBiMap.create(4);
        matchingBrackets.put("(", ")");
        matchingBrackets.put("[", "]");
        matchingBrackets.put("{", "}");
        matchingBrackets.put("<", ">");

        HashMap<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put(")", 1);
        scoreMap.put("]", 2);
        scoreMap.put("}", 3);
        scoreMap.put(">", 4);

        Stack<String> bracketStack = new Stack<>();

        for (var character : s.split("")) {
            if (isOpeningChar(character)) {
                bracketStack.push(character);
            } else {
                var potentialChar = matchingBrackets.inverse().get(character);
                var realChar = bracketStack.peek();
                if (realChar.equals(potentialChar)) {
                    bracketStack.pop();
                } else {
                    return scoreMap.get(character);
                }
            }
        }

        long score = 0;
        while (!bracketStack.empty()) {
            score *= 5;
            var leftOver = bracketStack.pop();
            score += scoreMap.get(matchingBrackets.get(leftOver));
        }

        return score;
    }
}
