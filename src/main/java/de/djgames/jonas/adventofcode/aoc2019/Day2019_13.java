package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;
import de.djgames.jonas.adventofcode.misc.Opcoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;

import static de.djgames.jonas.adventofcode.aoc2019.Day2019_03.Pos;


public class Day2019_13 extends Day {
    @Override
    public String part1Logic() {
        Opcoder opcoder = new Opcoder(input);
        opcoder.runOpcode();
        if (opcoder.getHaltType() != Opcoder.HaltTypes.EXIT99)
            throw new RuntimeException("AAAAAAH");
        int ret = 0;
        Queue<Long> queue = opcoder.getOutput();
        while (!queue.isEmpty()) {
            queue.poll();
            queue.poll();
            int id = Objects.requireNonNull(queue.poll()).intValue();
            if (id == 2) ret++;
        }

        return String.format("%d", ret);
    }

    @Override
    public String part2Logic() {
        HashMap<Pos, Pos> opSet = new HashMap<>();
        long[] baseArray = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
        baseArray[0] = 2;
        Opcoder opcoder = new Opcoder(baseArray);


        Queue<Long> queue = opcoder.getOutput();
        while (!queue.isEmpty()) {
            int x = queue.poll().intValue(), y = Objects.requireNonNull(queue.poll()).intValue(), id = Objects.requireNonNull(queue.poll()).intValue();
            Pos p = new Pos(x, y, id);
            opSet.put(p, p);
        }

        return String.format("%d", opSet.keySet().stream().filter(pos -> pos.getId() == 2).count());
    }
}
