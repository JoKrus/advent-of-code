package net.jcom.adventofcode.aoc2021.misc;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PacketParser {
    private final String input;
    private final ArrayList<Integer> versionNumbers;
    private final ArrayList<Long> valueList;
    private Mode currentMode;
    private int index;
    private final Stack<Pair<Integer, LengthMode>> currentLengthMode;
    private final Stack<Integer> currentLengthModeValue;
    private final HashMap<Integer, Integer> modeIndex;
    private final HashMap<LengthMode, List<Integer>> modeIndexMap;

    private int id = 0;

    public PacketParser(String bitString) {
        this.input = bitString;
        this.versionNumbers = new ArrayList<>();
        currentMode = Mode.VERSION;
        index = 0;
        valueList = new ArrayList<>();
        currentLengthMode = new Stack<>();
        currentLengthModeValue = new Stack<>();
        modeIndex = new HashMap<>();
        modeIndexMap = new HashMap<>();

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
                    if (typeNum == 4) {
                        currentMode = Mode.LITERAL;
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
                    valueList.add(decimal);
                    //Finished packet

                    if (currentLengthMode.peek().getRight() == LengthMode.TOTAL_AMOUNT) {
                        modeIndex.merge(currentLengthMode.peek().getLeft(), 1, Integer::sum);
                    }

                    while (!currentLengthModeValue.empty() && currentLengthModeValue.peek().equals(modeIndex.get(currentLengthMode.peek().getLeft()))) {
                        modeIndex.remove(currentLengthMode.peek().getLeft());
                        unregisterId(currentLengthMode.peek().getLeft(), currentLengthMode.peek().getRight());
                        currentLengthModeValue.pop();
                        currentLengthMode.pop();

                        if (!currentLengthMode.empty() && currentLengthMode.peek().getRight() == LengthMode.TOTAL_AMOUNT) {
                            modeIndex.merge(currentLengthMode.peek().getLeft(), 1, Integer::sum);
                        }
                    }

                    currentMode = Mode.VERSION;
                }
            }
        }
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
}
