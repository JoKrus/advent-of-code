package net.jcom.adventofcode.aoc2023;

import net.jcom.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day2023_02 extends Day {
    @Override
    public String part1Logic() {

        int maxRed = 12, maxGreen = 13, maxBlue = 14;

        var gamesMapped = getGames();

        return "" + gamesMapped.stream().filter(game -> game.isPossible(maxRed, maxGreen, maxBlue)).mapToInt(Game::id).sum();
    }

    private List<Game> getGames() {
        List<String> games = Arrays.stream(input.split("\n")).toList();
        var gamesMapped = games.stream().map(gameString -> {
            String[] arr = gameString.split(":");
            int id = Integer.parseInt(arr[0].split(" ")[1]);

            var draws = Arrays.stream(arr[1].split(";")).toList();
            var drawsMapped = draws.stream().map(drawString -> {
                var balls = drawString.split(",");
                int red = 0, green = 0, blue = 0;

                for (var ball : balls) {
                    var num = ball.trim().split(" ");
                    var amount = Integer.parseInt(num[0]);
                    switch (num[1]) {
                        case "red" -> red += amount;
                        case "green" -> green += amount;
                        case "blue" -> blue += amount;
                    }
                }

                return new Draw(red, green, blue);
            }).toList();

            return new Game(id, drawsMapped);
        }).toList();
        return gamesMapped;
    }

    @Override
    public String part2Logic() {
        var gamesMapped = getGames();

        return "" + gamesMapped.stream().mapToInt(Game::cubePower).sum();

    }

    public record Game(int id, List<Draw> draws) {
        boolean isPossible(int mR, int mG, int mB) {
            return draws.stream().allMatch(draw -> draw.isPossible(mR, mG, mB));
        }

        int cubePower() {
            int minR = 0, minG = 0, minB = 0;
            for (var draw : draws) {
                if (minR < draw.red) {
                    minR = draw.red;
                }

                if (minG < draw.green) {
                    minG = draw.green;
                }

                if (minB < draw.blue) {
                    minB = draw.blue;
                }
            }

            return minR * minG * minB;
        }
    }

    public record Draw(int red, int green, int blue) {
        boolean isPossible(int mR, int mG, int mB) {
            return red <= mR && green <= mG && blue <= mB;
        }
    }
}
