package de.djgames.jonas.adventofcode.aoc2020.misc;

public class Instruction {
    final Operation instruction;
    final int value;

    public Instruction(String instruction, int value) {
        this.instruction = Operation.valueOf(instruction.toUpperCase());
        this.value = value;
    }


    public enum Operation {
        NOP("nop"), JMP("jmp"), ACC("acc");

        public final String indicator;

        Operation(String indicator) {
            this.indicator = indicator;
        }
    }
}
