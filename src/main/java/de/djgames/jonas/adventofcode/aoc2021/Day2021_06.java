package de.djgames.jonas.adventofcode.aoc2021;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;
import java.util.HashMap;

public class Day2021_06 extends Day {
    @Override
    public String part1Logic() {
        HashMap<Integer, Long> lanternFishes = new HashMap<>();

        HashMap<Integer, Long> finalLanternFishes = lanternFishes;
        Arrays.stream(input.split(",")).forEach(s -> {
            int day = Integer.parseInt(s);
            finalLanternFishes.put(day, finalLanternFishes.getOrDefault(day, 0L) + 1);
        });

        for (int i = 0; i < 80; i++) {
            lanternFishes = proceedDay(lanternFishes);
        }

        long sum = lanternFishes.values().stream().mapToLong(l -> l).sum();
        return String.format("%d", sum);
    }

    private HashMap<Integer, Long> proceedDay(HashMap<Integer, Long> lanternFishes) {
        HashMap<Integer, Long> newLanternFishes = new HashMap<>();
        for (int i = 1; i <= 8; i++) {
            newLanternFishes.put(i - 1, lanternFishes.getOrDefault(i, 0L));
        }
        Long births = lanternFishes.getOrDefault(0, 0L);
        newLanternFishes.put(6, newLanternFishes.getOrDefault(6, 0L) + births);
        newLanternFishes.put(8, births);
        return newLanternFishes;
    }

    @Override
    public String part2Logic() {
        HashMap<Integer, Long> lanternFishes = new HashMap<>();

        HashMap<Integer, Long> finalLanternFishes = lanternFishes;
        Arrays.stream(input.split(",")).forEach(s -> {
            int day = Integer.parseInt(s);
            finalLanternFishes.put(day, finalLanternFishes.getOrDefault(day, 0L) + 1);
        });

        for (int i = 0; i < 256; i++) {
            lanternFishes = proceedDay(lanternFishes);
        }

        long sum = lanternFishes.values().stream().mapToLong(l -> l).sum();
        return String.format("%d", sum);
    }
}
