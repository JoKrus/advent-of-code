package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;

public class Day2022_09 extends Day {
    @Override
    public String part1Logic() {
        return getResult(2);
    }

    private String getResult(int n) {
        var instrs = input.split("\n");

        Pair<Integer, Integer>[] rope = new Pair[n];
        for (int i = 0; i < rope.length; i++) {
            rope[i] = Pair.of(0, 0);
        }

        HashSet<Pair<Integer, Integer>> visitedTail = new HashSet<>();
        visitedTail.add(rope[rope.length - 1]);

        for (var instr : instrs) {
            var movement = instr.split(" ");

            Pair<Integer, Integer> dir = switch (movement[0]) {
                case "L" -> Pair.of(-1, 0);
                case "R" -> Pair.of(1, 0);
                case "D" -> Pair.of(0, -1);
                case "U" -> Pair.of(0, 1);
                default -> Pair.of(0, 0);
            };
            int amount = Integer.parseInt(movement[1]);

            for (int i = 1; i <= amount; i++) {
                rope[0] = Pair.of(rope[0].getLeft() + dir.getLeft(), rope[0].getRight() + dir.getRight());
                for (int j = 0; j < rope.length - 1; j++) {
                    var head = rope[j];
                    var tail = rope[j + 1];

                    rope[j + 1] = adjustTail(head, tail);

                    if (j + 1 == rope.length - 1) {
                        visitedTail.add(rope[j + 1]);
                    }
                }
            }
        }

        return "" + visitedTail.size();
    }

    private Pair<Integer, Integer> adjustTail(Pair<Integer, Integer> head, Pair<Integer, Integer> tail) {
        int dx = Math.abs(head.getLeft() - tail.getLeft());
        int dy = Math.abs(head.getRight() - tail.getRight());

        if (dx <= 1 && dy <= 1) {
            return tail;
        } else if (dx >= 2 && dy == 0) {
            return Pair.of(tail.getLeft() + Integer.signum(head.getLeft() - tail.getLeft()), tail.getRight());
        } else if (dy >= 2 && dx == 0) {
            return Pair.of(tail.getLeft(), tail.getRight() + Integer.signum(head.getRight() - tail.getRight()));
        } else {
            return Pair.of(tail.getLeft() + Integer.signum(head.getLeft() - tail.getLeft()),
                    tail.getRight() + Integer.signum(head.getRight() - tail.getRight()));
        }
    }

    @Override
    public String part2Logic() {
        return getResult(10);
    }
}
