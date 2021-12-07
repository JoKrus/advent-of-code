package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.util.Arrays;

public class Day2021_07 extends Day {
    @Override
    public String part1Logic() {
        var inputInt = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).sorted().toArray();

        int lowestFuel = Integer.MAX_VALUE;
        int lowestFuelAt = -1;
        for (int alignCoordinate = inputInt[0]; alignCoordinate < inputInt[inputInt.length - 1]; ++alignCoordinate) {
            int fuel = 0;
            for (int num :
                    inputInt) {
                fuel += Math.abs(num - alignCoordinate);
            }
            if (fuel < lowestFuel) {
                lowestFuel = fuel;
                lowestFuelAt = alignCoordinate;
            }

        }

        return "%d".formatted(lowestFuel);
    }

    @Override
    public String part2Logic() {
        var inputInt = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).sorted().toArray();

        int lowestFuel = Integer.MAX_VALUE;
        int lowestFuelAt = -1;
        for (int alignCoordinate = inputInt[0]; alignCoordinate < inputInt[inputInt.length - 1]; ++alignCoordinate) {
            int fuel = 0;
            for (int num :
                    inputInt) {
                int dist = Math.abs(num - alignCoordinate);
                fuel += (dist * dist + dist) / 2;
            }
            if (fuel < lowestFuel) {
                lowestFuel = fuel;
                lowestFuelAt = alignCoordinate;
            }

        }

        return "%d".formatted(lowestFuel);
    }
}
