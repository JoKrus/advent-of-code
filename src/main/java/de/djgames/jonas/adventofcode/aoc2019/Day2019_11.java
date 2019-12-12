package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;
import de.djgames.jonas.adventofcode.misc.Opcoder;

import java.util.*;
import java.util.stream.Collectors;

import static de.djgames.jonas.adventofcode.aoc2019.Day2019_03.Pos;
import static de.djgames.jonas.adventofcode.aoc2019.Day2019_08.rowToString;

public class Day2019_11 extends Day {
    @Override
    public String part1Logic() {
        return exec(true);
    }

    private String exec(boolean part1) {
        HashMap<Pos, Integer> colorOnPos = new HashMap<>();
        Pos aktPosition = new Pos(0, 0);
        //care while drawing to invert Y
        int diffx = 0, diffy = 1;

        long[] baseArray = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
        Opcoder opcoder = new Opcoder(baseArray);

        while (true) {
            int colorForInput = colorOnPos.getOrDefault(aktPosition, part1 ? 0 : 1);
            opcoder.addInput(colorForInput);
            opcoder.runOpcode();
            int colorToPaint = Objects.requireNonNull(opcoder.getOutput().poll()).intValue();
            int newDir = Objects.requireNonNull(opcoder.getOutput().poll()).intValue();
            if (opcoder.getHaltType() != Opcoder.HaltTypes.EXIT_WAIT) {
                break;
            }
            colorOnPos.put(aktPosition, colorToPaint);

            if (newDir == 0) {
                int tmp = diffx;
                diffx = -diffy;
                diffy = tmp;
            } else if (newDir == 1) {
                int tmp = -diffx;
                diffx = diffy;
                diffy = tmp;
            } else throw new RuntimeException("NA Parser");

            aktPosition = new Pos(aktPosition.getX() + diffx, aktPosition.getY() + diffy);
        }

        if (part1) return String.format("%d", colorOnPos.keySet().size());

        List<Pos> afterX = colorOnPos.keySet().stream().sorted(Comparator.comparingInt(Pos::getX)).collect(Collectors.toList());
        List<Pos> afterY = colorOnPos.keySet().stream().sorted(Comparator.comparingInt(Pos::getY)).collect(Collectors.toList());

        int minx = afterX.get(0).getX(), maxx = afterX.get(afterX.size() - 1).getX(), miny = afterY.get(0).getY(), maxy = afterY.get(afterY.size() - 1).getY();

        int[][] layer = new int[maxy - miny + 1][maxx - minx + 1];
        for (Pos pos : colorOnPos.keySet()) {
            layer[pos.getY() - miny][pos.getX() - minx] = colorOnPos.get(pos);
        }

        StringBuilder ret = new StringBuilder();

        for (int[] ints : layer) {
            ret.insert(0, rowToString(ints));
            ret.insert(0, System.lineSeparator());
        }

        return ret.toString();
    }

    @Override
    public String part2Logic() {
        return exec(false);
    }
}
