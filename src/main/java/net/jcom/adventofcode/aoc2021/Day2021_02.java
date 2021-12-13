package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class Day2021_02 extends Day {
    @Override
    public String part1Logic() {
        AtomicLong horizontal = new AtomicLong();
        AtomicLong depth = new AtomicLong();

        Arrays.stream(input.split("\n")).forEach(s -> {
            var split = s.split(" ");
            var x = Long.parseLong(split[1]);
            switch (split[0]) {
                case "forward" -> horizontal.addAndGet(x);
                case "down" -> depth.addAndGet(x);
                case "up" -> depth.addAndGet(-x);
            }
        });

        return String.format("%d", horizontal.get() * depth.get());
    }

    @Override
    public String part2Logic() {
        AtomicLong horizontal = new AtomicLong();
        AtomicLong depth = new AtomicLong();
        AtomicLong aim = new AtomicLong();

        Arrays.stream(input.split("\n")).forEach(s -> {
            var split = s.split(" ");
            var x = Long.parseLong(split[1]);
            switch (split[0]) {
                case "forward" -> {
                    horizontal.addAndGet(x);
                    depth.addAndGet(aim.get() * x);
                }
                case "down" -> aim.addAndGet(x);
                case "up" -> aim.addAndGet(-x);
            }
        });

        return String.format("%d", horizontal.get() * depth.get());
    }
}
