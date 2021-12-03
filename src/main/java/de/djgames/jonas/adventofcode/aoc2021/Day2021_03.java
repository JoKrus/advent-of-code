package de.djgames.jonas.adventofcode.aoc2021;

import de.djgames.jonas.adventofcode.Day;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day2021_03 extends Day {
    @Override
    public String part1Logic() {
        String[] split = input.split("\n");
        String[][] ultraSplit = generateUltra(split);

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();

        for (int j = 0; j < ultraSplit[0].length; j++) {
            int zeroes = 0, ones = 0;
            for (String[] strings : ultraSplit) {
                if (strings[j].charAt(0) == '1') {
                    ones++;
                } else {
                    zeroes++;
                }
            }
            if (zeroes == 500) throw new RuntimeException();
            String moreCommon = zeroes > ones ? "0" : "1";
            String lessCommon = ones >= zeroes ? "0" : "1";
            gamma.append(moreCommon);
            epsilon.append(lessCommon);
        }

        var gammaInt = new BigInteger(gamma.toString(), 2);
        var epsilonInt = new BigInteger(epsilon.toString(), 2);

        return String.format("%s", gammaInt.multiply(epsilonInt));
    }

    private String[][] generateUltra(String[] split) {
        String[][] ret = new String[split.length][];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = split[i].split("");
        }
        return ret;
    }

    @Override
    public String part2Logic() {
        var splitOxy = Arrays.stream(input.split("\n")).collect(Collectors.toList());
        var splitCo2 = Arrays.stream(input.split("\n")).collect(Collectors.toList());

        int i = 0;
        while (splitOxy.size() > 1) {
            int finalI = i;
            String firstVals =
                    splitOxy.stream().map(s -> String.format("%s", s.charAt(finalI))).collect(Collectors.joining());
            int ones = StringUtils.countMatches(firstVals, "1");
            int zeroes = StringUtils.countMatches(firstVals, "0");

            char toKeep = ones >= zeroes ? '0' : '1';

            splitOxy = splitOxy.stream().filter(s -> s.charAt(finalI) != toKeep).collect(Collectors.toList());

            i++;
        }

        i = 0;
        while (splitCo2.size() > 1) {
            int finalI = i;
            String firstVals =
                    splitCo2.stream().map(s -> String.format("%s", s.charAt(finalI))).collect(Collectors.joining());
            int ones = StringUtils.countMatches(firstVals, "1");
            int zeroes = StringUtils.countMatches(firstVals, "0");

            char toKeep = ones < zeroes ? '0' : '1';

            splitCo2 = splitCo2.stream().filter(s -> s.charAt(finalI) != toKeep).collect(Collectors.toList());

            i++;
        }

        var oxyInt = new BigInteger(splitOxy.get(0), 2);
        var co2Int = new BigInteger(splitCo2.get(0), 2);

        return String.format("%s", oxyInt.multiply(co2Int));
    }
}
