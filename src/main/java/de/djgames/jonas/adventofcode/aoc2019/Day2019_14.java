package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2019_14 extends Day {
    @Override
    public String part1Logic() {
        Map<String, Pair<List<Pair<Integer, String>>, Integer>> reactionPossible = new HashMap<>();

        //Parse Input
        String[] reactionsString = input.split("\n");
        for (String reactionString : reactionsString) {
            List<Pair<Integer, String>> ingredients = new ArrayList<>();

            Pair<Integer, String> result;
            String[] split = reactionString.split("=>");
            String[] ingredientString = split[0].split(",");
            for (String inString : ingredientString) {
                inString = inString.trim();
                String[] ininString = inString.split(" ");
                ingredients.add(Pair.of(Integer.parseInt(ininString[0]), ininString[1]));
            }
            split[1] = split[1].trim();
            String[] ininString = split[1].split(" ");
            result = Pair.of(Integer.parseInt(ininString[0]), ininString[1]);

            reactionPossible.put(result.getRight(), Pair.of(ingredients, result.getLeft()));
        }


        return null;
    }

    @Override
    public String part2Logic() {
        return null;
    }
}
