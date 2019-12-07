package de.djgames.jonas.adventofcode.misc;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Opcoder {

    private int[] instructions;
    private int[] original;
    private int pointer;
    private HaltTypes haltType;
    private Queue<Integer> input;
    private Queue<Integer> output;
    private String ident;


    public Opcoder(int[] original, String ident) {
        this.original = original;
        input = new ArrayDeque<>();
        output = new ArrayDeque<>();
        this.ident = ident;
        reset();
    }

    public Opcoder(int[] original) {
        this(original, "Default");
    }

    public void runOpcode() {
        Function<Integer, Integer> firstPMode = opCodeEntry -> (opCodeEntry / 100) % 10;
        Function<Integer, Integer> secondPMode = opCodeEntry -> (opCodeEntry / 1000) % 10;
        Function<Integer, Integer> thirdPMode = opCodeEntry -> (opCodeEntry / 10000) % 10;
        BiFunction<Integer, Integer, Integer> value = (mode, val) -> mode != Mode.INDEX ? val : instructions[val];
        while (true) {
            if (pointer >= instructions.length) {
                haltType = HaltTypes.EXIT_LENGTH;
                break;
            } else if (instructions[pointer] == 99) {
                haltType = HaltTypes.EXIT99;
                break;
            }
            switch (instructions[pointer] % 100) {
                case InstructionTypes.ADD:
                    var num1 = value.apply(firstPMode.apply(instructions[pointer]), instructions[pointer + 1]);
                    var num2 = value.apply(secondPMode.apply(instructions[pointer]), instructions[pointer + 2]);
                    instructions[instructions[pointer + 3]] = num1 + num2;
                    pointer += 4;
                    break;
                case InstructionTypes.MULT:
                    num1 = value.apply(firstPMode.apply(instructions[pointer]), instructions[pointer + 1]);
                    num2 = value.apply(secondPMode.apply(instructions[pointer]), instructions[pointer + 2]);
                    instructions[instructions[pointer + 3]] = num1 * num2;

                    pointer += 4;
                    break;
                case InstructionTypes.INPUT:
                    if (input.isEmpty()) {
                        haltType = HaltTypes.EXIT_WAIT;
                        return;
                    }
                    instructions[instructions[pointer + 1]] = input.poll();

                    pointer += 2;
                    break;
                case InstructionTypes.OUTPUT:
                    output.add(value.apply(firstPMode.apply(instructions[pointer]), instructions[pointer + 1]));
                    pointer += 2;
                    break;
                case InstructionTypes.JUMP_TRUE:
                    num1 = value.apply(firstPMode.apply(instructions[pointer]), instructions[pointer + 1]);
                    num2 = value.apply(secondPMode.apply(instructions[pointer]), instructions[pointer + 2]);

                    pointer = num1 == 0 ? pointer + 3 : num2;
                    break;
                case InstructionTypes.JUMP_FALSE:
                    num1 = value.apply(firstPMode.apply(instructions[pointer]), instructions[pointer + 1]);
                    num2 = value.apply(secondPMode.apply(instructions[pointer]), instructions[pointer + 2]);

                    pointer = num1 != 0 ? pointer + 3 : num2;
                    break;
                case InstructionTypes.LESS_THAN:
                    num1 = value.apply(firstPMode.apply(instructions[pointer]), instructions[pointer + 1]);
                    num2 = value.apply(secondPMode.apply(instructions[pointer]), instructions[pointer + 2]);
                    instructions[instructions[pointer + 3]] = num1 < num2 ? 1 : 0;

                    pointer += 4;
                    break;
                case InstructionTypes.EQUALS:
                    num1 = value.apply(firstPMode.apply(instructions[pointer]), instructions[pointer + 1]);
                    num2 = value.apply(secondPMode.apply(instructions[pointer]), instructions[pointer + 2]);
                    instructions[instructions[pointer + 3]] = num1.equals(num2) ? 1 : 0;

                    pointer += 4;
                    break;
            }
        }
    }

    void reset() {
        instructions = Arrays.copyOf(original, original.length);
        pointer = 0;
        haltType = HaltTypes.NOT_EXITED;
        input.clear();
        output.clear();
    }

    public Queue<Integer> getOutput() {
        return output;
    }

    public void addInput(int... inputs) {
        input.addAll(Arrays.stream(inputs).boxed().collect(Collectors.toList()));
    }

    public void addInput(Queue<Integer> inputs) {
        while (!inputs.isEmpty()) input.add(inputs.poll());
    }

    public HaltTypes getHaltType() {
        return haltType;
    }

    @Override
    public String toString() {
        return "Opcoder{" +
                "ident='" + ident + '\'' +
                '}';
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
    }

    public static class Mode {
        public static final int INDEX = 0;
        public static final int IMMEDIATE = 1;
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
}
