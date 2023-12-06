package net.jcom.adventofcode.aoc2023;

import net.jcom.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2023_06 extends Day {
    private static ArrayList<Long> solve(List<Race> races) {
        ArrayList<Long> records = new ArrayList<>();
        for (int i = 0; i < races.size(); i++) {
            var race = races.get(i);
            long winWays = 0;
            for (int j = 0; j < race.time; j++) {
                int speed = j;
                long timeToDrive = race.time - j;
                long dist = timeToDrive * speed;

                if (dist > race.dist) {
                    winWays++;
                }
            }

            records.add(winWays);
        }
        return records;
    }

    @Override
    public String part1Logic() {
        var arr = input.split("\n");
        arr[0] = arr[0].split(":")[1];
        arr[1] = arr[1].split(":")[1];

        var times = Arrays.stream(arr[0].trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
        var dists = Arrays.stream(arr[1].trim().split("\\s+")).mapToLong(Long::parseLong).toArray();

        var races = new ArrayList<Race>();
        for (int i = 0; i < times.length; i++) {
            races.add(new Race(times[i], dists[i]));
        }

        ArrayList<Long> records = solve(races);

        return "" + records.stream().mapToLong(Long::longValue).reduce(1, (x, y) -> x * y);
    }

    @Override
    public String part2Logic() {
        var arr = input.split("\n");
        arr[0] = arr[0].split(":")[1];
        arr[1] = arr[1].split(":")[1];

        var times = Long.parseLong(String.join("", arr[0].trim().split("\\s+")));
        var dists = Long.parseLong(String.join("", arr[1].trim().split("\\s+")));

        var races = List.of(new Race(times, dists));

        ArrayList<Long> records = solve(races);

        return "" + records.stream().mapToLong(Long::longValue).reduce(1, (x, y) -> x * y);
    }

    public record Race(long time, long dist) {
    }
}
