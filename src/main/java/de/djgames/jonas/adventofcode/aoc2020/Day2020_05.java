package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day2020_05 extends Day {
    @Override
    public String part1Logic() {
        return String.format("%d", idStream().max().getAsInt());
    }

    private IntStream idStream() {
        return Arrays.stream(input.split("\n")).map(new SeatToID()).mapToInt(Integer::intValue);
    }

    @Override
    public String part2Logic() {
        var idList = Arrays.stream(idStream().toArray()).boxed().collect(Collectors.toList());

        for (int i = idStream().min().getAsInt() + 1; i < idStream().max().getAsInt() - 1; ++i) {
            if (idList.contains(i - 1) && idList.contains(i + 1) && !idList.contains(i)) {
                return String.format("%d", i);
            }
        }
        return null;
    }

    private static class SeatToID implements Function<String, Integer> {
        @Override
        public Integer apply(String s) {
            String row, col;
            row = s.substring(0, 7);
            col = s.substring(7);

            int rowI = 0, colI = 0;
            for (int i = 0; i < row.length(); i++) {
                int multi = 0;
                if (row.charAt(6 - i) == 'B')
                    multi = 1;
                rowI += multi * (multi << i);
            }

            for (int i = 0; i < col.length(); i++) {
                int multi = 0;
                if (col.charAt(2 - i) == 'R')
                    multi = 1;
                colI += multi * (multi << i);
            }

            return rowI * 8 + colI;
        }
    }
}
