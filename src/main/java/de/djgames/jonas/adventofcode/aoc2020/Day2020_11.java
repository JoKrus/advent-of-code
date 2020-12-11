package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;

import java.util.Arrays;

public class Day2020_11 extends Day {
    @Override
    public String part1Logic() {
        String[] lines = input.split("\n");
        int rows = lines.length;
        int columns = lines[0].length();
        String currentLobby = input.replaceAll("\n", "");
        //access with x + y * lenx;
        boolean change;
        do {
            change = false;
            StringBuilder nextLobby = new StringBuilder(currentLobby);
            for (int y = 0; y < rows; ++y) {
                for (int x = 0; x < columns; ++x) {
                    var currentChair = currentLobby.charAt(x + y * columns);
                    if (currentChair == '.') {
                        continue;
                    }
                    int neighbours = checkNeigbours(y, x, columns, rows, currentLobby);
                    if (currentChair == 'L' && neighbours == 0) {
                        change = true;
                        nextLobby.setCharAt(x + y * columns, '#');
                    } else if (currentChair == '#' && neighbours >= 4) {
                        change = true;
                        nextLobby.setCharAt(x + y * columns, 'L');
                    }
                }
            }
            currentLobby = nextLobby.toString();
        } while (change);

        return "" + Arrays.stream(currentLobby.split("")).filter(s -> s.equals("#")).count();
    }

    private int checkNeigbours(int y, int x, int lenx, int leny, String currentLobby) {
        int neighbours = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                if (x + dx < 0 || x + dx >= lenx) {
                    continue;
                }
                if (y + dy < 0 || y + dy >= leny) {
                    continue;
                }
                var neighbour = currentLobby.charAt(x + dx + (y + dy) * lenx);
                if (neighbour == '#') {
                    neighbours++;
                }
            }
        }
        return neighbours;
    }

    private int checkNeigbours2(int y, int x, int lenx, int leny, String currentLobby) {
        int neighbours = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                char neighbour = 'Ö';
                int dxx = dx;
                int dyy = dy;
                do {
                    if (x + dxx < 0 || x + dxx >= lenx) {
                        break;
                    }
                    if (y + dyy < 0 || y + dyy >= leny) {
                        break;
                    }
                    neighbour = currentLobby.charAt(x + dxx + (y + dyy) * lenx);
                    if (neighbour == '#') {
                        neighbours++;
                    }
                    dxx += dx;
                    dyy += dy;
                } while (neighbour == '.');

            }
        }
        return neighbours;
    }

    @Override
    public String part2Logic() {
        String[] lines = input.split("\n");
        int rows = lines.length;
        int columns = lines[0].length();
        String currentLobby = input.replaceAll("\n", "");
        //access with x + y * lenx;
        boolean change;
        do {
            change = false;
            StringBuilder nextLobby = new StringBuilder(currentLobby);
            for (int y = 0; y < rows; ++y) {
                for (int x = 0; x < columns; ++x) {
                    var currentChair = currentLobby.charAt(x + y * columns);
                    if (currentChair == '.') {
                        continue;
                    }
                    int neighbours = checkNeigbours2(y, x, columns, rows, currentLobby);
                    if (currentChair == 'L' && neighbours == 0) {
                        change = true;
                        nextLobby.setCharAt(x + y * columns, '#');
                    } else if (currentChair == '#' && neighbours >= 5) {
                        change = true;
                        nextLobby.setCharAt(x + y * columns, 'L');
                    }
                }
            }
            currentLobby = nextLobby.toString();
        } while (change);

        return "" + Arrays.stream(currentLobby.split("")).filter(s -> s.equals("#")).count();
    }

}
