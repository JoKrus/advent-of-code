package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;

import java.util.HashMap;
import java.util.HashSet;

public class Day2022_06 extends Day {
    @Override
    public String part1Logic() {
        return getSequenceStart(4);
    }

    @Override
    public String part2Logic() {
        return getSequenceStart(14);
    }

    private String getSequenceStart(int x) {
        String myInput = input.split("\n")[0];

        HashMap<Integer, String> lastStuff = new HashMap<>();

        int res = 0;

        for (int i = 0; i < myInput.length(); ++i) {
            if (lastStuff.size() == x) {
                if (new HashSet<>(lastStuff.values()).size() == x) {
                    res = i;
                    break;
                } else {
                    lastStuff.remove(i - x);
                }
            }
            var s = myInput.substring(i, i + 1);
            lastStuff.put(i, s);
        }

        return "" + res;
    }
}
