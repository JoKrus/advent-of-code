package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;
import net.jcom.adventofcode.aoc2021.misc.PacketParser;

import java.math.BigInteger;

public class Day2021_16 extends Day {
    @Override
    public String part1Logic() {
        String bits = hexToBinary(this.input);
        PacketParser packetParser = new PacketParser(bits);
        packetParser.run();
        return "%d".formatted(packetParser.getVersionNumberSum());
    }

    public static String hexToBinary(String hex) {
        StringBuilder val = new StringBuilder(new BigInteger(hex, 16).toString(2));

        int diff = hex.length() * 4 - val.length();

        for (int i = 0; i < diff; i++) {
            val.insert(0, "0");
        }

        return val.toString();
    }

    @Override
    public String part2Logic() {
        return null;
    }
}
