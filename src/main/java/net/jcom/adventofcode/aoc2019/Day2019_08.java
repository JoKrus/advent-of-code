package net.jcom.adventofcode.aoc2019;

import net.jcom.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day2019_08 extends Day {
    @Override
    public String part1Logic() {
        return doWork(true);
    }

    @Override
    public String part2Logic() {
        return doWork(false);
    }

    public String doWork(boolean part1) {
        List<int[][]> layersList = new ArrayList<>();
        int width = 25;
        int height = 6;
        ArrayList<String> layers = new ArrayList<>();
        String inputCpy = input;

        while (!inputCpy.isEmpty()) {
            layers.add(inputCpy.substring(0, width * height));
            inputCpy = inputCpy.substring(width * height);
        }

        for (String layerString : layers) {
            int[][] layerArr = new int[height][width];
            for (int i = 0; i < layerArr.length; i++) {
                for (int j = 0; j < layerArr[i].length; j++) {
                    int valIndex = i * layerArr[i].length + j;
                    layerArr[i][j] = Integer.parseInt(layerString.substring(valIndex, valIndex + 1));
                }
            }
            layersList.add(layerArr);
        }

        if (part1) {
            int[][] minLayer = layersList.stream().min(Comparator.comparingInt(o -> findAmountOfInArray(o, 0))).orElse(new int[0][0]);
            return String.format("%d", findAmountOfInArray(minLayer, 1) * findAmountOfInArray(minLayer, 2));
        }

        int[][] seenLayer = new int[height][width];
        for (int[] ints : seenLayer) {
            Arrays.fill(ints, 2);
        }

        for (int[][] array : layersList) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (seenLayer[i][j] == 2) {
                        seenLayer[i][j] = array[i][j];
                    }
                }
            }
        }

        StringBuilder ret = new StringBuilder();

        for (int[] ints : seenLayer) {
            ret.append(System.lineSeparator());
            ret.append(rowToString(ints));
        }

        return ret.toString();
    }

    public static int findAmountOfInArray(int[][] array, int toLookFor) {
        int ret = 0;
        for (int[] ints : array) {
            for (int anInt : ints) {
                if (anInt == toLookFor) ret++;
            }
        }
        return ret;
    }

    public static String rowToString(int[] row) {
        StringBuilder ret = new StringBuilder();
        for (int i : row) {
            if (i == 0) {
                ret.append(" ");
            } else {
                ret.append(i);
            }
        }
        return ret.toString();
    }
}
