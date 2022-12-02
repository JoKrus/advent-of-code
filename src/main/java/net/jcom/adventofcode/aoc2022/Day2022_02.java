package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;

public class Day2022_02 extends Day {
    @Override
    public String part1Logic() {
        var i = Arrays.stream(input.split("\n")).map(Match::new).mapToInt(Match::getScore).sum();
        return "" + i;
    }

    @Override
    public String part2Logic() {
        var i = Arrays.stream(input.split("\n")).map(Match2::new).mapToInt(Match2::getScore).sum();
        return "" + i;
    }

    private enum Hand {
        ROCK(1), PAPER(2), SCISSOR(3);
        final int value;

        Hand(int i) {
            value = i;
        }

        int winAgainst(Hand opp) {
            return switch (this) {
                case ROCK -> switch (opp) {
                    case ROCK -> 3;
                    case PAPER -> 0;
                    case SCISSOR -> 6;
                };
                case PAPER -> switch (opp) {
                    case ROCK -> 6;
                    case PAPER -> 3;
                    case SCISSOR -> 0;
                };
                case SCISSOR -> switch (opp) {
                    case ROCK -> 0;
                    case PAPER -> 6;
                    case SCISSOR -> 3;
                };
            };
        }
    }

    private enum Result {
        WIN, DRAW, LOSE
    }

    private static class Match {
        private Hand opponent;
        private Hand me;

        public Match(String s) {
            var a = s.split(" ");
            switch (a[0]) {
                case "A" -> opponent = Hand.ROCK;
                case "B" -> opponent = Hand.PAPER;
                case "C" -> opponent = Hand.SCISSOR;
            }
            switch (a[1]) {
                case "X" -> me = Hand.ROCK;
                case "Y" -> me = Hand.PAPER;
                case "Z" -> me = Hand.SCISSOR;
            }
        }

        public int getScore() {
            return me.winAgainst(opponent) + me.value;
        }
    }

    private static class Match2 {
        private Hand opponent;
        private Result result;

        public Match2(String s) {
            var a = s.split(" ");
            switch (a[0]) {
                case "A" -> opponent = Hand.ROCK;
                case "B" -> opponent = Hand.PAPER;
                case "C" -> opponent = Hand.SCISSOR;
            }
            switch (a[1]) {
                case "X" -> result = Result.LOSE;
                case "Y" -> result = Result.DRAW;
                case "Z" -> result = Result.WIN;
            }
        }

        private Hand getMe() {
            return DataBase.map.get(Pair.of(opponent, result));
        }

        public int getScore() {
            var me = getMe();
            return me.winAgainst(opponent) + me.value;
        }
    }

    private static class DataBase {
        public static final HashMap<Pair<Hand, Result>, Hand> map = new HashMap<>();

        static {
            map.put(Pair.of(Hand.SCISSOR, Result.WIN), Hand.ROCK);
            map.put(Pair.of(Hand.SCISSOR, Result.LOSE), Hand.PAPER);
            map.put(Pair.of(Hand.SCISSOR, Result.DRAW), Hand.SCISSOR);

            map.put(Pair.of(Hand.PAPER, Result.WIN), Hand.SCISSOR);
            map.put(Pair.of(Hand.PAPER, Result.LOSE), Hand.ROCK);
            map.put(Pair.of(Hand.PAPER, Result.DRAW), Hand.PAPER);

            map.put(Pair.of(Hand.ROCK, Result.WIN), Hand.PAPER);
            map.put(Pair.of(Hand.ROCK, Result.LOSE), Hand.SCISSOR);
            map.put(Pair.of(Hand.ROCK, Result.DRAW), Hand.ROCK);
        }
    }
}
