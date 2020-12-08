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
        instructions = parseInput(input);
        pointer = 0;
        accumulator = 0;
    }

    public void run(HaltMode mode) {
        if (mode == HaltMode.INFINITE_LOOP) {
            Set<Integer> alreadyExecuted = new HashSet<>();
            while (!alreadyExecuted.contains(pointer)) {
                boolean newlyAdded = alreadyExecuted.add(pointer);
                if (!newlyAdded) break;

                handleCurrentInstruction();
            }
        }
    }

    private void handleCurrentInstruction() {
        var currentInstr = instructions.get(pointer);
        switch (currentInstr.instruction) {
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

    private List<Instruction> parseInput(List<String> input) {
        return input.stream().map(s -> {
            String[] s1 = s.split(" ");
            return new Instruction(s1[0], Integer.parseInt(s1[1]));
        }).collect(Collectors.toList());
    }

    public enum HaltMode {
        INFINITE_LOOP;
    }
}
