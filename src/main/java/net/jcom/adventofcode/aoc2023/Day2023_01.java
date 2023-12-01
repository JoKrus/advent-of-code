package net.jcom.adventofcode.aoc2023;

import net.jcom.adventofcode.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day2023_01 extends Day {
    @Override
    public String part1Logic() {
        int val = Arrays.stream(input.split("\n")).mapToInt(value -> {
            char first = 'x', last = 'x';
            for (var ch : value.toCharArray()) {
                if (Character.isDigit(ch)) {
                    if (first == 'x') {
                        first = ch;
                    }
                    last = ch;
                }
            }
            if (first == 'x') {
                return 0;
            }
            return Integer.parseInt(new String(new char[]{first, last}));
        }).sum();

        return "" + val;
    }

    @Override
    public String part2Logic() {
        Map<String, Character> map = new HashMap<>();
        map.put("one", '1');
        map.put("two", '2');
        map.put("three", '3');
        map.put("four", '4');
        map.put("five", '5');
        map.put("six", '6');
        map.put("seven", '7');
        map.put("eight", '8');
        map.put("nine", '9');


        int val = Arrays.stream(input.split("\n")).mapToInt(value -> {
            char first = 'x', last = 'x';
            var chArray = value.toCharArray();
            for (int i = 0; i < chArray.length; i++) {
                char ch = chArray[i];
                StringBuilder text = new StringBuilder();
                if (Character.isDigit(ch)) {
                    if (first == 'x') {
                        first = ch;
                    }
                    last = ch;
                } else if (Character.isLetter(ch)) {
                    text.append(ch);
                    for (int j = i + 1; j < chArray.length && !Character.isDigit(chArray[j]); j++) {
                        char chj = chArray[j];
                        text.append(chj);

                        if (map.containsKey(text.toString())) {
                            if (first == 'x') {
                                first = map.get(text.toString());
                            }
                            last = map.get(text.toString());
                            break;
                        }
                    }
                }
            }
            if (first == 'x') {
                return 0;
            }
            return Integer.parseInt(new String(new char[]{first, last}));
        }).sum();

        return "" + val;
    }
}
