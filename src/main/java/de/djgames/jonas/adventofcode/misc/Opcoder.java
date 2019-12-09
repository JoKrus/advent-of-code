package de.djgames.jonas.adventofcode.misc;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Opcoder {

    private ArrayList<Long> instructions;
    private long[] original;
    private int pointer;
    private int relativeBase;
    private HaltTypes haltType;
    private Queue<Long> input;
    private Queue<Long> output;
    private String ident;


    public Opcoder(long[] original, String ident) {
        this.original = original;
        input = new ArrayDeque<>();
        output = new ArrayDeque<>();
        this.ident = ident;
        reset();
    }

    public Opcoder(long[] original) {
        this(original, "Default");
    }


    public void runOpcode() {
        Function<Long, Mode> firstPMode = opCodeEntry -> Mode.of((opCodeEntry / 100) % 10);
        Function<Long, Mode> secondPMode = opCodeEntry -> Mode.of((opCodeEntry / 1000) % 10);
        Function<Long, Mode> thirdPMode = opCodeEntry -> Mode.of((opCodeEntry / 10000) % 10);
        BiFunction<Mode, Long, Long> value = (mode, val) -> {
            long ret = 0;
            switch (mode) {
                case INDEX:
                    ret = instructions.get(val.intValue());
                    break;
                case IMMEDIATE:
                    ret = val;
                    break;
                case RELATIVE:
                    ret = instructions.get(relativeBase + val.intValue());
                    break;
            }

            return ret;
        };
        while (true) {
            if (pointer >= instructions.size()) {
                haltType = HaltTypes.EXIT_LENGTH;
                break;
            } else if (instructions.get(pointer) == 99) {
                haltType = HaltTypes.EXIT99;
                break;
            }
            var num1 = value.apply(firstPMode.apply(instructions.get(pointer)), instructions.get(pointer + 1));
            var num2 = value.apply(secondPMode.apply(instructions.get(pointer)), instructions.get(pointer + 2));
            switch ((int) (instructions.get(pointer) % 100)) {
                case InstructionTypes.ADD:
                    int index = instructions.get(pointer + 3).intValue();
                    if (index > instructions.size()) {
                        instructions.ensureCapacity(index + 1);
                    }
                    instructions.set(index, num1 + num2);
                    pointer += 4;
                    break;
                case InstructionTypes.MULT:
                    index = instructions.get(pointer + 3).intValue();
                    if (index > instructions.size()) {
                        instructions.ensureCapacity(index + 1);
                    }
                    instructions.set(instructions.get(pointer + 3).intValue(), num1 * num2);
                    pointer += 4;
                    break;
                case InstructionTypes.INPUT:
                    if (input.isEmpty()) {
                        haltType = HaltTypes.EXIT_WAIT;
                        return;
                    }
                    index = instructions.get(pointer + 1).intValue();
                    if (index > instructions.size()) {
                        instructions.ensureCapacity(index + 1);
                    }
                    instructions.set(index, input.poll());
                    pointer += 2;
                    break;
                case InstructionTypes.OUTPUT:
                    output.add(value.apply(firstPMode.apply(instructions.get(pointer)), instructions.get(pointer + 1)));
                    pointer += 2;
                    break;
                case InstructionTypes.JUMP_TRUE:
                    pointer = num1 == 0 ? pointer + 3 : num2.intValue();
                    break;
                case InstructionTypes.JUMP_FALSE:
                    pointer = num1 != 0 ? pointer + 3 : num2.intValue();
                    break;
                case InstructionTypes.LESS_THAN:
                    index = instructions.get(pointer + 3).intValue();
                    if (index > instructions.size()) {
                        instructions.ensureCapacity(index + 1);
                    }
                    instructions.set(index, (long) (num1 < num2 ? 1 : 0));
                    pointer += 4;
                    break;
                case InstructionTypes.EQUALS:
                    index = instructions.get(pointer + 3).intValue();
                    if (index > instructions.size()) {
                        instructions.ensureCapacity(index + 1);
                    }
                    instructions.set(index, (long) (num1.equals(num2) ? 1 : 0));
                    pointer += 4;
                    break;
                case InstructionTypes.ADJUST_BASE:
                    relativeBase = num1.intValue();
                    break;
            }
        }
    }

    void reset() {
        instructions = Arrays.stream(Arrays.copyOf(original, original.length)).
                boxed().collect(Collectors.toCollection(ArrayList::new));
        pointer = 0;
        relativeBase = 0;
        haltType = HaltTypes.NOT_EXITED;
        input.clear();
        output.clear();
    }

    public Queue<Long> getOutput() {
        return output;
    }

    public void addInput(long... inputs) {
        input.addAll(Arrays.stream(inputs).boxed().collect(Collectors.toList()));
    }

    public void addInput(Queue<Long> inputs) {
        while (!inputs.isEmpty()) input.add(inputs.poll());
    }

    public HaltTypes getHaltType() {
        return haltType;
    }

    @Override
    public String toString() {
        return this.ident;
    }

    public static class InstructionTypes {
        public static final int ADD = 1;
        public static final int MULT = 2;
        public static final int INPUT = 3;
        public static final int OUTPUT = 4;
        public static final int JUMP_TRUE = 5;
        public static final int JUMP_FALSE = 6;
        public static final int LESS_THAN = 7;
        public static final int EQUALS = 8;
        public static final int ADJUST_BASE = 9;
    }

    public enum Mode {
        INDEX(0),
        IMMEDIATE(1),
        RELATIVE(2);

        private int val;

        private Mode(int value) {
            val = value;
        }

        public static Mode of(long value) {
            int valueI = Math.toIntExact(value);
            switch (valueI) {
                case 0:
                    return INDEX;
                case 1:
                    return IMMEDIATE;
                case 2:
                    return RELATIVE;
            }
            return null;
        }

        public int getValue() {
            return val;
        }
    }

    public enum HaltTypes {
        EXIT99(-99),
        EXIT_LENGTH(-98),
        EXIT_WAIT(-97),
        NOT_EXITED(-100);

        private int val;

        HaltTypes(int value) {
            val = value;
        }

        public int getValue() {
            return val;
        }
    }

    /**
     * Old Opcode Parser, only compatible up to Day 7-1
     *
     * @param instrs            opcodeArray
     * @param progOutputAddress address where output is located, if < 0, output is last result of an opcode 4
     * @param progInput         input array for opcode 3
     * @return result of machine
     */
    public static int runOpcodeLegacy(int[] instrs, int progOutputAddress, int... progInput) {
        Function<Integer, Integer> firstPMode = opCodeEntry -> (opCodeEntry / 100) % 10;
        Function<Integer, Integer> secondPMode = opCodeEntry -> (opCodeEntry / 1000) % 10;
        BiFunction<Integer, Integer, Integer> value = (mode, val) -> mode != 0 ? val : instrs[val];
        int ret = 0;
        int i = 0;
        int inpI = 0;
        while (i < instrs.length && instrs[i] != 99) {
            switch (instrs[i] % 100) {
                case 1:
                    var num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    var num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    instrs[instrs[i + 3]] = num1 + num2;
                    i += 4;
                    break;
                case 2:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    instrs[instrs[i + 3]] = num1 * num2;
                    i += 4;
                    break;
                case 3:
                    instrs[instrs[i + 1]] = progInput[inpI++];
                    i += 2;
                    break;
                case 4:
                    ret = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    i += 2;
                    break;
                case 5:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    i = num1 == 0 ? i + 3 : num2;
                    break;
                case 6:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    i = num1 != 0 ? i + 3 : num2;
                    break;
                case 7:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    instrs[instrs[i + 3]] = num1 < num2 ? 1 : 0;
                    i += 4;
                    break;
                case 8:
                    num1 = value.apply(firstPMode.apply(instrs[i]), instrs[i + 1]);
                    num2 = value.apply(secondPMode.apply(instrs[i]), instrs[i + 2]);
                    instrs[instrs[i + 3]] = num1.equals(num2) ? 1 : 0;
                    i += 4;
                    break;
            }
        }
        return progOutputAddress < 0 ? ret : instrs[progOutputAddress];
    }
}
