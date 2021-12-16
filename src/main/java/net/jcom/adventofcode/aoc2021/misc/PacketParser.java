package net.jcom.adventofcode.aoc2021.misc;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.LongStream;

public class PacketParser {
    private final String input;
    private final ArrayList<Integer> versionNumbers;
    private Mode currentMode;
    private final HashMap<Integer, Operation> opertationMap;
    private int index;
    private final Stack<Pair<Integer, LengthMode>> currentLengthMode;
    private final Stack<Integer> currentLengthModeValue;
    private final HashMap<Integer, Integer> modeIndex;
    private final HashMap<LengthMode, List<Integer>> modeIndexMap;
    private final HashMap<Integer, List<Long>> childrenValues;
    private Operation currentOperation;

    private int id = 0;

    public PacketParser(String bitString) {
        this.input = bitString;
        this.versionNumbers = new ArrayList<>();
        currentMode = Mode.VERSION;
        index = 0;
        currentLengthMode = new Stack<>();
        currentLengthModeValue = new Stack<>();
        modeIndex = new HashMap<>();
        modeIndexMap = new HashMap<>();
        opertationMap = new HashMap<>();
        childrenValues = new HashMap<>();

        currentOperation = Operation.SUM;

        var myId = getId();
        this.currentLengthMode.push(Pair.of(myId, LengthMode.TOTAL_AMOUNT));
        registerId(myId, LengthMode.TOTAL_AMOUNT);
        currentLengthModeValue.push(1);
        modeIndex.put(myId, 0);
    }

    public void run() {
        while (!currentLengthMode.empty()) {
            switch (currentMode) {
                case VERSION -> {
                    String thisVersion = input.substring(index, index + 3);
                    versionNumbers.add(Integer.parseInt(thisVersion, 2));
                    currentMode = Mode.TYPE;
                    index += 3;
                    increaseAll(LengthMode.TOTAL_LENGTH, 3);
                }
                case TYPE -> {
                    String thisType = input.substring(index, index + 3);
                    int typeNum = Integer.parseInt(thisType, 2);

                    Operation op = Operation.findByValue(typeNum);
                    currentOperation = op;

                    if (typeNum == 4) {
                        currentMode = Mode.LITERAL;
                        int myId = getId();
                        opertationMap.put(myId, currentOperation);
                    } else {
                        //TODO set current operator
                        currentMode = Mode.LENGTH_TYPE;
                    }
                    index += 3;
                    increaseAll(LengthMode.TOTAL_LENGTH, 3);
                }
                case LENGTH_TYPE -> {
                    String thisType = input.substring(index, index + 1);
                    int myId = getId();
                    opertationMap.put(myId, currentOperation);
                    if (thisType.equals("0")) {
                        this.currentLengthMode.push(Pair.of(myId, LengthMode.TOTAL_LENGTH));
                        registerId(myId, LengthMode.TOTAL_LENGTH);
                        String length = this.input.substring(index + 1, index + 16);
                        int intLength = Integer.parseInt(length, 2);
                        this.currentLengthModeValue.push(intLength);
                        index += 16;
                        increaseAll(LengthMode.TOTAL_LENGTH, 16, myId);
                    } else {
                        this.currentLengthMode.push(Pair.of(myId, LengthMode.TOTAL_AMOUNT));
                        registerId(myId, LengthMode.TOTAL_AMOUNT);
                        String amount = this.input.substring(index + 1, index + 12);
                        int intAmount = Integer.parseInt(amount, 2);
                        this.currentLengthModeValue.push(intAmount);
                        index += 12;
                        increaseAll(LengthMode.TOTAL_LENGTH, 12, myId);
                    }
                    currentMode = Mode.VERSION;
                }
                case LITERAL -> {
                    String prefix = "";
                    StringBuilder value = new StringBuilder();
                    do {
                        prefix = input.substring(index, index + 1);
                        String val = input.substring(index + 1, index + 5);
                        value.append(val);
                        index += 5;
                        increaseAll(LengthMode.TOTAL_LENGTH, 5);
                    } while (prefix.equals("1"));
                    long decimal = Long.parseLong(value.toString(), 2);
                    addValue(currentLengthMode.peek().getLeft(), decimal);
                    //Finished packet
                    if (currentLengthMode.peek().getRight() == LengthMode.TOTAL_AMOUNT) {
                        modeIndex.merge(currentLengthMode.peek().getLeft(), 1, Integer::sum);
                    }

                    while (!currentLengthModeValue.empty() && currentLengthModeValue.peek().equals(modeIndex.get(currentLengthMode.peek().getLeft()))) {
                        Operation opOfPacket = opertationMap.get(currentLengthMode.peek().getLeft());
                        List<Long> values = childrenValues.get(currentLengthMode.peek().getLeft());
                        long result = performOperation(opOfPacket, values);

                        modeIndex.remove(currentLengthMode.peek().getLeft());
                        unregisterId(currentLengthMode.peek().getLeft(), currentLengthMode.peek().getRight());
                        currentLengthModeValue.pop();
                        currentLengthMode.pop();

                        if (!currentLengthMode.empty()) {
                            addValue(currentLengthMode.peek().getLeft(), result);
                        }

                        if (!currentLengthMode.empty() && currentLengthMode.peek().getRight() == LengthMode.TOTAL_AMOUNT) {
                            modeIndex.merge(currentLengthMode.peek().getLeft(), 1, Integer::sum);
                        }
                    }

                    currentMode = Mode.VERSION;
                }
            }
        }
    }

