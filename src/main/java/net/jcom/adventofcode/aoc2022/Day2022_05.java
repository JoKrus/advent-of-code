package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Day2022_05 extends Day {
    @Override
    public String part1Logic() {
        var inputs = input.split("\n\n");
        var startCrateDrawing = new ArrayList<>(Arrays.stream(inputs[0].split("\n")).toList());
        Collections.reverse(startCrateDrawing);
        var instructions = Arrays.stream(inputs[1].split("\n")).toList();

        var stackNumbers = startCrateDrawing.get(0).trim().split(" +");

        var stacks = new ArrayList<Stack<String>>();
        for (int i = 0; i < stackNumbers.length; ++i) {
            stacks.add(new Stack<>());
        }

        startCrateDrawing.remove(0);

        for (var crateLine : startCrateDrawing) {
            crateLine = StringUtils.rightPad(crateLine, stackNumbers.length * 4 - 1);
            var crateString = new StringBuilder();
            for (int i = 1; i < stackNumbers.length * 4 - 1; i += 4) {
                crateString.append(crateLine.charAt(i));
            }
            crateLine = crateString.toString();

            for (int i = 0; i < stacks.size(); i++) {
                var s = crateLine.substring(i, i + 1);
                if (!s.isBlank()) {
                    stacks.get(i).add(s);
                }
            }
        }

        for (var instr : instructions) {
            int amount, from, to;

            amount = Integer.parseInt(instr.split(" from ")[0].split("move ")[1]);
            from = Integer.parseInt(instr.split(" to ")[0].split("from ")[1]) - 1;
            to = Integer.parseInt(instr.split("to ")[1]) - 1;

            for (int i = 0; i < amount; i++) {
                var moveItem = stacks.get(from).pop();
                stacks.get(to).push(moveItem);
            }
        }


        StringBuilder res = new StringBuilder();

        for (int i = 0; i < stacks.size(); i++) {
            res.append(stacks.get(i).peek());
        }

        return res.toString();
    }

    @Override
    public String part2Logic() {
        var inputs = input.split("\n\n");
        var startCrateDrawing = new ArrayList<>(Arrays.stream(inputs[0].split("\n")).toList());
        Collections.reverse(startCrateDrawing);
        var instructions = Arrays.stream(inputs[1].split("\n")).toList();

        var stackNumbers = startCrateDrawing.get(0).trim().split(" +");

        var stacks = new ArrayList<Stack<String>>();
        for (int i = 0; i < stackNumbers.length; ++i) {
            stacks.add(new Stack<>());
        }

        startCrateDrawing.remove(0);

        for (var crateLine : startCrateDrawing) {
            crateLine = StringUtils.rightPad(crateLine, stackNumbers.length * 4 - 1);
            var crateString = new StringBuilder();
            for (int i = 1; i < stackNumbers.length * 4 - 1; i += 4) {
                crateString.append(crateLine.charAt(i));
            }
            crateLine = crateString.toString();

            for (int i = 0; i < stacks.size(); i++) {
                var s = crateLine.substring(i, i + 1);
                if (!s.isBlank()) {
                    stacks.get(i).add(s);
                }
            }
        }

        for (var instr : instructions) {
            int amount, from, to;

            amount = Integer.parseInt(instr.split(" from ")[0].split("move ")[1]);
            from = Integer.parseInt(instr.split(" to ")[0].split("from ")[1]) - 1;
            to = Integer.parseInt(instr.split("to ")[1]) - 1;

            var order = new StringBuilder();
            for (int i = 0; i < amount; i++) {
                var moveItem = stacks.get(from).pop();
                order.append(moveItem);
            }

            var reversedOrder = StringUtils.reverse(order.toString());

            for (int i = 0; i < amount; i++) {
                stacks.get(to).push(reversedOrder.substring(i, i + 1));
            }
        }

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < stacks.size(); i++) {
            res.append(stacks.get(i).peek());
        }

        return res.toString();
    }
}
