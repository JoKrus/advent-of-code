package de.djgames.jonas.adventofcode.aoc2020.misc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InstructionEngine {
    private int pointer;
    private List<Instruction> instructions;
    private int accumulator;

    public InstructionEngine(List<String> input) {
        this(parseInput(input), false);
    }

    public InstructionEngine(List<Instruction> input, boolean changeSignature) {
        instructions = input;
        pointer = 0;
        accumulator = 0;
    }

    public HaltMode run(HaltMode mode) {
        Set<Integer> alreadyExecuted = new HashSet<>();
        while (pointer >= 0 && pointer < instructions.size()) {
            boolean newlyAdded = alreadyExecuted.add(pointer);
            if (mode == HaltMode.INFINITE_LOOP || mode == HaltMode.ANY) {
                if (!newlyAdded) {
                    return HaltMode.INFINITE_LOOP;
                }
            }
            handleCurrentInstruction();
        }
        return HaltMode.END_OF_LINES;
    }


    private void handleCurrentInstruction() {
        var currentInstr = instructions.get(pointer);
        switch (currentInstr.operation) {
            case NOP:
                break;
            case JMP:
                pointer += currentInstr.value - 1;
                break;
            case ACC:
                accumulator += currentInstr.value;
                break;
        }
        pointer += 1;
    }

    public int getAccumulator() {
        return accumulator;
    }

    public static List<Instruction> parseInput(List<String> input) {
        return input.stream().map(s -> {
            String[] s1 = s.split(" ");
            return new Instruction(s1[0], Integer.parseInt(s1[1]));
        }).collect(Collectors.toList());
    }

    public enum HaltMode {
        INFINITE_LOOP, END_OF_LINES, ANY;
    }
}
