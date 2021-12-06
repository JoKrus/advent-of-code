package net.jcom.adventofcode.aoc2021;

import com.google.common.collect.HashBiMap;
import net.jcom.adventofcode.Day;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2021_04 extends Day {
    @Override
    public String part1Logic() {
        String[] split = input.split("\n");
        String drawOrder = split[0];

        ArrayList<Board> boards = new ArrayList<>();
        for (int i = 2; i < split.length; i += 6) {
            String boardString = " " + split[i] +
                    " " + split[i + 1] +
                    " " + split[i + 2] +
                    " " + split[i + 3] +
                    " " + split[i + 4];

            boards.add(new Board(boardString));
        }

        var drawInt = Arrays.stream(drawOrder.split(",")).mapToInt(Integer::parseInt).toArray();

        Board winner = null;
        int lastNumber = -1;
        for (int i = 0; winner == null && i < drawInt.length; i++) {
            lastNumber = drawInt[i];
            for (var board : boards) {
                board.addNumber(lastNumber);
                if (board.checkWin()) {
                    winner = board;
                    break;
                }
            }
        }

        var result = winner.getScore() * lastNumber;

        return String.format("%d", result);
    }

    @Override
    public String part2Logic() {
        String[] split = input.split("\n");
        String drawOrder = split[0];

        ArrayList<Board> boards = new ArrayList<>();
        for (int i = 2; i < split.length; i += 6) {
            String boardString = " " + split[i] +
                    " " + split[i + 1] +
                    " " + split[i + 2] +
                    " " + split[i + 3] +
                    " " + split[i + 4];

            boards.add(new Board(boardString));
        }

        var drawInt = Arrays.stream(drawOrder.split(",")).mapToInt(Integer::parseInt).toArray();

        int lastNumber = -1;
        for (int i = 0; i < drawInt.length; i++) {
            lastNumber = drawInt[i];
            for (var board : boards) {
                board.addNumber(lastNumber);
            }
            if (boards.size() == 1 && boards.get(0).checkWin()) {
                break;
            }
            boards.removeIf(Board::checkWin);
        }

        Board loser = null;
        loser = boards.get(0);

        var result = loser.getScore() * lastNumber;

        return String.format("%d", result);
    }

    // 19 17 62 78 27 61 13 30 75 25 14 66 72 37 79 49 91 97  0 23 12 52 41 92 18

    public static class Board {
        private HashBiMap<Integer, Point> locationMap;
        private HashSet<Integer> isCheckedMap;

        private static final int WIDTH_NUM = 3;
        private static final int WIDTH = 5;
        private static final int LENGTH = 5;

        public Board(String string) {
            locationMap = HashBiMap.create(25);
            isCheckedMap = new HashSet<>();

            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < LENGTH; y++) {
                    String pointValue = string.substring(WIDTH_NUM * x + WIDTH_NUM * WIDTH * y,
                            WIDTH_NUM * x + WIDTH_NUM * WIDTH * y + WIDTH_NUM).trim();
                    int val = Integer.parseInt(pointValue);
                    Point point = new Point(x, y);

                    locationMap.put(val, point);
                }
            }
        }

        public boolean checkWin() {
            return checkRows() || checkColumns();
        }

        private boolean checkColumns() {
            for (int i = 0; i < LENGTH; i++) {
                int finalI = i;
                var valuesOfRow = locationMap.inverse().entrySet().stream()
                        .filter(pointIntegerEntry -> pointIntegerEntry.getKey().y == finalI)
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toSet());

                if (isCheckedMap.containsAll(valuesOfRow)) {
                    return true;
                }
            }
            return false;
        }

        private boolean checkRows() {
            for (int i = 0; i < WIDTH; i++) {
                int finalI = i;
                var valuesOfRow = locationMap.inverse().entrySet().stream()
                        .filter(pointIntegerEntry -> pointIntegerEntry.getKey().x == finalI)
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toSet());

                if (isCheckedMap.containsAll(valuesOfRow)) {
                    return true;
                }
            }
            return false;
        }

        public void addNumber(int number) {
            isCheckedMap.add(number);
        }

        public int getScore() {
            var allValues = locationMap.keySet();
            allValues.removeAll(isCheckedMap);
            return allValues.stream().mapToInt(Integer::intValue).sum();
        }
    }
}