    private long performOperation(Operation opOfPacket, List<Long> values) {
        if (opOfPacket == null) {
            return 0;
        }

        LongStream longStream = longStream(values);
        return switch (opOfPacket) {
            case SUM -> longStream.sum();
            case PRODUCT -> longStream.reduce(1, Math::multiplyExact);
            case MINIMUM -> longStream.min().getAsLong();
            case MAXIMUM -> longStream.max().getAsLong();
            case LITERAL -> longStream.findFirst().getAsLong();
            case GREATER_THAN -> {
                boolean retBool = values.get(0) > values.get(1);
                yield retBool ? 1 : 0;
            }
            case LESS_THAN -> {
                boolean retBool = values.get(0) < values.get(1);
                yield retBool ? 1 : 0;
            }
            case EQUAL_TO -> {
                boolean retBool = values.get(0).equals(values.get(1));
                yield retBool ? 1 : 0;
            }
        };
    }

    private LongStream longStream(List<Long> values) {
        return values.stream().mapToLong(Long::longValue);
    }

    private void addValue(int id, long value) {
        var newList = childrenValues.getOrDefault(id, new ArrayList<>());
        newList.add(value);
        childrenValues.put(id, newList);
    }

    private void registerId(int id, LengthMode mode) {
        var newList = modeIndexMap.getOrDefault(mode, new ArrayList<>());
        newList.add(id);
        modeIndexMap.put(mode, newList);
    }

    private void unregisterId(int id, LengthMode mode) {
        var newList = modeIndexMap.getOrDefault(mode, new ArrayList<>());
        newList.remove((Integer) id);
        modeIndexMap.put(mode, newList);
    }

    private void increaseAll(LengthMode mode, int amount, int exceptThisId) {
        for (var id : modeIndexMap.getOrDefault(mode, new ArrayList<>())) {
            if (id == exceptThisId) {
                continue;
            }
            modeIndex.merge(id, amount, Integer::sum);
        }
    }

    private void increaseAll(LengthMode mode, int amount) {
        increaseAll(mode, amount, -1);
    }

    private int getId() {
        return id++;
    }

    public int getVersionNumberSum() {
        return this.versionNumbers.stream().mapToInt(Integer::intValue).sum();
    }

    public long getResult() {
        return this.childrenValues.get(0).get(0);
    }

    public enum Mode {
        VERSION, TYPE, LENGTH_TYPE, LITERAL, HALT
    }

    public enum LengthMode {
        TOTAL_LENGTH(0), TOTAL_AMOUNT(1);

        private final int val;

        LengthMode(int i) {
            this.val = i;
        }
    }

    public enum Operation {
        SUM(0), PRODUCT(1), MINIMUM(2), MAXIMUM(3), LITERAL(4), GREATER_THAN(5), LESS_THAN(6), EQUAL_TO(7);

        public final int val;

        Operation(int i) {
            this.val = i;
        }

        public static Operation findByValue(final int value) {
            return Arrays.stream(values()).filter(value1 -> value1.val == value).findFirst().orElse(null);
        }
    }
}
