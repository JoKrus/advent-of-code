package de.djgames.jonas.adventofcode.aoc2019;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.djgames.jonas.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Day2019_14_Broke extends Day {
    @Override
    public String part1Logic() {


        input = "10 ORE => 10 A\n" +
                "1 ORE => 1 B\n" +
                "7 A, 1 B => 1 C\n" +
                "7 A, 1 C => 1 D\n" +
                "7 A, 1 D => 1 E\n" +
                "7 A, 1 E => 1 FUEL";

        //reactions is unused
        List<Reaction> reactions = new ArrayList<>();

        HashBiMap<List<Pair<Integer, String>>, Pair<Integer, String>> reactionMap = HashBiMap.create();
        HashBiMap<Pair<List<Pair<Integer, String>>, Integer>, String> reactionMapPossible = HashBiMap.create();

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

            reactions.add(new Reaction(ingredients, result));
            reactionMap.put(ingredients, result);
            reactionMapPossible.put(
                    Pair.of(ingredients, result.getLeft()),
                    result.getRight());
        }

        BiMap<Pair<Integer, String>, List<Pair<Integer, String>>> inverseMap = reactionMap.inverse();
        BiMap<String, Pair<List<Pair<Integer, String>>, Integer>> inversePossible = reactionMapPossible.inverse();

        Map<String, Integer> needed = new LinkedHashMap<>();

        Map<String, Integer> beforeOre = new LinkedHashMap<>();

        List<String> reserve = new ArrayList<>();

        inverseMap.get(Pair.of(1, "FUEL")).forEach(integerStringPair -> needed.put(integerStringPair.getRight(), integerStringPair.getLeft()));

        while (!needed.isEmpty()) {
            Map<String, Integer> additional = new LinkedHashMap<>();
            List<String> remove = new ArrayList<>();
            for (String key : needed.keySet()) {
                Pair<List<Pair<Integer, String>>, Integer> reactionPoss = inversePossible.get(key);

                int neededTimes = needed.get(key);

                int amountOfKeyInReserve = Collections.frequency(reserve, key);
                int toProduce = amountOfKeyInReserve - neededTimes;
                if (toProduce < 0) {
                    while (reserve.contains(key)) {
                        reserve.remove(key);
                    }
                } else {
                    for (int i = 0; i < toProduce; ++i) {
                        reserve.remove(key);
                    }
                    toProduce = 0;
                }

                //so oft muss produziert werden
                toProduce = Math.abs(toProduce);
                int mult = (int) Math.ceil(1. * toProduce / reactionPoss.getRight());

                int leftover = toProduce % reactionPoss.getRight();

                for (var list : reactionPoss.getLeft()) {
                    if (list.getRight().equals("ORE")) {
                        //in before ore
                        beforeOre.put(key, beforeOre.getOrDefault(key, 0) + neededTimes);
                        //ist wie break, weil nur ein parameter
                    } else {
                        additional.put(list.getRight(), additional.getOrDefault(list.getRight(), 0) + list.getLeft() * mult);
                    }
                }

                for (int i = 0; i < leftover; i++) {
                    reserve.add(key);
                }

                remove.add(key);
            }

            for (var s : remove) {
                needed.remove(s);
            }

            for (String key : additional.keySet()) {
                needed.put(key, needed.getOrDefault(key, 0) + additional.get(key));
            }
        }

        for (var entry : beforeOre.entrySet()) {
            //  wie viele man erhält, bei reaktion im 2 bsp, bei b 3
            int factor = inversePossible.getOrDefault(entry.getKey(), Pair.of(Collections.emptyList(), -1)).getRight();
            if (factor == -1) continue;
            int old = entry.getValue();
            int i = factor * (entry.getValue() / factor);
            if (old != i)
                i = (factor * (1 + (entry.getValue() / factor)));
            entry.setValue(i);
        }

        return String.valueOf(
                beforeOre.keySet().stream().mapToInt(key -> inversePossible.get(key).getLeft().get(0).getLeft() * (beforeOre.get(key) / inversePossible.get(key).getRight())).sum());
    }

    @Override
    public String part2Logic() {
        return null;
    }

    private static class Reaction {
        List<Pair<Integer, String>> ingredients;
        Pair<Integer, String> result;

        public Reaction(List<Pair<Integer, String>> ingredients, Pair<Integer, String> result) {
            this.ingredients = ingredients;
            this.result = result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Reaction reaction = (Reaction) o;
            return Objects.equals(ingredients, reaction.ingredients) &&
                    Objects.equals(result, reaction.result);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ingredients, result);
        }
    }

}
