package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Day2021_11 extends Day {
    @Override
    public String part1Logic() {
        String[] split = input.split("\n");

        Map<Point, Integer> energy = new HashMap<>();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] split1 = s.split("");
            for (int j = 0; j < split1.length; j++) {
                String s1 = split1[j];
                energy.put(new Point(i, j), Integer.parseInt(s1));
            }
        }

        int totalFlashes = 0;
        for (int i = 0; i < 100; i++) {
            //perform step
            //increase all by 1
            Map<Point, Integer> energyNext = new HashMap<>(energy);
            energyNext.entrySet().forEach(pointIntegerEntry -> pointIntegerEntry.setValue(pointIntegerEntry.getValue() + 1));

            Set<Point> alreadyFlashed = new HashSet<>();
            Set<Point> flashedThisCheck = new HashSet<>();
            do {
                flashedThisCheck.clear();
                for (var entry :
                        energyNext.entrySet()) {
                    if (entry.getValue() > 9 && !alreadyFlashed.contains(entry.getKey())) {
                        flashedThisCheck.add(entry.getKey());
                        increaseAllNeighbors(entry, energyNext);
                    }
                }

                alreadyFlashed.forEach(point -> energyNext.put(point, 0));
                alreadyFlashed.addAll(flashedThisCheck);
                energy = energyNext;
            } while (!flashedThisCheck.isEmpty());

            totalFlashes += alreadyFlashed.size();
            alreadyFlashed.clear();
        }


        return "%d".formatted(totalFlashes);
    }

    private void increaseAllNeighbors(Map.Entry<Point, Integer> entry, Map<Point, Integer> energyNext) {
        List<Integer> dx = List.of(1, 1, 1, 0, 0, -1, -1, -1);
        List<Integer> dy = List.of(1, 0, -1, 1, -1, 1, 0, -1);
        for (int i = 0; i < dx.size(); i++) {
            var pp = new Point(entry.getKey().x + dx.get(i), entry.getKey().y + dy.get(i));
            if (energyNext.containsKey(pp)) {
                energyNext.put(pp, energyNext.get(pp) + 1);
            }
        }
    }

    public String mapToString(Map<Point, Integer> energy, int min, int max) {
        StringBuilder sb = new StringBuilder();
        for (int i = min; i <= max; i++) {
            for (int j = min; j <= max; j++) {
                sb.append(energy.get(new Point(i, j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String part2Logic() {
        String[] split = input.split("\n");

        Map<Point, Integer> energy = new HashMap<>();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] split1 = s.split("");
            for (int j = 0; j < split1.length; j++) {
                String s1 = split1[j];
                energy.put(new Point(i, j), Integer.parseInt(s1));
            }
        }
        int i = 0;
        while (true) {
            //perform step
            //increase all by 1
            Map<Point, Integer> energyNext = new HashMap<>(energy);
            energyNext.entrySet().forEach(pointIntegerEntry -> pointIntegerEntry.setValue(pointIntegerEntry.getValue() + 1));

            Set<Point> alreadyFlashed = new HashSet<>();
            Set<Point> flashedThisCheck = new HashSet<>();
            do {
                flashedThisCheck.clear();
                for (var entry :
                        energyNext.entrySet()) {
                    if (entry.getValue() > 9 && !alreadyFlashed.contains(entry.getKey())) {
                        flashedThisCheck.add(entry.getKey());
                        increaseAllNeighbors(entry, energyNext);
                    }
                }

                alreadyFlashed.forEach(point -> energyNext.put(point, 0));
                alreadyFlashed.addAll(flashedThisCheck);
                energy = energyNext;
            } while (!flashedThisCheck.isEmpty());

            i++;
            if (alreadyFlashed.size() == 100) {
                break;
            }
        }


        return "%d".formatted(i);
    }
}
