package net.jcom.adventofcode.aoc2020.misc;

public class Instruction {
    public Operation operation;
    public int value;

    public Instruction(String instruction, int value) {
        this.operation = Operation.valueOf(instruction.toUpperCase());
        this.value = value;
    }

    public Instruction(Instruction instruction) {
        this.operation = instruction.operation;
        this.value = instruction.value;
    }

    public enum Operation {
        NOP("nop"), JMP("jmp"), ACC("acc");

        public final String indicator;

        Operation(String indicator) {
            this.indicator = indicator;
        }
    }
}
