package net.jcom.adventofcode.aoc2020;

import net.jcom.adventofcode.Day;
import net.jcom.adventofcode.aoc2020.misc.Instruction;
import net.jcom.adventofcode.aoc2020.misc.InstructionEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2020_08 extends Day {
    @Override
    public String part1Logic() {
        var engine = new InstructionEngine(Arrays.stream(input.split("\n")).collect(Collectors.toList()));
        engine.run(InstructionEngine.HaltMode.INFINITE_LOOP);
        return String.format("%d", engine.getAccumulator());
    }

    @Override
    public String part2Logic() {
        var original = Arrays.stream(input.split("\n")).collect(Collectors.toList());
        List<List<Instruction>> allTheLists = generateAllSwaps(original);
        for (var list : allTheLists) {
            var engine = new InstructionEngine(list, false);
            var haltedOn = engine.run(InstructionEngine.HaltMode.ANY);
            if (haltedOn == InstructionEngine.HaltMode.END_OF_LINES) {
                return String.format("%d", engine.getAccumulator());
            }
        }
        return null;
    }

    private List<List<Instruction>> generateAllSwaps(List<String> original) {
        List<List<Instruction>> ret = new ArrayList<>();
        List<Instruction> insOriginal = InstructionEngine.parseInput(original);
        for (int i = 0; i < insOriginal.size(); ++i) {
            var copy = new ArrayList<Instruction>();
            for (var elem : insOriginal) {
                copy.add(new Instruction(elem));
            }
            var iElem = copy.get(i);
            if (iElem.operation == Instruction.Operation.NOP) {
                iElem.operation = Instruction.Operation.JMP;
            }
            if (iElem.operation == Instruction.Operation.JMP) {
                iElem.operation = Instruction.Operation.NOP;
            }
            ret.add(copy);
        }
        return ret;
    }
}
